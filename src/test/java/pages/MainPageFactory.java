package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageFactory extends BasePage{

    public MainPageFactory(WebDriver driver) {
        super(driver);
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    @FindBy(className = "home")
    private WebElement home;

    @FindBy(css = ".fa-plus-circle")
    private WebElement plusButton;

    @FindBy(xpath = "//*[text()='New Playlist']")
    private WebElement newPlaylist;

    @FindBy(xpath = "//*[@class='create']/input")
    private WebElement newPlaylistTextbox;

    @FindBy(xpath = "//*[@class='success show']")
    private WebElement createPlaylistSuccess;

// Unable to convert solve this problem
//    @FindBy(xpath = "//*[@href='#!/playlist/"+ playlistId +"']")
//    private WebElement playlistById;

    private WebElement getPlaylistById(String playlistId){
        return driver.findElement(By.xpath("//*[@href='#!/playlist/"+ playlistId +"']"));
    }

    @FindBy(xpath = "//*[@class='playlist playlist editing']/input")
    private WebElement playlistEditField;

    public boolean isMainPage() {
        try {
            return home.isDisplayed();
        } catch (TimeoutException err){
            return false;
        }
    }

    public String createPlaylist(String playlistName) {
        plusButton.click();
        newPlaylist.click();
        newPlaylistTextbox.sendKeys(playlistName);
        newPlaylistTextbox.sendKeys(Keys.ENTER);

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
        actions.doubleClick(playlist).perform();
        playlistEditField.sendKeys(Keys.CONTROL + "A");
        playlistEditField.sendKeys(newName);
        playlistEditField.sendKeys(Keys.ENTER);
    }
}