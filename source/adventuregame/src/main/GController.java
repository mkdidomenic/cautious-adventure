/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Game;
import game.Player;
import game.constructs.entity.character.PlayerCharacter;
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
    public GClient client;

    public boolean isHost;

    public boolean friendlyFire;

    public final static int FPS = 30; // frames per second
    public final static double SPF = 1 / ((double) FPS); // seconds per frame
    public static GController instance;

    public ArrayList<Player> players;

    public Player localplayer;

    public GController() {
        GController.instance = this;
        this.players = new ArrayList();
        this.friendlyFire = false;
        this.isHost = true;
        this.localplayer = null;
        this.client = null;
    }

    public void setup() {
        this.game = new Game();

        this.game.friendlyFire = this.friendlyFire;

        this.game.setupSpace();

        this.view = new GameView(this.game);
        this.keyHandler = new KeyHandler(this.view);

        for (Player p : this.players) {
            this.game.addPlayer(p);
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
            if (this.client != null) {
                Game g = this.client.sendGameMessage();
                System.out.println("got message");
                if (g != null) {
                    this.game = g;
                    System.out.println("set game");
                }
                PlayerCharacter p = this.game.space.getPlayerCharacter(
                        localplayer.ID);
            }
            this.view.update();
            if (this.game.debug != this.view.spacePanel.debug) {
                this.view.spacePanel.debug = this.game.debug;
            }
        }

    }

    public void handleLocalCommand() {
        for (char key : this.keyHandler.getKeys()) {
            game.handleCommand(this.localplayer.ID, Command.map(key));

        }
    }

}
