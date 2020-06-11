import java.awt.*;

public class Triangle implements Shape{
    private Point a;
    private Point b;
    private Point c;
    private Color color;

    public Triangle(Point a, Point b, Point c, Color color){
        this.a = a;
        this.b = b;
        this.c = c;
        this.color = color;
    }

    public Point getVertexA(){
        return a;
    }

    public Point getVertexB(){
        return b;
    }

    public Point getVertexC() {
        return c;
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
        return Math.abs((a.getX() * (b.getY() - c.getY()) +
                b.getX() * (c.getY() - a.getY()) +
                c.getX() * (a.getY() - b.getY())) / 2);
    }

    @Override
    public double getPerimeter() {
        double ac = Math.sqrt(Math.pow(a.getX() - c.getX(), 2) + Math.pow(a.getY() - c.getY(), 2));
        double ab = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
        double cb = Math.sqrt(Math.pow(c.getX() - b.getX(), 2) + Math.pow(c.getY()- b.getY(), 2));

        return ac + ab + cb;
    }

    @Override
    public void translate(Point p) {
        a.setLocation(a.getX() + p.getX(), a.getY() + p.getY());
        b.setLocation(b.getX() + p.getX(), b.getY() + p.getY());
        c.setLocation(c.getX() + p.getX(), c.getY() + p.getY());
    }

    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }

        Triangle otherTriangle = (Triangle)other;
        return this.a.equals(otherTriangle.getVertexA()) && this.b.equals(otherTriangle.getVertexB())
                && this.c.equals((otherTriangle.getVertexC()));
    }
}
