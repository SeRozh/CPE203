import java.util.List;

import processing.core.PImage;

final class Background
{
    private String id;
    private List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public List<PImage> getImages() { return images; }
    public String getId() { return id; }
    public int getImageIndex() { return imageIndex; }

    public PImage getCurrentImage(Background entity) {
        return images.get(entity.imageIndex);
    }
}
