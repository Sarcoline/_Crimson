package com.crimson.core.functional.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.SettingsDAO;
import com.crimson.core.dao.UserDAO;
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
    public void setDB() {
        settingsDAO.save(setting1);
        settingsDAO.save(setting2);
        settingsDAO.save(setting3);

        userDAO.save(user1);
        userDAO.save(user2);
    }

    @Test
    public void saveTest() {
        Setting setting = Setting.builder()
                .daysOfUpcomingEpisodes(10)
                .sendEpisodeList(true)
                .numberOfEpisodesOnUserPage(25)
                .build();

        settingsDAO.save(setting);

        Assert.assertEquals(settingsDAO.getAll().contains(setting), true);
    }

    @Test
    public void updateTest() {
        setting1.setDaysOfUpcomingEpisodes(20);

        settingsDAO.update(setting1);

        Assert.assertEquals(settingsDAO.getById(setting1.getId()).getDaysOfUpcomingEpisodes(), 20);
    }

    @Test
    public void deleteTest() {
        int listSize = settingsDAO.getAll().size();
        settingsDAO.delete(setting1);

        Assert.assertEquals(listSize - 1, settingsDAO.getAll().size());
        Assert.assertEquals(settingsDAO.getAll().contains(setting1), false);
    }

    @Test
    public void getAllSettingsTest() {
        Assert.assertEquals(settingsDAO.getAll().size(), 3);
    }

    @Test
    public void getSettingById() {
        Assert.assertEquals(settingsDAO.getById(setting1.getId()).equals(setting1), true);
    }
}
