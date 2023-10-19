package com.hamed.reserv.model;

import javax.persistence.*;

/**
 * @author H.Mohammadi
 * @created 2022/06/20
 */
@Entity
public class ReservTimeClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String doctorId;
    private Long timeSetId;
    private String dateSet;
    private String time;
    @Transient
    private Person person;

    public Long getTimeSetId() {
        return timeSetId;
    }

    public void setTimeSetId(Long timeSetId) {
        this.timeSetId = timeSetId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDateSet() {
        return dateSet;
    }

    public void setDateSet(String dateSet) {
        this.dateSet = dateSet;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

