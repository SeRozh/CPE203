import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Ore implements ActionEntity{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex = 0;
    private int actionPeriod;

    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;

    Random rand = new Random();

    public Ore(String id, Point position, List<PImage> images, int actionPeriod){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
    }

    public String getId() { return id; }
    public List<PImage> getImages(){ return images; }
    public int getImageIndex(){ return imageIndex; }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                CreateAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.position;

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = CreateEntity.createOreBlob(this.id + BLOB_ID_SUFFIX, pos,
                this.actionPeriod / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
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
                    String.format("getCurrentImage not supported for %s", this));
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