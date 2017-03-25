/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.constructs.Construct;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.PlayerCharacter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import utility.CollisionHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Space {

    public Spatial dimensions;
    public LinkedList<Construct> constructs;
    public LinkedList<Gharacter> gharacters;
    public LinkedList<PlayerCharacter> players;

    /**
     * takes a dimensions parameter MAIN CONSTRUCTOR TO EDIT, influences all
     * others base constructor
     *
     * @param dimensions
     */
    public Space(Spatial dimensions) {
        this.dimensions = dimensions;
        this.constructs = new LinkedList();
        this.gharacters = new LinkedList();
        this.players = new LinkedList();

    }

    /**
     * takes double parameters for the dimensions
     *
     * @param x - x width
     * @param y - y width
     * @param z - z height
     */
    public Space(double x, double y, double z) {
        this(new Spatial(x, y, z));
    }

    /**
     * default constructor to zero dimensions
     */
    public Space() {
        this(0, 0, 0);
    }

    ////////////////END CONSTRUCTORS/////////////
    public void addPlayerC(PlayerCharacter pc) {
        this.players.add(pc);
        this.addConstruct(pc);
    }

    public void addConstruct(Construct c) {
        this.constructs.add(c);
        if (c instanceof Gharacter) {
            Gharacter g = (Gharacter) c;
            this.gharacters.add(g);
        }
        this.constructs.sort(null);
    }

    public LinkedList<PlayerCharacter> getPlayers() {
        return (LinkedList) this.players.clone();
    }

    public LinkedList<PlayerCharacter> getGharacters() {
        return (LinkedList) this.gharacters.clone();
    }

    public LinkedList<Construct> getConstructs() {
        return (LinkedList) this.constructs.clone();
    }

    public PlayerCharacter getPlayerCharacter(Player player) {
        for (PlayerCharacter p : this.getPlayers()) {
            if (p.player == player) {
                return p;
            }
        }
        return null;
    }

    public void update() {
        //debug();
        //System.out.println("SPACE: " + this.getConstructs());
        try {
            this.constructs.sort(null);//sort for view ordering
        } catch (ArrayIndexOutOfBoundsException | ConcurrentModificationException e) {
            System.out.println("ERROR SORTING LIST - NONESSENTIAL");
        }
        //setup update loop
        ArrayList<Construct> removed = new ArrayList();
        LinkedList<Construct> consts = this.getConstructs();
        // update loop
        for (Construct c : consts) {
            c.update(); // update
            enforceBounds(c); // make sure inside bounds
            if (!(c.exists)) { // add to list to be removed
                removed.add(c);
            }
        }
        // remove everything that needs to be removed
        for (Construct c : removed) {
            this.constructs.remove(c);
            if (c instanceof Gharacter) {
                Gharacter g = (Gharacter) c;
                this.gharacters.remove(g);
                if (g instanceof PlayerCharacter) {
                    PlayerCharacter p = (PlayerCharacter) g;
                    this.players.remove(p);
                }
            }
        }
        // handle collisions
        consts = this.getConstructs();
        LinkedList<Construct> constrs = this.getConstructs();
        for (Construct c : consts) {
            collisionCheck(c, constrs);
        }
    }

    public void collisionCheck(Construct c, LinkedList<Construct> consts) {
        for (Construct a : consts) {
            if (CollisionHandler.checkCollision(c.hitbox, a.hitbox)) {
                c.onCollision(a);
            }
        }
    }

    public void enforceBounds(Construct c) {
        //boolean found_infraction = false;
        if (c.position.x > this.dimensions.x) {
            c.outOfBounds("x+");
        }
        if (c.position.y > this.dimensions.y) {
            c.outOfBounds("y+");
        }
        if (c.position.z > this.dimensions.z) {
            c.outOfBounds("z+");
        }
        //
        if (c.position.x < 0) {
            c.outOfBounds("x-");
        }
        if (c.position.y < 0) {
            c.outOfBounds("y-");
        }
        if (c.position.z < 0) {
            c.outOfBounds("z-");
        }

    }

    public boolean outsideBounds(Spatial pos) {
        boolean found_infraction = false;
        if (pos.x > this.dimensions.x) {
            found_infraction = true;
        }
        if (pos.y > this.dimensions.y) {
            found_infraction = true;
        }
        if (pos.z > this.dimensions.z) {
            found_infraction = true;
        }
        //
        if (pos.x < 0) {
            found_infraction = true;
        }
        if (pos.y < 0) {
            found_infraction = true;
        }
        if (pos.z < 0) {
            found_infraction = true;
        }
        return found_infraction;
    }

    /**
     *
     * Finds the character g is looking at.
     *
     * @param g character to check from
     * @param precision precision to check with
     * @return character g is looking at
     */
    public Gharacter trace(Gharacter g, double precision) {
        Spatial pos = g.position.copy();
        pos.x = pos.x + (g.x_orientation * (g.size.x + precision));
        while (!(outsideBounds(pos))) {
            for (Construct c : this.getConstructs()) {
                if (c instanceof Gharacter) {
                    Gharacter b = (Gharacter) c;
                    if (CollisionHandler.checkCollision(b.hitbox, pos)) {
                        return b;
                    }
                }
            }
            pos.x = pos.x + (g.x_orientation * precision);
        }
        return null;
    }

    public Spatial dims() {
        return this.dimensions;
    }

    public void debug() {
        //System.out.println(this.constructs);
        for (Construct c : this.constructs) {
            System.out.println(c.toString());
        }
    }

}
