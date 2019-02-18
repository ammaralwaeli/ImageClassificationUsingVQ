package image;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VQ {

    BufferedImage C[];
    BufferedImage imgs[],imgs2[];
    int D[] = new int[5];
    BufferedImage img;
    int w, h;
    private JLabel[] labels;
    private BufferedImage img2;

    private void getRandomBlocks(BufferedImage img) {
        DivideImage dd = new DivideImage();
        imgs = dd.divide(img);
        imgs2=new BufferedImage[imgs.length];
        C = new BufferedImage[5];
        C[0]=imgs[2];
        C[1]=imgs[5];
        C[2]=imgs[7];
        C[3]=imgs[0];
        C[4]=imgs[10];
        /*for (int i = 0; i < C.length; i++) {
            C[i] = imgs[(int) (Math.random() * imgs.length)];
        }*/
    }

    public int[] getD(int n) {
        int B;
        int d[] = new int[5];
        Color pixleB, pixleC;

        for (int k = 0; k < d.length; k++) {
            B = 64;
            int cc = 0, res = 0;
            for (int i = 0; i < C[k].getWidth() - 1; i++) {
                for (int j = 0; j < C[k].getHeight() - 1; j++) {
                    pixleB = new Color(imgs[n].getRGB(i, j));
                    pixleC = new Color(C[k].getRGB(i, j));
                    res += Math.abs(pixleB.getRed() - pixleC.getRed());
                    cc++;
                }
            }
            int d1;
            d[k] = res / (cc * cc);
            //System.out.print(d[k]+"\n");
        }
        //System.out.print("----------------------\n");
        return d;
    }

    public int getLeastD() {
        int min = D[0], n = 0;
        for (int i = 1; i < D.length; i++) {
            if (min > D[i]) {
                min = D[i];
                n = i;
            }
        }
        return n;
    }

    public void createAndShowUI(BufferedImage img) {
        JFrame frame = new JFrame("Satalite Image Classification");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        run(img);
        labels = new JLabel[imgs.length];
        for (int i = 0; i < imgs.length; i++) {
            labels[i] = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgs[i].getSource())));
            frame.getContentPane().add(labels[i]);
        }
        frame.setResizable(true);
        frame.setIconImage(img);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(img.getWidth(), img.getHeight());
        Container c = frame.getContentPane();
        /*img2 = new BufferedImage(c.getWidth(), c.getHeight(), 5);
        Graphics2D g2d = img2.createGraphics();
        c.printAll(g2d);
        g2d.dispose();
        return img2;*/
    }

    public void run(BufferedImage img) {
        Color pixleB, pixleC;
        int n, p;
        getRandomBlocks(img);
        for (int k = 0; k < imgs.length; k++) {
            imgs2[k] = new BufferedImage(imgs[k].getWidth(), imgs[k].getHeight(), 5);
            D = getD(k);
            n = getLeastD();
            for (int i = 0; i < C[n].getWidth() - 1; i++) {
                for (int j = 0; j < C[n].getHeight() - 1; j++) {
                    pixleB = new Color(imgs[k].getRGB(i, j));
                    pixleC = new Color(C[n].getRGB(i, j));
                    p = (pixleB.getBlue() + pixleC.getBlue()) / 2;
                    imgs2[k].setRGB(i, j, p);
                }
            }
        }
    }
}
