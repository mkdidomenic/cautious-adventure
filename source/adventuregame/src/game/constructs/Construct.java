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
public class Construct {

    // space handling things
    public Spatial position;
    public Spatial size;

    public boolean exists;

    //image stuff
    public ArrayList<BufferedImage> images;
    public int imageIndex;

    // orientation (mostly for images)
    public int x_orientation; // 1 for forward, -1 for backward

    // for when doing things
    public int action_timer;

    // hitbox for collisions
    public Hitbox hitbox;

    public Construct(Spatial position, Spatial size) {
        this.position = position;
        this.size = size;
        this.x_orientation = 1;
        this.images = new ArrayList();
        this.imageIndex = 0;
        this.action_timer = 0;
        this.hitbox = new Hitbox(position, size);
        this.exists = true;
    }

    public void addImage(String filename) {
        this.images.add(ImageHandler.getImage(filename));
    }

    public void update() {
        //System.out.println(this.hitbox.position);
    }

    public void onCollision(Construct c) {

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

}
