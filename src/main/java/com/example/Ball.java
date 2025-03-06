package com.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends Circle implements Movable,Bounceable{
    public double dx;
    public double dy;
    private boolean isPaused;

    public Ball(double x , double y, double radius, Color color,double dx,double dy){
        super(x, y, radius, color);
        this.dx = dx;
        this.dy = dy;
        this.isPaused = false;
    }

    
    public double getRadius(){
        return width / 2;
    }

    public double setY(double y){
        return this.y;
    }
    
    public double setX(double x){
        return this.x;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    @Override
    public double getX(){
        return this.x;
    }
    
    @Override
    public double getY(){
        return this.y;
    }

    @Override
    public void move(){
        if(!isPaused){
            x += dx;
            y += dy;
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
    public boolean isCollisionDetected(Shape other){
        if(other instanceof Paddle paddle){
            double ballLeft = getMinX();
            double ballRight = getMaxX();
            double ballTop = getMinY();
            double ballBottom = getMaxY();

            double paddleLeft = paddle.getMinX();
            double paddleRight = paddle.getMaxX();
            double paddleTop = paddle.getMinY();
            double paddleBottom = paddle.getMaxY();

            return ballBottom > paddleTop && ballTop < paddleBottom && ballRight>paddleLeft && ballLeft< paddleRight;

        }
        if (other instanceof Wall wall) {
            double ballLeft = getMinX();
            double ballRight = getMaxX();
            double ballTop = getMinY();
            double ballBottom = getMaxY();
    
            double wallLeft = wall.getX();
            double wallRight = wall.getX() + wall.getWidth();
            double wallTop = wall.getY();
            double wallBottom = wall.getY() + wall.getHeight();
    
            return ballBottom > wallTop && ballTop < wallBottom && ballRight > wallLeft && ballLeft < wallRight;
        }
        if (other instanceof Brick brick) {
            double ballLeft = getMinX();
            double ballRight = getMaxX();
            double ballTop = getMinY();
            double ballBottom = getMaxY();
    
            double brickLeft = brick.getX();
            double brickRight = brick.getMaxX();
            double brickTop = brick.getMinY();
            double brickBottom = brick.getMaxY();
    
            return ballBottom > brickTop && ballTop < brickBottom && ballRight > brickLeft && ballLeft < brickRight;
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(color);
        gc.fillOval(getMinX() , getMinY(), width, height);
    }

    @Override
    public double setDx(double dx) {
        return this.dx = dx;
    }
    @Override
    public double setDy(double dy) {
        return this.dy = dy;
    }
    @Override
    public double getDy(){
        return dy;
    }
    @Override
    public double getDx(){
        return dx;
    }

    @Override
    public void BounceX(){
        dx = -dx;
    }

    @Override
    public void BounceY(){
        dy = -dy;
    }

    @Override
    public void BounceXY(){
        dx = -dx;
        dy = -dy;
    }


}
