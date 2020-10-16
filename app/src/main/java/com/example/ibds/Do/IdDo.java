package com.example.ibds.Do;

import java.io.Serializable;

public class IdDo implements Serializable {
    String name="",password="",status="";

    public IdDo(String name, String password, String status) {
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
