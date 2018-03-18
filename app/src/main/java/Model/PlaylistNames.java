package Model;

/**
 * Created by abdsaam on 18/03/2018.
 */

public class PlaylistNames {
    public String title;
    public String id;
    public String img;

    public PlaylistNames() {
    }

    public PlaylistNames(String id, String title, String img) {
        this.title = title;
        this.id = id;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
