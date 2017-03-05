package com.crimson.core.service;


import com.crimson.core.dto.CommentDTO;
import com.crimson.core.model.Comment;

import java.time.LocalDate;
import java.util.List;

public interface CommentService {

    void save(CommentDTO commentDTO);

    void delete(Comment comment);

    void update(Comment comment);

    Comment getCommentById(Long idComment);

    List<Comment> getAllComments();

    List<Comment> getCommentByDate(LocalDate date);

    @SuppressWarnings("unchecked")
    List<Comment> getCommentByIdUser(Long idUser);

    @SuppressWarnings("unchecked")
    List<Comment> getCommentByIdTvShow(Long idTvShow);

    List getComments(long idTvShow, long idUser);
}
