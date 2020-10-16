package com.example.ibds.Do;

public class SignUpDo {
    String name;
    String email;
    String password;

    public SignUpDo() {
    }

    public SignUpDo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
