package com.algokelvin.performancedatascroll;

public class KamenRider {
    private String id;
    private int image;
    private String code;
    private String name;
    private String form_update;
    private String form_super;
    private String form_final;
    private String year;
    private String mc;
    private String actor;

    public KamenRider(String id, int image, String code, String name, String form_update, String form_super, String form_final, String year, String mc, String actor) {
        this.id = id;
        this.image = image;
        this.code = code;
        this.name = name;
        this.form_update = form_update;
        this.form_super = form_super;
        this.form_final = form_final;
        this.year = year;
        this.mc = mc;
        this.actor = actor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForm_update() {
        return form_update;
    }

    public void setForm_update(String form_update) {
        this.form_update = form_update;
    }

    public String getForm_super() {
        return form_super;
    }

    public void setForm_super(String form_super) {
        this.form_super = form_super;
    }

    public String getForm_final() {
        return form_final;
    }

    public void setForm_final(String form_final) {
        this.form_final = form_final;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
