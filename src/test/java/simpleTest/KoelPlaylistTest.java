package simpleTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import helpers.TestDataGenerator;

import java.time.Duration;


public class KoelPlaylistTest {
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
    public void createPlaylist_playlistCreated() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='email']")));
        WebElement email = driver.findElement(By.cssSelector("[type='email']"));
        WebElement password = driver.findElement(By.cssSelector("[type='password']"));
        WebElement button = driver.findElement(By.tagName("button"));
        email.sendKeys("testerjo168@gmail.com");
        password.sendKeys("te$t$tudent");
        button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'fa-plus-circle')]")));
        WebElement plusButton = driver.findElement(By.xpath("//*[contains(@class,'fa-plus-circle')]"));
        wait.until(ExpectedConditions.elementToBeClickable(plusButton));
        plusButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='↵ to save']")));
        WebElement createPlaylistTextbox = driver.findElement(By.xpath("//*[@placeholder='↵ to save']"));
        String playlistName = TestDataGenerator.getRandomString(7);
        createPlaylistTextbox.sendKeys(playlistName);
        createPlaylistTextbox.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='success show']")));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@class='success show']")).isDisplayed());
    }
}

//<div class="success show">Added 1 song into "p".</div>
//<div class="success show">Created playlist "fdsawde".</div>
//*[contains(text(),'Created playlist']