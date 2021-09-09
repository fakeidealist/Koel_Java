package api;

import com.github.javafaker.Faker;
import helpers.Token;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreatePlaylistRequest;
import models.PlaylistResponse;
import models.RenamePlaylistRequest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestKoel {
    private int playlistId;
    private String playlistName;
    private String token;
    private String updatedPlaylistName;
    @BeforeMethod
    public void createPlaylist(){
        Faker faker = new Faker();
        token = Token.getToken();
        playlistName = faker.funnyName().name();
        System.out.println("Before Method: Playlist name " + playlistName + " created.");
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
    @AfterMethod (enabled = true)
    public void deletePlaylistAfterTest(){
        Response response =  given()
                .baseUri("https://bbb.testpro.io/")
                .basePath("api/playlist/"+playlistId)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .delete()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
    }
    @Test (enabled = true)
    public void getPlaylist(){
        Response response = given()
                .baseUri("https://bbb.testpro.io/")
                .basePath("api/playlist")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        //Below will deserialize the array
        PlaylistResponse[] playlists = jsonPath.getObject("$",PlaylistResponse[].class);
        System.out.println("Get: " + playlists.length + " playlist(s)");
        //Below will find the exact playlist in the array
        for (PlaylistResponse pl : playlists){
            if (pl.getId() == playlistId){
                System.out.println("Get: Playlist name matches");
                Assert.assertEquals(playlistName,pl.getName());
            }
        }
    }
    @Test (enabled = false)    // This test does not work
    public void renamePlaylist() throws InterruptedException {   //Update the name of playlist created
        Thread.sleep(2000);
        Faker faker = new Faker();
        updatedPlaylistName = faker.animal().name();
        System.out.println("Put: Playlist is renamed to: " + updatedPlaylistName);
        RenamePlaylistRequest renamePlaylistRequest = new RenamePlaylistRequest(updatedPlaylistName);
        Response response = given()
                .baseUri("https://bbb.testpro.io/")
                .basePath("api/playlist/"+playlistId)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(renamePlaylistRequest)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        PlaylistResponse[] playlists = jsonPath.getObject("$",PlaylistResponse[].class);
        for (PlaylistResponse pl : playlists) {
            if (pl.getId() == playlistId) {
                System.out.println("Put: Updated playlist name matches");
                Assert.assertEquals(updatedPlaylistName, pl.getName());
            }
        }
    }

    @Test (enabled = false)
    public void deleteAllPlaylists() throws InterruptedException {
        Response response = given()
                .baseUri("https://bbb.testpro.io/")
                .basePath("api/playlist")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        //Below will deserialize the array
        PlaylistResponse[] playlists = jsonPath.getObject("$",PlaylistResponse[].class);
        System.out.println("# of playlists: " + playlists.length);
        //Below will iterate through each playlist and delete it
        for (PlaylistResponse pl : playlists){
            Thread.sleep(1000);
            given()
                    .baseUri("https://bbb.testpro.io/")
                    .basePath("api/playlist/"+pl.getId())
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .header("Authorization", token)
                    .when()
                    .delete()
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
        }
    }
}