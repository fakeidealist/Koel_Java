package tests;

import com.github.javafaker.Faker;
import helpers.DbAdapter;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;

public class ProfileTests extends BaseTest{
    Faker faker;
    @Test
    public void profileTest_renameUser_userRenamed(){
        faker = new Faker();
        String newName = faker.funnyName().name();
        System.out.println("User renamed to: " + newName);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(url);
        MainPage mainPage = loginPage.loginToApp(username, password);
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.renameUserName(newName);
        User userFromDb = DbAdapter.getUserByEmail(username);
        Assert.assertEquals(newName, userFromDb.getName());
    }
}
