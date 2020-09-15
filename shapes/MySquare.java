/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Logan
 */
public class MySquare extends MyShapes{
    private GraphicsContext graphicsContext;
    private ColorPicker colorPicker;
    private ColorPicker colorPickerFill;
    private Color colorStroke;
    private Color colorFill;

    private double startX, startY, endX, endY;

    private Rectangle square = new Rectangle();

    public MySquare(){ }

    public void setGraphicsContext(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
    }

    public void setColor(ColorPicker colorPicker){
        this.colorPicker = colorPicker;
        colorStroke = colorPicker.getValue();
    }

    public void setFill(ColorPicker colorPicker){
        colorPickerFill = colorPicker;
        colorFill = colorPickerFill.getValue();
    }

    public void setStartPoint(double startX, double startY){
        this.startX = startX;
        this.startY = startY;

        square.setX(startX);
        square.setY(startY);
    }

    public void setEndPoint(double endX, double endY){
        this.endX = endX;
        this.endY = endY;
    }

    public void setWidth(){
        square.setWidth(Math.abs((endX - startX)));
    }

    public void setHeight(){
        square.setHeight(Math.abs((endY - startY)));
    }

    public void check() {
        if (getX() > endX) {
            square.setX(endX);
        }
        if (getY() > endY) {
            square.setY(endY);
        }
    }

    public double getX(){
        return square.getX();
    }

    public double getY(){
        return square.getY();
    }

    public double getWidth(){
        return square.getWidth();
    }

    public double getHeight() {
        return square.getHeight();
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

        graphicsContext.fillRect(getX(), getY(), getWidth(), getWidth());
        graphicsContext.strokeRect(getX(), getY(), getWidth(), getWidth());
    }
}
