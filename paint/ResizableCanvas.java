/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.canvas.Canvas;

/**
 *
 * @author Logan
 */
public class ResizableCanvas extends Canvas {

    boolean saved;

    public ResizableCanvas() {
        saved = true;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    public void resizeCanvas(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
        
    }

    public boolean getCanvasSaved() {
        return saved;
    }

    public void setCanvasSaved(boolean saved) {
        this.saved = saved;
    }
}
