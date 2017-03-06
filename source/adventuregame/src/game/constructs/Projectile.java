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
public class Projectile extends Entity {

    public Projectile(Spatial position, Spatial size) {
        super(position, size);
    }

    public void onCollision(Construct c) {
        this.remove();
    }

}
