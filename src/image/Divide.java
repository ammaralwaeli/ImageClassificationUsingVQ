package image;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Divide {

    BufferedImage[] colImg;
    private int rows;
    private int cols;
    private int chunks;
    public JLabel[] labels;
    public JLabel label;
    public JFrame frame, f2;
    public BufferedImage img2,res;

    public BufferedImage createAndShowUI(BufferedImage img) {
        frame = new JFrame("Satalite Image Classification");
        initComponents(img);
        frame.pack();
        //frame.setVisible(true);
        Container c = frame.getContentPane();
        img2 = new BufferedImage(c.getWidth(), c.getHeight(), 5);
        Graphics2D g2d = img2.createGraphics();
        c.printAll(g2d);
        g2d.dispose();
        return img2;
    }

    public BufferedImage[] divide(BufferedImage img) {
        int chunkWidth = 2; // determines the chunk width and height
        int chunkHeight = 2;
        int count = 0;
        rows = img.getHeight() / 2;
        cols = img.getWidth() / 2;
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

    public void initComponents(BufferedImage img) {

        BufferedImage[] imgs = divide(img);
        img2 = new BufferedImage(img.getWidth(), img.getHeight(), 5);
        frame.getContentPane().setLayout(new GridLayout(rows, cols));
        labels = new JLabel[imgs.length];
        colImg = new BufferedImage[imgs.length];

        for (int i = 0; i < imgs.length; i++) {
            if (avg(imgs[i]) >= 0 && avg(imgs[i]) <= 100) {
                colImg[i] = coloring(imgs[i], Color.blue);
            } else if (avg(imgs[i]) >= 101 && avg(imgs[i]) <= 150) {
                colImg[i] = coloring(imgs[i], Color.darkGray);
            } else if (avg(imgs[i]) >= 151 && avg(imgs[i]) <= 200) {
                colImg[i] = coloring(imgs[i], Color.GREEN);
            } else if (avg(imgs[i]) >= 201 && avg(imgs[i]) <= 255) {
                colImg[i] = coloring(imgs[i], Color.yellow);
            }
        }
        //create JLabels with split images and add to frame contentPane
        for (int i = 0; i < imgs.length; i++) {
            labels[i] = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(colImg[i].getSource())));
            frame.getContentPane().add(labels[i]);
        }
    }

    public BufferedImage coloring(BufferedImage img, Color color) {
        //System.out.print(img.getType()+"\n");
        BufferedImage lum = new BufferedImage(img.getWidth(), img.getHeight(), 5);
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                lum.setRGB(i, j, color.getRGB());
            }
        }
        return lum;
    }

    public long avg(BufferedImage bi) {
        int hg = bi.getWidth();
        int wd = bi.getHeight();
        long sum = 0, count = 0, r;
        Color pixel;
        for (int i = 0; i < hg; i++) {
            for (int j = 0; j < wd; j++) {
                pixel = new Color(bi.getRGB(i, j));
                r = (pixel.getRed() + pixel.getBlue() + pixel.getGreen()) / 3;
                sum += r;
                count++;
            }
        }
        return (sum / count);
    }

    int getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
