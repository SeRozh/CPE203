import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Quake implements ActionEntity, AnimationEntity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex = 0;
    private int animationPeriod;

    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.animationPeriod = QUAKE_ANIMATION_PERIOD;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                CreateAction.createActivityAction(this, world, imageStore),
                QUAKE_ACTION_PERIOD);
        scheduler.scheduleEvent(this, CreateAction.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());

    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    @Override
    public int getAnimationPeriod() {
        return animationPeriod;
    }

    @Override
    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
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
    public Point getPosition(){
        return this.position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }
}
