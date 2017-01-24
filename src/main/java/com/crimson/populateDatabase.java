package com.crimson;

import com.crimson.context.CoreApplicationContext;
import com.crimson.core.dao.GenreDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Meow on 18.01.2017.
 */
@Service
public class populateDatabase {


    public static void main(String[] args) throws IOException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CoreApplicationContext.class);

        TvShowDAO tvShowDAO = applicationContext.getBean(TvShowDAO.class);
        UserDAO userDAO = applicationContext.getBean(UserDAO.class);
        GenreDAO genreDAO = applicationContext.getBean(GenreDAO.class);
        Genre fantasy = new Genre("Fantasy");
        Genre drama = new Genre("Drama");
        Genre comedy = new Genre("Comedy");
        genreDAO.addGenre(fantasy);
        genreDAO.addGenre(drama);
        genreDAO.addGenre(comedy);

        if (tvShowDAO.getAllTvShows().size() < 1) {
            TvShow tv = new TvShow();


            tv.setTitle("Game Of Thrones");
            tv.setNetwork("HBO");
            tv.setOverallRating(9.2);
            tv.setGenre("Fantasy");
            tv.setTrailerUrl("https://www.youtube.com/watch?v=EI0ib1NErqg");
            tv.setCountry("USA");
            tv.setReleaseYear(2010);
            Resource resource = applicationContext.getResource("classpath:/images/game-of-thrones/poster.jpg");
            InputStream in = resource.getInputStream();
            byte[] poster = IOUtils.toByteArray(in);
            tv.getPictures().put("poster", poster);
            Resource resource1 = applicationContext.getResource("classpath:/images/game-of-thrones/back.jpg");
            InputStream in1 = resource1.getInputStream();
            byte[] back = IOUtils.toByteArray(in1);
            tv.getPictures().put("back", back);
            Resource resource2 = applicationContext.getResource("classpath:/images/game-of-thrones/1.jpg");
            InputStream in2 = resource2.getInputStream();
            byte[] pic1 = IOUtils.toByteArray(in2);
            tv.getPictures().put("1", pic1);
            Resource resource3 = applicationContext.getResource("classpath:/images/game-of-thrones/2.jpg");
            InputStream in3 = resource3.getInputStream();
            byte[] pic2 = IOUtils.toByteArray(in3);
            tv.getPictures().put("2", pic2);
            Resource resource4 = applicationContext.getResource("classpath:/images/game-of-thrones/3.jpg");
            InputStream in4 = resource4.getInputStream();
            byte[] pic3 = IOUtils.toByteArray(in4);
            tv.getPictures().put("3", pic3);
            tv.setDescription("Seven noble families fight for control of the mythical land of Westeros. " +
                    "Political and sexual intrigue abound. The primary families are the Stark, Lannister, " +
                    "and Baratheon families. Robert Baratheon, King of Westeros, asks his old friend Eddard Stark to serve as his chief advisor. " +
                    "Eddard, suspecting that his predecessor had been murdered, accepts so that he can investigate further. It turns out more than one " +
                    "family is plotting to take the throne.");
            TvShow tv1 = new TvShow();
            tv1.setTitle("Shameless");
            tv1.setGenre("Drama");
            tv1.setNetwork("Showtime");
            tv1.setOverallRating(8.5);
            tv1.setTrailerUrl("https://www.youtube.com/watch?v=ITsirWLf-W8");
            tv1.setCountry("USA");
            tv1.setReleaseYear(2008);
            InputStream in5 = applicationContext.getResource("classpath:/images/shameless/poster.jpg").getInputStream();
            tv1.getPictures().put("poster", IOUtils.toByteArray(in5));
            InputStream in6 = applicationContext.getResource("classpath:/images/shameless/back.jpg").getInputStream();
            tv1.getPictures().put("back", IOUtils.toByteArray(in6));
            InputStream in7 = applicationContext.getResource("classpath:/images/shameless/1.jpg").getInputStream();
            tv1.getPictures().put("1", IOUtils.toByteArray(in7));
            InputStream in8 = applicationContext.getResource("classpath:/images/shameless/2.jpg").getInputStream();
            tv1.getPictures().put("2", IOUtils.toByteArray(in8));
            InputStream in9 = applicationContext.getResource("classpath:/images/shameless/3.jpg").getInputStream();
            tv1.getPictures().put("3", IOUtils.toByteArray(in9));
            tv1.setDescription(
                    "Based on the UK drama, Shameless, the series follows the exploits of an alcoholic patriarch and his large, blue-collar family.\n" +
                            "Set in working-class Chicago, the Gallagher family, a working class family of eight, must survive the ups and downs of today's recession. " +
                            "With a mother who is out of her element and an alcoholic father who usually ends up passed out on the living room floor, 18-year-old daughter " +
                            "Fiona is left with the task of keeping her five younger brothers and sisters on the straight and narrow. This series is based on the UK series of the same name.");
            TvShow tv2 = new TvShow();
            tv2.setGenre("Drama");
            tv2.setTitle("Prison Break");
            tv2.setNetwork("Fox");
            tv2.setOverallRating(7.7);
            tv2.setTrailerUrl("https://www.youtube.com/watch?v=AL9zLctDJaU");
            tv2.setCountry("USA");
            tv2.setReleaseYear(2004);
            InputStream in10 = applicationContext.getResource("classpath:/images/prison-break/poster.jpg").getInputStream();
            tv2.getPictures().put("poster", IOUtils.toByteArray(in10));
            InputStream in11 = applicationContext.getResource("classpath:/images/prison-break/back.jpg").getInputStream();
            tv2.getPictures().put("back", IOUtils.toByteArray(in11));
            InputStream in12 = applicationContext.getResource("classpath:/images/prison-break/1.jpg").getInputStream();
            tv2.getPictures().put("1", IOUtils.toByteArray(in12));
            InputStream in13 = applicationContext.getResource("classpath:/images/prison-break/2.jpg").getInputStream();
            tv2.getPictures().put("2", IOUtils.toByteArray(in13));
            InputStream in14 = applicationContext.getResource("classpath:/images/prison-break/3.jpg").getInputStream();
            tv2.getPictures().put("3", IOUtils.toByteArray(in14));
            tv2.setDescription(
                    "Due to a political conspiracy, an innocent man is sent to death row and his only hope is his brother, " +
                            "who makes it his mission to deliberately get himself sent to the same prison in order to break the both of them out, " +
                            "from the inside.");
            TvShow tv3 = new TvShow();
            tv3.setGenre("Comedy");
            tv3.setTitle("Dr House");
            tv3.setNetwork("Fox");
            tv3.setOverallRating(9.5);
            tv3.setTrailerUrl("https://www.youtube.com/watch?v=5DIADh4lMq8");
            tv3.setCountry("USA");
            tv3.setReleaseYear(2004);
            InputStream in15 = applicationContext.getResource("classpath:/images/house/poster.jpg").getInputStream();
            tv3.getPictures().put("poster", IOUtils.toByteArray(in15));
            InputStream in16 = applicationContext.getResource("classpath:/images/house/back.jpg").getInputStream();
            tv3.getPictures().put("back", IOUtils.toByteArray(in16));
            InputStream in17 = applicationContext.getResource("classpath:/images/house/1.jpg").getInputStream();
            tv3.getPictures().put("1", IOUtils.toByteArray(in17));
            InputStream in18 = applicationContext.getResource("classpath:/images/house/2.jpg").getInputStream();
            tv3.getPictures().put("2", IOUtils.toByteArray(in18));
            InputStream in19 = applicationContext.getResource("classpath:/images/house/3.jpg").getInputStream();
            tv3.getPictures().put("3", IOUtils.toByteArray(in19));
            tv3.setDescription(
                    "An antisocial maverick doctor who specializes in diagnostic medicine does whatever it takes to solve puzzling cases that  " +
                            "come his way using his crack team of doctors and his wits. ");


            User user = new User();
            user.setName("TestUser");
            user.setEmail("test@email.com");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode("123"));
            InputStream in20 = applicationContext.getResource("classpath:/images/user/user.jpg").getInputStream();
            user.setProfilePic(IOUtils.toByteArray(in15));
            userDAO.saveUser(user);
            tvShowDAO.saveTvShow(tv);
            tvShowDAO.saveTvShow(tv1);
            tvShowDAO.saveTvShow(tv2);
            tvShowDAO.saveTvShow(tv3);
        }

    }
}
