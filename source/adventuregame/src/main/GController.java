/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Game;
import view.View;

/**
 *
 * @author Mike
 */
public class GController {

    public Game game;
    public View view;

    public GController() {
        this.game = new Game();
        this.view = new View(this.game);

    }

    public void update() {
        this.game.update();
        this.view.update();
    }

}
