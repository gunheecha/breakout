package com.example;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Rectangle implements Breakable{
    private boolean isBroken; // 벽돌이 파괴되었는지 여부

    // 생성자
    public Brick(double x, double y, double width, double height, Color color) {
        super(x, y, width, height, color);
        this.isBroken = false; // 초기 상태는 파괴되지 않음
    }

    // 벽돌을 그리는 메서드
    @Override
    public void draw(GraphicsContext gc) {
        if (!isBroken) {
            gc.setFill(color);
            gc.fillRect(getMinX(), getMinY(), width, height);
        }
    }

    
    // 공과 충돌 여부 확인
    public boolean checkCollision(Ball ball) {
        if (isBroken) {
            return false; // 이미 파괴된 벽돌은 충돌하지 않음
        }

        double ballX = ball.getX();
        double ballY = ball.getY();
        double ballRadius = ball.getRadius();

        // 공이 벽돌의 경계와 충돌했는지 확인
        boolean collision =
            ballX + ballRadius > x &&
            ballX - ballRadius < x + width &&
            ballY + ballRadius > y &&
            ballY - ballRadius < y + height;

        if (collision) {
            breakBrick(); // 벽돌 파괴
        }

        return collision;
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
    public void breakBrick(){
        isBroken = true;
    }
}


