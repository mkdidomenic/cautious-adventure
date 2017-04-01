/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity;

import game.Game;
import java.awt.image.BufferedImage;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ProjectileNinjaStar extends Projectile {

    public final double default_damage = 10;

    public double moveSpeed = 2;

    public ProjectileNinjaStar(Spatial position) {
        super(position, new Spatial(2, 2, 2));
        this.images.remove(0);
        this.damage = default_damage;
        this.tangibility = false;
    }

    @Override
    public BufferedImage getImage() {
        return ImageHandler.getPNG("projectileNinjaStar", Long.toString(
                                   Game.instance.getCurrentFrame() % 3));
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

}
