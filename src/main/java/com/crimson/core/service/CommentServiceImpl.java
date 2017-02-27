package com.crimson.core.service;


import com.crimson.core.dao.CommentDAO;
import com.crimson.core.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public void save(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDAO.delete(comment);
    }

    @Override
    public void update(Comment comment) {
        commentDAO.update(comment);
    }

    @Override
    public Comment getCommentById(Long idComment) {
        return commentDAO.getCommentById(idComment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentDAO.getAllComments();
    }

    @Override
    public List<Comment> getCommentByDate(LocalDate date) {
        return commentDAO.getCommentsByDate(date);
    }

    @Override
    public List<Comment> getCommentByIdUser(Long idUser) {
        return commentDAO.getCommentByIdUser(idUser);
    }

    @Override
    public List<Comment> getCommentByIdTvShow(Long idTvShow) {
        return commentDAO.getCommentByIdTvShow(idTvShow);
    }


    @Override
    public List getComments(long idTvShow, long idUser) {
        return commentDAO.getComments(idTvShow, idUser);
    }
}
