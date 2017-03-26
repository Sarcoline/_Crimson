package com.crimson.core.service;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface UserService {
    void saveUser(UserDTO userDTO) throws IOException;

    List<UserDTO> getAllUsers();

    User getUserById(Long id);

    void deleteUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void changeProfilePic(UserDTO userDTO, MultipartFile file) throws IOException;

    UserDTO getUserByName(String name);

    void addTvShow2User(UserDTO user, TvShowDTO tvShow);

    void deleteTvShowFromUser(UserDTO user, TvShowDTO tvShow);

    void addEpisode2User(User user, Episode episode);

    void deleteEpisodeFromUser(User user, Episode episode);

    List<TvShowDTO> getUserTvShows(UserDTO userDTO);

    void addRating2User(User user, Rating rating);

    UserDTO getUserByToken(String token);

    boolean checkFollow(UserDTO userDTO, TvShowDTO tvShow);

    void deleteRatingFromUser(User user, Rating rating);

    void addSetting2User(User user, Setting setting);

    void deleteSettingFromUser(User user, Setting setting);

    void addRole2User(User user, Role role);

    void deleteRoleFromUser(User user, Role role);

    void addComment(User user, Comment comment);

    void addReview(User user, Review review);

    void deleteComment(User user, Comment comment);

    void deleteReview(User user, Review review);

    List<TvShow> getTvShows(User user);

    List<Episode> getEpisodes(User user);

    List<Rating> getRatings(User user);

    List<Role> getRoles(User user);

    List<Comment> getComments(User user);

    List<Review> getReviews(User user);

    List<TvShowDTO> getUserTvShowsSortedByMaxRating(UserDTO user);

    List<EpisodeDTO> getAllUnwatchedUserEpisodes(UserDTO user);

    List<EpisodeDTO> getAllUpcomingUserEpisodes(UserDTO userDTO, List<TvShowDTO> tvs, List<EpisodeDTO> watchedEpisodes);

    void updatePassword(UserDTO user, String password);

    boolean checkOldPassword(UserDTO userDTO, String password);

    void updateSettings(UserDTO user, int days, boolean send);

    void confirmUser(String token);

    UserDTO getUserByEmail(String email);

    void createPasswordResetTokenForUser(UserDTO userDTO, String token);

    String validatePasswordResetToken(long id, String token);

    void changeUserPassword(User user, String password);

    void deletePasswordResetToken(String token);
}
