package com.example;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape implements Drawable {
    protected  Color color;
    public Circle(double x, double y, double radius, Color color){
        super(x,y,radius*2,radius*2);
        this.color = color;
    }
    
    @Override
    public double getX(){
        return x;
    }
    
    @Override
    public double getY(){
        return y;
    }

    @Override
    public double getMinX(){
        return getX()-width/2;
    }
    
    @Override
    public double getMaxX(){
        return getX()+width/2;
    }

    @Override
    public double getMinY(){
        return getY()-height/2;
    }
    
    @Override
    public double getMaxY(){
        return getY()+height;
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(color);
        gc.fillOval(getMinX(), getMinY(), width, height);
    }
}