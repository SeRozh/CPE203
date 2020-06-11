import java.awt.*;

public class Circle implements Shape {
    private double radius;
    private Point center;
    private Color color;

    public Circle(double radius, Point center, Color color){
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    public Point getCenter(){
        return this.center;
    }

    public boolean equals(Object other) {
        if(other == null){
            return false;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }
        Circle otherCircle = (Circle)other;
        return this.radius == otherCircle.getRadius() && this.center.equals(otherCircle.getCenter())
                && this.color.equals(otherCircle.getColor());
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public double getArea() {
        return (Math.PI * Math.pow(this.radius, 2));
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * this.radius;
    }

    @Override
    public void translate(Point p) {
        center.setLocation(center.getX() + p.getX(), center.getY() + p.getY());
    }
}
