/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character.ai;

import game.Game;
import game.constructs.entity.character.Gharacter;
import java.util.Random;
import main.GController;

/**
 *
 * @author Mike
 */
public abstract class AI {

    public int timer;
    public int timerMax;
    public int sleepTimer;
    public Random random;
    public Gharacter gharacter;

    public AI(Gharacter gharacter) {
        this.gharacter = gharacter;
        this.random = new Random();
        this.timer = 0;
        this.timerMax = GController.FPS * 4;// default 3 second clock period
    }

    public void init() {

    }

    public void update() {
        handleTimer();

    }

    private void handleTimer() {
        if (this.timer > 0) {
            this.timer--;
        } else {
            this.timer = this.timerMax;
        }
    }

    public boolean percentChance(double percent) {
        return (this.random.nextDouble() <= (percent / 100));
    }

    public void closestTargetPC() {
        Game.instance.space.getPlayers();
        System.out.println(
                "AI GET CLOSEST TARGET PLAYER CHARACTER NOT IMPLEMENTED YET");
    }

}
