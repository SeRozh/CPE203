import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public abstract class Miner extends AnimationEntity implements Move{
    protected int resourceCount;
    protected int resourceLimit;

    public Miner(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                 int actionPeriod, int animationPeriod, int repeatCount) {
        super(id, position, images, actionPeriod, animationPeriod, 0);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    public int getResourceCount(){ return this.resourceCount; }
    public int getResourceLimit(){ return this.resourceLimit; }

    public boolean transform(WorldModel world,
                             EventScheduler scheduler, ImageStore imageStore) {
        if (this.getClass().equals(MinerNotFull.class)) {
            if (getResourceCount() >= getResourceLimit()) {
                MinerFull miner = CreateEntity.createMinerFull(getId(), getResourceLimit(),
                        getActionPeriod(), getAnimationPeriod(), getPosition(),
                        getImages());
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
                world.addEntity(miner);
                miner.scheduleActions(scheduler, world, imageStore);
                return true;
            }
            return false;
        }
        else {
            ActionEntity miner = CreateEntity.createMinerNotFull(getId(), getResourceLimit(), getPosition(),
                    getActionPeriod(), getAnimationPeriod(),
                    getImages());
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz,
                this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(),
                    this.getPosition().getY() + vert);
            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }
}
