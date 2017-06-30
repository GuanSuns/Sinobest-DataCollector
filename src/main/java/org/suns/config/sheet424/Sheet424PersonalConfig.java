package org.suns.config.sheet424;

/**
 * Created by guanl on 6/30/2017.
 */
public class Sheet424PersonalConfig {
    private static String[] inspectedHosts = {"192.168.14.82"};

    /*
    private static String[] inspectedHosts = {"150.12.201.40"};
    //Alternative host "150.12.201.42"
    */

    private static int port = 1521;
    private static String user = "dev_01";
    private static String password = "a";
    private static String sid = "ggjs";

    private static String[] fieldNamesTime = {"lastAnalysis"};

    private static String inspectTimeSQL = "select max(LAST_ANALYZED) "
            + fieldNamesTime[0] + " from dba_tables WHERE temporary='N'";

    private static String[] fieldNamesStat = {"temp Table Statistics"};

    private static String tempStatSQL = "select '有 '||count(*)||'张表' \""
            + fieldNamesStat[0] +"\"\n" +
            "  from dba_tables\n" +
            " where temporary='Y' AND  last_analyzed is not null\n";

    public static String[] getInspectedHosts() {
        return inspectedHosts;
    }

    public static void setInspectedHosts(String[] inspectedHosts) {
        Sheet424PersonalConfig.inspectedHosts = inspectedHosts;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Sheet424PersonalConfig.port = port;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Sheet424PersonalConfig.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Sheet424PersonalConfig.password = password;
    }

    public static String getSid() {
        return sid;
    }

    public static void setSid(String sid) {
        Sheet424PersonalConfig.sid = sid;
    }

    public static String[] getFieldNamesTime() {
        return fieldNamesTime;
    }

    public static void setFieldNamesTime(String[] fieldNamesTime) {
        Sheet424PersonalConfig.fieldNamesTime = fieldNamesTime;
    }

    public static String getInspectTimeSQL() {
        return inspectTimeSQL;
    }

    public static void setInspectTimeSQL(String inspectTimeSQL) {
        Sheet424PersonalConfig.inspectTimeSQL = inspectTimeSQL;
    }

    public static String[] getFieldNamesStat() {
        return fieldNamesStat;
    }

    public static void setFieldNamesStat(String[] fieldNamesStat) {
        Sheet424PersonalConfig.fieldNamesStat = fieldNamesStat;
    }

    public static String getTempStatSQL() {
        return tempStatSQL;
    }

    public static void setTempStatSQL(String tempStatSQL) {
        Sheet424PersonalConfig.tempStatSQL = tempStatSQL;
    }
}