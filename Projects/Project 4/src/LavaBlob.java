import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class LavaBlob extends EntityAnimation implements Motion
{

    private static final String QUAKE_KEY = "quake";

    public LavaBlob(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod, 0);
    }


    public void setActionPeriod(int p) { actionPeriod = p;}
    public void setAnimationPeriod(int p) { animationPeriod = p;}

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blacksmithTarget =
                world.findNearest(this.position, Blacksmith.class);
        long nextPeriod = this.actionPeriod;

        if (blacksmithTarget.isPresent()) {
            Point tgtPos = blacksmithTarget.get().getPosition();

            if (this.moveTo( world, blacksmithTarget.get(), scheduler)) {
                Entity quake = Factory.createQuake( imageStore.getImageList( QUAKE_KEY),tgtPos);

                world.addEntity( quake);
                nextPeriod += this.actionPeriod;
                ((EntityAction)quake).scheduleActions( scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore), nextPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz, this.position.getY());

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 || (occupant.isPresent()))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), this.position.getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent()))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }



    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = this.nextPosition( world, target.getPosition());

            if (!this.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


}
