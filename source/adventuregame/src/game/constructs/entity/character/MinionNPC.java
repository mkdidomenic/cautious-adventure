/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character;

import utility.Spatial;

/**
 *
 * @author Mike
 */
public class MinionNPC extends NonPlayerCharacter {

    public Gharacter parentG;

    public MinionNPC(Spatial position, Gharacter parent) {
        super(position);
        this.parentG = parent;
        this.parent = parent;
        this.ally = true;
    }

}
