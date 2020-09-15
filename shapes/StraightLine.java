package shapes;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Logan
 */
public class StraightLine extends MyShapes{
    private GraphicsContext graphicsContext;
    private ColorPicker colorPicker;
    private Color color;
    private Line line = new Line();

    public StraightLine(){
    }

    public void setGraphicsContext(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
    }

    public void setColor(ColorPicker colorPicker){
        this.colorPicker = colorPicker;
        color = colorPicker.getValue();
    }

    public void setStartPoint(double x, double y){
        line.setStartX(x);
        line.setStartY(y);
    }

    public void setEndPoint(double x, double y){
        line.setEndX(x);
        line.setEndY(y);
    }

    public double getStartX(){
        return line.getStartX();
    }

    public double getStartY(){
        return line.getStartY();
    }

    public double getEndX(){
        return line.getEndX();
    }

    public double getEndY(){
        return line.getEndY();
    }

    public ColorPicker getColor(){
        return colorPicker;
    }

    public void draw() {
        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(getStartX(), getStartY(), getEndX(), getEndY());
    }
}
