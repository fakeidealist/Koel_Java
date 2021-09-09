package tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPageFactory;
import pages.MainPageFactory;

public class LoginPageFactoryTests extends BaseTest{
    @Test
    public void loginPageFactoryTest_LoginWithCorrectCredetials_mainPageOpened(){
        LoginPageFactory loginpage = new pages.LoginPageFactory(driver);
        loginpage.open(url);
        MainPageFactory mainPage = loginpage.loginToApp(username, password);
        Assert.assertTrue(mainPage.isMainPage());
    }

    @Test
    public void loginPageFactoryTest_loginWithIncorrectCredentials_errorFrame(){
        LoginPageFactory loginpage = new LoginPageFactory(driver);
        loginpage.open(url);
        loginpage.loginToApp(username, "wrongPW");
        Assert.assertTrue(loginpage.isErrorFrame());
    }
}