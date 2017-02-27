package com.crimson.core.factory;


import com.crimson.core.model.Comment;

import java.time.LocalDate;

public class CommentFactory {
    public Comment getComment(String name) {


        Comment comment = null;


        switch (name.toLowerCase()) {
            case "comment1": {
                comment = Comment.builder()
                        .text("Ten serial jest beznadziejny")
                        .date(LocalDate.parse("2017-02-27"))
                        .build();
                break;
            }

            case "comment2": {
                comment = Comment.builder()
                        .text("Ten serial jest beznadziejny")
                        .date(LocalDate.parse("2017-02-27"))
                        .build();
                break;
            }
        }
        return comment;
    }
}
