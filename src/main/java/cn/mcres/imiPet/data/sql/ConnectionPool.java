package cn.mcres.imiPet.data.sql;

import cn.inrhor.imipet.ImiPet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;

/**
 * Create at 2019/11/17 10:37
 * Copyright Karlatemp
 * imiPet $ cn.mcres.imiPet.data.sql
 */
public class ConnectionPool {
    private final String url;
    private final String usr;
    private final String passwd;
    private final ConcurrentLinkedDeque<Connection> connections = new ConcurrentLinkedDeque<>();
    private final int min;
    private boolean closed;

    public interface Task<T> {
        T run(Connection connection) throws Throwable;
    }

    public ConnectionPool(int min, String url, String usr, String passwd) {
        this.url = url;
        this.usr = usr;
        this.passwd = passwd;
        this.min = min;
    }

    private Connection getConnection() throws SQLException {
        while (!connections.isEmpty()) {
            Connection peek = connections.poll();
            if (peek != null && !peek.isClosed()) {
                return peek;
            }
        }
        return start();
    }

    private Connection start() throws SQLException {
        if (closed) throw new SQLException("Connection pool closed.");
        return DriverManager.getConnection(url, usr, passwd);

    }

    public void open() throws SQLException {
        connections.add(start());
    }

    public void doTick() throws SQLException {
        if (closed) return;
        connections.removeIf(connection -> {
            try {
                return connection == null || connection.isClosed();
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (Throwable err) {
                    ImiPet.loader.getPlugin().getLogger().log(Level.SEVERE, "Error in closing connection.", err);
                }
                return true;
            }
        });
        while (connections.size() < min) {
            open();
        }
    }

    public int getAlivaConnections() {
        return connections.size();
    }

    public void close() {
        closed = true;
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                ImiPet.loader.getPlugin().getLogger().log(Level.SEVERE, "Error in closing connection pool", e);
            }
        }
        connections.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

    public <T> T post(Task<T> task) {
        if (task == null) return null;
        Connection connection;
        try {
            connection = getConnection();
        } catch (SQLException error) {
            throw new RuntimeException("Error in getting connection.", error);
        }
        Throwable throwable = null;
        try {
            return task.run(connection);
        } catch (Throwable error) {
            throwable = error;
        } finally {
            try {
                if (!connection.isClosed()) {
                    connections.add(connection);
                }
            } catch (Throwable e) {
                if (throwable != null) {
                    throwable.addSuppressed(e);
                } else {
                    throwable = e;
                }
            }
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        if (throwable instanceof Error) {
            throw (Error) throwable;
        }
        throw new RuntimeException(throwable);
    }
}
