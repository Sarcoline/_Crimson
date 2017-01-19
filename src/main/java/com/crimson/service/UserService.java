package com.crimson.service;

import com.crimson.dto.UserDTO;
import com.crimson.model.Episode;
import com.crimson.model.Rating;
import com.crimson.model.TvShow;
import com.crimson.model.User;

import java.io.IOException;
import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
public interface UserService {
    void saveUser(UserDTO userDTO) throws IOException;

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(User user);

    void updateUser(UserDTO userDTO);

    UserDTO getUserByName(String name);

    void addTvShow2User(UserDTO user, TvShow tvShow);

    void deleteTvShowFromUser(UserDTO user, TvShow tvShow);

    //User2Episode
    void addEpisode2User(User user, Episode episode);

    void deleteEpisodeFromUser(User user, Episode episode);

    public List<TvShow> getUserTvShows(UserDTO userDTO);

    void addRating2User(User user, Rating rating);

    public boolean checkFollow(UserDTO userDTO, TvShow tvShow);

    void deleteRatingFromUser(User user, Rating rating);
}
