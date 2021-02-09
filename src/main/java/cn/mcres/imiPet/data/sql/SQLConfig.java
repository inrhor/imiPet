package cn.mcres.imiPet.data.sql;

import java.util.concurrent.TimeUnit;

/**
 * Create at 2019/11/17 11:01
 * Copyright Karlatemp
 * imiPet $ cn.mcres.imiPet.data.sql
 */
public class SQLConfig {
    public String table_name, url, usr, pwd;
    public int min;
    public long cache_duration = 2L;
    public TimeUnit cache_time_unit = TimeUnit.SECONDS;
}
