package com.example.ibds.Do;

import java.io.Serializable;

public class LeadDo implements Serializable {
    String Name = "";
    String Eamil = "";
    String Company = "";
    String Phone = "";
    String Title = "";
    String Comment = "";
    String createdTime= "";
    public LeadDo(String name, String eamil, String company, String phone, String title, String comment, String createdTime) {
        this.Name = name;
        this.Eamil = eamil;
        this.Company = company;
        this.Phone = phone;
        this.Title = title;
        this.Comment = comment;
        this.createdTime = createdTime;
    }

    public String getName() {
        return Name;
    }

    public String getEamil() {
        return Eamil;
    }

    public String getCompany() {
        return Company;
    }

    public String getPhone() {
        return Phone;
    }

    public String getTitle() {
        return Title;
    }

    public String getComment() {
        return Comment;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}
