import processing.core.PImage;

import java.util.List;

public class Factory
{
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;

    public static Action createAnimationAction(EntityAnimation entity, int repeatCount) {
        return new Animation(entity, repeatCount);
    }

    public static Action createActivityAction(EntityAction entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }

    public static Blacksmith createBlacksmith(String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images);
    }

    public static LavaBlob createLavaBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new LavaBlob(id, position, images, actionPeriod, animationPeriod);
    }

    public static MinerFull createMinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerFull(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public static MinerNotFull createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MinerNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }


    public static Obstacle createObstacle(String id, Point position, List<PImage> images)
    {
        return new Obstacle( id, position, images);
    }

    public static Ore createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, images, actionPeriod);
    }

    public static OreBlob createOreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new OreBlob(id, position, images, actionPeriod, animationPeriod);
    }

    public static Quake createQuake(List<PImage> images, Point pos)
    {
        return new Quake(QUAKE_ID, pos, images,
                QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static Vein createVein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Vein(id, position, images, actionPeriod);
    }

    public static GifSoldier createGifSoldier(String id, Point position,
                                              List<PImage> images, int actionPeriod, int animationPeriod)
    {
        return new GifSoldier(id, position, images, actionPeriod, animationPeriod);
    }
}
