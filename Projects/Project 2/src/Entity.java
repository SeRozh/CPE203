import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public interface Entity
{
    PImage getCurrentImage(Object entity);
    Point getPosition();
    void setPosition(Point position);
}
