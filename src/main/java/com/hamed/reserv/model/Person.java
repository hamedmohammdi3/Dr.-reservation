package com.hamed.reserv.model;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * @author H.Mohammadi
 * @created 2022/06/10
 */
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name_family;
    private String telephone;
    private String address;
    @Column(unique = true, length = 100)
    private String email;
    private String pCode;
    private String specialty;
    private String password;
    @Column(nullable = true, length = 64)
    private String photos;
    @Column(nullable = true, length = 1000)
    private String aboutMe;
    private Boolean isDoctor;

    public Boolean getDoctor() {
        return isDoctor;
    }

    public void setDoctor(Boolean doctor) {
        isDoctor = doctor;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_family() {
        return name_family;
    }

    public void setName_family(String name_family) {
        this.name_family = name_family;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPCode() {
        return pCode;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name_family='" + name_family + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", pCode='" + pCode + '\'' +
                ", specialty='" + specialty + '\'' +
                ", password='" + password + '\'' +
                ", photos='" + photos + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }
}
