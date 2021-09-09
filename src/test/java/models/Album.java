package models;

public class Album {
    private int id;
    private int artistId;
    private String name;
    private String cover;

    public Album(int id, int artistId, String name, String cover) {
        this.id = id;
        this.artistId = artistId;
        this.name = name;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getName() {
        return name;
    }

    public String getCover() {
        return cover;
    }
}