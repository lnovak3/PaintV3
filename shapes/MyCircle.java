/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Logan
 */
public class MyCircle extends MyShapes{
    private GraphicsContext graphicsContext;
    private ColorPicker colorPicker;
    private ColorPicker colorPickerFill;
    private Color colorStroke;
    private Color colorFill;

    private double centerX, centerY, endX, endY;

    private Circle circle = new Circle();

    public MyCircle(){}

    public void setGraphicsContext(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
    }

    public void setColor(ColorPicker colorPicker){
        this.colorPicker = colorPicker;
        colorStroke = colorPicker.getValue();
    }

    public void setFill(ColorPicker colorPickerFill){
        this.colorPickerFill = colorPickerFill;
        colorFill = colorPickerFill.getValue();
    }

    public void setCenterPoint(double centerX, double centerY){
        this.centerX = centerX;
        this.centerY = centerY;

        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
    }

    public void setEndPoint(double endX, double endY){
        this.endX = endX;
        this.endY = endY;
    }

    public void setRadius(){
        circle.setRadius((Math.abs(endX - centerX) + Math.abs(endY - centerY)) / 2);
    }

    public void setRadius(double radius){
        circle.setRadius(radius);
    }

    public void check(){
        if(centerX > endX) {
            circle.setCenterX(endX);
        }
        if(centerY > endY) {
            circle.setCenterY(endY);
        }
    }

    public double getRadius(){
        return circle.getRadius();
    }

    public double getCenterX(){
        return circle.getCenterX();
    }

    public double getCenterY(){
        return circle.getCenterY();
    }

    public ColorPicker getColor(){
        return colorPicker;
    }

    public ColorPicker getFill(){
        return colorPickerFill;
    }

    public void draw(){
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setFill(colorFill);

        graphicsContext.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
        graphicsContext.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
    }
}
