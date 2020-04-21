package image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

public class CampSharePictureCoreUtilToolRead {


    public static String buildShareImage(String imageUrl, String qrcodeUrl)
        throws IOException, MalformedURLException, Exception {
        // 背景图片处理
        // '学段：2小学，3初中，4高中',
        String suffix = "png";
        Image imageTool = Toolkit.getDefaultToolkit().getImage(imageUrl);
        BufferedImage srcImage = toBufferedImage(imageTool);

        if (StringUtils.isNotEmpty(qrcodeUrl)) {
            BufferedImage qrcodeImage = null;
            // qrcodeImage=ImageIO.read(new File(qrcodeUrl));
            Image qrimgTool = Toolkit.getDefaultToolkit().getImage(qrcodeUrl);
            qrcodeImage = toBufferedImage(qrimgTool);
            qrcodeImage = zoomBySize(qrcodeImage, 350, 350, qrcodeUrl);
            // 添加时间范围 2019062415002004
            // share_image0 145,328 252, 530 514, 1065,
            srcImage = addImage(srcImage, 205, 520, qrcodeImage);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
        ImageIO.write(srcImage, suffix, baos);// 写入流中
        byte[] bytes = baos.toByteArray();// 转换成字节
        String fileName = DigestUtils.md5DigestAsHex((imageUrl + System.currentTimeMillis()).getBytes()) + "."+suffix;
        String path = System.getProperty("user.dir");
        String dirPath = path + File.separator + "tmp";
        File file = new File(dirPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String fullPath = dirPath + File.separator + fileName;
        FileUtil.upload(bytes, fullPath);
        FileUtil.qiniu(fileName, fullPath);

        return "http://resource.puxinwangxiao.com/" + fileName;
    }
    
    public static BufferedImage toBufferedImage(Image image) {
        if(image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        image=new ImageIcon(image).getImage();
        BufferedImage bimage=null;
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        int width = image.getWidth(null);
        if(width<=0) {
            width=430;
        }
        int height = image.getHeight(null);
        if(height<=0) {
            height=430;
        }
        try {

//            字段摘要
//            static int	BITMASK
//            表示保证完全不透明的图像数据（alpha 值为 1.0）或完全透明的图像数据（alpha 值为 0.0）。
//            static int	OPAQUE
//            表示保证完全不透明的图像数据，意味着所有像素 alpha 值都为 1.0。
//            static int	TRANSLUCENT
//            表示包含或可能包含位于 0.0 和 1.0（含两者）之间的任意 alpha 值的图像数据。
            int transparency = Transparency.TRANSLUCENT;//原背景
            GraphicsDevice gs=ge.getDefaultScreenDevice();
            GraphicsConfiguration gc=gs.getDefaultConfiguration();
            bimage=gc.createCompatibleImage(width, height, transparency);
        } catch (HeadlessException e) {
             e.printStackTrace();
        }
        if(bimage == null) {
            int type = BufferedImage.TYPE_INT_BGR;
            bimage=new BufferedImage(width, height, type);
        }
        
        Graphics g=bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
        
    }
    /**
     * 指定长和宽对图片进行缩放
     *
     * @param width
     *            长
     * @param height
     *            宽
     * @throws IOException
     */
    public static BufferedImage zoomBySize(BufferedImage srcImage, int width, int height,String imageURL) throws IOException {
        // 与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image _img = srcImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        return image;
    }
    
    private static BufferedImage addImage(BufferedImage srcImage, int x, int y, BufferedImage codeBufferimage){
        // 画半透明 价格
        Graphics2D graphics = (Graphics2D)srcImage.createGraphics();
        graphics.drawImage(codeBufferimage, x, y, null);
        graphics.dispose();
        return srcImage;
    }

    public static void main(String[] args) throws Exception {
        String wxpig="http://mmbiz.qpic.cn/mmbiz_jpg/lVnL1CNTGGLlGIAgcT6ZQQaaAxoe79sGCQplQ1IqsWNSwA5t5bHLoEsgibZicKq2RCS0WbTazMO3PlZSiceMqwVEQ/0";
        String n="E:/n.jpg";
        String w="E:/w.jpg";
        System.out.println(buildShareImage(w, n));
    }
 
}
