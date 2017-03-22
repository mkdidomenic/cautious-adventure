/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character.ai;

import game.Game;
import game.constructs.entity.character.Gharacter;

/**
 *
 * @author Mike
 */
public abstract class AI {

    public int timer;
    public int sleepTimer;
    public Gharacter gharacter;

    public AI(Gharacter gharacter) {
        this.gharacter = gharacter;
        timer = 0;
    }

    public void init() {

    }

    public void update() {

    }

    public void closestTargetPC() {
        Game.instance.space.getPlayers();
        System.out.println(
                "AI GET CLOSEST TARGET PLAYER CHARACTER NOT IMPLEMENTED YET");
    }

}
