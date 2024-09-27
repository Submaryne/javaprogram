package me.sub.util;

public class Vec2D {

    private double x, y;

    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vec2D add(double x, double y) {
        return new Vec2D(this.x + x, this.y + y);
    }

    public double distanceTo(Vec2D position) {
        final double distanceX = position.getX() - this.getX();
        final double distanceY = position.getY() - this.getY();
        return Math.sqrt(distanceY * distanceY + distanceX * distanceX);
    }
}



