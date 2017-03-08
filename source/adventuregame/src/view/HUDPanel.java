/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import utility.ImageHandler;

/**
 *
 * @author Mike
 */
public class HUDPanel extends JPanel {

    public Game game;
    public Label title;
    public BufferedImage bg;

    public HUDPanel(Game game) {
        this.title = new Label();
        this.bg = ImageHandler.getPNG("hudbg");;

    }

    public void setScale() {
        this.setBackgroundImage(this.bg);
    }

    public void setBackgroundImage(Image background) {
        int w = this.getWidth();
        int h = this.getHeight();
        if (h != 0 && w != 0) {
            this.bg = ImageHandler.toBufferedImage(
                    background.getScaledInstance(this.getWidth(),
                                                 this.getHeight(), 0));
        }
    }

    public void update() {

    }

    @Override
    public void paintComponent(Graphics g) {
        // background
        g.drawImage(bg, 0, 0, this);
    }

}
