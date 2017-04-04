package com.crimson.core.factory;


import com.crimson.core.model.Comment;

public class CommentFactory {
    public Comment getComment(String name) {


        Comment comment = null;


        switch (name.toLowerCase()) {
            case "comment1": {
                comment = Comment.builder()
                        .text("Ten serial jest beznadziejny")
                        .build();
                break;
            }

            case "comment2": {
                comment = Comment.builder()
                        .text("Ten serial jest beznadziejny")
                        .build();
                break;
            }
        }
        return comment;
    }
}
