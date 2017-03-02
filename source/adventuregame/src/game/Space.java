/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.constructs.Construct;
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

    /**
     * takes a dimensions parameter MAIN CONSTRUCTOR TO EDIT, influences all
     * others base constructor
     *
     * @param dimensions
     */
    public Space(Spatial dimensions) {
        this.dimensions = dimensions;
        this.constructs = new LinkedList();
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
    public void addConstruct(Construct c) {
        this.constructs.add(c);
    }

    public void update() {

    }

    public Spatial dims() {
        return this.dimensions;
    }

}
