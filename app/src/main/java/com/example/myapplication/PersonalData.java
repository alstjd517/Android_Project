package com.example.myapplication;



public class PersonalData {
    private String member_id;
    private String member_name;
    private String member_address;
    private String member_date;

    public String getMember_Detail() {
        return member_Detail;
    }

    public void setMember_Detail(String member_Detail) {
        this.member_Detail = member_Detail;
    }

    private String member_Detail;

    public String getMember_date() {
        return member_date;
    }

    public void setMember_date(String member_date) {
        this.member_date = member_date;
    }



    public String getMember_id() {
        return member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_address() {
        return member_address;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }
}