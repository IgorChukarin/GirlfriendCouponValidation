package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CouponImageCreator {
    private String text = "HELLO";
    private File source = new File("C:\\Users\\Zigor\\Desktop\\coupon.png");
    private File destination = new File("C:\\Users\\Zigor\\Desktop\\imageWithText.png");

    public static void addTextInImage(String text, String type) throws IOException {
        File source = new File("C:\\Users\\Zigor\\Desktop\\coupon.png");
        File destination = new File("C:\\Users\\Zigor\\Desktop\\imageWithText.png");
        BufferedImage image = ImageIO.read(source);
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage bold = new BufferedImage(image.getWidth(), image.getHeight(), imageType);
        Graphics2D w = (Graphics2D) bold.getGraphics();
        w.drawImage(image, 1, 2, null);
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
        w.setComposite(alpha);
        Color red = new Color(238,85,100);
        w.setColor(red);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));


        FontMetrics fmetrics = w.getFontMetrics();
        Rectangle2D rect = fmetrics.getStringBounds(text, w);

        int centerX = (image.getWidth() - (int) rect.getWidth())/2;
        int centerY = image.getHeight()/2;


        AffineTransform at = new AffineTransform();
        at.rotate(Math.PI / 2);
        w.transform(at);
        w.drawString(text, 150, -40);
        ImageIO.write(bold, type, destination);

        w.dispose();
    }
}
