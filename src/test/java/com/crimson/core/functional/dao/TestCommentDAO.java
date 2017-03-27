package com.crimson.core.functional.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.CommentDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.factory.CommentFactory;
import com.crimson.core.factory.TvShowFactory;
import com.crimson.core.factory.UserFactory;
import com.crimson.core.model.Comment;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback
public class TestCommentDAO {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private UserDAO userDAO;

    private CommentFactory commentFactory = new CommentFactory();
    private UserFactory userFactory = new UserFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();

    private Comment comment1 = commentFactory.getComment("comment1");
    private Comment comment2 = commentFactory.getComment("comment2");
    private User user = userFactory.getUser("aleks");
    private TvShow tvShow = tvShowFactory.getTvShow("friends");

    @Before
    public void setDB(){
        commentDAO.save(comment1);
        commentDAO.save(comment2);
        userDAO.save(user);
        tvShowDAO.save(tvShow);
    }

    @Test
    public void saveTest(){
        Comment comment = Comment.builder()
                .text("TEST")
                .build();
        int listSize = commentDAO.getAll().size();

        commentDAO.save(comment);

        Assert.assertEquals(listSize+1, commentDAO.getAll().size());
        Assert.assertEquals(commentDAO.getAll().contains(comment), true);
    }

    @Test
    public void updateTest(){
        Comment comment = commentDAO.getById(commentDAO.getAll().get(0).getId());
        comment.setText("CHANGED");

        commentDAO.update(comment);

        Assert.assertEquals(commentDAO.getAll().get(0).getText(), comment.getText());
    }

    @Test
    public void deleteTest(){
        int listSize = commentDAO.getAll().size();

        commentDAO.delete(comment1);

        Assert.assertEquals(listSize-1, commentDAO.getAll().size());
        Assert.assertEquals(commentDAO.getAll().contains(comment1), false);
    }

    @Test
    public void getCommentByIdTest(){
        Comment comment = commentDAO.getById(comment1.getId());

        Assert.assertEquals(comment.equals(comment1), true);
    }

    @Test
    public void getAllCommentsTest(){
        Assert.assertEquals(commentDAO.getAll().size(), 2);
    }

    @Test
    public void getCommentsByDateTest(){
        List<Comment> comments = commentDAO.getCommentsByDate(LocalDate.now());

        Assert.assertEquals(comments.size(), 2);
        Assert.assertEquals(comments.contains(comment1), true);
        Assert.assertEquals(comments.contains(comment2), true);
    }

    @Test
    public void getCommentByIdUserTest(){
        comment1.setUser(user);
        comment2.setUser(user);

        List<Comment> commentsByUser = commentDAO.getCommentByIdUser(user.getId());

        Assert.assertEquals(commentsByUser.contains(comment1), true);
        Assert.assertEquals(commentsByUser.contains(comment2), true);
    }

    @Test
    public void getCommentByIdTvShowTest(){
        comment1.setTvShow(tvShow);
        comment2.setTvShow(tvShow);

        List<Comment> commentsByTvShow = commentDAO.getCommentByIdTvShow(tvShow.getId());

        Assert.assertEquals(commentsByTvShow.contains(comment1), true);
        Assert.assertEquals(commentsByTvShow.contains(comment2), true);
    }

    @Test
    public void addTvShow2CommentTest() {
        commentDAO.addTvShow2Comment(comment1,tvShow);

        Assert.assertEquals(commentDAO.getById(comment1.getId()).getTvShow().equals(tvShow), true);
    }

    @Test
    public void deleteTvShowFromCommentTest() {
        commentDAO.deleteTvShowFromComment(comment1);

        Assert.assertEquals(commentDAO.getById(comment1.getId()).getTvShow(), null);
    }

    @Test
    public void addUser2CommentTest() {
        commentDAO.addUser2Comment(comment1,user);

        Assert.assertEquals(commentDAO.getById(comment1.getId()).getUser().equals(user), true);
    }

    @Test
    public void deleteUserFromCommentTest() {
        commentDAO.deleteUserFromComment(comment1);

        Assert.assertEquals(commentDAO.getById(comment1.getId()).getUser(), null);
    }

    @Test
    public void getCommentTest(){
        comment1.setUser(user);
        comment1.setTvShow(tvShow);

        comment2.setUser(user);
        comment2.setTvShow(tvShow);

        List<Comment> comments = commentDAO.getComments(tvShow.getId(), user.getId());

        Assert.assertEquals(comments.contains(comment1), true);
        Assert.assertEquals(comments.contains(comment2), true);
    }
}
