/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import java.awt.image.BufferedImage;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ImageBox extends Construct {

    public BufferedImage image;
    public int lifespan;
    public boolean hasParent;

    /**
     * specify the position, size, and lifespan
     *
     * @param position
     * @param size
     * @param lifespan
     * @param orientation
     */
    public ImageBox(Spatial position, Spatial size, int lifespan,
                    int orientation) {
        super(position, size);
        this.lifespan = lifespan;
        this.tangibility = false;
        this.x_orientation = orientation;
        this.image = null;
        this.hasParent = false;
    }

    @Override
    public void setParent(Construct c) {
        super.setParent(c);
        this.hasParent = true;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setImage(String filename) {
        this.image = ImageHandler.getPNG(filename);
    }

    public void setImage(String folder, String filename) {
        this.image = ImageHandler.getPNG(folder, filename);
    }

    @Override
    public void update() {
        super.update();
        if ((this.ticksExisted() > lifespan)) {
            this.remove();
        }
        if (this.hasParent && ((this.parent == null) || !(this.parent.exists))) {
            this.remove();
        }
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

}
