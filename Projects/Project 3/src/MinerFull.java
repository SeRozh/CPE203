import processing.core.PImage;
import java.util.List;
import java.util.Optional;


public class MinerFull extends Miner {
    public MinerFull(String id, Point position, List<PImage> images, int resourceLimit,
                     int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(getPosition(), Blacksmith.class);
        if (fullTarget.isPresent() &&
                this.moveTo(world, fullTarget.get(), scheduler))
        {
            this.transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    CreateAction.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }


    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
}