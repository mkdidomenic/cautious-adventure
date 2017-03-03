/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Entity extends Construct {

    public Spatial velocity;
    public Spatial acceleration;

    public Entity(Spatial position) {
        super(position);
        this.velocity = new Spatial(0, 0, 0);
        this.acceleration = new Spatial(0, 0, 0);
    }

    @Override
    public String toString() {
        String s = "";
        s += "Entity\n";
        s += ("x: " + this.position.x + "\n");
        s += ("y: " + this.position.y + "\n");
        s += ("z: " + this.position.z + "\n");
        s += ("\n");
        return s;
    }

    @Override
    public void update() {
        super.update();
        this.position.add(velocity);
        this.velocity.add(acceleration);
        this.gravity();

    }

    public void move(Spatial s) {
        this.position.add(s);
    }

    public void gravity() {
        // for now
        double gravity = -0.2;
        // if above ground
        if (this.position.z > 0) {
            this.velocity.z += gravity;
        }
        // if below or on ground
        if (this.position.z <= 0) {
            this.position.z = 0;
            this.velocity.z = 0;
            this.acceleration.z = 0;
        }
    }

}
