/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity;

import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ProjectileArrow extends Projectile {

    public final double default_damage = 10;

    public ProjectileArrow(Spatial position, Spatial size) {
        super(position, size);
        this.imageNames.remove(0);
        this.addpng("arrow");
        this.damage = default_damage;
        this.tangibility = false;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

}
