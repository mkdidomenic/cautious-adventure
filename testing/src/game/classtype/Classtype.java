/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.constructs.entity.character.Gharacter;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Mike
 */
public abstract class Classtype implements Serializable {

    public Gharacter gharacter;

    public Classtype(Gharacter g) {
        this.gharacter = g;
    }

    public static void setClasstype(Gharacter g, String ctype) {
        if (ctype.equals("Assassin")) {
            g.setClasstype(new ClasstypeAssassin(g));
        }
        if (ctype.equals("Viking")) {
            g.setClasstype(new ClasstypeViking(g));
        }
        if (ctype.equals("DummySkeleton")) {
            g.setClasstype(new ClasstypeDummySkeleton(g));
        }
        if (ctype.equals("Necromancer")) {
            g.setClasstype(new ClasstypeNecromancer(g));
        }
    }

    /**
     * setup the attributes for each specific class
     */
    public abstract void setupAttributes();

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
