package com.crimson.core.service;

import com.crimson.core.dao.*;
import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.TvShowSearchDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.*;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private RatingDAO ratingDAO;

    @Autowired
    private SettingsDAO settingsDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PasswordResetTokenDAO passwordResetTokenDAO;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Override
    public void saveUser(UserDTO userDTO) throws IOException {
        Role role = roleDAO.getAll().get(0);
        User user = mapperFacade.map(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        addRole2User(user, role);
        user.setSetting(new Setting(false, 10, 7));
        if (userDTO.getUploadedPic() != null) {
            user.setProfilePic(userDTO.getUploadedPic().getBytes());
        } else {
            InputStream in = context.getResource("classpath:/images/user/user.jpg").getInputStream();
            user.setProfilePic(IOUtils.toByteArray(in));
        }
        userDAO.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        userDAO.getAll().forEach(user -> users.add(mapperFacade.map(user, UserDTO.class)));
        return users;
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        User user = userDAO.getById(userDTO.getId());
        userDAO.delete(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user2 = userDAO.getById(userDTO.getId());
        user2.setEmail(userDTO.getEmail());
        userDAO.update(user2);
    }

    @Override
    public void changeProfilePic(UserDTO userDTO, MultipartFile file) throws IOException {
        User user2 = userDAO.getById(userDTO.getId());
        user2.setProfilePic(file.getBytes());
        userDAO.update(user2);
    }

    @Override
    public UserDTO getUserByName(String name) {
        return mapperFacade.map(userDAO.getUserByName(name), UserDTO.class);
    }

    @Override
    public UserDTO getUserByToken(String token) {
        return mapperFacade.map(userDAO.getUserByToken(token), UserDTO.class);
    }

    @Override
    public byte[] getUserProfilePicture(String name) {
        return userDAO.getUserProfilePicture(name);
    }

    @Override
    public boolean checkFollow(UserDTO userDTO, TvShowDTO tvShow) {
        return userDAO.getUserByName(userDTO.getName()).getTvShows().contains(tvShowDAO.getById(tvShow.getId()));
    }

    @Override
    public void addTvShow2User(UserDTO userDTO, TvShowDTO tvShowDTO) {
        User user = userDAO.getById(userDTO.getId());
        TvShow tv = tvShowDAO.getById(tvShowDTO.getId());
        if (!userDAO.getTvShows(user).contains(tv)) {
            List<TvShow> tvShows = userDAO.getTvShows(user);
            tvShows.add(tv);
            user.setTvShows(tvShows);
            userDAO.update(user);
        }
        if (!tvShowDAO.getUsers(tv).contains(user)) {
            List<User> users = tvShowDAO.getUsers(tv);
            users.add(user);
            tv.setUsers(users);
            tvShowDAO.update(tv);
        }
    }

    @Override
    public void deleteTvShowFromUser(UserDTO userDTO, TvShowDTO tvShow) {
        User user = userDAO.getUserByName(userDTO.getName());
        TvShow tv = tvShowDAO.getById(tvShow.getId());
        if (userDAO.getTvShows(user).contains(tv)) {
            List<TvShow> tvShows = userDAO.getTvShows(user);
            tvShows.remove(tv);
            user.setTvShows(tvShows);
            userDAO.update(user);
        }
        if (tvShowDAO.getUsers(tv).contains(user)) {
            List<User> users = tvShowDAO.getUsers(tv);
            users.remove(user);
            tv.setUsers(users);
            tvShowDAO.update(tv);
        }
    }

    @Override
    public List<TvShowDTO> getUserTvShows(UserDTO userDTO) {
        List<TvShowDTO> tvs = new ArrayList<>();
        userDAO.getTvShows(userDAO.getUserByName(userDTO.getName())).forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowDTO.class)));
        return tvs;
    }

    //User2Episode

    @Override
    public void addEpisode2User(User user, Episode episode) {
        if (!userDAO.getEpisodes(user).contains(episode)) {
            List<Episode> episodes = userDAO.getEpisodes(user);
            episodes.add(episode);
            user.setEpisodes(episodes);
            userDAO.update(user);
        }
        if (!episodeDAO.getUsers(episode).contains(user)) {
            List<User> users = episodeDAO.getUsers(episode);
            users.add(user);
            episode.setUsers(users);
            episodeDAO.update(episode);
        }
    }

    @Override
    public void deleteEpisodeFromUser(User user, Episode episode) {
        if (userDAO.getEpisodes(user).contains(episode)) {
            List<Episode> episodes = userDAO.getEpisodes(user);
            episodes.remove(episode);
            user.setEpisodes(episodes);
            userDAO.update(user);
        }
        if (episodeDAO.getUsers(episode).contains(user)) {
            List<User> users = episodeDAO.getUsers(episode);
            users.remove(user);
            episode.setUsers(users);
            episodeDAO.update(episode);
        }
    }

    //Rating

    @Override
    public void addRating2User(User user, Rating rating) {
        if (rating.getUser() != user) {
            rating.setUser(user);
            ratingDAO.update(rating);
        }
        if (!userDAO.getRatings(user).contains(rating)) {
            List<Rating> ratings = userDAO.getRatings(user);
            ratings.add(rating);
            user.setRatings(ratings);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteRatingFromUser(User user, Rating rating) {
        if (rating.getUser() == user) {
            rating.setUser(null);
            ratingDAO.update(rating);
        }
        if (userDAO.getRatings(user).contains(rating)) {
            List<Rating> ratings = userDAO.getRatings(user);
            ratings.remove(rating);
            user.setRatings(ratings);
            userDAO.update(user);
        }
    }

    //User2Setting
    @Override
    public void addSetting2User(User user, Setting setting) {
        if (setting.getUser() != user) {
            setting.setUser(user);
            settingsDAO.update(setting);
        }
        if (user.getSetting() != setting) {
            user.setSetting(setting);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteSettingFromUser(User user, Setting setting) {
        if (setting.getUser() == user) {
            setting.setUser(null);
            settingsDAO.update(setting);
        }
        if (user.getSetting() == setting) {
            user.setSetting(null);
            userDAO.update(user);
        }
    }

    //User2Role

    @Override
    public void addRole2User(User user, Role role) {
        if (!roleDAO.getUsers(role).contains(user)) {
            List<User> users = roleDAO.getUsers(role);
            users.add(user);
            role.setUsers(users);
            roleDAO.update(role);
        }
        if (!userDAO.getRoles(user).contains(role)) {
            List<Role> roles = userDAO.getRoles(user);
            roles.add(role);
            user.setRoles(roles);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteRoleFromUser(User user, Role role) {
        if (roleDAO.getUsers(role).contains(user)) {
            List<User> users = roleDAO.getUsers(role);
            users.remove(user);
            role.setUsers(users);
            roleDAO.update(role);
        }
        if (userDAO.getRoles(user).contains(role)) {
            List<Role> roles = userDAO.getRoles(user);
            roles.remove(role);
            user.setRoles(roles);
            userDAO.update(user);
        }

    }

    @Override
    public void addComment(User user, Comment comment) {
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
    public void addReview(User user, Review review) {
        if (review.getUser() != user) {
            review.setUser(user);
            reviewDAO.update(review);
        }
        if (!userDAO.getReviews(user).contains(review)) {
            List<Review> reviews = userDAO.getReviews(user);
            reviews.add(review);
            user.setReviews(reviews);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteComment(User user, Comment comment) {
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
    public void deleteReview(User user, Review review) {
        if (review.getUser() == user) {
            review.setUser(null);
            reviewDAO.update(review);
        }
        if (userDAO.getReviews(user).contains(review)) {
            List<Review> reviews = userDAO.getReviews(user);
            reviews.remove(review);
            user.setReviews(reviews);
            userDAO.update(user);
        }
    }

    @Override
    public List<TvShow> getTvShows(User user) {
        return userDAO.getTvShows(user);
    }

    @Override
    public List<Episode> getEpisodes(User user) {
        return userDAO.getEpisodes(user);
    }

    @Override
    public List<Rating> getRatings(User user) {
        return userDAO.getRatings(user);
    }

    @Override
    public List<Role> getRoles(User user) {
        return userDAO.getRoles(user);
    }

    @Override
    public List<Comment> getComments(User user) {
        return userDAO.getComments(user);
    }

    @Override
    public List<Review> getReviews(User user) {
        return userDAO.getReviews(user);
    }

    @Override
    public List<TvShowSearchDTO> getUserTvShowsSortedByMaxRating(UserDTO userDTO) {

        List<TvShowSearchDTO> sortedList = new ArrayList<>();
        List<Rating> unsortedList = userDTO.getRatings();
        unsortedList.sort(Comparator.comparingInt(Rating::getValue).reversed());
        unsortedList.forEach(rating -> sortedList.add(mapperFacade.map(rating.getTvShow(), TvShowSearchDTO.class)));

        return sortedList;
    }

    @Override
    public List<EpisodeDTO> getAllUnwatchedUserEpisodes(UserDTO userDTO) {

        User user = userDAO.getById(userDTO.getId());
        List<TvShow> allFollowedUserTvShows = userDAO.getTvShows(user);

        List<EpisodeDTO> allUnwatchedUserEpisodes = new ArrayList<>();

        List<Episode> allWatchedUserEpisodes = userDAO.getEpisodes(user);

        allFollowedUserTvShows.forEach(tvShow -> {
            List<Episode> tvShowEpisodes = tvShow.getEpisodes();
            tvShowEpisodes.forEach(episode -> {
                if (!allWatchedUserEpisodes.contains(episode))
                    allUnwatchedUserEpisodes.add(mapperFacade.map(episode, EpisodeDTO.class));
            });
        });
        return allUnwatchedUserEpisodes;
    }

    @Override
    public List<EpisodeDTO> getAllUpcomingUserEpisodes(UserDTO userDTO, List<TvShowDTO> tvs, List<EpisodeDTO> watchedEpisodes) {

        int days = userDTO.getSetting().getDaysOfUpcomingEpisodes();
        List<EpisodeDTO> allFutureUserEpisodes = new ArrayList<>();
        List<EpisodeDTO> allFutureUserEpisodesDTO = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDate = LocalDate.now().plusDays(days);

        tvs.forEach(tv -> {
            if (tv.getFinishYear() == 0) allFutureUserEpisodes.addAll(tv.getEpisodes());
        });

        allFutureUserEpisodes.removeAll(watchedEpisodes);

        for (EpisodeDTO episode : allFutureUserEpisodes) {
            LocalDate episodeDate = episode.getReleaseDate();
            if (episodeDate.isAfter(currentDate) && episodeDate.isBefore(lastDate))
                allFutureUserEpisodesDTO.add(episode);
        }

        allFutureUserEpisodesDTO.sort(Comparator.comparing(EpisodeDTO::getReleaseDate));
        return allFutureUserEpisodesDTO;
    }

    @Override
    public void updatePassword(UserDTO userDTO, String password) {
        User user = userDAO.getById(userDTO.getId());
        user.setPassword(encoder.encode(password));
        userDAO.update(user);
    }

    @Override
    public boolean checkOldPassword(UserDTO userDTO, String password) {
        return encoder.matches(password, userDTO.getPassword());
    }

    @Override
    public void updateSettings(UserDTO userDTO, int days, boolean send) {
        User user = userDAO.getById(userDTO.getId());
        user.getSetting().setDaysOfUpcomingEpisodes(days);
        user.getSetting().setSendEpisodeList(send);
        userDAO.update(user);
    }

    @Override
    public boolean confirmUser(String token) {
        User user = userDAO.getUserByToken(token);
        if (user == null) return false;
        user.setToken(null);
        user.setActive(true);
        userDAO.update(user);
        return true;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userDAO.getUserByEmail(email);
        return mapperFacade.map(user, UserDTO.class);
    }

    @Override
    public void createPasswordResetTokenForUser(UserDTO userDTO, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, userDAO.getById(userDTO.getId()));
        passwordResetTokenDAO.save(myToken);
    }

    @Override
    public String validatePasswordResetToken(long id, String token) {
        PasswordResetToken passToken =
                passwordResetTokenDAO.findByToken(token);
        if ((passToken == null) || (passToken.getUser()
                .getId() != id)) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return "expired";
        }

        User user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Collections.singletonList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        userDAO.update(user);
    }

    @Override
    public void deletePasswordResetToken(String token) {
        PasswordResetToken tokenObj = passwordResetTokenDAO.findByToken(token);
        User user = tokenObj.getUser();
        user.setPasswordResetToken(null);
        userDAO.update(user);
        passwordResetTokenDAO.delete(tokenObj);
    }

    @Override
    public void setIsAdult(long id) {
        User user = userDAO.getById(id);
        user.setAdult(true);
        userDAO.update(user);
    }

}




