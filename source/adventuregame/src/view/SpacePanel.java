/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Space;
import game.constructs.Construct;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class SpacePanel extends JPanel {

    public Space space;

    private int width;
    private int height;
    private boolean scalechange;

    public double xm;
    public double ym;
    public BufferedImage background;

    public SpacePanel(Space space) {
        super();
        this.space = space;
        this.background = ImageHandler.getCCBackground();
        this.scalechange = false;
    }

    public void setScale() {
        int w = this.getWidth();
        int h = this.getHeight();
        if ((w != this.width) && (h != this.height)) {
            this.scalechange = true;
            xm = ((double) w) / this.space.dimensions.x;
            ym = ((double) h) / this.space.dimensions.y;
            this.setBackgroundImage(this.background);
        } else {
            this.scalechange = false;
        }
    }

    public void setBackgroundImage(Image background) {
        this.background = ImageHandler.toBufferedImage(
                background.getScaledInstance(this.getWidth(),
                                             this.getHeight(), 0));
    }

    public void update() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw background
        if (this.scalechange) {
            this.drawBackground(g);
        }
        // draw components
        for (Construct c : this.space.constructs) {
            drawConstructTD(g, c);
        }

    }

    public void drawBackground(Graphics g) {
        g.drawImage(this.background, 0, 0, this);
    }

    /**
     * *
     * Draws from a top down perspective
     *
     * @param g
     * @param c
     */
    public void drawConstructTD(Graphics g, Construct c) {
        BufferedImage i = c.getImage();
        Spatial cpos = c.position.copy();
        cpos.x = cpos.x - (c.size.x / 2);
        cpos.y = cpos.y + (c.size.y / 2);
        int x = mapX(cpos);
        int y = mapY(cpos);
        int sx = scaleX(c.size.x);
        int sy = scaleY(c.size.y);
        Image im = i.getScaledInstance(sx, sy, 0);
        // Image im = i.getScaledInstance((int) c.size.x, (int) c.size.y, 0);
        //System.out.println(x + " : " + y);
        g.drawImage(im, x, y, this);
        //g.drawRect(x1, y1, sx + x1, sy + y1);
    }

    public int scaleX(double x) {
        return (int) ((x) * xm);
    }

    public int scaleY(double y) {
        return (int) ((y) * ym);
    }

    public int mapX(Spatial s) {
        int x;
        x = (int) ((s.x) * xm);
        return x;
    }

    public int mapY(Spatial s) {
        int y;
        int h = this.getHeight();
        y = h - ((int) ((s.y) * ym));
        return y;
    }

}
