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
public class StunBox extends DamageBox {

    public int stunframes;

    public StunBox(Spatial position, Spatial size) {
        super(position, size);
        this.stunframes = 0;
        this.damage = 0;
    }

    public void setStun(int stunframes) {
        this.stunframes = stunframes;
    }

    @Override
    public double hurts(Construct c) {
        if (c instanceof Gharacter) {
            Gharacter g = (Gharacter) c;
            g.stun(this.stunframes);
        }
        return super.hurts(c);
    }

}
