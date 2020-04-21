package image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.DigestUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.qiniu.common.QiniuException;

public class CampSharePictureUtil {

    /**
     * 按比例对图片进行缩放.
     * 
     * @param scale
     *            缩放比例
     * @throws IOException
     */
    public static BufferedImage zoomByScale(BufferedImage srcImage, double scale) throws IOException {
        BufferedImage result = null;
        try {
            BufferedImage im = srcImage;

            /* 原始图像的宽度和高度 */
            int width = im.getWidth();
            int height = im.getHeight();

            // 压缩计算
            // float resizeTimes = 0.3f; /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/

            /* 调整后的图片的宽度和高度 */
            int toWidth = (int)(width * scale);
            int toHeight = (int)(height * scale);

            /* 新生成结果图片 */
            result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

            result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH), 0, 0,
                null);

        } catch (Exception e) {
            System.out.println("创建缩略图发生异常" + e.getMessage());
        }

        return result;
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
    public static BufferedImage zoomBySize(BufferedImage srcImage, int width, int height) throws IOException {
        // 与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image _img = srcImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        // OutputStream out = new FileOutputStream(destFile);
        // ImageIO.write(image, ext, out);
        // out.close();
        return image;
    }

    public static String buildShareImage(String imageUrl, String codeUrl, Integer partMode)
        throws IOException, MalformedURLException, Exception {
        // 背景图片处理
        // '学段：2小学，3初中，4高中',
        String filename = "spell_camp_image.jpg";
        String suffix="jpg";
        if(StringUtils.isNotEmpty(imageUrl)) {
            int lastIndexOf = imageUrl.lastIndexOf(".");
            suffix=imageUrl.substring(lastIndexOf+1);
            if("jpeg".equalsIgnoreCase(suffix)) {
                suffix="jpeg";
                filename=imageUrl;
            }else if("jpg".equalsIgnoreCase(suffix)) {
                suffix="jpg";
                filename=imageUrl;
            }else if("png".equalsIgnoreCase(suffix)) {
                suffix="png";
                filename=imageUrl;
            }
        }
 
        BufferedImage srcImage = null;
        if(!filename.startsWith("http")) {
            ClassPathResource resource = new ClassPathResource(filename);
            srcImage = ImageIO.read(resource.getInputStream());
        }else {
            srcImage=ImageIO.read(new URL(filename));
        }

        // 二维码图片处理
        BufferedImage codeBufferimage = urlToBufferImageDeleteWhite(codeUrl,220);
        codeBufferimage=zoomBySize(codeBufferimage, 215, 215);

        // 添加时间范围 2019062415002004
        // share_image0 145,328  252, 530  514, 1065, 
        srcImage = addImage(srcImage,494,1078,codeBufferimage);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
        ImageIO.write(srcImage, suffix, baos);// 写入流中
        byte[] bytes = baos.toByteArray();// 转换成字节
        String fileName = DigestUtils.md5DigestAsHex((codeUrl + System.currentTimeMillis()).getBytes()) + "."+suffix;
        String path = System.getProperty("user.dir");
        String dirPath = path + File.separator + "tmp";
        File file = new File(dirPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String fullPath = dirPath + File.separator + fileName;
        FileUtilShare.upload(bytes, fullPath);
        FileUtilShare.qiniu(fileName, fullPath);

        return "http://resource.puxinwangxiao.com/" + fileName;
    }
    
    public static String buildIntroduceShareImage(String imageUrl, String codeUrl, Integer partMode)
        throws IOException, MalformedURLException, Exception {
        // 背景图片处理
        // '学段：2小学，3初中，4高中',
        String filename = "introduce_camp.jpg";
        String suffix="jpg";
        if(StringUtils.isNotEmpty(imageUrl)) {
            int lastIndexOf = imageUrl.lastIndexOf(".");
            suffix=imageUrl.substring(lastIndexOf+1);
            if("jpeg".equalsIgnoreCase(suffix)) {
                suffix="jpeg";
                filename=imageUrl;
            }else if("jpg".equalsIgnoreCase(suffix)) {
                suffix="jpg";
                filename=imageUrl;
            }else if("png".equalsIgnoreCase(suffix)) {
                suffix="png";
                filename=imageUrl;
            }
        }
 
        BufferedImage srcImage = null;
        if(!filename.startsWith("http")) {
            ClassPathResource resource = new ClassPathResource(filename);
            srcImage = ImageIO.read(resource.getInputStream());
        }else {
            srcImage=ImageIO.read(new URL(filename));
        }

        // 二维码图片处理
        BufferedImage codeBufferimage = urlToBufferImageDeleteWhite(codeUrl,220);
        codeBufferimage=zoomBySize(codeBufferimage, 180, 180);

        // 添加时间范围 2019062415002004
        // share_image0 145,328  252, 530  514, 1065,  514 ,1085
        srcImage = addImage(srcImage,320,725,codeBufferimage);
        
        //srcImage=zoomByScale(srcImage, 0.7D);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
        ImageIO.write(srcImage, suffix, baos);// 写入流中
        byte[] bytes = baos.toByteArray();// 转换成字节
        String fileName = DigestUtils.md5DigestAsHex((codeUrl + System.currentTimeMillis()).getBytes()) + "."+suffix;
        String path = System.getProperty("user.dir");
        String dirPath = path + File.separator + "tmp";
        File file = new File(dirPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String fullPath = dirPath + File.separator + fileName;
        FileUtilShare.upload(bytes, fullPath);
        FileUtilShare.qiniu(fileName, fullPath);

        return "http://resource.puxinwangxiao.com/" + fileName;
    }
    
    /**
               *   全民转介绍
     * @param imageUrl
     * @param codeUrl
     * @param partMode
     * @return
     * @throws IOException
     * @throws MalformedURLException
     * @throws Exception
     */
    public static String buildAllIntroduceShareImage(String imageUrl, String codeUrl, Integer partMode,String wxName,String wxAvatar)
        throws IOException, MalformedURLException, Exception {
        // 背景图片处理
        // '学段：2小学，3初中，4高中',
        String filename = "introduce_camp.jpg";
        String suffix="jpg";
       if(StringUtils.isEmpty(imageUrl)) {
           imageUrl="http://resource.puxinwangxiao.com/30aadebbc9b6001a56f365e805e8059c.jpg";
       }
        if(StringUtils.isNotEmpty(imageUrl)) {
            int lastIndexOf = imageUrl.lastIndexOf(".");
            suffix=imageUrl.substring(lastIndexOf+1);
            if("jpeg".equalsIgnoreCase(suffix)) {
                suffix="jpeg";
                filename=imageUrl;
            }else if("jpg".equalsIgnoreCase(suffix)) {
                suffix="jpg";
                filename=imageUrl;
            }else if("png".equalsIgnoreCase(suffix)) {
                suffix="png";
                filename=imageUrl;
            }
        }
 
        BufferedImage srcImage = null;
        if(!filename.startsWith("http")) {
            ClassPathResource resource = new ClassPathResource(filename);
            srcImage = ImageIO.read(resource.getInputStream());
        }else {
            srcImage=ImageIO.read(new URL(filename));
        }

        // 二维码图片处理
        BufferedImage codeBufferimage = urlToBufferImageDeleteWhite(codeUrl,220);
        codeBufferimage=zoomBySize(codeBufferimage, 220, 220);
        // 添加时间范围 2019062415002004
        // share_image0 145,328  252, 530  514, 1065,  514 ,1095 310,715
        
        srcImage = addImage(srcImage,494 ,1078,codeBufferimage,wxName,wxAvatar);
        
        //srcImage=zoomByScale(srcImage, 0.7D);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
        ImageIO.write(srcImage, suffix, baos);// 写入流中
        byte[] bytes = baos.toByteArray();// 转换成字节
        String fileName = DigestUtils.md5DigestAsHex((codeUrl + System.currentTimeMillis()).getBytes()) + "."+suffix;
        String path = System.getProperty("user.dir");
        String dirPath = path + File.separator + "tmp";
        File file = new File(dirPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String fullPath = dirPath + File.separator + fileName;
        FileUtilShare.upload(bytes, fullPath);
        FileUtilShare.qiniu(fileName, fullPath);

        return "https://resource.puxinwangxiao.com/" + fileName;
    }
    
    public static String buildShareImage(String codeUrl, Integer partMode)
        throws IOException, MalformedURLException, Exception {
        // 背景图片处理
        // '学段：2小学，3初中，4高中',
        String filename = "spell_camp_image.jpg";
 
        ClassPathResource resource = new ClassPathResource(filename);
        BufferedImage srcImage = ImageIO.read(resource.getInputStream());

        // 二维码图片处理
        BufferedImage codeBufferimage = urlToBufferImageDeleteWhite(codeUrl,220);
        codeBufferimage=zoomBySize(codeBufferimage, 220, 220);

        // System.out.println(codeBufferimage.getWidth());
        // System.out.println(codeBufferimage.getHeight());
        // 添加时间范围 2019062415002004
        // share_image0 145,328  252, 530  514, 1065, 
        srcImage = addImage(srcImage,514,1095,codeBufferimage);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
        ImageIO.write(srcImage, "jpg", baos);// 写入流中
        byte[] bytes = baos.toByteArray();// 转换成字节
        String fileName = DigestUtils.md5DigestAsHex((codeUrl + System.currentTimeMillis()).getBytes()) + ".jpg";
        String path = System.getProperty("user.dir");
        String dirPath = path + File.separator + "tmp";
        File file = new File(dirPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String fullPath = dirPath + File.separator + fileName;
        FileUtilShare.upload(bytes, fullPath);
        FileUtilShare.qiniu(fileName, fullPath);

        return "http://resource.puxinwangxiao.com/" + fileName;
    }

    public static BufferedImage urlToBufferImage(String codeUrl) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        BufferedImage image = null;
        try {
            bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 240, 240, hints);
            image = toBufferImage(bitMatrix);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public static BufferedImage urlToBufferImage(String codeUrl,int size) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        BufferedImage image = null;
        try {
            bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, size, size, hints);
            image = toBufferImage(bitMatrix);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public static BufferedImage urlToBufferImageDeleteWhite(String codeUrl,int size) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        BufferedImage image = null;
        try {
            bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, size, size, hints);
            bitMatrix = deleteWhite(bitMatrix);//删除白边
            image = toBufferImage(bitMatrix);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int common=10;
        int resWidth = rec[2] ;
        int resHeight = rec[3];

        BitMatrix resMatrix = new BitMatrix(resWidth+ common, resHeight + common);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i+common/2, j+common/2);
                }
            }
        }
        return resMatrix;
    } 

    public static BufferedImage toBufferImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? 0xff000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    private static BufferedImage addImage(BufferedImage srcImage, int x, int y, BufferedImage codeBufferimage,String wxName,String wxAvatar) throws IOException {
        int size = 30;
       
        // 画半透明 价格
        Graphics2D graphics = (Graphics2D)srcImage.createGraphics();
        Font font = new Font("黑体", Font.BOLD, size);
        graphics.setColor(Color.white); 
        graphics.setFont(font);
        if(StringUtils.isNotEmpty(wxAvatar)) {
            try {
                graphics.drawImage(zoomByScale(ImageIO.read(new URL(wxAvatar)), 0.65D), 20, 30, null);
            } catch (Exception e) {
                e.printStackTrace();
                //图片异常处理
                graphics.drawImage(zoomByScale(ImageIO.read(new ClassPathResource("default_wx_avatar.jpg").getInputStream()), 0.65D), 20, 30, null);
            }
        }else {
            graphics.drawImage(zoomByScale(ImageIO.read(new ClassPathResource("default_wx_avatar.jpg").getInputStream()), 0.65D), 20, 30, null);
        }
        if(wxName!= null && wxName.length()>6) {
            wxName=wxName.substring(0, 6)+"...";
        }
        graphics.drawString(wxName, 115, 65);
        graphics.drawImage(codeBufferimage, x, y, null);
        graphics.dispose();

        return srcImage;
    }
    private static BufferedImage addImage(BufferedImage srcImage, int x, int y, BufferedImage codeBufferimage){
        int size = 30;
        
        // 画半透明 价格
        Graphics2D graphics = (Graphics2D)srcImage.createGraphics();
        graphics.drawImage(codeBufferimage, x, y, null);
        graphics.dispose();

        return srcImage;
    }

    private static BufferedImage addQrcode(BufferedImage srcImage, int x, int y, BufferedImage codeBufferimage) {
        int size = 30;
        // 画半透明 价格
        Graphics2D graphics = (Graphics2D)srcImage.createGraphics();
        graphics.drawImage(codeBufferimage, x, y, null);
        graphics.dispose();

        return srcImage;
    }

    public static void main(String[] args) throws Exception {
//        String codeUrl1 = "https://www.baidu.com/";
//        String codeUrl="https://topic.puxinwangxiao.com/drill/?product_id=119134778528407554&c_c_p=&c_c_id=&org_id=119134778528407554&recommend_id=39321235168206848&stu_id1=41540177244758016&stu_id2=41540177244758016&type_forward=refer";
//        ///String shareImage = buildShareImage(codeUrl, null);
//        // String shareQrcode = buildShareQrcode(codeUrl);
//        String buildIntroduceShareImage = buildIntroduceShareImage("", codeUrl, null);
//        System.out.println(buildIntroduceShareImage);
        
        //生成图文二维码
        String codeUrl="https://topic.puxinwangxiao.com/all_referral/?org_id=137146700183937024&activity_id=137145969691369474&stu1=25596330346061824&stu2=&c_c_p=20200114ceshi&c_c_id=137146134271664128";
        String imageUrl="http://resource.puxinwangxiao.com/c51f2b87fccbbce7fdd51a0db570a451.jpg";
        //"http://resource.puxinwangxiao.com/622d99e1b2c1358ef5ccee38828c8442.jpg";
/*        String shareImage="";
        System.out.println(codeUrl.length());
        shareImage = CampSharePictureUtil.buildAllIntroduceShareImage(imageUrl, codeUrl, null,"测试测试","");
            //http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ5RTY2tn2WGZVQ8uToBVrYyBI6YhO1NINbqwIuavaGcKl9Nd5JxynbicVwicsqHE07BZicrII7MCDbg/132
        System.out.println(shareImage);*/
        
        //toqiniufile();
        String codeUrl1="https://m.puxinwangxiao.com/camp/?activity_id=146202711448395778&c_c_p=&c_c_id=&org_id=145205678155538432&recommend_id=126647720262017024&stu_id1=127502987958722560&stu_id2=";

        System.out.println(buildShareImage(codeUrl1, null));
        System.out.println(buildShareImage(imageUrl, codeUrl1, null));
        System.out.println(buildIntroduceShareImage("", codeUrl1, null));
        System.out.println(buildAllIntroduceShareImage(imageUrl, codeUrl1, null, "", ""));
            
 
    }

    private static void toqiniufile() throws IOException, QiniuException {
        //test1 http://resource.puxinwangxiao.com/30aadebbc9b6001a56f365e805e8059c.jpg
        String suffix="jpg";
        BufferedImage srcImage = ImageIO.read(new File("E:/test1.jpg"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
        ImageIO.write(srcImage, suffix, baos);// 写入流中
        byte[] bytes = baos.toByteArray();// 转换成字节
        String fileName = DigestUtils.md5DigestAsHex(("111" + System.currentTimeMillis()).getBytes()) + "."+suffix;
        String path = System.getProperty("user.dir");
        String dirPath = path + File.separator + "tmp";
        File file = new File(dirPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String fullPath = dirPath + File.separator + fileName;
        FileUtilShare.upload(bytes, fullPath);
        FileUtilShare.qiniu(fileName, fullPath);

       System.out.println("http://resource.puxinwangxiao.com/" + fileName);;
    }
}
