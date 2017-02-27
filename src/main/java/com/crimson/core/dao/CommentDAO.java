package com.crimson.core.dao;


import com.crimson.core.model.Comment;

import java.time.LocalDate;
import java.util.List;

public interface CommentDAO extends BaseDAO<Comment, Long> {

    List<Comment> getCommentsByDate(LocalDate date);

    @SuppressWarnings("unchecked")
    List<Comment> getCommentByIdUser(Long idUser);

    @SuppressWarnings("unchecked")
    List<Comment> getCommentByIdTvShow(Long idTvShow);

    List getComments(long idTvShow, long idUser);
}
