

import processing.core.PImage;
import java.util.List;


public abstract class Entity
{
    protected String id;
    protected Point position;
    protected List<PImage> images;
    protected int imageIndex;

    public Entity(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }
    public Point getPosition() { return position;}
    public List<PImage> getImages() { return images; }

    public PImage getCurrentImage() {return images.get(imageIndex);}


    public void setImages(List<PImage> p) { images = p;}
    public void setPosition(Point point) {position = point;}
}



