package com.crimson.core.service;

import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.Rating;
import com.crimson.core.model.User;

import java.io.IOException;
import java.util.List;


public interface UserService {
    void saveUser(UserDTO userDTO) throws IOException;

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(User user);

    void updateUser(UserDTO userDTO);

    UserDTO getUserByName(String name);

    void addTvShow2User(UserDTO user, TvShowDTO tvShow);

    void deleteTvShowFromUser(UserDTO user, TvShowDTO tvShow);

    //User2Episode
    void addEpisode2User(User user, Episode episode);

    void deleteEpisodeFromUser(User user, Episode episode);

    List<TvShowDTO> getUserTvShows(UserDTO userDTO);

    void addRating2User(User user, Rating rating);

    boolean checkFollow(UserDTO userDTO, TvShowDTO tvShow);

    void deleteRatingFromUser(User user, Rating rating);
}
