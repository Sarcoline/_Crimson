package com.crimson;

import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.Role;
import com.crimson.core.model.Setting;
import com.crimson.core.model.TvShow;
import com.crimson.core.service.RoleService;
import com.crimson.core.service.TvShowService;
import com.crimson.core.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PopulateDatabase {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TvShowService tvShowDAO;

    @Autowired
    private UserService userDAO;

    @Autowired
    private RoleService roleService;

    @EventListener(ContextRefreshedEvent.class)
    public void populate() throws IOException, ParseException {
        if (tvShowDAO.getAllTvShows().size() < 1) {
            TvShow tv = new TvShow();


            tv.setTitle("Game Of Thrones");
            tv.setNetwork("HBO");
            tv.setOverallRating(10d);
            tv.setGenre("Fantasy");
            tv.setTrailerUrl("https://www.youtube.com/watch?v=EI0ib1NErqg");
            tv.setCountry("USA");
            tv.setReleaseYear(2010);

            LocalDate date = LocalDate.now().plusDays(5);
            LocalDate date1 = LocalDate.now().plusDays(10);

            List<Episode> GameOfThronesEpisodes = new ArrayList();
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 1").number(1).releaseDate(LocalDate.now()).season(1).idTvShow(1L).episodeSummary("Summary 1").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 2").number(2).releaseDate(LocalDate.now()).season(1).idTvShow(1L).episodeSummary("Summary 2").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 3").number(3).releaseDate(LocalDate.now()).season(1).idTvShow(1L).episodeSummary("Summary 3").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 4").number(4).releaseDate(LocalDate.now()).season(1).idTvShow(1L).episodeSummary("Summary 4").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 5").number(5).releaseDate(LocalDate.now()).season(1).idTvShow(1L).episodeSummary("Summary 5").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 1").number(1).releaseDate(LocalDate.now()).season(2).idTvShow(1L).episodeSummary("Summary 1").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 2").number(2).releaseDate(LocalDate.now()).season(2).idTvShow(1L).episodeSummary("Summary 2").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 3").number(3).releaseDate(date).season(2).idTvShow(1L).episodeSummary("Summary 3").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 4").number(4).releaseDate(date).season(2).idTvShow(1L).episodeSummary("Summary 4").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 5").number(5).releaseDate(date).season(2).idTvShow(1L).episodeSummary("Summary 5").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 1").number(1).releaseDate(date).season(3).idTvShow(1L).episodeSummary("Summary 3").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 2").number(2).releaseDate(date1).season(3).idTvShow(1L).episodeSummary("Summary 4").build());
            GameOfThronesEpisodes.add(Episode.builder().title("Episode 3").number(3).releaseDate(date1).season(3).idTvShow(1L).episodeSummary("Summary 5").build());
            tv.setEpisodes(GameOfThronesEpisodes);
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
            tv1.setOverallRating(9d);
            tv1.setTrailerUrl("https://www.youtube.com/watch?v=ITsirWLf-W8");
            tv1.setCountry("USA");
            tv1.setReleaseYear(2008);


            List<Episode> ShamelessEpisodes = new ArrayList();
            ShamelessEpisodes.add(Episode.builder().title("Episode 1").number(1).releaseDate(LocalDate.now()).season(1).idTvShow(2L).episodeSummary("Summary 1").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 2").number(2).releaseDate(LocalDate.now()).season(1).idTvShow(2L).episodeSummary("Summary 2").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 3").number(3).releaseDate(LocalDate.now()).season(1).idTvShow(2L).episodeSummary("Summary 3").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 4").number(4).releaseDate(LocalDate.now()).season(1).idTvShow(2L).episodeSummary("Summary 4").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 5").number(5).releaseDate(date).season(1).idTvShow(2L).episodeSummary("Summary 5").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 1").number(1).releaseDate(date).season(2).idTvShow(2L).episodeSummary("Summary 1").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 2").number(2).releaseDate(date).season(2).idTvShow(2L).episodeSummary("Summary 2").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 3").number(3).releaseDate(date).season(2).idTvShow(2L).episodeSummary("Summary 3").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 4").number(4).releaseDate(date1).season(2).idTvShow(2L).episodeSummary("Summary 4").build());
            ShamelessEpisodes.add(Episode.builder().title("Episode 5").number(5).releaseDate(date1).season(2).idTvShow(2L).episodeSummary("Summary 5").build());
            tv1.setEpisodes(ShamelessEpisodes);
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
            tv2.setOverallRating(7d);
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
            tv3.setTitle("House M.D.");
            tv3.setNetwork("Fox");
            tv3.setOverallRating(1d);
            tv3.setTrailerUrl("https://www.youtube.com/watch?v=5DIADh4lMq8");
            tv3.setCountry("USA");
            tv3.setReleaseYear(2004);
            tv3.setFinishYear(2012);
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

            TvShow tv4 = new TvShow();
            tv4.setGenre("Comedy");
            tv4.setTitle("Friends");
            tv4.setNetwork("NBC");
            tv4.setOverallRating(3d);
            tv4.setTrailerUrl("https://www.youtube.com/watch?v=hDNNmeeJs1Q");
            tv4.setCountry("USA");
            tv4.setReleaseYear(1994);
            tv4.setFinishYear(2004);
            InputStream in20 = applicationContext.getResource("classpath:/images/friends/poster.jpg").getInputStream();
            tv4.getPictures().put("poster", IOUtils.toByteArray(in20));
            InputStream in21 = applicationContext.getResource("classpath:/images/friends/back.jpg").getInputStream();
            tv4.getPictures().put("back", IOUtils.toByteArray(in21));
            InputStream in22 = applicationContext.getResource("classpath:/images/friends/1.jpg").getInputStream();
            tv4.getPictures().put("1", IOUtils.toByteArray(in22));
            InputStream in23 = applicationContext.getResource("classpath:/images/friends/2.jpg").getInputStream();
            tv4.getPictures().put("2", IOUtils.toByteArray(in23));
            InputStream in24 = applicationContext.getResource("classpath:/images/friends/3.jpg").getInputStream();
            tv4.getPictures().put("3", IOUtils.toByteArray(in24));
            tv4.setDescription(
                    "Rachel Green, Ross Geller, Monica Geller, Joey Tribbiani, Chandler Bing and Phoebe Buffay are all friends,  " +
                            "living off of one another in the heart of New York City. Over the course of ten years, this average group of buddies goes through massive mayhem, " +
                            "family trouble, past and future romances, fights, laughs, tears and surprises as they learn what it really means to be a friend.");

            TvShow tv5 = new TvShow();
            tv5.setGenre("Drama");
            tv5.setTitle("Belfer");
            tv5.setNetwork("Canal+");
            tv5.setOverallRating(5d);
            tv5.setTrailerUrl("https://www.youtube.com/watch?v=QJBo2ZAtWBA");
            tv5.setCountry("Poland");
            tv5.setReleaseYear(2016);
            InputStream in25 = applicationContext.getResource("classpath:/images/belfer/poster.jpg").getInputStream();
            tv5.getPictures().put("poster", IOUtils.toByteArray(in25));
            InputStream in26 = applicationContext.getResource("classpath:/images/belfer/back.jpg").getInputStream();
            tv5.getPictures().put("back", IOUtils.toByteArray(in26));
            InputStream in27 = applicationContext.getResource("classpath:/images/belfer/1.jpg").getInputStream();
            tv5.getPictures().put("1", IOUtils.toByteArray(in27));
            InputStream in28 = applicationContext.getResource("classpath:/images/belfer/2.jpg").getInputStream();
            tv5.getPictures().put("2", IOUtils.toByteArray(in28));
            InputStream in29 = applicationContext.getResource("classpath:/images/belfer/3.jpg").getInputStream();
            tv5.getPictures().put("3", IOUtils.toByteArray(in29));
            tv5.setDescription(
                    "The death of a young girl. To town comes a new teacher who begins his own investigation.  " +
                            "Until the end no one knows who really is a murderer. ");

            TvShow tv6 = new TvShow();
            tv6.setGenre("Drama");
            tv6.setTitle("Sherlock");
            tv6.setNetwork("BBC One");
            tv6.setOverallRating(5d);
            tv6.setTrailerUrl("https://www.youtube.com/watch?v=38_c6dh6Y6M");
            tv6.setCountry("UK");
            tv6.setReleaseYear(2010);
            InputStream in30 = applicationContext.getResource("classpath:/images/sherlock/poster.jpg").getInputStream();
            tv6.getPictures().put("poster", IOUtils.toByteArray(in30));
            InputStream in31 = applicationContext.getResource("classpath:/images/sherlock/back.jpg").getInputStream();
            tv6.getPictures().put("back", IOUtils.toByteArray(in31));
            InputStream in32 = applicationContext.getResource("classpath:/images/sherlock/1.jpg").getInputStream();
            tv6.getPictures().put("1", IOUtils.toByteArray(in32));
            InputStream in33 = applicationContext.getResource("classpath:/images/sherlock/2.jpg").getInputStream();
            tv6.getPictures().put("2", IOUtils.toByteArray(in33));
            InputStream in34 = applicationContext.getResource("classpath:/images/sherlock/3.jpg").getInputStream();
            tv6.getPictures().put("3", IOUtils.toByteArray(in34));
            tv6.setDescription("In this modernized version of the Conan Doyle characters, using his detective plots, " +
                    "Sherlock Holmes lives in early 21st century London and acts more cocky towards Scotland Yard's " +
                    "detective inspector Lestrade because he's actually less confident. Doctor Watson is now a fairly" +
                    " young veteran of the Afghan war, less adoring and more active.");

            TvShow tv7 = new TvShow();
            tv7.setGenre("Drama");
            tv7.setTitle("Call the midwife");
            tv7.setNetwork("BBC One");
            tv7.setOverallRating(5d);
            tv7.setTrailerUrl("https://www.youtube.com/watch?v=aVfdZevxf_o");
            tv7.setCountry("UK");
            tv7.setReleaseYear(2012);
            InputStream in35 = applicationContext.getResource("classpath:/images/midwife/poster.jpg").getInputStream();
            tv7.getPictures().put("poster", IOUtils.toByteArray(in35));
            InputStream in36 = applicationContext.getResource("classpath:/images/midwife/back1.jpg").getInputStream();
            tv7.getPictures().put("back", IOUtils.toByteArray(in36));
            InputStream in37 = applicationContext.getResource("classpath:/images/midwife/1.jpg").getInputStream();
            tv7.getPictures().put("1", IOUtils.toByteArray(in37));
            InputStream in38 = applicationContext.getResource("classpath:/images/midwife/2.jpg").getInputStream();
            tv7.getPictures().put("2", IOUtils.toByteArray(in38));
            InputStream in39 = applicationContext.getResource("classpath:/images/midwife/3.jpg").getInputStream();
            tv7.getPictures().put("3", IOUtils.toByteArray(in39));
            tv7.setDescription("Based on the memoirs of Jennifer Worth; the story follows twenty-two year old Jenny," +
                    " who in 1957 leaves her comfortable home to become a midwife in London's East End. She is" +
                    " surprised to find that she will be living in a convent: Nonnatus House. Working alongside " +
                    "fellow nurses and the medically-trained nuns, Jenny has her eyes opened to the harsh living " +
                    "conditions of the slums, but she also discovers the warm hearts and the bravery of the mothers. " +
                    "Even after Jenny leaves Nonnatus, she continues to chronicle the lives of the midwives who have" +
                    "become her family.");


            TvShow tv8 = new TvShow();
            tv8.setGenre("Drama");
            tv8.setTitle("Breaking Bad");
            tv8.setNetwork("AMC");
            tv8.setOverallRating(5d);
            tv8.setTrailerUrl("https://www.youtube.com/watch?v=HhesaQXLuRY");
            tv8.setCountry("USA");
            tv8.setReleaseYear(2008);
            tv8.setFinishYear(2013);
            InputStream in40 = applicationContext.getResource("classpath:/images/breakingBad/poster.jpg").getInputStream();
            tv8.getPictures().put("poster", IOUtils.toByteArray(in40));
            InputStream in41 = applicationContext.getResource("classpath:/images/breakingBad/back.jpg").getInputStream();
            tv8.getPictures().put("back", IOUtils.toByteArray(in41));
            InputStream in42 = applicationContext.getResource("classpath:/images/breakingBad/1.jpg").getInputStream();
            tv8.getPictures().put("1", IOUtils.toByteArray(in42));
            InputStream in43 = applicationContext.getResource("classpath:/images/breakingBad/2.jpg").getInputStream();
            tv8.getPictures().put("2", IOUtils.toByteArray(in43));
            InputStream in44 = applicationContext.getResource("classpath:/images/breakingBad/3.jpg").getInputStream();
            tv8.getPictures().put("3", IOUtils.toByteArray(in44));
            tv8.setDescription("When chemistry teacher Walter White is diagnosed with Stage III cancer and given only two years to live, " +
                    "he decides he has nothing to lose. He lives with his teenage son, who has cerebral palsy, and his wife, in New Mexico. " +
                    "Determined to ensure that his family will have a secure future, Walt embarks on a career of drugs and crime. " +
                    "He proves to be remarkably proficient in this new world as he begins manufacturing and selling methamphetamine with one of his former students. " +
                    "The series tracks the impacts of a fatal diagnosis on a regular, hard working man, and explores how a " +
                    "fatal diagnosis affects his morality and transforms him into a major player of the drug trade.");


            TvShow tv9 = new TvShow();
            tv9.setGenre("Adventure");
            tv9.setTitle("Lost");
            tv9.setNetwork("ABC");
            tv9.setOverallRating(5d);
            tv9.setTrailerUrl("https://www.youtube.com/watch?v=KTu8iDynwNc");
            tv9.setCountry("USA");
            tv9.setReleaseYear(2004);
            tv9.setFinishYear(2010);
            InputStream in45 = applicationContext.getResource("classpath:/images/lost/poster.jpg").getInputStream();
            tv9.getPictures().put("poster", IOUtils.toByteArray(in45));
            InputStream in46 = applicationContext.getResource("classpath:/images/lost/back.jpg").getInputStream();
            tv9.getPictures().put("back", IOUtils.toByteArray(in46));
            InputStream in47 = applicationContext.getResource("classpath:/images/lost/1.jpg").getInputStream();
            tv9.getPictures().put("1", IOUtils.toByteArray(in47));
            InputStream in48 = applicationContext.getResource("classpath:/images/lost/2.jpg").getInputStream();
            tv9.getPictures().put("2", IOUtils.toByteArray(in48));
            InputStream in49 = applicationContext.getResource("classpath:/images/lost/3.jpg").getInputStream();
            tv9.getPictures().put("3", IOUtils.toByteArray(in49));
            tv9.setDescription("Life is laid bare as a group of plane crash survivors find themselves stranded on a remote Pacific island. " +
                    "The trauma of the crash soon becomes overshadowed by the island itself, " +
                    "where unseen creatures stalk the jungle, paranormal happenings abound and astonishing coincidences reveal themselves. " +
                    "In this unique environment emotions swell as the survivors battle their inner and outer demons, " +
                    "and strive to live together - so that they won't die alone.");


            TvShow tv10 = new TvShow();
            tv10.setGenre("Crime");
            tv10.setTitle("Fargo");
            tv10.setNetwork("FX");
            tv10.setOverallRating(5d);
            tv10.setTrailerUrl("https://www.youtube.com/watch?v=gKs8DzjPDMU");
            tv10.setCountry("USA");
            tv10.setReleaseYear(2014);
            InputStream in50 = applicationContext.getResource("classpath:/images/fargo/poster.jpg").getInputStream();
            tv10.getPictures().put("poster", IOUtils.toByteArray(in50));
            InputStream in51 = applicationContext.getResource("classpath:/images/fargo/back.jpg").getInputStream();
            tv10.getPictures().put("back", IOUtils.toByteArray(in51));
            InputStream in52 = applicationContext.getResource("classpath:/images/fargo/1.jpg").getInputStream();
            tv10.getPictures().put("1", IOUtils.toByteArray(in52));
            InputStream in53 = applicationContext.getResource("classpath:/images/fargo/2.jpg").getInputStream();
            tv10.getPictures().put("2", IOUtils.toByteArray(in53));
            InputStream in54 = applicationContext.getResource("classpath:/images/fargo/3.jpg").getInputStream();
            tv10.getPictures().put("3", IOUtils.toByteArray(in54));
            tv10.setDescription("Various chronicles of deception, intrigue and murder in and around frozen Minnesota." +
                    " Yet all of these tales mysteriously lead back one way or another to Fargo, ND.");


            TvShow tv11 = new TvShow();
            tv11.setGenre("Horror");
            tv11.setTitle("Stranger Things");
            tv11.setNetwork("Netflix");
            tv11.setOverallRating(5d);
            tv11.setTrailerUrl("https://www.youtube.com/watch?v=gKs8DzjPDMU");
            tv11.setCountry("USA");
            tv11.setReleaseYear(2016);
            InputStream in55 = applicationContext.getResource("classpath:/images/stranger-things/poster.jpg").getInputStream();
            tv11.getPictures().put("poster", IOUtils.toByteArray(in55));
            InputStream in56 = applicationContext.getResource("classpath:/images/stranger-things/back.jpg").getInputStream();
            tv11.getPictures().put("back", IOUtils.toByteArray(in56));
            InputStream in57 = applicationContext.getResource("classpath:/images/stranger-things/1.jpg").getInputStream();
            tv11.getPictures().put("1", IOUtils.toByteArray(in57));
            InputStream in58 = applicationContext.getResource("classpath:/images/stranger-things/2.jpg").getInputStream();
            tv11.getPictures().put("2", IOUtils.toByteArray(in58));
            InputStream in59 = applicationContext.getResource("classpath:/images/stranger-things/3.jpg").getInputStream();
            tv11.getPictures().put("3", IOUtils.toByteArray(in59));
            tv11.setDescription("In a small town where everyone knows everyone, a peculiar incident starts a chain of events that" +
                    " leads to the disappearance of a child - which begins to tear at the fabric of an otherwise peaceful " +
                    "community. Dark government agencies and seemingly malevolent supernatural forces converge on the town " +
                    "while a few locals begin to understand that there's more going on than meets the eye.");


            TvShow tv12 = new TvShow();
            tv12.setGenre("Drama");
            tv12.setTitle("Taboo");
            tv12.setNetwork("FX");
            tv12.setOverallRating(5d);
            tv12.setTrailerUrl("https://www.youtube.com/watch?v=6ZYAQSlIhM4");
            tv12.setCountry("USA");
            tv12.setReleaseYear(2016);
            InputStream in60 = applicationContext.getResource("classpath:/images/taboo/poster.jpg").getInputStream();
            tv12.getPictures().put("poster", IOUtils.toByteArray(in60));
            InputStream in61 = applicationContext.getResource("classpath:/images/taboo/back.jpg").getInputStream();
            tv12.getPictures().put("back", IOUtils.toByteArray(in61));
            InputStream in62 = applicationContext.getResource("classpath:/images/taboo/1.jpg").getInputStream();
            tv12.getPictures().put("1", IOUtils.toByteArray(in62));
            InputStream in63 = applicationContext.getResource("classpath:/images/taboo/2.jpg").getInputStream();
            tv12.getPictures().put("2", IOUtils.toByteArray(in63));
            InputStream in64 = applicationContext.getResource("classpath:/images/taboo/3.jpg").getInputStream();
            tv12.getPictures().put("3", IOUtils.toByteArray(in64));
            tv12.setDescription("James Keziah Delaney returns to 1814 London after 10 years in Africa to discover that" +
                    " he has been left a mysterious legacy by his father. Driven to wage war on those who have wronged" +
                    " him, Delaney finds himself in a face-off against the East India Company, whilst playing a " +
                    "dangerous game between two warring nations, Britain and America.");


            TvShow tv13 = new TvShow();
            tv13.setGenre("Crime");
            tv13.setTitle("True Detective");
            tv13.setNetwork("HBO");
            tv13.setOverallRating(5d);
            tv13.setTrailerUrl("https://www.youtube.com/watch?v=mXG1netn9_g");
            tv13.setCountry("USA");
            tv13.setReleaseYear(2014);
            tv13.setForAdult(true);
            InputStream in65 = applicationContext.getResource("classpath:/images/true-detective/poster.jpg").getInputStream();
            tv13.getPictures().put("poster", IOUtils.toByteArray(in65));
            InputStream in66 = applicationContext.getResource("classpath:/images/true-detective/back.jpg").getInputStream();
            tv13.getPictures().put("back", IOUtils.toByteArray(in66));
            InputStream in67 = applicationContext.getResource("classpath:/images/true-detective/1.jpg").getInputStream();
            tv13.getPictures().put("1", IOUtils.toByteArray(in67));
            InputStream in68 = applicationContext.getResource("classpath:/images/true-detective/2.jpg").getInputStream();
            tv13.getPictures().put("2", IOUtils.toByteArray(in68));
            InputStream in69 = applicationContext.getResource("classpath:/images/true-detective/3.jpg").getInputStream();
            tv13.getPictures().put("3", IOUtils.toByteArray(in69));
            tv13.setDescription("In 2012, Louisiana State Police Detectives Rust Cohle and Martin Hart are brought in " +
                    "to revisit a homicide case they worked in 1995. As the inquiry unfolds in present day through " +
                    "separate interrogations, the two former detectives narrate the story of their investigation, " +
                    "reopening unhealed wounds, and drawing into question their supposed solving of a bizarre " +
                    "ritualistic murder in 1995. The timelines braid and converge in 2012 as each man is pulled back " +
                    "into a world they believed they'd left behind. In learning about each other and their killer, " +
                    "it becomes clear that darkness lives on both sides of the law.");

            TvShow tv14 = new TvShow();
            tv14.setGenre("SciFi");
            tv14.setTitle("Westworld");
            tv14.setNetwork("HBO");
            tv14.setOverallRating(5d);
            tv14.setTrailerUrl("https://www.youtube.com/watch?v=IuS5huqOND4");
            tv14.setCountry("USA");
            tv14.setReleaseYear(2016);
            InputStream in70 = applicationContext.getResource("classpath:/images/westworld/poster.jpg").getInputStream();
            tv14.getPictures().put("poster", IOUtils.toByteArray(in70));
            InputStream in71 = applicationContext.getResource("classpath:/images/westworld/back.jpg").getInputStream();
            tv14.getPictures().put("back", IOUtils.toByteArray(in71));
            InputStream in72 = applicationContext.getResource("classpath:/images/westworld/1.jpg").getInputStream();
            tv14.getPictures().put("1", IOUtils.toByteArray(in72));
            InputStream in73 = applicationContext.getResource("classpath:/images/westworld/2.jpg").getInputStream();
            tv14.getPictures().put("2", IOUtils.toByteArray(in73));
            InputStream in74 = applicationContext.getResource("classpath:/images/westworld/3.jpg").getInputStream();
            tv14.getPictures().put("3", IOUtils.toByteArray(in74));
            tv14.setDescription("Westworld isn't your typical amusement park. Intended for rich vacationers, the" +
                    " futuristic park -- which is looked after by robotic \"hosts\" -- allows its visitors to " +
                    "live out their fantasies through artificial consciousness. No matter how illicit the fantasy " +
                    "may be, there are no consequences for the park's guests, allowing for any wish to be indulged.");

            Role role = new Role("USER");
            Role role1 = new Role("ADMIN");
            roleService.saveRole(role);
            roleService.saveRole(role1);
            UserDTO user = new UserDTO();
            user.setName("TestUser");
            user.setEmail("kissakot@gmail.com");
            user.setPassword("123");
            user.setActive(true);
            user.getRoles().add(roleService.getAllRoles().get(1));
            user.setSetting(new Setting(true, 10, 7));
            userDAO.saveUser(user);
            tvShowDAO.saveTvShow(tv);
            tvShowDAO.saveTvShow(tv1);
            tvShowDAO.saveTvShow(tv2);
            tvShowDAO.saveTvShow(tv3);
            tvShowDAO.saveTvShow(tv4);
            tvShowDAO.saveTvShow(tv5);
            tvShowDAO.saveTvShow(tv6);
            tvShowDAO.saveTvShow(tv7);
            tvShowDAO.saveTvShow(tv8);
            tvShowDAO.saveTvShow(tv9);
            tvShowDAO.saveTvShow(tv10);
            tvShowDAO.saveTvShow(tv11);
            tvShowDAO.saveTvShow(tv12);
            tvShowDAO.saveTvShow(tv13);
            tvShowDAO.saveTvShow(tv14);
        }
    }
}
