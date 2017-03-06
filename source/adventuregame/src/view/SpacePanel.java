/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Space;
import game.collision.Hitbox;
import game.constructs.Construct;
import game.constructs.entity.Entity;
import game.constructs.entity.character.Gharacter;
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

    public boolean debug = false;

    public Space space;

    private int width;
    private int height;
    private boolean scalechange;

    public final double screenAngle = 30 * Math.PI / 180; // degrees to radians

    public double xm;
    public double ym;
    public double vm; // vertical multiplier
    public BufferedImage background;

    public SpacePanel(Space space) {
        super();
        this.space = space;
        this.background = ImageHandler.getBackground();
        this.scalechange = false;
    }

    public void setScale() {
        int w = this.getWidth();
        int h = this.getHeight();
        if ((w != this.width) && (h != this.height)) {
            this.scalechange = true;
            xm = ((double) w) / this.space.dimensions.x;
            ym = ((double) h) / this.space.dimensions.y;
            vm = ((double) h) / (this.space.dimensions.y * Math.sin(screenAngle) + this.space.dimensions.z * Math.cos(
                    screenAngle));
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

    /**
     * PAINTS THE WINDOW ON EACH FRAME
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw background
        if (this.scalechange) {
            this.drawBackground(g);
        }
        // draw components
        for (Construct c : this.space.getConstructs()) {
            drawConstructF(g, c);

            if ((c instanceof Entity)) {
                Entity e = (Entity) c;
                if (e instanceof Gharacter) {
                    Gharacter gh = (Gharacter) e;
                    drawHealthBar(g, c, gh.health / gh.max_health);
                }
            }

            if (debug) {
                drawHitboxF(g, c.hitbox);
                drawL(g, c.position.copy());
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
        // cpos.z = cpos.z
        int v = mapV(cpos.y, cpos.z);

        //int sv = scaleV(c.size.y, c.size.z);
        int sv = scaleZ(c.size.z);
        Image im = i.getScaledInstance(sx, sv, 0);
        g.drawImage(im, x, v - sv, c.x_orientation * im.getWidth(this),
                    im.getHeight(
                            this), this);
    }

    public void drawHealthBar(Graphics g, Construct c, double fraction) {
        int barWidth = (int) (.6 * scaleX(c.size.x));
        int barHeight = barWidth / 8;
        int barBound = (int) (0.05 * barWidth);
        int barFloat = barHeight;

        Spatial p = c.position.copy();
        p.z += c.size.z;
        int x = mapX(p.x);
        int y = mapV(p.y, p.z) - barFloat;
        g.setColor(Color.black);
        g.fillRect(x - barWidth / 2 - barBound,
                   y - barHeight / 2 - barFloat - barBound,
                   barWidth + barBound * 2,
                   barHeight + barBound * 2);

        g.setColor(Color.green);
        g.fillRect(x - barWidth / 2,
                   y - barHeight / 2 - barFloat,
                   barWidth,
                   barHeight);
    }

    public void drawHitboxF(Graphics g, Hitbox b) {
        b = new Hitbox(b.position.copy(), b.size.copy());
        b.position.z += b.size.z / 2;
        int ys = (int) (b.size.y * Math.cos(screenAngle));
        g.setColor(Color.red);

        g.drawRect(mapX(b.x1()), mapV(b.y1(), b.z1()), scaleX(b.size.x), ys);

        g.setColor(Color.orange);
        g.drawLine(mapX(b.x1()), ys + mapV(b.y1(), b.z1()), mapX(b.x1()), mapV(
                   b.y1(), b.z2()) + ys);
        g.drawLine(mapX(b.x2()), ys + mapV(b.y1(), b.z1()), mapX(b.x2()), mapV(
                   b.y1(), b.z2()) + ys);

        g.setColor(Color.yellow);
        g.drawRect(mapX(b.x1()), mapV(b.y1(), b.z2()), scaleX(b.size.x), ys);

    }

    public void drawL(Graphics g, Spatial s) {
        g.setColor(Color.MAGENTA);
        g.drawLine(mapX(0), mapV(0, 0), mapX(s.x), mapV(s.y, s.z));
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

    public int scaleZ(double z) {
        return (int) ((z) * vm);
    }

    public int scaleV(double y, double z) {
        double v = y * Math.sin(this.screenAngle) + z * Math.cos(
                this.screenAngle);
        return (int) ((v) * vm);
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

    public int mapV(double y, double z) {
        int v;
        int h = this.getHeight();
        double s = y * Math.sin(this.screenAngle) + z * Math.cos(
                this.screenAngle);
        v = h - ((int) ((s) * vm));
        return v;
    }

}
