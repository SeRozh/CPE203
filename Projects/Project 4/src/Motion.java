public interface Motion
{
    boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    Point nextPosition(WorldModel world,Point pos);
}
