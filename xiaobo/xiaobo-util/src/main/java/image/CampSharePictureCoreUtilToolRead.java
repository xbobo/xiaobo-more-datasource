package com.puxinwangxiao.erp.core.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import image.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

public class CampSharePictureCoreUtilToolRead {


    public static String buildShareImage(String imageUrl, String qrcodeUrl)
            throws IOException, MalformedURLException, Exception {
        // 背景图片处理
        // '学段：2小学，3初中，4高中',
        String suffix = "png";
        BufferedImage srcImage=ImageIO.read(new URL(imageUrl));

//        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
//        ColorModel colorModel = new ComponentColorModel(cs, true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);
//        WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, srcImage.getWidth(), srcImage.getHeight(), 4, null);
//        BufferedImage destImage = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), Transparency.TRANSLUCENT);
        destImage=addImage(destImage, 0, 0, srcImage);
        if (StringUtils.isNotEmpty(qrcodeUrl)) {
            BufferedImage qrcodeImage=ImageIO.read(new URL(qrcodeUrl));
            qrcodeImage = zoomBySize(qrcodeImage, 350, 350, qrcodeUrl);
            // 添加时间范围 2019062415002004
            // share_image0 145,328 252, 530 514, 1065,
            destImage = addImage(destImage, 205, 520, qrcodeImage);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
        ImageIO.write(destImage, suffix, baos);// 写入流中
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

        return "https://resource.puxinwangxiao.com/" + fileName;
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

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        return image;
    }

    private static BufferedImage addImage(BufferedImage srcImage, int x, int y, BufferedImage codeBufferimage){
        // 画半透明 价格
        Graphics2D graphics = (Graphics2D)srcImage.getGraphics();
        graphics.drawImage(codeBufferimage, x, y,null);
        graphics.dispose();
        return srcImage;
    }

    public static void main(String[] args) throws Exception {
        String wxpig="http://mmbiz.qpic.cn/mmbiz_jpg/lVnL1CNTGGLlGIAgcT6ZQQaaAxoe79sGCQplQ1IqsWNSwA5t5bHLoEsgibZicKq2RCS0WbTazMO3PlZSiceMqwVEQ/0";
        String w="http://resource.puxinwangxiao.com/0ca86b74e95a46049511170c12b8fd8d.png";
        String n="http://resource.puxinwangxiao.com/0dcd69f10848ca7dac6e9b6385c917e5.jpg";
        System.out.println(buildShareImage(w, n));

    }

}
