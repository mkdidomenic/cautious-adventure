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

    public double damage = 10;

    public ProjectileArrow(Spatial position, Spatial size) {
        super(position, size);
        this.images.remove(0);
        this.addpng("arrow");
    }

    @Override
    public double hurts(Construct c) {
        if (c == this.parent){
            return 0 ;
        }
        return this.damage;
    }

    @Override
    public void onCollision(Construct c) {
        //System.out.println("arrow");
        this.remove();
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }
   
    @Override
    public boolean tangible(){
        return false;
    }

}
