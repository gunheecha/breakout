package com.example;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Movable{

    private double dx;
    private boolean isPaused;
    public boolean moveLeft = false;
    public boolean moveRight = false;
    public Paddle(double x, double y, double width, double height, Color color,double dx){
        super(x, y, width, height,color);
        this.dx = dx;
        this.isPaused = false;
    }
    

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setX(double z){
        this.x = z;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    @Override
    public void move(){
        if(!isPaused){
            if(moveLeft){  // moveLeft가 true일 때만 왼쪽으로 이동
                x -= dx;
            }
            if(moveRight){  // moveRight가 true일 때만 오른쪽으로 이동
                x += dx;
            }
        }
    }


    @Override
    public void pause(){
        isPaused = true;
    }

    @Override
    public void resume(){
        isPaused = false;
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
    public double setDx(double dx) {
        return this.dx = dx;
    }

    @Override
    public double setDy(double dy) {
        return 0; 
    }

    @Override
    public double getDy(){
        return 0;
    }

    @Override
    public double getDx(){
       return dx;
    }

    @Override
    public boolean isCollisionDetected(Shape other){
        if(other instanceof Wall wall){
            double paddleLeft = getMinX();
            double paddleRight = getMaxX();

            double wallLeft = wall.getX();
            double wallRight = wall.getX() + wall.getWidth();
            
            return paddleLeft < wallLeft && paddleRight > wallRight;
        }
        
        return false;
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(color);
        gc.fillRect(getMinX(), getMinY(), width, height);
    }
}
