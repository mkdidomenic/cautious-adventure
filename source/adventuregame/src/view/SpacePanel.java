/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Space;
import game.collision.Hitbox;
import game.constructs.Construct;
import java.awt.Color;
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

    public final boolean debug = true;

    public Space space;

    private int width;
    private int height;
    private boolean scalechange;

    public final double screenAngle = 30 * 2 * Math.PI / 360; // degrees to radians

    public double xm;
    public double ym;
    public double vm; // vertical multiplier
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
            vm = ((double) h) / (this.space.dimensions.y + this.space.dimensions.z);
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
            drawConstructF(g, c);
            if (debug) {
                drawHitboxF(g, c.hitbox);
            }
        }

    }

    public void drawBackground(Graphics g) {
        g.drawImage(this.background, 0, 0, this);
    }

    /**
     * draws from a frontwards 3D perspective
     *
     * @param g
     * @param c
     */
    public void drawConstructF(Graphics g, Construct c) {
        BufferedImage i = c.getImage();
        Spatial cpos = c.position.copy();
        // handle x stuff
        cpos.x = cpos.x - (c.size.x / 2 * c.x_orientation);
        int x = mapX(cpos.x);
        int sx = scaleX(c.size.x);
        // handle vertical stuff
        cpos.y = cpos.y - (c.size.y / 2);
        // cpos.z = cpos.z
        double v = cpos.y * Math.sin(screenAngle) + cpos.z * Math.cos(
                screenAngle);

        int y = mapY(v);

        int sy = scaleY(c.size.z);
        Image im = i.getScaledInstance(sx, sy, 0);
        g.drawImage(im, x, y, c.x_orientation * im.getWidth(this), im.getHeight(
                    this), this);
    }

    public void drawHitboxF(Graphics g, Hitbox box) {
        //g.drawRect(x, y, width, height);
        g.setColor(Color.red);
        double v1 = box.y1() * Math.sin(screenAngle) + box.z1() * Math.cos(
                screenAngle);
        double vs = box.size.y / 2 * Math.sin(screenAngle) + box.size.z * Math.cos(
                screenAngle);
        g.drawRect(mapX(box.x1()), mapY(v1), scaleX(box.size.x),
                   scaleY(vs));
        g.setColor(Color.orange);
        g.drawRect(mapX(box.x1()), mapY(v1 + vs / 2), scaleX(box.size.x),
                   scaleY(box.size.y * Math.cos(screenAngle)));
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
        int x = mapX(cpos.x);
        int y = mapY(cpos.y);
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

    public int mapX(double s) {
        int x;
        x = (int) ((s) * xm);
        return x;
    }

    public int mapY(double s) {
        int y;
        int h = this.getHeight();
        y = h - ((int) ((s) * ym));
        return y;
    }

}
