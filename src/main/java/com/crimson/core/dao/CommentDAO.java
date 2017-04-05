package com.crimson.core.dao;


import com.crimson.core.model.Comment;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.time.LocalDate;
import java.util.List;

public interface CommentDAO extends BaseDAO<Comment, Long> {

    List<Comment> getCommentsByDate(LocalDate date);

    void addTvShow2Comment(Comment comment, TvShow tvShow);

    void deleteTvShowFromComment(Comment comment);

    void addUser2Comment(Comment comment, User user);

    void deleteUserFromComment(Comment comment);

    List<Comment> getCommentByIdUser(Long idUser);

    List<Comment> getCommentByIdTvShow(Long idTvShow);

    List<Comment> getComments(long idTvShow, long idUser);
}
