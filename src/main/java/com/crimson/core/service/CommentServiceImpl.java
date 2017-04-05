package com.crimson.core.service;


import com.crimson.core.dao.CommentDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.CommentDTO;
import com.crimson.core.model.Comment;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
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
        Comment comment1 = new Comment(commentDTO.getText());
        addTvShow2Comment(comment1, tvShowDAO.getById(commentDTO.getIdTvShow()));
        addUser2Comment(comment1, userDAO.getById(commentDTO.getUser().getId()));
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
    public void addTvShow2Comment(Comment comment, TvShow tvShow) {
        if (comment.getTvShow() != tvShow) {
            commentDAO.addTvShow2Comment(comment, tvShow);
        }
        if (!tvShowDAO.getComments(tvShow).contains(comment)) {
            tvShowDAO.addComment(tvShow, comment);
        }
    }

    @Override
    public void deleteTvShowFromComment(Comment comment, TvShow tvShow) {
        if (comment.getTvShow() == tvShow) {
            commentDAO.deleteTvShowFromComment(comment);
        }
        if (tvShowDAO.getComments(tvShow).contains(comment)) {
            tvShowDAO.deleteComment(tvShow, comment);
        }
    }

    @Override
    public void addUser2Comment(Comment comment, User user) {
        if (comment.getUser() != user) {
            commentDAO.addUser2Comment(comment, user);
        }
        if (!userDAO.getComments(user).contains(comment)) {
            userDAO.addComment(user, comment);
        }
    }

    @Override
    public void deleteUserFromComment(Comment comment, User user) {
        if (comment.getUser() == user) {
            commentDAO.deleteUserFromComment(comment);
        }
        if (userDAO.getComments(user).contains(comment)) {
            userDAO.deleteComment(user, comment);
        }
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
    public List<Comment> getComments(long idTvShow, long idUser) {
        return commentDAO.getComments(idTvShow, idUser);
    }
}
