package com.miruna.hospitalmanager.application.pacient;

public class Pacient2 {
    private String id;
    private String name;
    private String surname;
    private String age;
    private String cnp;
    private String dateIn;
    private String dateEx;

    public Pacient2(String id, String name, String surname, String age, String cnp, String dateIn, String dateEx) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.cnp = cnp;
        this.dateIn = dateIn;
        this.dateEx = dateEx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateEx() {
        return dateEx;
    }

    public void setDateEx(String dateEx) {
        this.dateEx = dateEx;
    }
}
