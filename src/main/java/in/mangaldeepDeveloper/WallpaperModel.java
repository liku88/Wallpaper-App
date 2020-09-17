package in.mangaldeepDeveloper;

public class WallpaperModel {
    private int id;
    private String originalLink, mediumLink;

    public WallpaperModel() {
    }

    public WallpaperModel(int id, String originalLink, String mediumLink) {
        this.id = id;
        this.originalLink = originalLink;
        this.mediumLink = mediumLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public String getMediumLink() {
        return mediumLink;
    }

    public void setMediumLink(String mediumLink) {
        this.mediumLink = mediumLink;
    }
}
