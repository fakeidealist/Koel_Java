package tests;

import com.github.javafaker.Faker;
import helpers.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPageFactory;
import pages.MainPageFactory;

public class PlaylistPageFactoryTests extends BaseTest{

    @Test
    public void playlistPageFactoryTest_createPlaylist_playlistCreated(){
//        String playlistName = TestDataGenerator1.getRandomString(7);
        Faker faker = new Faker();
        String playlistName = faker.funnyName().name();
        LoginPageFactory loginpage = new LoginPageFactory(driver);
        loginpage.open(url);
        MainPageFactory mainPage = loginpage.loginToApp(username, password);
        String playlistId = mainPage.createPlaylist(playlistName);

        Assert.assertTrue(mainPage.isPlaylistExist(playlistId, playlistName));
    }

    @Test(enabled = true)
    public void playlistPageFactoryTest_renamePlaylist_playlistRenamed(){
        Faker faker = new Faker();
        String playlistName = faker.funnyName().name();

        LoginPageFactory loginpage = new LoginPageFactory(driver);
        loginpage.open(url);
        MainPageFactory mainPage = loginpage.loginToApp(username, password);
        String playlistId = mainPage.createPlaylist(playlistName);

        String newName = faker.ancient().hero();
        mainPage.renamePlaylist(playlistId,newName);

        Assert.assertTrue(mainPage.isPlaylistExist(playlistId, newName));
    }
}