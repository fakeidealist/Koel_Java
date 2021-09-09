package tests;

import com.github.javafaker.Faker;
import helpers.Token;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreatePlaylistRequest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;

import static io.restassured.RestAssured.given;

public class HybridApiPlusSelenium extends BaseTest {
    private String token;
    private String playlistName;
    private int playlistId;
    Faker faker;
    @BeforeMethod
    public void startUp(){
        faker = new Faker();
        token = Token.getToken();
        playlistName = faker.funnyName().name();
        System.out.println("Playlist name: " + playlistName + " created.");
        CreatePlaylistRequest playlistRequest = new CreatePlaylistRequest(playlistName);
        Response response =  given()
                .baseUri("https://bbb.testpro.io/")
                .basePath("api/playlist")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(playlistRequest)
                .when()
                .post()// can change here to POST, PUT, DELETE, or PATCH
                .then()
                .statusCode(200) // We assert status code is 200
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        playlistId = jsonPath.getInt("id");
    }
    @AfterMethod
    public void cleanUp(){
        given()
                .baseUri("https://bbb.testpro.io/")
                .basePath("api/playlist/"+playlistId)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .delete();
    }
    @Test
    public void renamePlaylist_playlistRenamed(){
        String newName = faker.dragonBall().character();
        System.out.println("Playlist renamed to: " + newName);
        LoginPage loginpage = new LoginPage(driver);
        loginpage.open(url);
        MainPage mainPage = loginpage.loginToApp(username, password);
        mainPage.renamePlaylist(playlistId+"", newName); //Adding empty string would convert it into string
        Assert.assertTrue(mainPage.isPlaylistExist(playlistId+"",newName));
    }
}