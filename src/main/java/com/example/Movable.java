package com.example;

public interface Movable {
    void move();
    double getDx();
    double getDy();
    double setDx(double dx);
    double setDy(double dy);
    void pause();
    void resume();
    boolean isCollisionDetected(Shape other);
}
