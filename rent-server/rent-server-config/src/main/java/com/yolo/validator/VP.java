package com.yolo.validator;

public enum VP {
    /**
     * 电话号码
     */
    MOBILE("^1[0-9][0-9]\\d{8}$"),
    /**
     * 英文
     */
    ENGLISH("^[A-Za-z]+$"),
    /**
     * 中文
     */
    CHINESE("^[\u0391-\uFFE5]+$"),
    /**
     * 数字
     */
    NUMBER("^\\d+$"),
    /**
     * 整型
     */
    INTEGER("^[-\\+]?\\d+$"),
    /**
     * 小数
     */
    DOUBLE("^[-\\+]?\\d+(\\.\\d+)?$"),
    /**
     * 邮箱
     */
    EMAIL("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}"),

    /**
     * 身份证
     */
    IDENTITY_CARD("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$"),

    /**
     * 统一信用代码
     */
    CREDIT_NO("^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$"),

    /**
     * 中文姓名
     */
    CHINESE_NAME("^[\u0391-\uFFE5]{2,10}"),
    ;

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    VP(String pattern) {
        this.pattern = pattern;
    }


}
