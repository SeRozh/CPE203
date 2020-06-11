public class CreateAction {
    public static Action createAnimationAction (AnimationEntity entity, int repeatCount){
        return new AnimationAction(entity, null, null, repeatCount);
    }

    public static Action createActivityAction (ActionEntity entity, WorldModel world, ImageStore imageStore)
    {
        return new ActivityAction(entity, world, imageStore, 0);
    }
}
