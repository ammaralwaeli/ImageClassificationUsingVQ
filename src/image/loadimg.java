package image;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class loadimg {

    public int wid;
    public int hgt;
    public int[][] red;
    public int[][] gre;
    public int[][] blu;
    private String address;

    public BufferedImage load1(String s) throws IOException {
        int i, j;
        int hg, wd;
        address = s;
        BufferedImage img = ImageIO.read(new File(address));
        wid = img.getWidth(); 
        hgt = img.getHeight();
        hg = (hgt - 1);
        wd = (wid - 1);
        red = new int[wd][hg];
        gre = new int[wd][hg];
        blu = new int[wd][hg];
        BufferedImage lum = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
       
        for (j = 0; j < hg; j++) {
            for (i = 0; i < wd; i++) {
                Color b1 = new Color(img.getRGB(i, j));
                red[i][j] = b1.getRed();
                gre[i][j] = b1.getGreen();
                blu[i][j] = b1.getBlue();
                Color avg = new Color(red[i][j], gre[i][j], blu[i][j]);
                lum.setRGB(i, j, avg.getRGB());
            }
        }

        return lum;

    }
}