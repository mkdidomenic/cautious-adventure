/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import game.collision.Hitbox;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Construct implements Comparable {

    // space handling things
    public Spatial position;
    public Spatial size;

    public boolean exists;

    //image stuff
    public ArrayList<BufferedImage> images;
    public int imageIndex;

    // orientation (mostly for images)
    public int x_orientation; // 1 for forward, -1 for backward

    // for when doing things, counts down, zero when inactive
    public int actionTimer;
    public String state;

    // hitbox for collisions
    public Hitbox hitbox;

    public Construct(Spatial position, Spatial size) {
        this.position = position;
        this.size = size;
        this.x_orientation = 1;
        this.images = new ArrayList();
        this.imageIndex = 0;
        this.actionTimer = 0;
        this.state = "IDLE";
        this.hitbox = new Hitbox(position, size);
        this.exists = true;
    }

    public void addImage(String filename) {
        this.images.add(ImageHandler.getImage(filename));
    }

    public void addpng(String filename) {
        this.images.add(ImageHandler.getImage("src/res/" + filename + ".png"));
    }

    public void handleTimer() {
        if (this.actionTimer > 0) {
            this.actionTimer--;
        }
    }

    public boolean setAction(int ticks) {
        if (this.actionTimer == 0) {
            this.actionTimer = ticks;
            return true;
        } else {
            return false;
        }
    }

    public void update() {
        this.handleTimer();
        //System.out.println(this);
    }

    /// Collision stuff
    public void onCollision(Construct c) {
        //System.out.println("COLLISION: " + this.getClass());
    }

    public boolean tangible() {
        return true;
    }

    public double hurts(Construct c) {
        return 0;
    }

    // end collision stuff
    public void outOfBounds(String s) {

    }

    public void setOrientation(int o) {
        this.x_orientation = o;
    }

    public BufferedImage getImage() {
        return this.images.get(imageIndex);
    }

    public void remove() {
        this.exists = false;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Construct)) {
            System.out.println("unequal");
            return 0;
        } else {
            Construct c = (Construct) o;
            if (c.position.y > this.position.y) {
                return 1;
            } else if (c.position.y < this.position.y) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        String s = "Construct\n";
        s += "X: " + this.position.x + "\n";
        s += "Y: " + this.position.y + "\n";
        s += "Z: " + this.position.z + "\n\n";
        return s;
    }

}
