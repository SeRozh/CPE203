import processing.core.PImage;
import java.util.Optional;
import java.util.List;
import java.util.Random;

public class Vein extends EntityAction{

    public static final Random rand = new Random();

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    public static final String ORE_KEY = "ore";

    public Vein(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod) {

        super(id, position, images,actionPeriod);

    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent()) {
            Entity ore = Factory.createOre(ORE_ID_PREFIX + this.id, openPt.get(),
                    ORE_CORRUPT_MIN + rand.nextInt(
                            ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList( ORE_KEY));
            world.addEntity( ore);
            ((EntityAction)ore).scheduleActions( scheduler, world, imageStore);
        }
        scheduler.scheduleEvent( this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

}
