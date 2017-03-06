/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.constructs.PlayerCharacter;
import java.util.ArrayList;
import main.Command;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Game {

    public ArrayList<Player> players;
    public Space space;
    public static Game instance;

    public Game() {
        Game.instance = this;
        this.players = new ArrayList();
    }

    public void setupSpace() {
        this.space = new Space(100, 120, 100);
        for (Player p : this.players) {
            Spatial pos = new Spatial(this.space.dimensions.x / 2,
                                      this.space.dimensions.y / 2,
                                      0);
            //Spatial size = new Spatial(100, 100, 10);
            this.space.addPlayerC(new PlayerCharacter(p, pos));
        }
    }

    public void addPlayer(String name) {
        Player p = new Player(name);
        this.players.add(p);

    }

    public void update() {
        this.space.update();
    }

    public boolean debug = false;
    public void handleCommand(int playerID, Command c) {
        if (c.isPlayerCommand) {
            for (PlayerCharacter pc : this.space.players) {
                if (playerID == pc.ID()) {
                    pc.handleCommand(c);
                }
            }
        } else {
            if (c == Command.DEBUG){
                this.debug = !(this.debug);
            }
        }
    }

}
