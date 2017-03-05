/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Game;
import javax.swing.JFrame;

/**
 *
 * @author Mike
 */
public class View extends JFrame {

    public SpacePanel panel;
    public Game game;
    private int width;
    private int height;

    public View(Game game) {
        this.width = 600;
        this.height = 600;
        this.setSize(this.width, this.height);
        this.setVisible(true);

        // spacepanel subcomponent
        this.panel = new SpacePanel(game.space);
        this.add(this.panel);
        this.panel.setSize(this.width, this.height);
        this.panel.setScale();
    }

    public void update() {
        if ((this.width != this.getWidth()) && (this.height != this.getHeight())) {
            this.width = this.getWidth();
            this.height = this.getHeight();
            this.panel.setScale();
        }
    }

}
