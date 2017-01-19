package com.crimson.service;

import com.crimson.dao.UserDAO;
import com.crimson.dto.UserDTO;
import com.crimson.model.Episode;
import com.crimson.model.Rating;
import com.crimson.model.TvShow;
import com.crimson.model.User;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

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
//        List usersDto = new ArrayList();
//        for (User user: userDAO.getAllUsers()) {
//            usersDto.add(mapperFacade.map(user, UserDTO.class));
//        }
//        return usersDto;
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void deleteUser(User user){
        userDAO.deleteUser(user);
    }

    @Override
    public void updateUser(UserDTO userDTO){
        User user = mapperFacade.map(userDTO, User.class);
        userDAO.updateUser(user);
    }

    @Override
    public UserDTO getUserByName(String name){
        return mapperFacade.map(userDAO.getUserByName(name), UserDTO.class);

    }

    @Override
    public boolean checkFollow(UserDTO userDTO, TvShow tvShow ) {
        return userDAO.getUserByName(userDTO.getName()).getUserTvShowList().contains(tvShow);
    }

    @Override
    public void addTvShow2User(UserDTO userDTO, TvShow tvShow){
        userDAO.getUserByName(userDTO.getName()).getUserTvShowList().add(tvShow);
    }

    @Override
    public void deleteTvShowFromUser(UserDTO userDTO, TvShow tvShow){
        userDAO.getUserByName(userDTO.getName()).getUserTvShowList().remove(tvShow);
    }

    @Override
    public List<TvShow> getUserTvShows(UserDTO userDTO){
        return userDAO.getUserByName(userDTO.getName()).getUserTvShowList();
    }
    //User2Episode
    @Override
    public void addEpisode2User(User user, Episode episode){
        userDAO.addEpisode2User(user, episode);
    }

    @Override
    public void deleteEpisodeFromUser(User user, Episode episode){
        userDAO.deleteEpisodeFromUser(user,episode);
    }

    //Rating

    @Override
    public void addRating2User(User user, Rating rating){
        userDAO.addRating2User(user,rating);
    }

    @Override
    public void deleteRatingFromUser(User user, Rating rating){
        userDAO.deleteRatingFromUser(user,rating);
    }
}
