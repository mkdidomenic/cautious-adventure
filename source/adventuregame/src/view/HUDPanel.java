/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Game;
import game.constructs.entity.character.PlayerCharacter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;
import utility.ImageHandler;

/**
 *
 * @author Mike
 */
public class HUDPanel extends JPanel {

    public Label title;
    public BufferedImage bg;

    public HUDPanel() {
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

        // paint other stuff
        int borderx = this.getWidth() / 40;
        int bordery = this.getHeight() / 5;
        int w = this.getWidth() - borderx * 2 - borderx;
        int h = this.getHeight() - bordery * 2 - bordery;
        int divw = (int) ((1.0 / 8) * w);

        int fsize = 16;
        g.setColor(Color.white);

        int start = borderx;
        if (this.hasPlayers()) {
            LinkedList<PlayerCharacter> pc = Game.instance.space.getPlayers();
            for (int i = 0; i < pc.size(); i++) {
                PlayerCharacter p = pc.get(i);
                String name = p.player.name;
                //g.drawRect(start, bordery + 5, divw, h);
                // name
                g.setFont(new Font("Chalkboard", Font.PLAIN, fsize));
                g.drawString(name, start, bordery + fsize / 2);
                // health
                g.drawString(
                        "Health: " + Integer.toString((int) p.health) + "/" + Integer.toString(
                                (int) p.max_health), start,
                        bordery + (int) (fsize * 1.5));
                start = start + divw;
            }
        }
    }

    private boolean hasPlayers() {
        if (Game.instance == null) {
            return false;
        }
        if (Game.instance.space == null) {
            return false;
        }
        if (Game.instance.space.getPlayers() == null) {
            return false;
        }
        if (Game.instance.space.getPlayers().size() == 0) {
            return false;
        }
        return true;
    }

}
