package me.sub;

import me.sub.obj.Entity;
import me.sub.util.Vec2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Panel extends JPanel {

    private final List<Entity> entityList;

    public Panel() {
        this.entityList = new ArrayList<>();

        for (int i = 0; i < 300; i++) {
            final Vec2D pos = new Vec2D(ThreadLocalRandom.current().nextInt(0, 1000), ThreadLocalRandom.current().nextInt(0, 1000));
            this.entityList.add(new Entity(pos, Entity.EntityType.values()[ThreadLocalRandom.current().nextInt(0, 3)]));
        }

        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
    }

    public void onUpdate() {
        new ArrayList<>(entityList).forEach(e -> {
            e.onUpdate();

            if (e.getPosition().getX() >= this.getWidth()) {
                e.setPosition(e.getPosition().add(-this.getWidth(), 0));
            }else if (e.getPosition().getX() <= 0) {
                e.setPosition(e.getPosition().add(this.getWidth(), 0));
            }else if (e.getPosition().getY() >= this.getHeight()) {
                e.setPosition(e.getPosition().add(0, -this.getHeight()));
            }else if (e.getPosition().getY() <= 0) {
                e.setPosition(e.getPosition().add(0, this.getHeight()));
            }

            this.entityList.stream().filter(e1 -> e1 != e).forEach(e1 -> {
                final double G = 0.01D; // Gravitational constant
                final double distanceX = e1.getPosition().getX() - e.getPosition().getX();
                final double distanceY = e1.getPosition().getY() - e.getPosition().getY();
                double distanceSquared = distanceX * distanceX + distanceY * distanceY;
                double distance = Math.sqrt(distanceSquared);

                // Apply gravitational force that depletes over distance
                double forceMagnitude = G * e.getMass() * e1.getMass() / distanceSquared;
                double forceX = forceMagnitude * distanceX / distance;
                double forceY = forceMagnitude * distanceY / distance;

                // Convert force to acceleration (F = ma, so a = F/m)
                e1.setVelocity(e1.getVelocity().add(-forceX / e1.getMass(), -forceY / e1.getMass()));
                e.setVelocity(e.getVelocity().add(forceX / e.getMass(), forceY / e.getMass()));

                // Check for collision
                if (distance <= 4) { // 4 is the sum of both radii (2 + 2)
                    // Calculate normal vector
                    double nx = distanceX / distance;
                    double ny = distanceY / distance;

                    // Calculate relative velocity
                    double dvx = e1.getVelocity().getX() - e.getVelocity().getX();
                    double dvy = e1.getVelocity().getY() - e.getVelocity().getY();

                    // Calculate relative velocity in terms of the normal direction
                    double velocityAlongNormal = dvx * nx + dvy * ny;

                    // Do not resolve if velocities are separating
                    if (velocityAlongNormal > 0) {
                        return;
                    }

                    // Calculate restitution (energy loss factor, 1.0 is perfectly elastic)
                    double restitution = 0.8;

                    // Calculate impulse scalar
                    double impulse = -(1 + restitution) * velocityAlongNormal;
                    impulse /= 1 / e.getMass() + 1 / e1.getMass();

                    // Apply impulse
                    double impulseX = impulse * nx;
                    double impulseY = impulse * ny;

                    e.setVelocity(e.getVelocity().add(-impulseX / e.getMass(), -impulseY / e.getMass()));
                    e1.setVelocity(e1.getVelocity().add(impulseX / e1.getMass(), impulseY / e1.getMass()));
                }
            });
        });
    }

    @Override
    public void paint(Graphics graphics) {
        Toolkit.getDefaultToolkit().sync();
        super.paint(graphics);
        final Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        this.onUpdate();

        for (Entity e : entityList) {
            final Vec2D pos = e.getPosition();

            switch (e.getType()) {
                case PAPER:
                    graphics.setColor(Color.BLUE);
                    break;
                case ROCK:
                    graphics.setColor(Color.GREEN);
                    break;
                case SCISSORS:
                    graphics.setColor(Color.RED);
                    break;
            }

            final double radius = 2.0D;
            Shape theCircle = new Ellipse2D.Double(e.getPosition().getX() - radius, e.getPosition().getY() - radius, 2.0 * radius, 2.0 * radius);
            g2d.fill(theCircle);
        }
    }
}
