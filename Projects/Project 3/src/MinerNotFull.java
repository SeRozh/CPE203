import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends Miner {
    public MinerNotFull(String id, Point position, List<PImage> images,
                        int actionPeriod, int animationPeriod,int resourceCount,int resourceLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> notFullTarget = world.findNearest(this.position, Ore.class);

        if (notFullTarget.isEmpty() || !this.moveTo(world, notFullTarget.get(), scheduler)
                || !this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    CreateAction.createActivityAction(this,world, imageStore),
                    getActionPeriod());
        }
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
}

