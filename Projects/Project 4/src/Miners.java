import java.util.List;
import processing.core.PImage;

public abstract class Miners extends EntityAnimation implements Motion
{
    protected int resourceCount;
    protected int resourceLimit;

    public Miners(String id, Point position,
                 List<PImage> images, int resourceLimit,
                 int resourceCount, int actionPeriod,
                 int animationPeriod,int repeatCount)
    {
        super(id, position, images,actionPeriod, animationPeriod, 0);
        this.resourceCount=resourceCount;
        this.resourceLimit=resourceLimit;
    }



    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        if (this.getClass().equals(MinerNotFull.class))
        {

            if (this.resourceCount >= this.resourceLimit)
            {
                MinerFull miner = Factory.createMinerFull(this.id, this.resourceLimit,
                        this.position, this.actionPeriod,
                        this.animationPeriod,
                        this.images);

                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);

                world.addEntity(miner);
                miner.scheduleActions(scheduler, world, imageStore);

                return true;
            }


        }
        else
            {

                Entity miner = Factory.createMinerNotFull(this.id, this.resourceLimit,
                        this.position, this.actionPeriod,
                        this.animationPeriod,
                        this.images);

                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);

                world.addEntity(miner);
                ((EntityAction) miner).scheduleActions(scheduler, world, imageStore);


            }

        return false;
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }
}

