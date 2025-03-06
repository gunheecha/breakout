package com.example;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall extends Rectangle implements Drawable {

    public Wall(double x, double y, double width, double height, Color color) {
        super(x, y, width, height,color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
    }
    
    public double getWidth(){
        return this.width;
    }

    public double getHeight(){
        return this.height;
    }

    @Override
    public double getX(){
        return x;
    }
    @Override
    public double getY(){
        return y;
    }
}

