package com.csl.cupcake_factory.Model;

public class Categories {

    private String cname, cid;

    public Categories() {

    }

    public Categories(String cname, String description, String cid, String date, String time) {
        this.cname = cname;
        this.cid = cid;

    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


}
