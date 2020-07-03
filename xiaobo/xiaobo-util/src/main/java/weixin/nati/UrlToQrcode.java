package weixin.nati;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class UrlToQrcode {

	
	
	
	/**
	 * 类型转换
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferImage(BitMatrix matrix) {
		int width=matrix.getWidth();
		int height=matrix.getHeight();
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				image.setRGB(x, y, matrix.get(x, y) == true ? 0xff000000 : 0xFFFFFFFF);
			}
		}
		return image;
	}
	/**
	 * url 转为 base64
	 * 在前端JS 接受的时候需要解一下url

	decodeURI(data.res_data.png_url)//利用js自带的api 进行解码

ok 这样就可以直接使用我们传过去的base64 进行图片展示了

记得要我们传回去的参数前面加上

data:image/png;base64,
	 * @param codeUrl
	 * @return
	 */
	public static String imageToBase64(String codeUrl) {
		MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
		Map hints=new HashMap();
		BitMatrix bitMatrix=null;
		BufferedImage image = null;
			try {
				bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 250,250, hints);
				image=toBufferImage(bitMatrix);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
				ImageIO.write(image, "png", baos);//写入流中
				byte[] bytes = baos.toByteArray();//转换成字节
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				String png_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
				png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
				return "data:image/png;base64,"+png_base64; 
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "";
	}
	
	/**
	 * url 转为 BufferedImage
	 * @param codeUrl
	 * @return
	 */
	public static BufferedImage urlToBufferimage(String codeUrl) {
		MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
		Map hints=new HashMap();
		BitMatrix bitMatrix=null;
		BufferedImage image = null;
			try {
				bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 250,250, hints);
				return toBufferImage(bitMatrix);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(imageToBase64("weixin://wxpay/bizpayurl?pr=otXXBUQ"));
	}
	 
}
