/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Construct {

    public Spatial position;
    public Spatial size;
    public ArrayList<BufferedImage> images;
    public int imageIndex;

    public Construct(Spatial position, Spatial size) {
        this.position = position;
        this.size = size;
        this.images = new ArrayList();
        this.imageIndex = 0;
    }

    public void addImage(String filename) {
        this.images.add(ImageHandler.getImage(filename));
    }

    public void update() {

    }

    public BufferedImage getImage() {
        return this.images.get(imageIndex);
    }

}
