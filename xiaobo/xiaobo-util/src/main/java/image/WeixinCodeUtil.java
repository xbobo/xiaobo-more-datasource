package image;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @version v1.2
 * @Author pan
 * @ClassName: ValidateActionJson
 * @Description TODO
 * @Date $ $
 */
public class WeixinCodeUtil {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    // 生成带logo的二维码图片
    public static byte[] drawLogoQRCode(File codeFile, String logoUrl) {
        byte[] buffer = new byte[1024 * 1024];
        try {

            /**
             * 读取二维码图片，并构建绘图对象
             */

            BufferedImage image = ImageIO.read(codeFile);
            System.out.println("---image--"+image);
            BufferedImage image1 = toGray(image);
            System.out.println("---image1--"+image1);
            Graphics2D g = image1.createGraphics();
            g.setColor(Color.black);
            /**
             * 读取Logo图片
             */

            BufferedImage logo = ImageIO.read(getInPutStream(logoUrl));
            System.out.println("---logo--"+logo);

            int widthLogo = image.getWidth() /4;

            int heightLogo = image.getWidth() / 4; //保持二维码是正方形的

            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;

            //开始绘制图片
           // g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.9f));
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.setColor(Color.white);
            g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
            g.drawRect(1, 1, image.getWidth() - 1, image.getHeight()- 1);
            g.drawRect(0, 0, image.getWidth()-2, image.getHeight()- 2);
            // g.setStroke(new BasicStroke(3f));
            //g.draw(shape);

            g.dispose();

            buffer = imageToBytes(image1, "png");

            System.out.println("bs.toByteArray()---"+buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(buffer.length);
        return buffer;
    }

    public static byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static BufferedImage toGray(BufferedImage srcImg){
        return new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_sRGB), null).filter(srcImg, null);
    };

    public static InputStream getInPutStream(String templateName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(templateName);
        System.out.println("classPathResource--------"+classPathResource);
        InputStream inputStream = classPathResource.getInputStream();

        return inputStream;
    }
    
    public static void main(String[] args) throws Exception {
    	  /**
         * 读取二维码图片，并构建绘图对象
         */

        BufferedImage image = ImageIO.read(new URL("http://resource.puxinwangxiao.com/6cfe3267ec057cd3b4cb2b0db7aefc0c.jpg"));
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.black);
        /**
         * 读取Logo图片
         */

        int widthLogo = image.getWidth() /4;

        int heightLogo = image.getWidth() / 4; //保持二维码是正方形的

        // 计算图片放置位置
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - heightLogo) / 2;

        //开始绘制图片
       // g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.9f));
        g.drawImage(image, x, y, widthLogo, heightLogo, null);
        g.setColor(Color.white);
        g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
        g.drawRect(1, 1, image.getWidth() - 1, image.getHeight()- 1);
        g.drawRect(0, 0, image.getWidth()-2, image.getHeight()- 2);
        // g.setStroke(new BasicStroke(3f));
        //g.draw(shape);

        g.dispose();
        
        FileOutputStream fo = new FileOutputStream(new File("E:/1.png"));
		ImageIO.write(image, "png", fo);
		System.out.println("over");
	}
}
