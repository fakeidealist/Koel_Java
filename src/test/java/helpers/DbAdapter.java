package helpers;

import models.Album;
import models.Artist;
import models.Playlist;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAdapter {
    //In order to connect to database, we need to know 3 variables
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://104.237.13.60/dbkoel";
    private static final String USER = "dbuser01";
    private static final String PASSWORD = "pa$$01";

    private static Connection connection = null; // Null as requirement
    private static Statement statement = null;

    public static List<Artist> getAllArtists(){
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists a ";
        //First, we need to create a connection
        //After that, we need to create a statement in the connection
        //getConnection can throw exception, so we will put it in try catch block
        try{
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            statement = connection.createStatement();
            //Passing our query inside of statement.
            //And put it in a collection-like object; number of records
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){         // In here two fields we care about: id and name.
                int number = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Artist artist = new Artist(number, name);
                artists.add(artist);
            }

        } catch (SQLException err){}

        return artists;
    }
    public static List<Album> getAllAlbums(){
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums a ";
        try{
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int artId = resultSet.getInt("artist_id");
                String name = resultSet.getString("name");
                String cover = resultSet.getString("cover");
                Album album = new Album(id, artId, name, cover);
                albums.add(album);
            }

        } catch (SQLException err){}

        return albums;
    }
    public static Playlist getPlaylistById(int playlistId){
        Playlist playlist = null;
        String query = "SELECT * FROM playlists p WHERE user_id = 1052 and id = " + playlistId;
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                playlist = new Playlist(id, name);
            }
        } catch (SQLException | ClassNotFoundException err){}
        finally {   // Finally is the part which will run in any case
            if(connection!=null){
                try{
                    connection.close();
                } catch (SQLException ignored){}
            }
        }

        return playlist;
    }
    public static User getUserByEmail(String userEmail){
        User user = null;
        String query = "SELECT * FROM users WHERE email = " + "'" + userEmail + "'";
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //Because the ResultSet Cursor is pointing to just before the first record when the data is loaded,
            //we need to use the next method to move to the first row.
            resultSet.next();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            user = new User(id, name, email);


        } catch (SQLException | ClassNotFoundException err){}
        finally {
            if(connection!=null){
                try{
                    connection.close();
                } catch (SQLException ignored){}
            }
        }

        return user;
    }
}