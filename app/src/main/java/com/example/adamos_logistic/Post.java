package com.example.adamos_logistic;

public class Post {


    private String SURNAME;

    private String NAME;

    private String SECONDNAME;

    private String EMAIL;

    private String PASSWORD;


    public Post(String SURNAME, String NAME, String SECONDNAME, String EMAIL, String PASSWORD) {
        this.SURNAME = SURNAME;
        this.NAME = NAME;
        this.SECONDNAME = SECONDNAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public String getNAME() {
        return NAME;
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
