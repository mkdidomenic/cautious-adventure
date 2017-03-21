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
    public Construct parent;

    /**
     * specify the parent to hold
     *
     * @param parent
     */
    public ImageBox(Construct parent) {
        super(parent.position, parent.size);
        this.parent = parent;
        this.lifespan = 0;
        this.tangibility = false;
        image = null;
    }

    /**
     * for convenience, same as constructor with position and size, but dont
     * have to specify
     *
     * @param parent
     * @param lifespan
     */
    public ImageBox(Construct parent, int lifespan) {
        super(parent.position, parent.size);
        this.parent = null;
        this.lifespan = lifespan;
        this.tangibility = false;
        image = null;
    }

    /**
     * specify the position, size, and lifespan
     *
     * @param position
     * @param size
     * @param lifespan
     */
    public ImageBox(Spatial position, Spatial size, int lifespan) {
        super(position, size);
        this.lifespan = lifespan;
        this.parent = null;
        this.tangibility = false;
        image = null;
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
        if ((this.ticksExisted() > lifespan) && (this.parent == null)) {
            this.remove();
        }
        if ((this.ticksExisted() > lifespan) && (this.parent != null)) {
            if (!(this.parent.isExistant())) {
                this.remove();
            }
        }
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

}
