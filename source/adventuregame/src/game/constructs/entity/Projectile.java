/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity;

import game.constructs.Construct;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Projectile extends Entity {

    protected Construct parent;

    public Projectile(Spatial position, Spatial size) {
        super(position, size);
        parent = null;
        this.addpng("null");
    }

    public void setParent(Construct parent) {
        this.parent = parent;
    }

    @Override
    public void update() {
        super.update();
        //System.out.println(this.position);
        if (this.position.z <= 0) {
            this.remove();
        }
    }

    @Override
    public void outOfBounds(String s) {
        this.remove();
    }

    @Override
    public void onCollision(Construct c) {
        super.onCollision(c);
        //System.out.println("PROJECTILE COLLISION");
    }

}
