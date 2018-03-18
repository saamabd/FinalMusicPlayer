package Model;

import java.io.Serializable;

/**
 * Created by abdsaam on 17/01/2018.
 */

public class TopTracks implements Serializable {
    private String ArtistName;
    private String img;
    private String track;
    private String url;

    public TopTracks(String artistName, String img, String track) {
        ArtistName = artistName;
        this.img = img;
        this.track = track;
    }

    public TopTracks(String artistName, String img, String track, String url) {
        ArtistName = artistName;
        this.img = img;
        this.track = track;
        this.url = url;
    }

    public TopTracks() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
