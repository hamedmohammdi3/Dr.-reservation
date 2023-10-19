package com.hamed.reserv.model;

import javax.persistence.*;

/**
 * @author H.Mohammadi
 * @created 2022/06/16
 */
@Entity
public class TimeSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String personId;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c11;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c12;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c13;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c14;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c15;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c16;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c17;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c18;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c19;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c21;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c22;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c23;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c24;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c25;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c26;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c27;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c28;
    @Column(columnDefinition = "varchar(25) default 'false'")
    private String c29;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }


    public String getC11() {
        return c11;
    }

    public void setC11(String c11) {
        this.c11 = c11;
    }

    public String getC12() {
        return c12;
    }

    public void setC12(String c12) {
        this.c12 = c12;
    }

    public String getC13() {
        return c13;
    }

    public void setC13(String c13) {
        this.c13 = c13;
    }

    public String getC14() {
        return c14;
    }

    public void setC14(String c14) {
        this.c14 = c14;
    }

    public String getC15() {
        return c15;
    }

    public void setC15(String c15) {
        this.c15 = c15;
    }

    public String getC16() {
        return c16;
    }

    public void setC16(String c16) {
        this.c16 = c16;
    }

    public String getC17() {
        return c17;
    }

    public void setC17(String c17) {
        this.c17 = c17;
    }

    public String getC18() {
        return c18;
    }

    public void setC18(String c18) {
        this.c18 = c18;
    }

    public String getC19() {
        return c19;
    }

    public void setC19(String c19) {
        this.c19 = c19;
    }

    public String getC21() {
        return c21;
    }

    public void setC21(String c21) {
        this.c21 = c21;
    }

    public String getC22() {
        return c22;
    }

    public void setC22(String c22) {
        this.c22 = c22;
    }

    public String getC23() {
        return c23;
    }

    public void setC23(String c23) {
        this.c23 = c23;
    }

    public String getC24() {
        return c24;
    }

    public void setC24(String c24) {
        this.c24 = c24;
    }

    public String getC25() {
        return c25;
    }

    public void setC25(String c25) {
        this.c25 = c25;
    }

    public String getC26() {
        return c26;
    }

    public void setC26(String c26) {
        this.c26 = c26;
    }

    public String getC27() {
        return c27;
    }

    public void setC27(String c27) {
        this.c27 = c27;
    }

    public String getC28() {
        return c28;
    }

    public void setC28(String c28) {
        this.c28 = c28;
    }

    public String getC29() {
        return c29;
    }

    public void setC29(String c29) {
        this.c29 = c29;
    }
}
