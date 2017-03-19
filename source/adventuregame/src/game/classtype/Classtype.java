/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.constructs.entity.character.Gharacter;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mike
 */
public abstract class Classtype {

    public Gharacter gharacter;

    public Classtype(Gharacter g) {
        this.gharacter = g;
    }

    /**
     * calls the appropriate ability function within the classtype
     *
     * @param i
     */
    public abstract void ability(int i);

    /**
     * gets the image that should be displayed for the character
     *
     * @return
     */
    public abstract BufferedImage getImage();

    public void update() {
    }

}
