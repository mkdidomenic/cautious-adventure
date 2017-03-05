/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Game;
import utility.KeyHandler;
import view.View;

/**
 *
 * @author Mike
 */
public class GController {

    private boolean running;
    public Game game;
    public View view;
    public KeyHandler keyHandler;

    public final int FPS = 30; // frames per second
    public final double SPF = 1 / ((double) FPS); // seconds per frame

    public GController() {
        this.game = new Game();

        this.game.addPlayer("Mike");
        this.game.setupSpace();

        this.view = new View(game);
        this.keyHandler = new KeyHandler(this.view);

    }

    public void run() {
        this.running = true;
        double starttime;
        double endtime;
        while (this.running) {
            starttime = (double) System.currentTimeMillis() / 1000.0;
            this.update();
            do {
                endtime = (double) System.currentTimeMillis() / 1000.0;
            } while ((endtime - starttime) < SPF);
        }

    }

    public void update() {
        this.handleKeys();
        this.handleCommand();
        this.game.update();
        this.view.update();

    }

    public void handleKeys() {

        //this.game.
        boolean debug = true;
        if (debug) {
            //boolean b = this.keyHandler.haskey();
            //System.out.println(b);
            //System.out.println(this.keyHandler.getKeys());
            //if (this.keyHandler.haskey()) {
            //    System.out.println("lkjadsf");
            //    System.out.println(this.keyHandler.currentkeys);
            //}
        }
    }

    public void handleCommand() {
        for (char key : this.keyHandler.getKeys()) {
            game.handleCommand(0, Command.map(key));

        }
    }

}
