import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class WorkSpace {
    private List<Shape> shapes;

    public WorkSpace() {
        this.shapes = new ArrayList<>();
    }

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public Shape get(int index) {
        return shapes.get(index);
    }

    public int size() {
        return shapes.size();
    }

    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        for (Shape shape : shapes) {
            if (shape instanceof Circle) {
                circles.add((Circle) shape);
            }
        }
        return circles;
    }

    public List<Rectangle> getRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (Shape shape : shapes) {
            if (shape instanceof Rectangle) {
                rectangles.add((Rectangle) shape);
            }
        }
        return rectangles;
    }

    public List<Triangle> getTriangles() {
        List<Triangle> triangles = new ArrayList<>();
        for (Shape shape : shapes) {
            if (shape instanceof Triangle) {
                triangles.add((Triangle) shape);
            }
        }
        return triangles;
    }

    public List<Shape> getShapesByColor(Color color) {
        List<Shape> shapeList = new ArrayList<>();
        for (Shape shape : shapes) {
            if (shape.getColor().equals(color)) {
                shapeList.add(shape);
            }
        }
        return shapeList;
    }

    public double getAreaOfAllShapes() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.getArea();
        }
        return sum;
    }

    public double getPerimeterOfAllShapes(){
        double perim = 0;
        for(Shape shape : shapes){
            perim += shape.getPerimeter();
        }
        return perim;
    }
}