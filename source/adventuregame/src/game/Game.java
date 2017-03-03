/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.constructs.Player;
import main.Command;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Game {

    public Space space;

    public Game() {
        this.space = new Space(100, 100, 100);
        this.space.addPlayer(new Player(new Spatial(this.space.dimensions.x / 2,
                                                    this.space.dimensions.y / 2,
                                                    0)));
    }

    public void update() {
        this.space.update();
    }

    public void handleCommand(Command c) {
        if (c.isPlayerCommand) {
            this.space.players.get(0).handleCommand(c);
        }
    }

}
