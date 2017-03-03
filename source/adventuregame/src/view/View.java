/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Game;
import game.Space;
import javax.swing.JFrame;

/**
 *
 * @author Mike
 */
public class View extends JFrame {

    public SpacePanel panel;

    public View() {
        this.setSize(600, 600);
        this.setVisible(true);
        this.panel = new SpacePanel();
        this.add(this.panel);
    }

    public void update() {

    }

    public void draw(Game game) {
        this.render(game.space);
    }

    public void render(Space s) {

    }

}
