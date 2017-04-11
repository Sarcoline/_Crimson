package com.crimson.core.service;


import com.crimson.core.dto.CommentDTO;
import com.crimson.core.model.Comment;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.time.LocalDate;
import java.util.List;

public interface CommentService {

    void save(CommentDTO commentDTO, String name);

    void delete(Comment comment);

    void update(Comment comment);

    void addTvShow2Comment(Comment comment, TvShow tvShow);

    void deleteTvShowFromComment(Comment comment, TvShow tvShow);

    void addUser2Comment(Comment comment, User user);

    void deleteUserFromComment(Comment comment, User user);

    Comment getCommentById(Long idComment);

    List<Comment> getAllComments();

    List<Comment> getCommentByDate(LocalDate date);

    List<Comment> getCommentByIdUser(Long idUser);

    List<Comment> getCommentByIdTvShow(Long idTvShow);

    List<Comment> getComments(long idTvShow, long idUser);
}
