/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Game;
import java.util.ArrayList;
import utility.KeyHandler;
import view.GameView;

/**
 *
 * @author Mike
 */
public class GController {

    private boolean running;
    public Game game;
    public GameView view;
    public KeyHandler keyHandler;

    public boolean isHost;

    public boolean friendlyFire;

    public final static int FPS = 30; // frames per second
    public final static double SPF = 1 / ((double) FPS); // seconds per frame
    public static GController instance;
    private long currentFrame;

    public ArrayList<ArrayList<String>> playerandct;

    public GController() {
        GController.instance = this;
        this.playerandct = new ArrayList();
        this.friendlyFire = false;
        this.isHost = true;
    }

    public void setup() {
        this.game = new Game();
        this.currentFrame = 0;

        this.game.friendlyFire = this.friendlyFire;

        this.game.setupSpace();

        this.view = new GameView(this.game);
        this.keyHandler = new KeyHandler(this.view);

        for (ArrayList a : this.playerandct) {
            this.game.addPlayer((String) a.get(0), (String) a.get(1));
        }
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
                //System.out.println(this.currentFrame);
            } while ((endtime - starttime) < SPF);
            this.currentFrame++;
        }

    }

    public void update() {
        if (this.isHost) {
            this.handleLocalCommand();
            this.game.update();
            this.view.update();
            if (this.game.debug != this.view.spacePanel.debug) {
                this.view.spacePanel.debug = this.game.debug;
            }
        } else {
            this.view.update();
            if (this.game.debug != this.view.spacePanel.debug) {
                this.view.spacePanel.debug = this.game.debug;
            }
        }

    }

    public long getCurrentFrame() {
        return this.currentFrame;
    }

    public void handleLocalCommand() {
        for (char key : this.keyHandler.getKeys()) {
            game.handleCommand(0, Command.map(key));

        }
    }

}
