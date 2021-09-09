package tests;

import enums.BrowserType;
import helpers.BrowserFabric;
import helpers.Screenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    protected String username;
    protected String password;
    protected String url;
    protected int errorCount = 0;

    @Parameters({"url", "username", "password", "browser"})
    @BeforeMethod
    public void startUp(String url, String username, String password, String browser){
        BrowserType type = browser.equals("chrome") ? BrowserType.CHROME : BrowserType.FIREFOX;
        driver = BrowserFabric.getDriver(BrowserType.CHROME);
        driver.manage().window().maximize();
        this.username = username;
        this.password = password;
        this.url = url;
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult) throws InterruptedException {
        if(iTestResult.getStatus() == iTestResult.FAILURE) {
            Screenshot.get(driver, iTestResult.getName());
        }
        Thread.sleep(2000);
        driver.quit();
    }
}