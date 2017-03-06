/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.constructs.Construct;
import game.constructs.entity.character.PlayerCharacter;
import java.util.ArrayList;
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
        this.constructs.sort(null);
    }

    public LinkedList<Construct> getConstructs() {
        return (LinkedList) this.constructs.clone();
    }

    public void update() {
        //debug();
        //System.out.println("SPACE: " + this.getConstructs());
        this.constructs.sort(null);//sort for ordering
        ArrayList<Construct> removed = new ArrayList();
        for (Construct c : this.getConstructs()) {
            c.update();
            enforceBounds(c);
            collisionCheck(c);
            if (!(c.exists)) {
                removed.add(c);
            }
        }
        for (Construct c : removed) {
            this.constructs.remove(c);
        }
    }

    public void collisionCheck(Construct c) {
        for (Construct a : this.getConstructs()) {
            if (CollisionHandler.checkCollision(c.hitbox, a.hitbox)) {
                c.onCollision(c);
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

    public void debug() {
        //System.out.println(this.constructs);
        for (Construct c : this.constructs) {
            System.out.println(c.toString());
        }
    }

    public Spatial dims() {
        return this.dimensions;
    }

}
