package com.crimson.core.service;

import com.crimson.core.dao.RoleDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.*;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Autowired
    private RoleDAO roleDAO;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Override
    public void saveUser(UserDTO userDTO) throws IOException {
        Role role = roleDAO.getAll().get(0);
        User user = mapperFacade.map(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add(role);
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
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return userDAO.getAll();
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
    public void updateUser(UserDTO userDTO) throws IOException {
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
    public boolean checkFollow(UserDTO userDTO, TvShowDTO tvShow) {
        return userDAO.getUserByName(userDTO.getName()).getTvShows().contains(tvShowDAO.getById(tvShow.getId()));
    }

    @Override
    public void addTvShow2User(UserDTO userDTO, TvShowDTO tvShow) {
        userDAO.getUserByName(userDTO.getName()).getTvShows().add(mapperFacade.map(tvShow, TvShow.class));
    }

    @Override
    public void deleteTvShowFromUser(UserDTO userDTO, TvShowDTO tvShow) {
        User user = userDAO.getUserByName(userDTO.getName());
        TvShow tv = tvShowDAO.getById(tvShow.getId());
        user.getTvShows().remove(tv);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TvShowDTO> getUserTvShows(UserDTO userDTO) {
        List tvs = new ArrayList();
        userDAO.getUserByName(userDTO.getName()).getTvShows().forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowDTO.class)));
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

    //User2Setting
    @Override
    public void addSetting2User(User user, Setting setting) {
        userDAO.addSetting2User(user, setting);
    }

    @Override
    public void deleteSettingFromUser(User user, Setting setting) {
        userDAO.deleteSettingFromUser(user, setting);
    }

    //User2Role

    @Override
    public void addRole2User(User user, Role role) {
        userDAO.addRole2User(user, role);
    }

    @Override
    public void deleteRoleFromUser(User user, Role role) {
        userDAO.deleteRoleFromUser(user, role);
    }


    //Extra Methods
    @Override
    public List<TvShowDTO> getUserTvShowsSortedByMaxRating(UserDTO userDTO) {

        List<TvShowDTO> sortedList = new ArrayList<>();
        List<Rating> unsortedList = userDTO.getRatings();
        unsortedList.sort(Comparator.comparingInt(Rating::getValue).reversed());
        unsortedList.forEach(rating -> sortedList.add(mapperFacade.map(rating.getTvShow(), TvShowDTO.class)));

        return sortedList;
    }

    @Override
    public List<EpisodeDTO> getAllUnwatchedUserEpisodes(UserDTO userDTO) {

        User user = userDAO.getById(userDTO.getId());
        List<TvShow> allFollowedUserTvShows = user.getTvShows();

        List<EpisodeDTO> allUnwatchedUserEpisodes = new ArrayList<>();

        List<Episode> allWatchedUserEpisodes = user.getEpisodes();

        allFollowedUserTvShows.forEach(tvShow -> {
            List<Episode> tvShowEpisodes = tvShow.getEpisodes();
            tvShowEpisodes.forEach(episode -> {
                if (!allWatchedUserEpisodes.contains(episode))
                    allUnwatchedUserEpisodes.add(mapperFacade.map(episode, EpisodeDTO.class));
            });
        });


        return allUnwatchedUserEpisodes;
    }

    //TODO slow af
    @Override
    public List<EpisodeDTO> getAllUpcomingUserEpisodes(UserDTO userDTO, List<TvShowDTO> tvs, List<EpisodeDTO> watchedEpisodes) {

        int days = userDTO.getSetting().getDaysOfUpcomingEpisodes();
        List<EpisodeDTO> allFutureUserEpisodes = new ArrayList<>();
        List<EpisodeDTO> allFutureUserEpisodesDTO = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDate = LocalDate.now().plusDays(days);

        tvs.forEach(tv -> {
               if(tv.getFinishYear() == 0) allFutureUserEpisodes.addAll(tv.getEpisodes());
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
    public void updateSettings(UserDTO userDTO, int days) {
        User user = userDAO.getById(userDTO.getId());
        user.getSetting().setDaysOfUpcomingEpisodes(days);
        userDAO.update(user);
    }
}
