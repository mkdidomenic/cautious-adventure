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
public class ProjectileArrow extends Projectile {

    public int damage = 10;

    public ProjectileArrow(Spatial position, Spatial size) {
        super(position, size);
        this.images.remove(0);
        this.addpng("arrow");
    }

    @Override
    public int hurts() {
        return this.getDamage();
    }

    @Override
    public void onCollision(Construct c) {
        this.remove();
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
