package com.example.adamos_logistic;

public class Post {


    private String SURNAME;

    private String NAME;

    private String SECONDNAME;

    private String EMAIL;

    private String PASSWORD;

    private boolean IS_REG;

    private String SUCCESS;

    private String USER_ID;

    private String ERROR;

    private String ORDER_ID;


    public Post(String SURNAME, String NAME, String SECONDNAME, String EMAIL, String PASSWORD, boolean IS_REG) {
        this.SURNAME = SURNAME;
        this.NAME = NAME;
        this.SECONDNAME = SECONDNAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.IS_REG = IS_REG;
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

    public String getSUCCESS() {
        return SUCCESS;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getERROR() {
        return ERROR;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }
}
