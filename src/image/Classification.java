package image;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Classification {

    BufferedImage C[], imgs2[];
    Color blocks[];
    private int[] D;
    private BufferedImage img2;
    private JFrame frame;
    private JLabel labels[];
    private int c, r;
    VQ v;

    private void init(BufferedImage img) {
        v = new VQ();
        v.run(img);
        C = v.C;
        imgs2 = new BufferedImage[v.imgs.length];
        blocks = new Color[5];
        blocks[0] = new Color(0, 0, 255);
        blocks[1] = new Color(255, 0, 0);
        blocks[2] = new Color(255, 255, 0);
        blocks[3] = new Color(0, 255, 0);
        blocks[4] = new Color(121, 68, 59);
    }

    private int[] getD(int n) {
        int B;
        int d[] = new int[5];
        Color pixleB, pixleC;
        for (int k = 0; k < d.length; k++) {
            B = 64;
            int cc = 0, res = 0;
            for (int i = 0; i < C[k].getWidth() - 1; i++) {
                for (int j = 0; j < C[k].getHeight() - 1; j++) {
                    pixleB = new Color(v.imgs[n].getRGB(i, j));
                    pixleC = new Color(C[k].getRGB(i, j));
                    res += Math.abs(pixleB.getRed() - pixleC.getRed());
                    cc++;
                }
            }
            d[k] = res / (cc * cc);
        }
        return d;
    }

    private int getLeastD() {
        int min = D[0], n = 0;

        for (int i = 1; i < D.length; i++) {
            if (min > D[i]) {
                min = D[i];
                n = i - 1;
            }
        }
        return n;
    }

    public void run(BufferedImage img) {
        init(img);
        Color pixleB, pixleC;
        int n;

        labels = new JLabel[imgs2.length];
        for (int k = 0; k < imgs2.length; k++) {
            D = v.getD(k);
            n = v.getLeastD();
            imgs2[k] = new BufferedImage(v.imgs[k].getWidth(), v.imgs[k].getHeight(), 5);
            for (int i = 0; i < imgs2[k].getWidth(); i++) {
                for (int j = 0; j < imgs2[k].getHeight(); j++) {
                    imgs2[k].setRGB(i, j, blocks[n].getRGB());
                }
            }
        }
        for (int i = 0; i < imgs2.length; i++) {
            labels[i] = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgs2[i].getSource())));
            frame.getContentPane().add(labels[i]);
        }
    }

    /*private void loop(int k, int n) {
        for (int i = 0; i < imgs2[k].getWidth(); i++) {
            for (int j = 0; j < imgs2[k].getHeight(); j++) {
                imgs2[k].setRGB(i, j, blocks[n].getRGB());
            }
        }
    }*/

    public void createAndShowUI(BufferedImage img) {
        frame = new JFrame("Satalite Image Classification");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        run(img);
        frame.setResizable(true);
        frame.setIconImage(img);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(img.getWidth(), img.getHeight());
        Container cont = frame.getContentPane();
        img2 = new BufferedImage(cont.getWidth(), cont.getHeight(), 5);
        Graphics2D g2d = img2.createGraphics();
        cont.printAll(g2d);
        g2d.dispose();
    }
}
