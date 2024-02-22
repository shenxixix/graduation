package com.yolo.hw.user.util;

/**
 * @description  基础信息初始化类
 * @author
 * @date
 * @version
 */
public class FaceFactory {
    // 请求域名
    private static String host;
    // 项目Id(应用Id）
    private static String project_id;
    // 项目密钥(应用密钥）
    private static String project_scert;

    private static boolean debug=false;

    private FaceFactory(){};
    public static void init(String host,String project_id,String project_scert) {
        FaceFactory.setHost(host);
        FaceFactory.setProject_id(project_id);
        FaceFactory.setProject_scert(project_scert);
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        FaceFactory.host = host;
    }

    public static String getProject_id() {
        return project_id;
    }

    public static void setProject_id(String project_id) {
        FaceFactory.project_id = project_id;
    }

    public static String getProject_scert() {
        return project_scert;
    }

    public static void setProject_scert(String project_scert) {
        FaceFactory.project_scert = project_scert;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        FaceFactory.debug = debug;
    }
}
