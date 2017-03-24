/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.classtype.Classtype;
import game.classtype.ClasstypeAssassin;
import game.classtype.ClasstypeDummySkeleton;
import game.classtype.ClasstypeNecromancer;
import game.classtype.ClasstypeViking;
import game.constructs.Construct;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.NonPlayerCharacter;
import game.constructs.entity.character.PlayerCharacter;
import game.constructs.entity.character.ai.AI;
import game.constructs.entity.character.ai.AISimple;
import java.util.ArrayList;
import java.util.Random;
import main.Command;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Game {

    public ArrayList<Player> players;
    public Player player;
    public Space space;
    public static Game instance;

    public boolean friendlyFire;

    public Random random;

    public Game() {
        Game.instance = this;
        this.players = new ArrayList();
        this.random = new Random();
        this.friendlyFire = false;
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
        this.setupLevel();
    }

    public void addPlayer(String name) {
        Player p = new Player(name);
        if (this.players.isEmpty()) {
            this.player = p;
        }
        this.players.add(p);
        if (this.space != null) {
            Spatial pos = new Spatial(this.space.dimensions.x / 2,
                                      this.space.dimensions.y / 2,
                                      0);
            //Spatial size = new Spatial(100, 100, 10);
            PlayerCharacter pc = new PlayerCharacter(p, pos);
            //pc.setClasstype(new ClasstypeAssassin(pc));
            //pc.setClasstype(new ClasstypeViking(pc));
            //pc.setClasstype(new ClasstypeDummySkeleton(pc));
            pc.setClasstype(new ClasstypeNecromancer(pc));
            this.space.addPlayerC(pc);
        }

    }

    public void update() {
        this.space.update();
    }

    public void setupLevel() {
        NonPlayerCharacter npc = new NonPlayerCharacter(new Spatial(75, 50, 20));
        //npc.setClasstype(new ClasstypeViking(npc));
        //npc.setClasstype(new ClasstypeAssassin(npc));
        npc.setClasstype(new ClasstypeDummySkeleton(npc));
        AISimple ai = new AISimple(npc);
        //ai.action = Command.ACTION1;
        //AISmack ai = new AISmack(npc);
        //AIDummySkeleton ai = new AIDummySkeleton(npc);
        AI gai = (AI) ai;
        npc.setAI(gai);
        Construct con = new Construct(new Spatial(10, 10, 0),
                                      new Spatial(10, 10, 10));
        con.addpng("square");
        this.space.addConstruct(con);
        this.space.addConstruct(npc);

        //NonPlayerCharacter npc2 = new NonPlayerCharacter(new Spatial(75, 90, 20));
        //npc.setClasstype(new ClasstypeViking(npc));
        //npc2.setClasstype(new ClasstypeAssassin(npc2));
        //npc2.setAI(new AISimple(npc2));
        //this.space.addConstruct(npc2);
    }

    public boolean debug = false;

    public void handleCommand(int playerID, Command c) {
        if (c.isPlayerCommand) {
            for (PlayerCharacter pc : this.space.getPlayers()) {
                if (playerID == pc.ID()) {
                    pc.handleCommand(c);
                }
            }
        } else {
            if (c == Command.DEBUG) {
                this.debugger();
            }
        }
    }

    public void debugger() {
        this.debug = !(this.debug);
        if (true) {
            return;
        }
        if (this.debug) {
            for (Construct c : this.space.getConstructs()) {
                if (c instanceof Gharacter) {
                    Gharacter ch = (Gharacter) c;
                    ch.move_speed = 0;
                }
            }
        } else {
            for (Construct c : this.space.getConstructs()) {
                if (c instanceof Gharacter) {
                    Gharacter ch = (Gharacter) c;
                    ch.move_speed = ch.normal_move_speed;
                }
            }
        }
    }

    private void unused() {
        Gharacter g = new Gharacter(new Spatial(0, 0, 0));
        Classtype ct = new ClasstypeAssassin(g);
        ct = new ClasstypeViking(g);
        ct = new ClasstypeDummySkeleton(g);
        ct = new ClasstypeNecromancer(g);

    }

}
