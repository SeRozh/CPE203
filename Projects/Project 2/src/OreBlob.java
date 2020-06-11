import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class OreBlob implements ActionEntity, AnimationEntity, Move{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex = 0;
    private int actionPeriod;
    private int animationPeriod;

    public static final String QUAKE_KEY = "quake";

    Random rand = new Random();

    public OreBlob(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    @Override
    public void scheduleActions (EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                CreateAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    @Override
    public void executeActivity (WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<Entity> blobTarget = world.findNearest(this.position, Vein.class);
        long nextPeriod = this.actionPeriod;

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (this.moveTo(world, blobTarget.get(), scheduler)) {
                Quake quake = CreateEntity.createQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, CreateAction.createActivityAction(this, world, imageStore), nextPeriod);
    }

    @Override
    public int getAnimationPeriod () {
        return this.animationPeriod;
    }

    @Override
    public void nextImage () {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    @Override
    public boolean moveTo (WorldModel world, Entity target, EventScheduler scheduler){
        if (this.position.adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

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

    @Override
    public Point nextPosition (WorldModel world, Point destPos){
        int horiz = Integer.signum(destPos.getX() - this.position.getY());
        Point newPos = new Point(this.position.getX() + horiz, this.position.getY());

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 || (occupant.isPresent() && !(occupant.get() instanceof Ore))) {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), this.position.getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent() && !(occupant.get() instanceof Ore))) {
                newPos = this.position;
            }
        }
        return newPos;
    }

    @Override
    public PImage getCurrentImage (Object entity){
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
    public Point getPosition () {
        return position;
    }

    @Override
    public void setPosition (Point position){
        this.position = position;
    }
}
