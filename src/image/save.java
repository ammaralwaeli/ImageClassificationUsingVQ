package image;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class save {

    public void si(BufferedImage img1) throws IOException {
        String n = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setFocusable(true);
        int o = chooser.showSaveDialog(null);
        if (o != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "NO Image Selected.");
        } else {
            n = chooser.getSelectedFile().getAbsolutePath();
            ImageIO.write(img1, "bmp", new File(n));
        }
    }
}