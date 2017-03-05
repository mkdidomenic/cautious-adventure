/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.constructs.Construct;
import game.constructs.PlayerCharacter;
import java.util.LinkedList;
import java.util.List;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Space {

    public Spatial dimensions;
    public List<Construct> constructs;
    public List<PlayerCharacter> players;

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
    }

    public void update() {
        //debug();
        for (Construct c : this.constructs) {
            c.update();
            enforceBounds(c);
        }
    }

    public void enforceBounds(Construct c) {
        if (c.position.x > this.dimensions.x) {
            c.position.x = this.dimensions.x - c.size.x;
        }
        if (c.position.y > this.dimensions.y) {
            c.position.y = this.dimensions.y - c.size.y;
        }
        if (c.position.z > this.dimensions.z) {
            c.position.z = this.dimensions.z - c.size.z;
        }
        //
        if (c.position.x < 0) {
            c.position.x = 0 + c.size.x;
        }
        if (c.position.y < 0) {
            c.position.y = 0 + c.size.y;
        }
        if (c.position.z < 0) {
            c.position.z = 0 + c.size.z;
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
