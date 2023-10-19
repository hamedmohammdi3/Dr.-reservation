package com.hamed.reserv.util;

/**
 * @author H.Mohammadi
 * @created 2022/06/20
 */
public enum TimeEnum {
    c11("ساعت 8-9 وقت اول"),
    c12 ("ساعت 9-10 وقت اول"),
    c13("ساعت 10-11 وقت اول"),
    c14("ساعت 11-12 وقت اول"),
    c15("ساعت 13-14 وقت اول"),
    c16("ساعت 14-15 وقت اول"),
    c17("ساعت 16-17 وقت اول"),
    c18("ساعت 18-19 وقت اول"),
    c19("ساعت 20-21 وقت اول"),
    c21("ساعت 8-9 وقت دوم"),
    c22("ساعت 9-10 وقت دوم"),
    c23("ساعت 10-11 وقت دوم"),
    c24("ساعت 11-12 وقت دوم"),
    c25("ساعت 13-14 وقت دوم"),
    c26("ساعت 14-15 وقت دوم"),
    c27("ساعت 16-17 وقت دوم"),
    c28("ساعت 18-19 وقت دوم"),
    c29("ساعت 20-21 وقت دوم");

    private String value;

    TimeEnum(String value) {

        this.value = value;
    }
    public String value() {
        return value;
    }

}