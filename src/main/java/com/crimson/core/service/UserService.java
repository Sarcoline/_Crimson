package com.crimson.core.service;

import com.crimson.core.dto.*;
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

    void changeProfilePic(String username, MultipartFile file) throws IOException;

    UserDisplayDTO getUserDisplayByName(String name);

    UserDTO getUserByName(String name);

    void addTvShow2User(String username, long id);

    void deleteTvShowFromUser(String username, long id);

    void addEpisode2User(User user, Episode episode);

    void deleteEpisodeFromUser(User user, Episode episode);

    List<TvShowDTO> getUserTvShows(UserDTO userDTO);

    void addRating2User(User user, Rating rating);

    UserDTO getUserByToken(String token);

    byte[] getUserProfilePicture(String name);

    boolean checkFollow(String name, long id);

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

    List<TvShowSearchDTO> getUserTvShowsSortedByMaxRating(long id);

    List<EpisodeDTO> getAllUnwatchedUserEpisodes(UserDTO user);

    List<Long> getWatchedEpisodesIds(UserDisplayDTO user);

    List<EpisodeDTO> getAllUpcomingUserEpisodes(UserDTO userDTO, List<TvShowDTO> tvs, List<EpisodeDTO> watchedEpisodes);

    List<EpisodeFromJson> getUpcomingEpisodes(UserDisplayDTO user);

    void updatePassword(UserDTO user, String password);

    boolean checkOldPassword(UserDTO userDTO, String password);

    void updateSettings(String username, int days, boolean send);

    boolean confirmUser(String token);

    UserDTO getUserByEmail(String email);

    void createPasswordResetTokenForUser(UserDTO userDTO, String token);

    String validatePasswordResetToken(long id, String token);

    void changeUserPassword(User user, String password);

    void deletePasswordResetToken(String token);

    void setIsAdult(long id);
}
