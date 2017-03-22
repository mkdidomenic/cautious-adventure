/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import game.constructs.entity.character.Gharacter;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class KnockDownBox extends DamageBox {

    public int stunframes;

    public KnockDownBox(Spatial position, Spatial size) {
        super(position, size);
        this.stunframes = 0;
        this.damage = 0;
    }

    public void setKnockDown(int stunframes) {
        this.stunframes = stunframes;
    }

    @Override
    public void gharacteract(Gharacter g) {
        if ((g != this.parent) && true) {
            g.knockDown(this.stunframes);
        }
    }

}
