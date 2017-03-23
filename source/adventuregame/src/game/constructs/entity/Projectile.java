/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity;

import game.constructs.Construct;
import game.constructs.entity.character.Gharacter;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Projectile extends Entity {

    public Projectile(Spatial position, Spatial size) {
        super(position, size);
        parent = null;
        this.addpng("null");
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
    public void gharacteract(Gharacter g) {
        if ((this.shouldDamage(g)) && g.vulnerable(this)) {
            g.damage(this.damage);
        }
    }

    @Override
    public void outOfBounds(String s) {
        this.remove();
    }

    @Override
    public void onCollision(Construct c) {
        super.onCollision(c);
        if (this.shouldDamage(c) || c.isTangible()) {
            this.remove();
        }
    }

}
