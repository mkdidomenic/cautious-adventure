/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character.ai;

import game.constructs.entity.character.Gharacter;
import main.Command;

/**
 *
 * @author Mike
 */
public class AISimple extends AI {

    public AISimple(Gharacter gharacter) {
        super(gharacter);
    }

    @Override
    public void init() {
        this.gharacter.setOrientation(-1);
    }

    @Override
    public void update() {
        this.gharacter.handleCommand(Command.ACTION4);
    }

}
