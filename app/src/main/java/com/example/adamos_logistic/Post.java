package com.example.adamos_logistic;

import com.google.gson.annotations.SerializedName;

public class Post {


    public String NAME;

    public String SURNAME;

    public String SECONDNAME;

    public String EMAIL;

    public String PASSWORD;

    //@SerializedName("body")


    public Post(String NAME, String SURNAME, String SECONDNAME, String EMAIL, String PASSWORD) {
        this.NAME = NAME;
        this.SURNAME = SURNAME;
        this.SECONDNAME = SECONDNAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public String getNAME() {
        return NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public String getSECONDNAME() {
        return SECONDNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}