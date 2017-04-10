package com.crimson.scheduler;

import com.crimson.core.dto.UserDTO;
import com.crimson.core.service.MailService;
import com.crimson.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;


@Component
public class MailScheduler {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSchedulerJob mailSchedulerJob;


    //Scheduler that send once a day list of upcoming episodes to user
    @Async
    @Scheduled (cron = "0 0/10 * * * *")
    public void sendMail () throws MessagingException {

        List<UserDTO> users = userService.getAllUsers();
        users.forEach(user -> {
            if (mailSchedulerJob.canExecute(user)) {
                String body = mailSchedulerJob.createMessage(user);
                try {
                    mailSchedulerJob.sendMessage(user, body);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
