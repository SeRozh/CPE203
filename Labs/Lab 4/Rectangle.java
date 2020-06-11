import java.awt.*;

public class Rectangle implements Shape {
    private double width;
    private double height;
    private Point topLeft;
    private Color color;

    public Rectangle(double width, double height, Point topLeft, Color c){
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
        this.color = c;
    }

    public double getWidth(){
        return this.width;
    }
    public void setWidth(double width){
        this.width = width;
    }
    public double getHeight(){
        return this.height;
    }
    public void setHeight(double height){
        this.height = height;
    }
    public Point getTopLeft(){
        return this.topLeft;
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
        return this.width * this.height;
    }

    @Override
    public double getPerimeter() {
        double h = 2 * this.height;
        double w = 2 * this.width;
        return h + w;
    }

    @Override
    public void translate(Point p) {
        topLeft.setLocation(topLeft.getX() + p.getX(), topLeft.getY() + p.getY());
    }

    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }

        Rectangle otherRectangle = (Rectangle)other;
        return this.width == otherRectangle.getWidth() && this.height == otherRectangle.getHeight()
                && this.topLeft.equals(otherRectangle.getTopLeft()) && this.color.equals(otherRectangle.getColor());
    }
}
