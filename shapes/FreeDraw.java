/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 *
 * @author Logan
 */
public class FreeDraw extends MyShapes{
     private GraphicsContext graphicsContext;
    private ColorPicker cpLine;
    private Color color;

    private double startX, startY, endX, endY;

    private ArrayList<Double> xValues = new ArrayList<>();
    private ArrayList<Double> yValues = new ArrayList<>();

    public FreeDraw (){}

    public void setGraphicsContext(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
    }

    public void setColor(ColorPicker colorPicker){
        cpLine = colorPicker;
        color = colorPicker.getValue();
    }

    public void setStartPoint(double startX, double startY){
        this.startX = startX;
        this.startY= startY;
    }

    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public void addPoint(double x, double y){
        xValues.add(x);
        yValues.add(y);
    }

    public double getStartX(){
        return startX;
    }

    public double getStartY(){
        return startY;
    }

    public double getEndX(){
        return endX;
    }

    public double getEndY(){
        return endY;
    }

    public double[] getAllXValues(){
        double[] xVals = new double[xValues.size()];

        for(int i = 0; i < xValues.size(); i++){
            xVals[i] = xValues.get(i);
        }

        return xVals;
    }

    public double[] getAllYValues(){
        double[] yVals = new double[yValues.size()];

        for(int i = 0; i < yValues.size(); i++){
            yVals[i] = yValues.get(i);
        }

        return yVals;
    }

    public ColorPicker getColor(){
        return cpLine;
    }

    public void draw(){
        graphicsContext.setStroke(color);
        graphicsContext.beginPath();
        graphicsContext.lineTo(getStartX(), getStartY());

        for(int i = 0; i < xValues.size(); i++){
            graphicsContext.lineTo(xValues.get(i), yValues.get(i));
            graphicsContext.stroke();
        }

        graphicsContext.lineTo(getEndX(), getEndY());
        graphicsContext.stroke();

        graphicsContext.closePath();
    }
}
