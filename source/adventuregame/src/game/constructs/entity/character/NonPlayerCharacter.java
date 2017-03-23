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
        setupNPC();
    }

    public NonPlayerCharacter(Spatial position, Spatial size) {
        super(position, size);
        setupNPC();
    }

    private void setupNPC() {
        this.ai = null;
        this.ally = false;
    }

    public void setAlly(boolean ally) {
        this.ally = ally;
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
