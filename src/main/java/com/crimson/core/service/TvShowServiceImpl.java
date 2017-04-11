package com.crimson.core.service;

import com.crimson.core.dao.*;
import com.crimson.core.dto.*;
import com.crimson.core.model.*;
import com.github.slugify.Slugify;
import ma.glasnost.orika.MapperFacade;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class TvShowServiceImpl implements TvShowService {

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private RatingDAO ratingDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public void saveTvShow(TvShow tvShow) {
        tvShowDAO.save(tvShow);
    }

    @Override
    public List<TvShow> getAllTvShows() {
        return tvShowDAO.getAll();
    }

    @Override
    public TvShowDTO getTvById(Long id) {
        return mapperFacade.map(tvShowDAO.getById(id), TvShowDTO.class);
    }

    @Override
    public TvShowDTO getTvByIdWithEpisodes(Long id) {
        return mapperFacade.map(tvShowDAO.getTvByIdWithEpisodes(id), TvShowDTO.class);
    }

    @Override
    public TvShowDTO getTvBySlug(String slug) {
        return mapperFacade.map(tvShowDAO.getTvBySlug(slug), TvShowDTO.class);
    }

    @Override
    public List<TvShow> getTvByGenre(String genre) {
        return tvShowDAO.getTvByGenre(genre);
    }

    @Override
    public List<TvShow> getTvByCountry(String country) {
        return tvShowDAO.getTvByCountry(country);
    }

    @Override
    public List<TvShow> getTvByYear(int releaseYear) {
        return tvShowDAO.getTvByYear(releaseYear);
    }

    @Override
    public List<TvShow> getTvByNetwork(String network) {
        return tvShowDAO.getTvByNetwork(network);
    }


    @Override
    public ImageDTO getTvPictures(String slug) {
        return mapperFacade.map(tvShowDAO.getTvBySlug(slug), ImageDTO.class);
    }

    @Override
    public byte[] getTvPics(String slug, String name) {
        return tvShowDAO.getTvShowPicture(slug).get(name);
    }

    @Override
    public void deleteTvShow(String name) {
        TvShow tv = tvShowDAO.getTvBySlug(name);
        tv.getUsers().clear();
        tv.getEpisodes().forEach(episode -> {
            episode.getUsers().clear();
            userDAO.getAll().forEach(user -> {
                user.getTvShows().remove(tv);
                user.getEpisodes().remove(episode);
            });
        });
        tvShowDAO.delete(tv);
    }

    @Override
    public void updateTvShow(TvShowDTO tvshow) {
        Slugify slugify = new Slugify();
        TvShow tv = tvShowDAO.getById(tvshow.getId());
        tv.setCountry(tvshow.getCountry());
        tv.setNetwork(tvshow.getNetwork());
        tv.setDescription(tvshow.getDescription());
        tv.setReleaseYear(tvshow.getReleaseYear());
        tv.setGenre(tvshow.getGenre());
        tv.setTitle(tvshow.getTitle());
        tv.setSlug(slugify.slugify(tvshow.getTitle()));
        tvShowDAO.update(tv);
    }

    @Override
    public long tvShowsSize() {
        return tvShowDAO.tvShowsSize();
    }

    @Override
    public List<Long> getIdsOfEpisodes(String name) {
        return tvShowDAO.getIdsOfWatchedEpisodes(name);
    }

    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addUser2TvShow(User user, TvShow tvShow) {
        if (!tvShowDAO.getUsers(tvShow).contains(user)) {
            List<User> users = tvShowDAO.getUsers(tvShow);
            users.add(user);
            tvShow.setUsers(users);
            tvShowDAO.update(tvShow);
        }
        if (!userDAO.getTvShows(user).contains(tvShow)) {
            List<TvShow> tvShows = userDAO.getTvShows(user);
            tvShows.add(tvShow);
            user.setTvShows(tvShows);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteUserFromTvShow(User user, TvShow tvShow) {
        if (tvShowDAO.getUsers(tvShow).contains(user)) {
            List<User> users = tvShowDAO.getUsers(tvShow);
            users.remove(user);
            tvShow.setUsers(users);
            tvShowDAO.update(tvShow);
        }
        if (userDAO.getTvShows(user).contains(tvShow)) {
            List<TvShow> tvShows = userDAO.getTvShows(user);
            tvShows.remove(tvShow);
            user.setTvShows(tvShows);
            userDAO.update(user);
        }
    }

    //TvShow2Genre

    @Override
    public void addGenre2TvShow(TvShow tvShow, Genre genre) {
        if (!tvShowDAO.getGenres(tvShow).contains(genre)) {
            List<Genre> genres = tvShowDAO.getGenres(tvShow);
            genres.add(genre);
            tvShow.setGenres(genres);
            tvShowDAO.update(tvShow);
        }
        if (!genreDAO.getTvShows(genre).contains(tvShow)) {
            List<TvShow> tvShows = genreDAO.getTvShows(genre);
            tvShows.add(tvShow);
            genre.setTvShows(tvShows);
            genreDAO.update(genre);
        }
    }

    @Override
    public void deleteGenreFromTvShow(TvShow tvShow, Genre genre) {
        if (tvShowDAO.getGenres(tvShow).contains(genre)) {
            List<Genre> genres = tvShowDAO.getGenres(tvShow);
            genres.remove(genre);
            tvShow.setGenres(genres);
            tvShowDAO.update(tvShow);
        }
        if (genreDAO.getTvShows(genre).contains(tvShow)) {
            List<TvShow> tvShows = genreDAO.getTvShows(genre);
            tvShows.remove(tvShow);
            genre.setTvShows(tvShows);
            genreDAO.update(genre);
        }
    }

    //TvShow2Episode

    @Override
    public void addEpisode2TvShow(TvShow tvShow, Episode episode) {
        if (!tvShowDAO.getEpisodes(tvShow).contains(episode)) {
            List<Episode> episodes = tvShowDAO.getEpisodes(tvShow);
            episodes.add(episode);
            tvShow.setEpisodes(episodes);
            tvShowDAO.update(tvShow);
        }
        if (episode.getTvShow() != tvShow) {
            episode.setTvShow(tvShow);
            episodeDAO.update(episode);
        }

    }

    @Override
    public void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode) {
        if (tvShowDAO.getEpisodes(tvShow).contains(episode)) {
            List<Episode> episodes = tvShowDAO.getEpisodes(tvShow);
            episodes.remove(episode);
            tvShow.setEpisodes(episodes);
            tvShowDAO.update(tvShow);
        }
        if (episode.getTvShow() == tvShow) {
            episode.setTvShow(null);
            episodeDAO.update(episode);
        }

    }

    //TvShowRating

    @Override
    public void addRating2TvShow(TvShow tvShow, Rating rating) {
        if (!tvShowDAO.getRatings(tvShow).contains(rating)) {
            List<Rating> ratings = tvShowDAO.getRatings(tvShow);
            ratings.add(rating);
            tvShow.setRatings(ratings);
            tvShowDAO.update(tvShow);
        }
        if (rating.getTvShow() != tvShow) {
            rating.setTvShow(tvShow);
            ratingDAO.update(rating);
        }
    }

    @Override
    public void deleteRatingFromTvShow(TvShow tvShow, Rating rating) {
        if (tvShowDAO.getRatings(tvShow).contains(rating)) {
            List<Rating> ratings = tvShowDAO.getRatings(tvShow);
            ratings.remove(rating);
            tvShow.setRatings(ratings);
            tvShowDAO.update(tvShow);
        }
        if (rating.getTvShow() == tvShow) {
            rating.setTvShow(null);
            ratingDAO.update(rating);
        }
    }

    @Override
    public void addComment(TvShow tvShow, Comment comment) {
        if (!tvShowDAO.getComments(tvShow).contains(comment)) {
            List<Comment> comments = tvShowDAO.getComments(tvShow);
            comments.add(comment);
            tvShow.setComments(comments);
            tvShowDAO.update(tvShow);
        }
        if (comment.getTvShow() != tvShow) {
            comment.setTvShow(tvShow);
            commentDAO.update(comment);
        }
    }

    @Override
    public void addReview(TvShow tvShow, Review review) {
        if (!tvShowDAO.getReviews(tvShow).contains(review)) {
            List<Review> reviews = tvShowDAO.getReviews(tvShow);
            reviews.add(review);
            tvShow.setReviews(reviews);
            tvShowDAO.update(tvShow);
        }
        if (review.getTvShow() != tvShow) {
            review.setTvShow(tvShow);
            reviewDAO.update(review);
        }
    }

    @Override
    public void deleteComment(TvShow tvShow, Comment comment) {
        if (tvShowDAO.getComments(tvShow).contains(comment)) {
            List<Comment> comments = tvShowDAO.getComments(tvShow);
            comments.remove(comment);
            tvShow.setComments(comments);
            tvShowDAO.update(tvShow);
        }
        if (comment.getTvShow() == tvShow) {
            comment.setTvShow(null);
            commentDAO.update(comment);
        }
    }

    @Override
    public void deleteReview(TvShow tvShow, Review review) {
        if (tvShowDAO.getReviews(tvShow).contains(review)) {
            List<Review> reviews = tvShowDAO.getReviews(tvShow);
            reviews.remove(review);
            tvShow.setReviews(reviews);
            tvShowDAO.update(tvShow);
        }
        if (review.getTvShow() == tvShow) {
            review.setTvShow(null);
            reviewDAO.update(review);
        }
    }

    @Override
    public List<User> getUsers(TvShow tv) {
        return tvShowDAO.getUsers(tv);
    }

    @Override
    public List<Genre> getGenres(TvShow tv) {
        return tvShowDAO.getGenres(tv);
    }

    @Override
    public List<Episode> getEpisodes(TvShow tv) {
        return tvShowDAO.getEpisodes(tv);
    }

    @Override
    public List<Rating> getRatings(TvShow tv) {
        return tvShowDAO.getRatings(tv);
    }

    @Override
    public List<Comment> getComments(TvShow tv) {
        return tvShowDAO.getComments(tv);
    }

    @Override
    public List<Review> getReviews(TvShow tv) {
        return tvShowDAO.getReviews(tv);
    }


    @Override
    public List<TvShowSearchDTO> getAllTvShowByMaxRating() {
        List<TvShow> tvShows = tvShowDAO.getTvShowsByMaxRating();
        List<TvShowSearchDTO> tvs = new ArrayList<>();
        tvShows.forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowSearchDTO.class)));
        return tvs;
    }

    @Override
    public List<TvShowSearchDTO> searchTvShow(String pattern) {
        List<TvShowSearchDTO> tvs = new ArrayList<>();
        tvShowDAO.searchTvShow(pattern).forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowSearchDTO.class))
        );
        return tvs;
    }

    @Override
    public List<TvShowSearchDTO> filterTvShows(double min, double max) {
        List<TvShowSearchDTO> tvs = new ArrayList<>();
        tvShowDAO.filterTvShows(min, max).forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowSearchDTO.class))
        );
        return tvs;
    }

    @Override
    public void updateTvShowPicture(String name, String key, MultipartFile pic1) throws IOException {
        TvShow tv = tvShowDAO.getTvBySlug(name);
        tv.getPictures().put(key, pic1.getBytes());
        tvShowDAO.update(tv);
    }

    @Override
    public void saveTvShowDTO(TvShowAddDTO tvShowAddDTO) throws IOException {
        TvShow tv = mapperFacade.map(tvShowAddDTO, TvShow.class);
        tv.getPictures().put("1", tvShowAddDTO.getPic1().getBytes());
        tv.getPictures().put("2", tvShowAddDTO.getPic2().getBytes());
        tv.getPictures().put("3", tvShowAddDTO.getPic3().getBytes());
        tv.getPictures().put("back", tvShowAddDTO.getBack().getBytes());
        tv.getPictures().put("poster", tvShowAddDTO.getPoster().getBytes());
        tvShowDAO.save(tv);
    }


    @Override
    public int tvShowsLastPageNumber() {
        int listSizeOnPage = 20;
        Long countResults = tvShowDAO.getTvShowsToPaginationByQuery();
        if ((countResults % listSizeOnPage) == 0) return (int) (countResults / listSizeOnPage);
        else return (int) (countResults / listSizeOnPage) + 1;
    }

    @Override
    public List<TvShowSearchDTO> tvShowsPaginationList(int pageNumber) {
        int lastPage = tvShowsLastPageNumber();
        List<TvShow> tvShows = new ArrayList<>();
        List<TvShowSearchDTO> tvShowsToReturn = new ArrayList<>();
        if (lastPage <= pageNumber) {
            tvShows = tvShowDAO.queryGettingTvShowListForPage(pageNumber, 25);
        }
        tvShows.forEach(
                tvShow -> tvShowsToReturn.add(mapperFacade.map(tvShow, TvShowSearchDTO.class))
        );

        return tvShowsToReturn;
    }

    public FilterResponseDTO filter(SearchFilterParameters parameters, int page) {
        FilterResponse response = new FilterResponse();
        Session session = tvShowDAO.getSession();
        Criteria c = session.createCriteria(TvShow.class);
        if (parameters.getGenre() != null) {
            c.add(Restrictions.eq("genre", parameters.getGenre()));
        }
        if (parameters.getReleaseYearStart() != null) {
            c.add(Restrictions.ge("releaseYear", parameters.getReleaseYearStart()));
        }
        if (parameters.getReleaseYearEnd() != null) {
            c.add(Restrictions.le("releaseYear", parameters.getReleaseYearEnd()));
        }
        if (parameters.getCountry() != null) {
            c.add(Restrictions.eq("country", parameters.getCountry()));
        }
        if (parameters.getNetwork() != null) {
            c.add(Restrictions.eq("network", parameters.getNetwork()));
        }
        if (parameters.getMinimalRating() != null) {
            c.add(Restrictions.ge("overallRating", parameters.getMinimalRating()));
        }
        if (parameters.getMaximumRating() != null) {
            c.add(Restrictions.le("overallRating", parameters.getMaximumRating()));
        }
        int lastPage;
        int listSizeOnPage = 20;
        int countResults = (c.list().size());
        response.setSize(countResults);
        if ((countResults % listSizeOnPage) == 0) lastPage = (countResults / listSizeOnPage);
        else lastPage = countResults / listSizeOnPage + 1;

        if (page <= lastPage) {
            c.setFirstResult((page - 1) * 20);
            c.setMaxResults(20);
        }
        response.setTvShows(c.list());
        return mapperFacade.map(response, FilterResponseDTO.class);
    }


}
