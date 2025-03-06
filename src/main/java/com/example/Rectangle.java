package com.example;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape implements Drawable{
    protected  Color color;

    public Rectangle(double x,double y, double width, double height,Color color){
        super(x,y,width,height);
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
        return getX() - width / 2;
    }
    
    @Override
    public double getMaxX(){
        return getX() + width / 2;
    }

    @Override
    public double getMinY(){
        return getY() - height / 2;
    }
    
    @Override
    public double getMaxY(){
        return getY() + height / 2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);  // 사각형의 색상 설정
        gc.fillRect(getMinX(), getMinY(), width, height);  // 사각형 그리기
    }

}
