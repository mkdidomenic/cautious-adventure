/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.collision;

import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Hitbox {

    public Spatial position;
    public Spatial size;

    public Hitbox(Spatial position, Spatial size) {
        this.position = position;
        this.size = size;
    }

    public double x1() {
        return this.position.x - this.size.x / 2;
    }

    public double y1() {
        return this.position.y - this.size.y / 2;
    }

    public double z1() {
        return this.position.z;
    }

    public double x2() {
        return this.position.x + this.size.x / 2;
    }

    public double y2() {
        return this.position.y + this.size.y / 2;
    }

    public double z2() {
        return this.position.z + this.size.z;
    }

}
