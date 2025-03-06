package com.example;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape implements Drawable {
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public Shape(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public abstract double getX();
    public abstract double getY();
    public abstract double getMinX();
    public abstract double getMaxX();
    public abstract double getMinY();
    public abstract double getMaxY();
    @Override
    public abstract void draw(GraphicsContext gc);
}
