/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mike
 */
public class ImageHandler {

    public static BufferedImage getImage(String filename) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filename));
            return image;
        } catch (IOException ex) {
            System.out.println("utility.ImageHandler: Error loading " + filename);
        }
        return null;
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     * @see
     * http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
     * @see https://code.google.com/archive/p/game-engine-for-java/source#31
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null),
                                                 img.getHeight(null),
                                                 BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static BufferedImage getCCFrogglet() {
        return getImage("src/res/frogglet.jpg");
    }

    public static BufferedImage getBlackSquare() {
        return getImage("src/res/square.png");
    }

    public static BufferedImage getCCBackground() {
        return getImage("src/res/bg.jpg");
    }

}
