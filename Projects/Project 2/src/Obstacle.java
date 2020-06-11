import processing.core.PImage;

import processing.core.PImage;

import java.util.List;

public class Obstacle implements Entity{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex = 0;
    private int resourceLimit;
    private int resourceCount;
    private int animationPeriod;
    private int actionPeriod;

    public String getId() { return id; }
    public List<PImage> getImages() { return images; }
    public int getImageIndex() { return imageIndex; }
    public int getAnimationPeriod() { return animationPeriod; }

    public Obstacle(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                    int animationPeriod, int actionPeriod){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;
    }

    @Override
    public PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return images.get(imageIndex);
        }
        else if (entity instanceof Entity) {
            return images.get(imageIndex);
        }
        else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            this));
        }
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }
}