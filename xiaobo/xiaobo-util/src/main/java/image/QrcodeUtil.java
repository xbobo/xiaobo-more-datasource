package image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import cn.hutool.core.img.Img;

public class QrcodeUtil {

	public static  final String LOGO_NAME="logo66.png";
	private static final Logger logger = LoggerFactory.getLogger(QrcodeUtil.class);
	/**
	 * 类型转换
	 * 
	 * @param matrix
	 * @return
	 */
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

	/**
	 * url 转为 base64 在前端JS 接受的时候需要解一下url
	 * 
	 * decodeURI(data.res_data.png_url)//利用js自带的api 进行解码
	 * 
	 * ok 这样就可以直接使用我们传过去的base64 进行图片展示了
	 * 
	 * 记得要我们传回去的参数前面加上 
	 * 
	 *  data:image/png;base64, 
	 * 
	 * @param codeUrl
	 * @return
	 */
	public static String imageToBase64(String codeUrl) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		BitMatrix bitMatrix = null;
		BufferedImage image = null;
		try {
			bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 250, 250, hints);
			image = toBufferImage(bitMatrix);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
			ImageIO.write(image, "png", baos);// 写入流中
			byte[] bytes = baos.toByteArray();// 转换成字节
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String png_base64 = encoder.encodeBuffer(bytes).trim();// 转换成base64串
			png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");// 删除 \r\n
			return "data:image/png;base64," + png_base64;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
 
	public static String imageInsertLogoToBase64(String codeUrl) {
		try {
			BufferedImage createImage = QrCodeUtils.createImage(codeUrl, LOGO_NAME, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
			ImageIO.write(createImage, "png", baos);// 写入流中
			byte[] bytes = baos.toByteArray();// 转换成字节
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String png_base64 = encoder.encodeBuffer(bytes).trim();// 转换成base64串
			png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");// 删除 \r\n
			return "data:image/png;base64," + png_base64;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static BufferedImage imageInsertLogo(String codeUrl) {
		try {
			BufferedImage createImage = QrCodeUtils.createImage(codeUrl, LOGO_NAME,true);
			return createImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static BufferedImage imageInsertLogo(String codeUrl,int size) {
		try {
			BufferedImage createImage = QrCodeUtils.createImageCreateLogo(codeUrl, LOGO_NAME, false,size);
			return createImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * url 转为 BufferedImage
	 * 
	 * @param codeUrl
	 * @return
	 */
	public static BufferedImage urlToBufferimage(String codeUrl) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		BitMatrix bitMatrix = null;
		BufferedImage image = null;
		try {
			bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 250, 250, hints);
			return toBufferImage(bitMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage urlToBufferimageChage(String codeUrl,int weight,int height) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		BitMatrix bitMatrix = null;
		BufferedImage image = null;
		try {
			bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, weight, height, hints);
			return toBufferImage(bitMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static BufferedImage buildLogo35() throws Exception {
		BufferedImage image = new BufferedImage(35, 35, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g2d = image.createGraphics();
		
		// 画图BasicStroke是JDK中提供的一个基本的画笔类,我们对他设置画笔的粗细，就可以在drawPanel上任意画出自己想要的图形了。
		g2d.setColor(new Color(47, 169, 250));
		g2d.setStroke(new BasicStroke(1f));
		g2d.fillRoundRect(0, 0, image.getWidth(), image.getHeight(),3,3);
		Font font = new Font(PictureUtil.FONT_STYLE, Font.BOLD, 12);
		g2d.setColor(Color.white);
		g2d.setFont(font);
		g2d.drawString("朴新", 5, 14);
		g2d.drawString("网校", 5, 28);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// 释放对象
		g2d.dispose();
		image = Img.from(image).round(0.3).getImg();
         return image;
	}
	public static BufferedImage buildLogo60() throws Exception {
		BufferedImage image = new BufferedImage(60, 60, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g2d = image.createGraphics();
		// 画图BasicStroke是JDK中提供的一个基本的画笔类,我们对他设置画笔的粗细，就可以在drawPanel上任意画出自己想要的图形了。
		g2d.setColor(new Color(47, 169, 250));
		g2d.setStroke(new BasicStroke(1f));
		g2d.fillRoundRect(0, 0, image.getWidth(), image.getHeight(),10,10);
		Font font = new Font(PictureUtil.FONT_STYLE, Font.BOLD, 20);
		g2d.setColor(Color.white);
		g2d.setFont(font);
		g2d.drawString("朴新", 9, 25);
		g2d.drawString("网校", 9, 50);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// 释放对象
		g2d.dispose();
		image = Img.from(image).round(0.3).getImg();
         return image;
	}
	public static void main(String[] args) throws Exception {
		// System.out.println(imageToBase64("www.baidu.com"));
		//System.out.println(System.getProperty("user.dir"));
		//BufferedImage source = urlToBufferimage("www.baidu.com");
		String url="https://topic.puxinwangxiao.com/class_details?product_id=51714470863740928&recommend_id=53211186754527232";
		BufferedImage source = QrCodeUtils.createImage(url, LOGO_NAME, true);
		//BufferedImage source = imageInsertLogo("http://www.baidu.com",200);
        FileOutputStream fo = new FileOutputStream(new File("E:/1111-logo.png"));
		ImageIO.write(source, "png", fo);
		//System.out.println(imageInsertLogoToBase64("http://www.baidu.com"));
		
	}
}
