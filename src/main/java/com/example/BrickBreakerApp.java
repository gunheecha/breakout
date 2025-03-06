package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BrickBreakerApp extends Application {
    public boolean moveLeft = false;
    public boolean moveRight = false;
    private boolean gameFinished = false;
    

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        List<Shape> shapes = new ArrayList<>();
        
        Ball ball = new Ball(400, 300, 10,Color.YELLOW,3,3);
        Paddle paddle = new Paddle(400, 550, 100, 20,Color.GREEN,5);
        Wall topWall = new Wall(0, 0, canvas.getWidth(), 10, Color.LIGHTGRAY);  // 위쪽 벽
        Wall bottomWall = new Wall(0, canvas.getHeight() - 10, canvas.getWidth(), 10, Color.RED);  // 아래쪽 벽
        Wall leftWall = new Wall(0, 0, 10, canvas.getHeight(), Color.LIGHTGRAY);  // 왼쪽 벽
        Wall rightWall = new Wall(canvas.getWidth() - 10, 0, 10, canvas.getHeight(), Color.LIGHTGRAY);  // 오른쪽 벽
        List<Brick> bricks = new ArrayList<>();
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 20;
        double padding = 5;
        double startX = 50;
        double startY = 50;

        shapes.add(ball);
        shapes.add(paddle);
        shapes.add(topWall);
        shapes.add(bottomWall);
        shapes.add(leftWall);
        shapes.add(rightWall);
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                bricks.add(new Brick(x, y, brickWidth, brickHeight, Color.BLUE));
            }
        }

        shapes.addAll((Collection<? extends Shape>) (Collection<?>) bricks);

        // 게임 루프
        AnimationTimer gameLoop = new AnimationTimer() {
            int frameCount = 0;
            @Override
            public void handle(long now) {
                if(gameFinished){
                    return;
                }
                // 화면 초기화
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Ball 업데이트
                ball.move();
                // Paddle 업데이트
                paddle.move();
                // Ball 과 Wall의 충돌 확인
                if(ball.isCollisionDetected(leftWall)){
                    ball.BounceX();
                    ball.setX(leftWall.getX() + ball.getWidth()/2);
                }
                if(ball.isCollisionDetected(rightWall)){
                    ball.BounceX();
                    ball.setX((rightWall.getX() + rightWall.getWidth()) - ball.getWidth() / 2);
                }
                if(ball.isCollisionDetected(topWall)){
                    ball.BounceY();
                    ball.setY(topWall.getY() + ball.getHeight()/2);
                }
                // 아래벽과 충돌시 게임종료
                if(ball.isCollisionDetected(bottomWall)){
                    gameFinished = true; // 게임 완료 플래그 설정
                    showGameOverPopup();
                    ball.BounceY();
                    ball.setY(topWall.getY() + topWall.getHeight() - ball.getHeight()/2);
                }
                // Paddle 충돌 확인
                if(ball.isCollisionDetected(paddle)){
                    ball.setDy(-ball.getDy());
                    double paddleBottom = paddle.getY() + paddle.getHeight();
                    ball.setY(paddleBottom - ball.getHeight());
                    double paddleLeft = paddle.getX();
                    double paddleRight = paddle.getX() + paddle.getWidth();
                    if(ball.getX()<paddleLeft){
                        ball.setX(paddleLeft);
                    }else if(ball.getX()> paddleRight - ball.getWidth()){
                        ball.setX(paddleRight - ball.getWidth());
                    }
                    if (ball.getX() < paddleLeft + ball.getWidth() && ball.getX() > paddleLeft) {
        
                        ball.setDx(-ball.getDx()); 
                    } else if (ball.getX() + ball.getWidth() > paddleRight && ball.getX() + ball.getWidth() < paddleRight + ball.getWidth()) {
                        
                        ball.setDx(-ball.getDx()); 
                    }
                }
                if(paddle.isCollisionDetected(leftWall)){
                    paddle.setX(leftWall.getX() + paddle.getWidth());
                }
                if(paddle.isCollisionDetected(rightWall)){
                    paddle.setX((rightWall.getX()+rightWall.width) - paddle.getWidth());
                }
                // 벽돌  충돌 처리
                for (int i = 0; i < bricks.size(); i++) {
                    Brick brick = bricks.get(i);

                    // 벽돌과 공의 충돌 확인
                    if (brick.checkCollision(ball)) {
                        ball.setDy(-ball.getDy()); // 충돌 시 공의 y 방향 반전
                        bricks.remove(i);  // 벽돌 파괴 후 리스트에서 제거
                        frameCount++;
                        if(frameCount >= 50){
                            gameFinished = true;
                            showGameWinPopup();
                        }
                        break; // 한 번 충돌 후 벽돌 제거하고 반복문 종료
                    }

                }

                for (Shape shape : shapes) {
                    shape.draw(gc);
                }
            }
        };
        gameLoop.start();

        // 레이아웃 설정
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(new StackPane(canvas), 800, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = true;
                paddle.setMoveLeft(true);  // Paddle의 moveLeft를 true로 설정
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = true;
                paddle.setMoveRight(true);  // Paddle의 moveRight를 true로 설정
            }
        });
        
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = false;
                paddle.setMoveLeft(false);  // Paddle의 moveLeft를 false로 설정
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = false;
                paddle.setMoveRight(false);  // Paddle의 moveRight를 false로 설정
            }
        });
        

        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 팝업을 표시하고 종료하는 메서드
    private void showGameOverPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Game Over! Thank you for playing.", ButtonType.OK);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            // 팝업 닫기 후 게임 종료
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Platform.exit(); // 게임 종료
                }
            });
        });
    }
    //게임 승리 팝업
    private void showGameWinPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Game Win! Thank you for playing.", ButtonType.OK);
            alert.setTitle("Game Win");
            alert.setHeaderText(null);

            // 팝업 닫기 후 게임 종료
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Platform.exit(); // 게임 종료
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}