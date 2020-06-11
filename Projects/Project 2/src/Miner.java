public interface Miner extends Move, ActionEntity, AnimationEntity{
    boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}
