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
public class MyRectangle extends MyShapes{

    private GraphicsContext graphicsContext;
    private ColorPicker colorPicker;
    private ColorPicker colorPickerFill;
    private Color colorStroke;
    private Color colorFill;
    public double startX, startY, endX, endY, width, height;

    private Rectangle rectangle = new Rectangle();

    public MyRectangle() {
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void setColor(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
        colorStroke = colorPicker.getValue();
    }

    public void setFill(ColorPicker colorPicker) {
        this.colorPickerFill = colorPicker;
        colorFill = colorPickerFill.getValue();
    }

    public void setStartPoint(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;

        rectangle.setX(startX);
        rectangle.setY(startY);
    }

    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public void setWidth() {
        this.width = Math.abs((endX - startX));

        rectangle.setWidth(Math.abs((endX - startX)));
    }

    public void setHeight() {
        this.height = Math.abs((endY - startY));

        rectangle.setHeight(Math.abs((endY - startY)));
    }

    public void check() {
        if (getX() > endX) {
            rectangle.setX(endX);
        }
        if (getY() > endY) {
            rectangle.setY(endY);
        }
    }

    public double getX() {
        return rectangle.getX();
    }

    public double getY() {
        return rectangle.getY();
    }

    public double getWidth() {
        return rectangle.getWidth();
    }

    public double getHeight() {
        return rectangle.getHeight();
    }

    public ColorPicker getColor() {
        return colorPicker;
    }

    public ColorPicker getFill() {
        return colorPickerFill;
    }

    public void draw() {
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setFill(colorFill);

        graphicsContext.fillRect(getX(), getY(), getWidth(), getHeight());
        graphicsContext.strokeRect(getX(), getY(), getWidth(), getHeight());
    }

}
