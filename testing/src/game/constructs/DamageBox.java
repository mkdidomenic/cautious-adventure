/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import game.constructs.entity.character.Gharacter;
import java.awt.image.BufferedImage;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class DamageBox extends Construct {

    public double default_damage = 1;
    public boolean knockdown;

    public BufferedImage image = null;

    public DamageBox(Spatial position, Spatial size) {
        super(position, size);
        this.tangibility = false;
        this.damage = default_damage;
        this.knockdown = false;
        this.image = null;
    }

    @Override
    public void gharacteract(Gharacter g) {
        if ((this.shouldDamage(g)) && (g.vulnerable(this))) {
            g.damage(this.damage);
            if (this.knockdown) {
                g.interruptActionTimer();
                g.setActionTimer(30);
                g.setState(Gharacter.State.FALLEN);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.ticksExisted() > 0) {
            this.remove();
        }
    }

    public void setImage(String folder, String filename) {
        this.image = ImageHandler.getPNG(folder, filename);
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

}
