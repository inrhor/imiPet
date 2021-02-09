package cn.inrhor.imipet.utlis

import org.bukkit.Bukkit

object MsgUtil {
    fun send(msg : String) {
        Bukkit.getConsoleSender().sendMessage(msg.replace("&", "§"))
    }

    fun send(msg : List<String>) {
        for (s in msg) {
            send(s)
        }
    }
}