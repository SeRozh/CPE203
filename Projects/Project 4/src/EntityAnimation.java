import processing.core.PImage;
import java.util.List;

public abstract class EntityAnimation extends EntityAction
{
    protected int animationPeriod;
    protected int repeatCount;

    public EntityAnimation(String id, Point position,
                          List<PImage> images, int actionPeriod,
                          int animationPeriod, int repeatCount) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
        this.repeatCount = repeatCount;

    }

    public int getAnimationPeriod() { return animationPeriod; }


    public void nextImage() {this.imageIndex = (this.imageIndex + 1) % this.images.size(); }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }

}

