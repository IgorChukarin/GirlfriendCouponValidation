package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CouponImageCreator {
    final static File blankCoupon = new File("C:\\Users\\Zigor\\Desktop\\projects\\GirlfriendCouponValidation\\src\\main\\images\\coupon.png");
    final static File finalCoupon = new File("C:\\Users\\Zigor\\Desktop\\projects\\GirlfriendCouponValidation\\src\\main\\images\\finalCoupon.png");
    final static Color codeColor = new Color(238,84,214);
    final static Color descriptionColor = new Color(218,67,196);
    final static Font codeFont = new Font(Font.SANS_SERIF, Font.BOLD, 60);
    final static String type = "png";

    public static void addTextInImage(String code, String description) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(blankCoupon);
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage bufferedImageWithType = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), imageType);
        Graphics2D graphics2D = (Graphics2D) bufferedImageWithType.getGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, null);

        graphics2D.setColor(codeColor);
        graphics2D.setFont(codeFont);

        AffineTransform normalTransform = graphics2D.getTransform();
        AffineTransform rotatedTransform = new AffineTransform();
        rotatedTransform.rotate(-Math.PI / 2, bufferedImage.getWidth()/2, bufferedImage.getHeight() / 2);
        graphics2D.transform(rotatedTransform);
        graphics2D.drawString(code, bufferedImage.getWidth()/2 - 100, bufferedImage.getHeight() / 2 - 170);

        graphics2D.transform(rotatedTransform);
        graphics2D.transform(rotatedTransform);
        graphics2D.transform(rotatedTransform);
        graphics2D.setColor(descriptionColor);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        graphics2D.drawString(description, bufferedImage.getWidth() / 2 - 89, bufferedImage.getHeight() / 2 - 13);

        ImageIO.write(bufferedImageWithType, type, finalCoupon);
        graphics2D.dispose();
    }

    public static void main(String[] args) {
        try {
            CouponImageCreator.addTextInImage("000000", "Поездка на такси");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




