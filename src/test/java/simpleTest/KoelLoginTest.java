package simpleTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class KoelLoginTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private FluentWait<WebDriver> fluentWait;

    @BeforeMethod
    public void startUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
        fluentWait = new FluentWait<WebDriver>(driver)
                .pollingEvery(Duration.ofMillis(200))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class);
        driver.get("http://koelapp.testpro.io");

    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void loginToKoel_correctCredentials_loggedtoApp() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='email']")));
        WebElement email = driver.findElement(By.cssSelector("[type='email']"));
        WebElement password = driver.findElement(By.cssSelector("[type='password']"));
        WebElement button = driver.findElement(By.tagName("button"));
        email.sendKeys("testerjo168@gmail.com");
        password.sendKeys("te$t$tudent");
        button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".home")));
        Assert.assertTrue(driver.findElement(By.className("home")).isDisplayed());
    }
    @Test
    public void loginToKoel_wrongCredentials_errorFrame() {


        fluentWait.until((x->x.findElement(By.cssSelector("[type='email']")).isDisplayed()));
        WebElement email = driver.findElement(By.cssSelector("[type='email']"));
        WebElement password = driver.findElement(By.cssSelector("[type='password']"));
        WebElement button = driver.findElement(By.tagName("button"));
        email.sendKeys("testerjo168@gmail.com");
        password.sendKeys("wrongpassword");
        button.click();
        fluentWait.until((x->x.findElement(By.cssSelector(".error")).isDisplayed()));
        Assert.assertTrue(driver.findElement(By.className("error")).isDisplayed());
    }
}