package me.sub.obj;

import me.sub.util.Vec2D;

import java.util.concurrent.ThreadLocalRandom;

public class Entity {

    public enum EntityType {
        POSITIVE, NEGATIVE, NEUTRAL
    }

    private Vec2D position;
    private EntityType type;
    private Vec2D velocity;

    public Entity(Vec2D position, EntityType type) {
        this.position = position;
        this.type = type;
//        this.velocity = new Vec2D(ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.3D, ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.3D);
        this.velocity = new Vec2D(0.0D, 0.0D);
    }

    public double getMass() {
        return 10.0D;
    }

    public void onUpdate() {
        this.position = this.position.add(this.velocity.getX(), this.velocity.getY());
    }

    public Vec2D getPosition() {
        return position;
    }

    public void setPosition(Vec2D position) {
        this.position = position;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public Vec2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec2D velocity) {
        this.velocity = velocity;
    }
}
