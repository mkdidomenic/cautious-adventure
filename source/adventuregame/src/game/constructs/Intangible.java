/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Intangible extends Construct {

    public Intangible(Spatial position, Spatial size) {
        super(position, size);
    }

    @Override
    public boolean tangible() {
        return false;
    }

}
