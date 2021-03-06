package com.crimson.core.unit.service;

import com.crimson.core.dao.*;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.*;
import com.crimson.core.service.UserServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestUserService {

    @Mock
    private UserDAO userDAO;
    @Mock
    private RoleDAO roleDAO;
    @Mock
    private MapperFacade mapperFacade;
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private ApplicationContext context;
    @Mock
    private TvShowDAO tvShowDAO;
    @Mock
    private EpisodeDAO episodeDAO;
    @Mock
    private RatingDAO ratingDAO;
    @Mock
    private SettingsDAO settingsDAO;
    @Mock
    private CommentDAO commentDAO;
    @Mock
    private ReviewDAO reviewDAO;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Before
    public void setUp(){

    }

    @Test
    public void getAllUserTest() {
        User user = User.builder().name("Test").build();
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userDAO.getAll()).thenReturn(users);

        userService.getAllUsers();

        Mockito.verify(userDAO, Mockito.times(1)).getAll();
    }

    @Test
    public void getUserByIdTest() {
        User user = User.builder().name("Test").build();
        when(userDAO.getById(anyLong())).thenReturn(user);

        userService.getUserById(1L);

        Mockito.verify(userDAO, Mockito.times(1)).getById(Matchers.anyLong());
    }

    @Test
    public void deleteUserTest(){
        User user = User.builder().name("Test").build();
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");

        when(userDAO.getById(anyLong())).thenReturn(user);
        doNothing().when(userDAO).delete(anyObject());

        userService.deleteUser(userDTO);

        Mockito.verify(userDAO, Mockito.times(1)).getById(Matchers.anyLong());
        Mockito.verify(userDAO, Mockito.times(1)).delete(anyObject());
    }

    @Test
    public void updateUserTest(){
        User user = User.builder().name("Test").build();
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");
        userDTO.setEmail("abc@wp.pl");

        when(userDAO.getById(anyLong())).thenReturn(user);
        doNothing().when(userDAO).update(anyObject());

        userService.updateUser(userDTO);

        Mockito.verify(userDAO, Mockito.times(1)).getById(Matchers.anyLong());
        Mockito.verify(userDAO, Mockito.times(1)).update(anyObject());
    }

    @Test
    public void changeProfilePicTest() throws IOException {
        User user = User.builder().name("Test").build();
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");
        userDTO.setEmail("abc@wp.pl");
        byte[] bytes = new byte['x'];
        MultipartFile file = new MockMultipartFile("string",bytes);

        when(userDAO.getUserByName(anyString())).thenReturn(user);
        doNothing().when(userDAO).update(anyObject());

        userService.changeProfilePic(userDTO.getName(),file);

        Mockito.verify(userDAO, Mockito.times(1)).getUserByName(Matchers.anyString());
        Mockito.verify(userDAO, Mockito.times(1)).update(anyObject());
    }

    @Test
    public void getUserByName(){
        User user = User.builder().name("Test").build();

        when(userDAO.getUserByName(anyString())).thenReturn(user);

        userService.getUserByName(user.getName());

        Mockito.verify(userDAO, Mockito.times(1)).getUserByName(Matchers.anyString());
    }

    @Test
    public void getUserByToken(){
        User user = User.builder().name("Test").build();

        when(userDAO.getUserByToken(anyString())).thenReturn(user);

        userService.getUserByToken(user.getToken());

        Mockito.verify(userDAO, Mockito.times(1)).getUserByToken(Matchers.anyString());
    }

    @Test
    public void checkFollowTest(){
        when(userDAO.checkFollow(anyString(),anyLong())).thenReturn(true);

        userService.checkFollow("name",1L);

        Mockito.verify(userDAO, Mockito.times(1)).checkFollow(anyString(),anyLong());
}

    @Test
    public void addTvShow2UserTest(){
        User user = User.builder().name("Test").build();
        TvShow tv = TvShow.builder().build();

        when(userDAO.getUserByName(anyString())).thenReturn(user);
        when(tvShowDAO.getById(anyLong())).thenReturn(tv);
        doNothing().when(userDAO).update(anyObject());
        doNothing().when(tvShowDAO).update(anyObject());

        userService.addTvShow2User("name",1L);

        Mockito.verify(userDAO, Mockito.times(1)).getUserByName(Matchers.anyString());
        Mockito.verify(tvShowDAO, Mockito.times(1)).getById(Matchers.anyLong());
        Mockito.verify(userDAO,Mockito.times(1)).update(anyObject());
        Mockito.verify(tvShowDAO,Mockito.times(1)).update(anyObject());
    }

    @Test
    public void deleteTvShowFromUserTest(){
        User user = User.builder().name("Test").build();
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userDAO.getUserByName(anyString())).thenReturn(user);
        when(tvShowDAO.getById(anyLong())).thenReturn(tv);
        when(userDAO.getTvShows(anyObject())).thenReturn(tvs);
        when(tvShowDAO.getUsers(anyObject())).thenReturn(users);
        doNothing().when(userDAO).update(anyObject());
        doNothing().when(tvShowDAO).update(anyObject());

        userService.deleteTvShowFromUser("name",1L);

        Mockito.verify(userDAO, Mockito.times(1)).getUserByName(Matchers.anyString());
        Mockito.verify(tvShowDAO, Mockito.times(1)).getById(Matchers.anyLong());
        Mockito.verify(userDAO,Mockito.times(1)).update(anyObject());
        Mockito.verify(tvShowDAO,Mockito.times(1)).update(anyObject());
    }

    @Test
    public void getUserTvShowsTest(){
        User user = User.builder().name("Test").build();
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");
        TvShow tv = TvShow.builder().build();
        TvShowDTO tvDTO = new TvShowDTO();
        tvDTO.setTitle("Test");

        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);

        when(userDAO.getTvShows(anyObject())).thenReturn(tvs);
        when(userDAO.getUserByName(anyString())).thenReturn(user);

        userService.getUserTvShows(userDTO);

        Mockito.verify(userDAO, Mockito.times(1)).getUserByName(Matchers.anyString());
        Mockito.verify(userDAO, Mockito.times(1)).getTvShows(anyObject());
    }

    @Test
    public void addEpisode2UserTest(){
        User user = User.builder().name("Test").build();
        Episode episode = Episode.builder().build();

        doNothing().when(userDAO).update(anyObject());
        doNothing().when(episodeDAO).update(anyObject());

        userService.addEpisode2User(user,episode);

        Mockito.verify(userDAO).update(anyObject());
        Mockito.verify(episodeDAO).update(anyObject());
    }

    @Test
    public void deleteEpisodeFromUserTest(){
        User user = User.builder().name("Test").build();
        Episode episode = Episode.builder().build();
        List<Episode> episodes = new ArrayList<>();
        episodes.add(episode);
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userDAO.getEpisodes(anyObject())).thenReturn(episodes);
        when(episodeDAO.getUsers(anyObject())).thenReturn(users);
        doNothing().when(userDAO).update(anyObject());
        doNothing().when(episodeDAO).update(anyObject());

        userService.deleteEpisodeFromUser(user,episode);

        Mockito.verify(userDAO,times(2)).getEpisodes(anyObject());
        Mockito.verify(episodeDAO,times(2)).getUsers(anyObject());
        Mockito.verify(userDAO).update(anyObject());
        Mockito.verify(episodeDAO).update(anyObject());
    }

    @Test
    public void addRating2UserTest(){
        User user = User.builder().name("Test").build();
        Rating rating = Rating.builder().build();

        doNothing().when(ratingDAO).update(anyObject());
        doNothing().when(userDAO).update(anyObject());

        userService.addRating2User(user,rating);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(ratingDAO,times(1)).update(anyObject());
    }

    @Test
    public void deleteRatingFromUserTest(){
        User user = User.builder().name("Test").build();
        Rating rating = Rating.builder().build();
        rating.setUser(user);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        doNothing().when(ratingDAO).update(anyObject());
        doNothing().when(userDAO).update(anyObject());
        when(userDAO.getRatings(anyObject())).thenReturn(ratings);

        userService.deleteRatingFromUser(user,rating);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(ratingDAO,times(1)).update(anyObject());
        Mockito.verify(userDAO,times(2)).getRatings(anyObject());
    }

    @Test
    public void addSetting2UserTest(){
        User user = User.builder().name("Test").build();
        Setting setting = Setting.builder().build();

        doNothing().when(userDAO).update(anyObject());
        doNothing().when(settingsDAO).update(anyObject());

        userService.addSetting2User(user,setting);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(settingsDAO,times(1)).update(anyObject());
    }

    @Test
    public void deleteSettingFromUserTest(){
        User user = User.builder().name("Test").build();
        Setting setting = Setting.builder().build();
        user.setSetting(setting);
        setting.setUser(user);

        doNothing().when(userDAO).update(anyObject());
        doNothing().when(settingsDAO).update(anyObject());

        userService.deleteSettingFromUser(user,setting);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(settingsDAO,times(1)).update(anyObject());
    }

    @Test
    public void addRole2UserTest(){
        User user = User.builder().name("Test").build();
        Role role = Role.builder().roleName("USER").build();

        doNothing().when(userDAO).update(anyObject());
        doNothing().when(roleDAO).update(anyObject());

        userService.addRole2User(user,role);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(roleDAO,times(1)).update(anyObject());
    }

    @Test
    public void deleteRoleFromUserTest(){
        User user = User.builder().name("Test").build();
        Role role = Role.builder().roleName("USER").build();
        List<User> users = new ArrayList<>();
        users.add(user);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        doNothing().when(userDAO).update(anyObject());
        doNothing().when(roleDAO).update(anyObject());
        when(userDAO.getRoles(anyObject())).thenReturn(roles);
        when(roleDAO.getUsers(anyObject())).thenReturn(users);

        userService.deleteRoleFromUser(user,role);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(roleDAO,times(1)).update(anyObject());
        Mockito.verify(userDAO,times(2)).getRoles(anyObject());
        Mockito.verify(roleDAO,times(2)).getUsers(anyObject());
    }

    @Test
    public void addCommentTest(){
        User user = User.builder().name("Test").build();
        Comment comment = Comment.builder().build();

        doNothing().when(commentDAO).update(anyObject());
        doNothing().when(userDAO).update(anyObject());

        userService.addComment(user,comment);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(commentDAO,times(1)).update(anyObject());
    }

    @Test
    public void deleteCommentTest(){
        User user = User.builder().name("Test").build();
        Comment comment = Comment.builder().build();
        comment.setUser(user);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        doNothing().when(commentDAO).update(anyObject());
        doNothing().when(userDAO).update(anyObject());
        when(userDAO.getComments(anyObject())).thenReturn(comments);

        userService.deleteComment(user,comment);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(commentDAO,times(1)).update(anyObject());
        Mockito.verify(userDAO,times(2)).getComments(anyObject());
    }

    @Test
    public void addReviewTest() {
        User user = User.builder().name("Test").build();
        Review review = Review.builder().build();

        doNothing().when(reviewDAO).update(anyObject());
        doNothing().when(userDAO).update(anyObject());

        userService.addReview(user,review);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(reviewDAO,times(1)).update(anyObject());
    }

    @Test
    public void deleteReviewTest() {
        User user = User.builder().name("Test").build();
        Review review = Review.builder().build();
        review.setUser(user);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        doNothing().when(reviewDAO).update(anyObject());
        doNothing().when(userDAO).update(anyObject());
        when(userDAO.getReviews(anyObject())).thenReturn(reviews);

        userService.deleteReview(user,review);

        Mockito.verify(userDAO,times(1)).update(anyObject());
        Mockito.verify(reviewDAO,times(1)).update(anyObject());
        Mockito.verify(userDAO,times(2)).getReviews(anyObject());
    }

    @Test
    public void getTvShowsTest(){
        User user = User.builder().name("Test").build();
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);

        when(userDAO.getTvShows(anyObject())).thenReturn(tvs);

        userService.getTvShows(user);

        Mockito.verify(userDAO, Mockito.times(1)).getTvShows(anyObject());
    }

    @Test
    public void getEpisodeTest(){
        User user = User.builder().name("Test").build();
        Episode episode = Episode.builder().build();
        List<Episode> episodes = new ArrayList<>();
        episodes.add(episode);

        when(userDAO.getEpisodes(anyObject())).thenReturn(episodes);

        userService.getEpisodes(user);

        Mockito.verify(userDAO, Mockito.times(1)).getEpisodes(anyObject());
    }

    @Test
    public void getRatingsTest(){
        User user = User.builder().name("Test").build();
        Rating rating = Rating.builder().build();
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        when(userDAO.getRatings(anyObject())).thenReturn(ratings);

        userService.getRatings(user);

        Mockito.verify(userDAO, Mockito.times(1)).getRatings(anyObject());
    }

    @Test
    public void getRolesTest(){
        User user = User.builder().name("Test").build();
        Role role = Role.builder().build();
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        when(userDAO.getRoles(anyObject())).thenReturn(roles);

        userService.getRoles(user);

        Mockito.verify(userDAO, Mockito.times(1)).getRoles(anyObject());
    }

    @Test
    public void getCommentTest(){
        User user = User.builder().name("Test").build();
        Comment comment = Comment.builder().build();
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        when(userDAO.getComments(anyObject())).thenReturn(comments);

        userService.getComments(user);

        Mockito.verify(userDAO, Mockito.times(1)).getComments(anyObject());
    }

    @Test
    public void getRviewsTest(){
        User user = User.builder().name("Test").build();
        Review review = Review.builder().build();
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(userDAO.getReviews(anyObject())).thenReturn(reviews);

        userService.getReviews(user);

        Mockito.verify(userDAO, Mockito.times(1)).getReviews(anyObject());
    }

    @Test
    public void getUserTvShowsSortedByMaxRatingTest(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");
        TvShow tv = TvShow.builder().build();
        TvShowDTO tvDTO = new TvShowDTO();
        tvDTO.setTitle("Test");
        List<Rating> ratings = new ArrayList<>();
        Rating rating = Rating.builder().build();
        rating.setTvShow(tv);
        ratings.add(rating);
        userDTO.setRatings(ratings);

        //userService.getUserTvShowsSortedByMaxRating(userDTO);
    }

    @Test
    public void getAllUnwatchedUserEpisodesDTO(){
        User user = User.builder().name("Test").build();
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");

        when(userDAO.getById(anyLong())).thenReturn(user);
        when(userDAO.getTvShows(anyObject())).thenReturn(new ArrayList<>());
        when(userDAO.getEpisodes(anyObject())).thenReturn(new ArrayList<>());
        when(tvShowDAO.getEpisodes(anyObject())).thenReturn(new ArrayList<>());

        userService.getAllUnwatchedUserEpisodes(userDTO);

        Mockito.verify(userDAO,times(1)).getById(anyLong());
        Mockito.verify(userDAO,times(1)).getTvShows(anyObject());
        Mockito.verify(userDAO,times(1)).getEpisodes(anyObject());
    }

    @Test
    public void updatePasswordTest(){
        User user = User.builder().name("Test").build();
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");

        when(userDAO.getById(anyLong())).thenReturn(user);
        when(encoder.encode(anyString())).thenReturn("password");
        doNothing().when(userDAO).update(anyObject());

        userService.updatePassword(userDTO,"password");

        Mockito.verify(userDAO,times(1)).getById(anyLong());
        Mockito.verify(encoder,times(1)).encode(anyString());
        Mockito.verify(userDAO,times(1)).update(anyObject());
    }

    @Test
    public void checkOldPasswordTest(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");

        when(encoder.encode(anyString())).thenReturn("password");

        userService.checkOldPassword(userDTO,"password");

        Mockito.verify(encoder,times(1)).matches(any(),anyString());
    }

    @Test
    public void changePasswordTest(){
        User user = User.builder().name("Test").build();

        when(encoder.encode(anyString())).thenReturn("password");
        doNothing().when(userDAO).update(anyObject());

        userService.changeUserPassword(user,"passwordtest");

        Mockito.verify(encoder,times(1)).encode(anyString());
        Mockito.verify(userDAO,times(1)).update(anyObject());
    }

    @Test
    public void updateSettingsTest(){
        User user = User.builder().name("Test").build();
        Setting setting = Setting.builder().build();
        user.setSetting(setting);

        when(userDAO.getUserByName(anyString())).thenReturn(user);
        doNothing().when(userDAO).update(anyObject());

        userService.updateSettings("name",5,false);

        Mockito.verify(userDAO,times(1)).getUserByName(anyString());
        Mockito.verify(userDAO,times(1)).update(anyObject());
    }

    @Test
    public void confirmUserTest(){
        User user = User.builder().name("Test").build();

        when(userDAO.getUserByToken(anyString())).thenReturn(user);
        doNothing().when(userDAO).update(anyObject());

        userService.confirmUser("token");

        Mockito.verify(userDAO,times(1)).getUserByToken(anyString());
        Mockito.verify(userDAO,times(1)).update(anyObject());
    }

    @Test
    public void getUserByEmailTest(){
        User user = User.builder().name("Test").build();

        when(userDAO.getUserByEmail(anyString())).thenReturn(user);

        userService.getUserByEmail("test");

        Mockito.verify(userDAO,times(1)).getUserByEmail(anyString());
    }

    @Test
    public void isAdultTest(){
        User user = User.builder().name("Test").build();

        when(userDAO.getById(anyLong())).thenReturn(user);
        doNothing().when(userDAO).update(anyObject());

        userService.setIsAdult(1L);

        Mockito.verify(userDAO,times(1)).getById(anyLong());
        Mockito.verify(userDAO,times(1)).update(anyObject());
    }

    @Test
    public void saveUserTest() throws IOException {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");
        User userD = User.builder().name("Test").build();
        Role role = Role.builder().roleName("USER_ROLE").build();
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        String path = "classpath:/images/user/user.jpg";

        when(roleDAO.getAll()).thenReturn(roles);
        when(mapperFacade.map(Matchers.anyObject(),Matchers.anyObject())).thenReturn(userD);
        when(encoder.encode(anyString())).thenReturn("password");
        when(context.getResource(anyString())).thenReturn(null);
    }
}
