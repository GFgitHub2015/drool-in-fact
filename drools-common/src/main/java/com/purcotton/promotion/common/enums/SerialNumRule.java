package com.purcotton.promotion.common.enums;

public enum SerialNumRule {

    ACTIVITY_CODE("活动编号", "P", "CREATE", "yyMMdd", "3位顺序号", 3),
    COMMOGROUP_CODE("商品组编号", "GN", "COMMO_CREATE", "yyMMdd", "3位顺序号", 3),
    PRESALE_CODE("预售活动编号", "YDH", "YDH", "yyMMdd", "3位顺序号", 3),
    SPELL_GROUP_CODE("拼团活动编号","TDH","TDH","yyMMdd","3位顺序号",3),
    SPIKE_CODE("秒杀活动编号", "S", "SPK", "yyMMdd", "3位顺序号", 3),
    GIFT_CODE("满足活动编号", "GF", "GIFT", "yyMMdd", "3位顺序号", 3),
    EXTCARD_CODE("外卡编号", "WK", "EXTCARD", "yyMMdd", "3位顺序号", 3),
    INTERNAL_CODE("内购会", "I", "INTERNAL", "yyMMdd", "3位顺序号", 3),
    EXCHANGE_CODE("积分商城活动编号", "EX", "EXCHANGE", "yyMMdd", "3位顺序号", 3),
    ;

    private String billType;
    //column1
    private String prefix;

    private String cacheKey;

    //column2
    private String dateFormat;
    private String column3;
    private Integer serialLength;

    SerialNumRule(String billType, String prefix, String cacheKey, String dateFormat, String column3, Integer serialLength) {
        this.billType = billType;
        this.prefix = prefix;
        this.cacheKey = cacheKey;
        this.dateFormat = dateFormat;
        this.column3 = column3;
        this.serialLength = serialLength;
    }

    public String getBillType() {
        return billType;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getColumn3() {
        return column3;
    }

    public Integer getSerialLength() {
        return serialLength;
    }
}
