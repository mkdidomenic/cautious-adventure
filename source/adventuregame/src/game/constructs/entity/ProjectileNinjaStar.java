/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity;

import game.constructs.Construct;
import java.awt.image.BufferedImage;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ProjectileNinjaStar extends Projectile {

    public final double default_damage = 10;

    public ProjectileNinjaStar(Spatial position) {
        super(position, new Spatial(2, 1, 2));
        this.images.remove(0);
        this.damage = default_damage;
    }

    @Override
    public BufferedImage getImage() {
        return ImageHandler.getPNG("projectileNinjaStar", "1");//Long.toString(GController.instance.getCurrentFrame() % 1));
    }

    @Override
    public double hurts(Construct c) {
        if (c == this.parent) {
            return 0;
        }
        return this.damage;
    }

    @Override
    public void onCollision(Construct c) {
        //System.out.println(this.getClass());
        //System.out.println("arrow");
        this.remove();
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public boolean tangible() {
        return false;
    }

}
