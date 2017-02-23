package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.factory.SettingFactory;
import com.crimson.core.factory.UserFactory;
import com.crimson.core.model.Setting;
import com.crimson.core.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback()
public class TestSettingDAO {

    @Autowired
    private SettingsDAO settingsDAO;

    @Autowired
    private UserDAO userDAO;

    private SettingFactory settingFactory = new SettingFactory();
    private UserFactory userFactory = new UserFactory();

    private Setting setting1 = settingFactory.getSetting("setting_1");
    private Setting setting2 = settingFactory.getSetting("setting_2");
    private Setting setting3 = settingFactory.getSetting("setting_3");

    private User user1 = userFactory.getUser("aleks");
    private User user2 = userFactory.getUser("kamil");


    @Before
    public void setDB(){
        settingsDAO.saveSetting(setting1);
        settingsDAO.saveSetting(setting2);
        settingsDAO.saveSetting(setting3);

        userDAO.saveUser(user1);
        userDAO.saveUser(user2);
    }

    @Test
    public void saveTest(){
        Setting setting = Setting.builder()
                .daysOfUpcomingEpisodes(10)
                .episodesFromBegining(true)
                .numberOfEpisodesOnUserPage(25)
                .build();

        settingsDAO.saveSetting(setting);

        Assert.assertEquals(settingsDAO.getAllSettings().contains(setting), true);
    }

    @Test
    public void updateTest(){
        setting1.setDaysOfUpcomingEpisodes(20);

        settingsDAO.updateSetting(setting1);

        Assert.assertEquals(settingsDAO.getSettingById(setting1.getId()).getDaysOfUpcomingEpisodes(), 20);
    }

    @Test
    public void deleteTest(){
        int listSize = settingsDAO.getAllSettings().size();
        settingsDAO.deleteSetting(setting1);

        Assert.assertEquals(listSize-1, settingsDAO.getAllSettings().size());
        Assert.assertEquals(settingsDAO.getAllSettings().contains(setting1), false);
    }

    @Test
    public void getAllSettingsTest(){
        Assert.assertEquals(settingsDAO.getAllSettings().size(), 3);
    }

    @Test
    public void getSettingById(){
        Assert.assertEquals(settingsDAO.getSettingById(setting1.getId()).equals(setting1), true);
    }

    @Test
    public void addUser2SettingTest(){
        settingsDAO.addUser2Setting(user1, setting1);

        Assert.assertEquals(settingsDAO.getSettingById(setting1.getId()).getUser2Setting(), user1);
    }

    @Test
    public void deleteUserFromSettingTest(){
        addUser2SettingTest();

        Assert.assertEquals(settingsDAO.getSettingById(setting1.getId()).getUser2Setting(), user1);

        settingsDAO.deleteUserFromSetting(user1, setting1);

        Assert.assertEquals(settingsDAO.getSettingById(setting1.getId()).getUser2Setting(), null);
    }
}
