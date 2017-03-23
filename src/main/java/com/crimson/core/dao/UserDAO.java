package com.crimson.core.dao;

import com.crimson.core.model.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


public interface UserDAO extends BaseDAO<User, Long> {

    User getUserByName(String name);

    @Cacheable("myCache")
    User getUserByToken(String token);

    void addTvShow2User(User user, TvShow tvShow);

    void deleteTvShowFromUser(User user, TvShow tvShow);

    void addEpisode2User(User user, Episode episode);

    void deleteEpisodeFromUser(User user, Episode episode);

    void addRating2User(User user, Rating rating);

    void deleteRatingFromUser(User user, Rating rating);

    void addSetting2User(User user, Setting setting);

    void deleteSettingFromUser(User user);

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
}
