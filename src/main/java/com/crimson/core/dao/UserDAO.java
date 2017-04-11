package com.crimson.core.dao;

import com.crimson.core.model.*;

import java.util.List;


public interface UserDAO extends BaseDAO<User, Long> {

    User getUserByName(String name);

    User getUserByToken(String token);

    List<TvShow> getTvShows(User user);

    List<Episode> getEpisodes(User user);

    List<Rating> getRatings(User user);

    List<Role> getRoles(User user);

    List<Comment> getComments(User user);

    List<Review> getReviews(User user);

    User getUserByEmail(String email);

    byte[] getUserProfilePicture(String name);

    boolean checkFollow(String userName, long idTv);
}
