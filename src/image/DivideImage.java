/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ammar ALwaeli
 */
public class DivideImage {

    private int rows;
    private int cols;
    private int chunks;

    public BufferedImage[] divide(BufferedImage img) {
        int chunkWidth = 64; // determines the chunk width and height
        int chunkHeight = 64;
        int count = 0;
        rows = img.getHeight() / 64;
        cols = img.getWidth() / 64;
        //System.out.print("rows "+rows+"\ncols "+cols+"\nwd "+img.getWidth()+"\nht "+img.getHeight()+"\n");
        chunks = cols * rows;
        //System.out.print(chunks+"\n");
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, img.getType());
                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(img, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
    public int getClos(){
        return cols;
    }
    public int getRows(){
        return rows;
    }
}
