package com.crimson.core.service;


import com.crimson.core.dao.CommentDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.CommentDTO;
import com.crimson.core.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public void save(CommentDTO commentDTO) {
        Comment comment1 = new Comment(commentDTO.getText(),commentDTO.getDate());
        comment1.setTvShow(tvShowDAO.getById(commentDTO.getIdTvShow()));
        comment1.setUser(userDAO.getById(commentDTO.getUser().getId()));
        commentDAO.save(comment1);
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
        return commentDAO.getById(idComment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentDAO.getAll();
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
