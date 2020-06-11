import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class OreBlob extends AnimationEntity implements Move {

    private static final String QUAKE_KEY = "quake";
    private static final Random rand = new Random();

    public OreBlob(String id, Point position, List<PImage> images,
                   int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> blobTarget = world.findNearest(this.position, Vein.class);
        long nextPeriod = getActionPeriod();

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (this.moveTo(world, blobTarget.get(), scheduler)) {
                Quake quake = CreateEntity.createQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                ((ActionEntity) quake).scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this, CreateAction.createActivityAction(this, world, imageStore), nextPeriod);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getY());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 || (occupant.isPresent() && !(occupant.get() instanceof Ore))) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent() && !(occupant.get() instanceof Ore))) {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }
}