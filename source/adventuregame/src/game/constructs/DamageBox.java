/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import java.awt.image.BufferedImage;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class DamageBox extends Construct {

    public double default_damage = 1;
    public Construct parent;

    public DamageBox(Spatial position, Spatial size) {
        super(position, size);
        this.tangibility = false;
        this.damage = default_damage;
    }

    public void setParent(Construct parent) {
        this.parent = parent;
    }

    @Override
    public double hurts(Construct c) {
        if (c == this.parent) {
            return 0;
        }
        return this.damage;
    }

    @Override
    public void update() {
        super.update();
        if (this.ticksExisted() > 0) {
            this.remove();
        }
    }

    @Override
    public BufferedImage getImage() {
        return null;
    }

}
