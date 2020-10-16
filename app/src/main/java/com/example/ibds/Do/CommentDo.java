package com.example.ibds.Do;

import java.io.Serializable;

public class CommentDo implements Serializable {
    String name ="";
    String comment="";

    public CommentDo(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
