package com.crimson.scheduler;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.service.MailService;
import com.crimson.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;


@Service
public class MailScheduler {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Async
    @Scheduled (cron = "0 15 10 ? * *") //10:15AM kazdego dnia
    public void sendMail () throws MessagingException {

        List<UserDTO> users = userService.getAllUsers();
        users.forEach(user -> {
            List<EpisodeDTO> upcoming = userService.getAllUpcomingUserEpisodes(user, user.getTvShows(), user.getEpisodes());
            if (upcoming.size() > 0) {
                String body = "<h3>Hey, it's your daily episodes list: </h3>" +
                        "<ul>";
                StringBuilder sB = new StringBuilder(body);
                upcoming.forEach(episode -> sB.append(String.format("<li>%s - S%sE%s - %s - %s</li>",
                        episode.getTvShow().getTitle(), episode.getSeason(), episode.getNumber(),
                        episode.getTitle(), episode.getReleaseDate())));
                sB.append("</ul>");
                body = sB.toString();
                try {
                    mailService.sendMailWithEpisodes(user.getEmail(), body);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
