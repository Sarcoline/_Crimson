package com.crimson.scheduler;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.service.MailService;
import com.crimson.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class MailSchedulerJob {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;


    //Check if job can execute for given user
    boolean canExecute(UserDTO user) {
        List<EpisodeDTO> upcoming = userService.getAllUpcomingUserEpisodes(user, user.getTvShows(), user.getEpisodes());
        return upcoming.size() > 0 && user.getSetting().getSendEpisodeList();
    }

    //Create message string with list of upcoming episodes for given user
    String createMessage(UserDTO user) {
        List<EpisodeDTO> upcoming = userService.getAllUpcomingUserEpisodes(user, user.getTvShows(), user.getEpisodes());
        String body = "<h3>Hey, it's your daily episodes list: </h3>" +
                "<ul>";
        StringBuilder sB = new StringBuilder(body);
        upcoming.forEach(episode -> sB.append(String.format("<li>%s - S%sE%s - %s - %s</li>",
                episode.getTvShow().getTitle(), episode.getSeason(), episode.getNumber(),
                episode.getTitle(), episode.getReleaseDate())));
        sB.append("</ul>");
        return sB.toString();
    }

    //Send mail with episode list to user
    void sendMessage(UserDTO user, String body) throws MessagingException {
        mailService.sendMail(user.getEmail(),"Crimson - Daily episode list", body);
    }

}
