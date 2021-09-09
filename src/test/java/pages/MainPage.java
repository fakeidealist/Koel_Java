package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

public class MainPage extends BasePage{
    private static Logger logger = LogManager.getLogger(MainPage.class);

    public MainPage(WebDriver driver) {
        super(driver);
    }

    private WebElement getHomeButton(){
        By homeBy = By.className("home");
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeBy));
        return driver.findElement(homeBy);
    }

    private void clickPlusButton(){
        logger.debug("I'm at click plusButton method");

        By plusButtonBy = By.cssSelector(".fa-plus-circle");

        logger.info("By -> Created by css selector .fa-plus-circle");
        wait.until(ExpectedConditions.elementToBeClickable(plusButtonBy));

        logger.info("Wait until element become clickable");
        for (int i = 0; i < 5 ; i++){
            try {
                logger.warn("In the try block");
                driver.findElement(plusButtonBy).click();
                return;
            } catch (ElementClickInterceptedException ignored){
                logger.error("In the catch block");
            }
        }
    }

    private WebElement getNewPlaylist(){
        By newPlaylistBy = By.xpath("//*[text()='New Playlist']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newPlaylistBy));
        return driver.findElement(newPlaylistBy);
    }

    private WebElement getNewSmartPlaylist(){
        By newSmartPlaylistBy = By.xpath("//*[text()='New Smart Playlist']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newSmartPlaylistBy));
        return driver.findElement(newSmartPlaylistBy);
    }

    private WebElement getNewPlaylistTextbox(){
        By newPlaylistTextboxBy = By.xpath("//*[@class='create']/input");
        return driver.findElement(newPlaylistTextboxBy);
    }

    private WebElement getNewSmartPlaylistTextbox(){
        By newSmartPlaylistTextboxBy = By.xpath("//*[@class='form-row']/input");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newSmartPlaylistTextboxBy));
        return driver.findElement(newSmartPlaylistTextboxBy);
    }
    //    This is to click right-click to get to Edit button
    private WebElement getPlaylistEditButton(String playlistId){
        By getPlaylistEditButtonBy = (By.xpath("//*[@href='#!/playlist/" + playlistId + "']//following-sibling::nav//child::ul//child::li"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getPlaylistEditButtonBy));
        return driver.findElement(getPlaylistEditButtonBy);
    }

    private WebElement getGroupButton(){
        By groupButtonBy = By.xpath("//*[text()=' GROUP']");
        return driver.findElement(groupButtonBy);
    }

    private WebElement getRuleButton(){
        By ruleButtonBy = By.xpath("//*[text()=' RULE']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ruleButtonBy));
        return driver.findElement(ruleButtonBy);
    }

    private WebElement getFirstDropdownOption(){
        By firstDropdownBy = By.xpath("//*[@class='row']/select[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstDropdownBy));
        return driver.findElement(firstDropdownBy);
    }

    private WebElement getLengthOption(){
        By lengthOptionBy = By.xpath("//*[text()='Length']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(lengthOptionBy));
        return driver.findElement(lengthOptionBy);
    }

    private WebElement getSecondDropdownOption(){
        By secondDropdownOptionBy = By.xpath("//*[@class='row']/select[2]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(secondDropdownOptionBy));
        return driver.findElement(secondDropdownOptionBy);
    }

    private WebElement getIsGreaterThanOption(){
        By isGreaterThanOptionBy = By.xpath("//*[text()='is greater than']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(isGreaterThanOptionBy));
        return driver.findElement(isGreaterThanOptionBy);
    }

    private WebElement getIsGreaterThanTextbox(){
        By isGreaterThanTextboxBy = By.xpath("//*[@class='row']/input[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(isGreaterThanTextboxBy));
        return driver.findElement(isGreaterThanTextboxBy);
    }

    private WebElement clickNewSmartPlaylistSaveButton(){
        By newSmartPlaylistSaveButtonBy = By.xpath("//*[text()='Save']");
        return driver.findElement(newSmartPlaylistSaveButtonBy);
    }

    private WebElement getCreatePlaylistSuccess(){
        By createPlaylistSuccessBy = By.xpath("//*[@class='success show']");
        return driver.findElement(createPlaylistSuccessBy);
    }

    private WebElement getPlaylistById(String playlistId){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@href='#!/playlist/"+ playlistId +"']")));
        return driver.findElement(By.xpath("//*[@href='#!/playlist/"+ playlistId +"']"));
    }

    private WebElement getPlaylistEditField(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='playlist playlist editing']/input")));
        return driver.findElement(By.xpath("//*[@class='playlist playlist editing']/input"));
    }

    public boolean isMainPage() {
        try {
            return getHomeButton().isDisplayed();
        } catch (TimeoutException err){
            return false;
        }
    }

    public String createPlaylist(String playlistName) {
        clickPlusButton();
        getNewPlaylist().click();
        getNewPlaylistTextbox().sendKeys(playlistName);
        getNewPlaylistTextbox().sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='success show']")));
        return driver.getCurrentUrl().split("/")[5];
    }

    public String createSmartPlaylist(String smartPlaylistName, int songLengthGreaterThan) {
        clickPlusButton();
        getNewSmartPlaylist().click();
        getNewSmartPlaylistTextbox().sendKeys(smartPlaylistName);
        getGroupButton().click();
        getRuleButton().click();
        getFirstDropdownOption().click();
        getLengthOption().click();
        getSecondDropdownOption().click();
        getIsGreaterThanOption().click();
        getIsGreaterThanTextbox().sendKeys(String.valueOf(songLengthGreaterThan));
        clickNewSmartPlaylistSaveButton().click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='success show']")));
        return driver.getCurrentUrl().split("/")[5];
    }

    public boolean isPlaylistExist(String playlistId, String playlistName) {
        try {
            return getPlaylistById(playlistId).getText().equals(playlistName);
        } catch (NoSuchElementException xx){
            return false;
        }
    }

    public void renamePlaylist(String playlistId, String newName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement playlist = getPlaylistById(playlistId);
        js.executeScript("arguments[0].scrollIntoView();", playlist);
        Actions actions = new Actions(driver);
        actions.contextClick(playlist).perform();
        getPlaylistEditButton(playlistId).click();
//        actions.doubleClick(playlist).perform();
        getPlaylistEditField().sendKeys(Keys.CONTROL + "A");
        getPlaylistEditField().sendKeys(newName);
        getPlaylistEditField().sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@href='#!/playlist/"+ playlistId +"']")));
    }
}