/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Logan
 */
public class MyEllipse extends MyShapes{

    private GraphicsContext graphicsContext;
    private ColorPicker colorPicker;
    private ColorPicker colorPickerFill;

    private double centerX, centerY, endX, endY;
    private Color colorStroke;
    private Color colorFill;

    private Ellipse ellipse = new Ellipse();

    public MyEllipse() {
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void setColor(ColorPicker colorPicker) {
        colorPicker = colorPicker;
        colorStroke = colorPicker.getValue();
    }

    public void setFill(ColorPicker colorPicker) {
        colorPickerFill = colorPicker;
        colorFill = colorPickerFill.getValue();
    }

    public void setCenterPoint(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;

        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
    }

    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public void setRadius() {
        ellipse.setRadiusX(Math.abs((endX - centerX)));
        ellipse.setRadiusY(Math.abs((endY - centerY)));
    }

    public void check() {
        if (centerX > endX) {
            ellipse.setCenterX(endX);
        }
        if (centerY > endY) {
            ellipse.setCenterY(endY);
        }
    }

    public double getCenterX() {
        return ellipse.getCenterX();
    }

    public double getCenterY() {
        return ellipse.getCenterY();
    }

    public double getRadiusX() {
        return ellipse.getRadiusX();
    }

    public double getRadiusY() {
        return ellipse.getRadiusY();
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

        graphicsContext.strokeOval(getCenterX(), getCenterY(), getRadiusX(), getRadiusY());
        graphicsContext.fillOval(getCenterX(), getCenterY(), getRadiusX(), getRadiusY());
    }
}
