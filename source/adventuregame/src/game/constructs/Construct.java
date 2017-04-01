/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import game.Game;
import game.collision.Hitbox;
import game.constructs.entity.character.Gharacter;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import main.GController;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Construct implements Comparable, Serializable {

    // space handling things
    public Spatial position;
    public Spatial size;

    public boolean exists;

    //image stuff
    public ArrayList<String> imageNames;
    public int imageIndex;

    // orientation (mostly for images)
    public int x_orientation; // 1 for forward, -1 for backward
    public boolean orientationCanChange = true;

    // for when doing things, counts down, zero when inactive
    public int actionTimer;

    // tick this was created at
    public long starttick;

    // hitbox for collisions
    public Hitbox hitbox;
    // tangibility for collisions
    public boolean tangibility;
    // damage given on collision
    public double damage;

    public Construct parent;

    private boolean ally;

    public Construct(Spatial position, Spatial size) {
        this.position = position;
        this.size = size;
        this.x_orientation = 1;
        this.imageNames = new ArrayList();
        this.imageIndex = 0;
        this.actionTimer = 0;
        this.hitbox = new Hitbox(position, size);
        this.exists = true;
        this.starttick = Game.instance.getCurrentFrame();
        this.tangibility = true;
        this.damage = 0;
        this.parent = null;
        this.ally = false;
    }

    public void addImage(String filename) {
        this.imageNames.add(filename);
    }

    public void clearImages() {
        this.imageNames.clear();
    }

    public void addpng(String filename) {
        this.imageNames.add("src/res/" + filename + ".png");
    }

    public long ticksExisted() {
        return (Game.instance.getCurrentFrame() - this.starttick);
    }

    public void setAlly(boolean allegiance) {
        this.ally = allegiance;
    }

    public void handleTimers() {
        if (this.actionTimer > 0) {
            this.actionTimer--;
        }
    }

    /**
     * sets the actions timer to a number of ticks if no other action is
     * occurring
     *
     * @param ticks
     * @return true if action timer is successfully set, false otherwise.
     */
    public boolean setActionTimer(int ticks) {
        if (this.actionTimer == 0) {
            this.actionTimer = ticks;
            return true;
        } else {
            return false;
        }
    }

    /**
     * sets the actions timer to a number of seconds if no other action is
     * occurring
     *
     * @param seconds
     * @return true if action timer is successfully set, false otherwise.
     */
    public boolean setActionTimerS(int seconds) {
        int ticks = seconds * GController.FPS;
        return this.setActionTimer(ticks);
    }

    public boolean canAct() {
        return (this.actionTimer == 0);
    }

    public void interruptActionTimer() {
        this.actionTimer = 0;
    }

    public void update() {
        this.handleTimers();
        //System.out.println(this);
    }

    /// Collision stuff
    public void onCollision(Construct c) {
        //System.out.println("COLLISION: " + this.getClass());
    }

    public boolean isTangible() {
        return this.tangibility;
    }

    public void setParent(Construct parent) {
        this.parent = parent;
    }

    public boolean shouldDamage(Construct g) {
        if (!(g instanceof Gharacter)) {
            return false;
        }
        if (g == this.parent) {
            return false;
        } else if ((this.isAlly() == g.isAlly()) && (!(Game.instance.friendlyFire))) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isInFrontOf(Spatial pos) {
        if (this.position.x > pos.x) {
            return (this.x_orientation < 0);
        } else if (this.position.x < pos.x) {
            return (this.x_orientation > 0);
        } else {
            return false;
        }
    }

    public boolean isAlly() {
        if (this.ally) {
            return true;
        } else if (this.parent != null) {
            if (this.parent.isAlly()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * What to do on collision with a gharacter
     *
     * @param g - the gharacter collided with
     */
    public void gharacteract(Gharacter g) {

    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    // end collision stuff
    public void outOfBounds(String s) {

    }

    public void setOrientation(int o) {
        if (orientationCanChange) {
            this.x_orientation = o;
        }
    }

    public BufferedImage getImage() {
        return ImageHandler.getImage(this.imageNames.get(imageIndex));
    }

    public void remove() {
        this.exists = false;
    }

    public boolean isExistant() {
        return this.exists;
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
