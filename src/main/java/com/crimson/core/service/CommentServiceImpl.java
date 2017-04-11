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
    public void save(CommentDTO commentDTO, String name) {
        Comment comment1 = new Comment(commentDTO.getText());
        addTvShow2Comment(comment1, tvShowDAO.getById(commentDTO.getIdTvShow()));
        addUser2Comment(comment1, userDAO.getUserByName(name));
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
            comment.setTvShow(tvShow);
            commentDAO.update(comment);
        }
        if (!tvShowDAO.getComments(tvShow).contains(comment)) {
            List<Comment> comments = tvShowDAO.getComments(tvShow);
            comments.add(comment);
            tvShow.setComments(comments);
            tvShowDAO.update(tvShow);
        }
    }

    @Override
    public void deleteTvShowFromComment(Comment comment, TvShow tvShow) {
        if (comment.getTvShow() == tvShow) {
            comment.setTvShow(null);
            commentDAO.update(comment);
        }
        if (tvShowDAO.getComments(tvShow).contains(comment)) {
            List<Comment> comments = tvShowDAO.getComments(tvShow);
            comments.remove(comment);
            tvShow.setComments(comments);
            tvShowDAO.update(tvShow);
        }
    }

    @Override
    public void addUser2Comment(Comment comment, User user) {
        if (comment.getUser() != user) {
            comment.setUser(user);
            commentDAO.update(comment);
        }
        if (!userDAO.getComments(user).contains(comment)) {
            List<Comment> comments = userDAO.getComments(user);
            comments.add(comment);
            user.setComments(comments);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteUserFromComment(Comment comment, User user) {
        if (comment.getUser() == user) {
            comment.setUser(null);
            commentDAO.update(comment);
        }
        if (userDAO.getComments(user).contains(comment)) {
            List<Comment> comments = userDAO.getComments(user);
            comments.remove(comment);
            user.setComments(comments);
            userDAO.update(user);
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
