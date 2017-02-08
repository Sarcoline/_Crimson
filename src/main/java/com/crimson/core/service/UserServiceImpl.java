package com.crimson.core.service;

import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ApplicationContext context;

    @Override
    public void saveUser(UserDTO userDTO) throws IOException {
        User user = mapperFacade.map(userDTO, User.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        InputStream in = context.getResource("classpath:/images/user/user.jpg").getInputStream();
        user.setProfilePic(IOUtils.toByteArray(in));
        userDAO.saveUser(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        // User user =  mapperFacade.map(userDTO, User.class);
        User user = userDAO.getUserById(userDTO.getId());
        userDAO.deleteUser(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user2 = userDAO.getUserById(userDTO.getId());
        user2.setEmail(userDTO.getEmail());
        //User user = mapperFacade.map(userDTO, User.class);
        userDAO.updateUser(user2);
    }

    @Override
    public UserDTO getUserByName(String name) {
        return mapperFacade.map(userDAO.getUserByName(name), UserDTO.class);

    }

    @Override
    public boolean checkFollow(UserDTO userDTO, TvShowDTO tvShow) {
        return userDAO.getUserByName(userDTO.getName()).getUserTvShowList().contains(tvShowDAO.getTvById(tvShow.getId()));
    }

    @Override
    public void addTvShow2User(UserDTO userDTO, TvShowDTO tvShow) {
        userDAO.getUserByName(userDTO.getName()).getUserTvShowList().add(mapperFacade.map(tvShow, TvShow.class));
    }

    @Override
    public void deleteTvShowFromUser(UserDTO userDTO, TvShowDTO tvShow) {
        User user = userDAO.getUserByName(userDTO.getName());
        TvShow tv = tvShowDAO.getTvById(tvShow.getId());
        user.getUserTvShowList().remove(tv);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TvShowDTO> getUserTvShows(UserDTO userDTO) {
        List tvs = new ArrayList();
        for (TvShow tv : userDAO.getUserByName(userDTO.getName()).getUserTvShowList()) {
            tvs.add(mapperFacade.map(tv, TvShowDTO.class));
        }
        return tvs;
    }

    //User2Episode

    @Override
    public void addEpisode2User(User user, Episode episode) {
        userDAO.addEpisode2User(user, episode);
    }

    @Override
    public void deleteEpisodeFromUser(User user, Episode episode) {
        userDAO.deleteEpisodeFromUser(user, episode);
    }

    //Rating

    @Override
    public void addRating2User(User user, Rating rating) {
        userDAO.addRating2User(user, rating);
    }

    @Override
    public void deleteRatingFromUser(User user, Rating rating) {
        userDAO.deleteRatingFromUser(user, rating);
    }


    //Extra Methods
    @Override
    public List<TvShow> getUserTvShowsSortedByMaxRating(UserDTO userDTO){
        User user = userDAO.getUserById(userDTO.getId());
        return userDAO.getUserTvShowsSortedByMaxRating(user);}

    @Override
    public List<Episode> getAllUnwatchedUserEpisodes(UserDTO userDTO){
        User user = userDAO.getUserById(userDTO.getId());
        return userDAO.getAllUnwatchedUserEpisodes(user);}

    @Override
    public List<Episode> getAllUpcomingUserEpisodes(UserDTO userDTO){
        User user = userDAO.getUserById(userDTO.getId());
        return userDAO.getAllUpcomingUserEpisodes(user);}
}
