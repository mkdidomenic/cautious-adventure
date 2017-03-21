/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character;

import game.constructs.entity.character.ai.AI;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class NonPlayerCharacter extends Gharacter {

    public AI ai;

    public NonPlayerCharacter(Spatial position) {
        super(position);
        this.ai = null;
    }

    public NonPlayerCharacter(Spatial position, Spatial size) {
        super(position, size);
        this.ai = null;
    }

    public void setAI(AI ai) {
        this.ai = ai;
        if (ai != null) {
            ai.init();
        }
    }

    @Override
    public void update() {
        super.update();
        if (ai != null) {
            ai.update();
        }

    }

}
