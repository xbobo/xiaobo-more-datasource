package image;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.util.DigestUtils;

import cn.hutool.core.img.Img;

public class PictureUtil {
	public static final Integer ROW_LEN = 12;
	public static final double SCAL_RATIO = 0.7;

	public static final int show_weight = 312;
	public static int show_height = 550;
	public static final int qrcode_weight = 98;
	public static final int qrcode_height = 98;
//	、将Font的创建从物理字体改为逻辑字体
//  Serif、SansSerif、Monospaced、Dialog 和 DialogInput 随意选择
	public static final String FONT_STYLE = "宋体";

	/**
	 * 按比例对图片进行缩放.
	 * 
	 * @param scale 缩放比例
	 * @throws IOException
	 */
	public static BufferedImage zoomByScale(BufferedImage srcImage, double scale) throws IOException {
		BufferedImage result = null;
		try {
			BufferedImage im =srcImage;

			/* 原始图像的宽度和高度 */
			int width = im.getWidth();
			int height = im.getHeight();

			//压缩计算
			//float resizeTimes = 0.3f;  /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/

			/* 调整后的图片的宽度和高度 */
			int toWidth = (int) (width * scale);
			int toHeight = (int) (height * scale);

			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight,
					BufferedImage.TYPE_INT_RGB);

			result.getGraphics().drawImage(
					im.getScaledInstance(toWidth, toHeight,
							java.awt.Image.SCALE_SMOOTH), 0, 0, null);


		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}

		return result;
	}

	/**
	 * 指定长和宽对图片进行缩放
	 *
	 * @param width  长
	 * @param height 宽
	 * @throws IOException
	 */
	public static BufferedImage zoomBySize(BufferedImage srcImage, int width, int height) throws IOException {
		// 与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
		Image _img = srcImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();
		graphics.drawImage(_img, 0, 0, null);
		graphics.dispose();
//        OutputStream out = new FileOutputStream(destFile);
//        ImageIO.write(image, ext, out);
//        out.close();
		return image;
	}

	public static BufferedImage addPriceIcon(BufferedImage srcImage,String price) {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		int size=width/ROW_LEN/2;
		//画半透明 价格
		Graphics2D graphics = (Graphics2D) srcImage.createGraphics();
		// 1.0f为透明度 ，值从0-1.0，依次变得不透明 
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
		graphics.setColor(Color.black);
		graphics.setStroke(new BasicStroke(1f));
		//graphics.fillRect(width-40, height-20, 40,15);
		int len=price.length();
		 
		int scale=10;
		int radioScale=10;
		if(width>=2500) {
			scale=200;
			radioScale=35;
		}else if(width>1000&&width<2500) {
			scale=167;
			radioScale=30;
		}else if(width<=1000&&width>500) {
			scale=100;
			radioScale=25;
		}else if(width<=500) {
			scale=50;
			radioScale=15;
		}
		graphics.fillRoundRect(width-width/scale-len*size, height-size*3+size/2, size*len-size,size*2, width/radioScale, width/radioScale);
		// 释放对象 透明度设置结束
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		Font font = new Font(PictureUtil.FONT_STYLE, Font.BOLD, size);
		graphics.setColor(Color.white);
		PriceFont priceFont = new PriceFont();
		Font definedFont = priceFont.getDefinedFont(Font.BOLD, size);
		graphics.setFont(definedFont);
		graphics.drawString(price,width-width/scale-len*size+size/2, (int)(height-size-size/4));
		graphics.dispose();
		 
		return srcImage;
	}
	public static BufferedImage buildExceptionImage(String strurl, BufferedImage srcImage, Exception e)
			throws IOException, MalformedURLException {
		if ("Unsupported Image Type".equals(e.getMessage())) {
			// Find a suitable ImageReader
			final Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
			ImageReader reader = null;
			while (readers.hasNext()) {
				reader = (ImageReader) readers.next();
				if (reader.canReadRaster()) {
					break;
				}
			}
			// Stream the image file (the original CMYK image)
			final ImageInputStream input = ImageIO
					.createImageInputStream(new URL(strurl).openConnection().getInputStream());
			reader.setInput(input);
			// Read the image raster
			final Raster raster = reader.readRaster(0, null);
			// Create a new RGB image
			final BufferedImage bi = new BufferedImage(raster.getWidth(), raster.getHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);
			// Fill the new image with the old raster
			bi.getRaster().setRect(raster);
			srcImage = bi;
		}
		final int width = srcImage.getWidth();
		final int height = srcImage.getHeight();
		return srcImage;
	}
	public static String buildImageInsertLogo(String strurl, String content, String codeUrl,String price)
			throws IOException, MalformedURLException, Exception {
		BufferedImage srcImage = null;
		try {
			srcImage = ImageIO.read(new URL(strurl));
		} catch (Exception e) {
			srcImage = buildExceptionImage(strurl, srcImage, e);
		}

		srcImage=zoomBySize(srcImage, 560, 320);
		int _width = srcImage.getWidth() ;
		int _height = srcImage.getHeight();
		int padding=_width/50;
		_width=padding*2+_width;
		srcImage=addPriceIcon(srcImage,price);
		int coententLen = content.length();
		int size=_width/ROW_LEN;
		if (coententLen > 0) {
			int num = coententLen / ROW_LEN;
			if (coententLen % ROW_LEN > 0) {
				num = num + 1;
			}
			show_height = size * num+_height+_width/2+20;
		}
		
		BufferedImage destImage = new BufferedImage(_width, show_height+50, BufferedImage.TYPE_INT_RGB);
		initBackageColor(destImage);
		int curHeight = padding;
		int contentlength = content.length();

		BufferedImage codeBufferimage = QrcodeUtil.imageInsertLogo(codeUrl,240);

		// 添加图片
		addImageToBufferImageCommon(srcImage, destImage, padding, curHeight);
		curHeight = curHeight + _height + size/4;
		// 添加标题
		curHeight = addContentToBufferImageContents(destImage, curHeight, content, contentlength);
		curHeight = curHeight + size/2;
		// 添加二维码
		addImageToBufferImageMin(codeBufferimage, destImage, 0, curHeight-10);
		curHeight = curHeight + codeBufferimage.getHeight()+size/2;
		// 添加二维码提示
		addContentToBufferImageSimpleTips(destImage, curHeight, "长按识别二维码");
		// 圆角处理
		destImage = (BufferedImage)Img.from(destImage).round(0.1).getImg();
		//srcImage = zoomImage(destImage, (int)(_width*0.35), (int)(show_height*0.35));
		//BufferedImage zoomImage = zoomImage(destImage, 300, 400);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
		ImageIO.write(destImage, "png", baos);// 写入流中
		byte[] bytes = baos.toByteArray();// 转换成字节
		String fileName = DigestUtils.md5DigestAsHex((strurl + System.currentTimeMillis()).getBytes()) + ".png";
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


	private static int addContentToBufferImageContents(BufferedImage destImage, int curHeight, String content,
			int contentlength) {
		if (contentlength > 0) {
			int size=destImage.getWidth()/ROW_LEN;
			int num = contentlength / ROW_LEN;
			if (contentlength % ROW_LEN > 0) {
				num = num + 1;
			}
			for (int x = 0; x < num; x++) {
				int startIndex = x * ROW_LEN;
				int endIndex = ROW_LEN * (x + 1);
				if (contentlength < endIndex) {
					endIndex = contentlength;
				}
				String curContent = content.substring(startIndex, endIndex);
				curHeight = curHeight + size ;
				addContentToBufferImageSimple(destImage, curHeight, curContent);
			}
		}
		return curHeight;
	}

	public static void initBackageColor(BufferedImage destImage) {
		Graphics2D g = (Graphics2D) destImage.getGraphics();
		// 设置背景色 并赋予背景色
		g.setBackground(Color.WHITE);// 设置背景色
		g.clearRect(0, 0, destImage.getWidth(), destImage.getHeight());// 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
	}

	public static void addImageToBufferImageCommon(BufferedImage srcImage, BufferedImage destImage, int x, int y) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		srcImage = (BufferedImage)Img.from(srcImage).round(0.1).getImg();
		graphics.drawImage(srcImage, x, y, null);
	}

	public static void addImageToBufferImageMin(BufferedImage srcImage, BufferedImage destImage, int x, int y) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		int dwidth = destImage.getWidth();
		int swidth = srcImage.getWidth();
		int startX = 0;
		if (dwidth > swidth) {
			startX = (dwidth - swidth) / 2;
		}
		// 设置颜色和画笔粗细
//		graphics.setBackground(Color.black);
//		graphics.setColor(Color.black);
//		// g2d.setStroke(new BasicStroke(3));
//		BasicStroke stroke = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f,
//				new float[] { 2, 1 }, 0f);
//		graphics.setStroke(stroke);
//		graphics.clearRect(startX, y, srcImage.getWidth(), srcImage.getHeight());// 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
//		graphics.drawRect(startX, y, srcImage.getWidth(), srcImage.getHeight());
		graphics.drawImage(srcImage, startX, y, null);
	}

	public static void addContentToBufferImage(BufferedImage destImage, int x, int y, String strContent, int rowWidth) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		Font font = new Font(FONT_STYLE, Font.BOLD, 18);
		graphics.setColor(new Color(51, 51, 51));
		graphics.setFont(font);
		// 获取字符串 字符的总宽度
		int strWidth = getStringLength(graphics, strContent);
		// 每一行字符串宽度
		// int rowWidth = 600;
		// System.out.println("每行字符宽度:" + rowWidth);
		// 获取字符高度
		int strHeight = getStringHeight(graphics);
		// 字符串总个数
		// System.out.println("字符串总个数:" + strContent.length());
		int rows = 1;
		if (strWidth > rowWidth) {
			int rowstrnum = getRowStrNum(strContent.length(), rowWidth, strWidth);
			rows = getRows(strWidth, rowWidth);
			String temp = "";
			for (int i = 0; i < rows; i++) {
				// 获取各行的String
				if (i == rows - 1) {
					// 最后一行
					temp = strContent.substring(i * rowstrnum, strContent.length());
				} else {
					temp = strContent.substring(i * rowstrnum, i * rowstrnum + rowstrnum);
				}
				if (i > 0) {
					// 第一行不需要增加字符高度，以后的每一行在换行的时候都需要增加字符高度
					y = y + strHeight;
				}
				graphics.drawString(temp, x, y);
			}
		} else {
			// 直接绘制
			graphics.drawString(strContent, x, y);
		}
	}

	public static void addContentToBufferImageSimple(BufferedImage destImage, int y, String strContent) {
		int width = destImage.getWidth();
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		int fontsize=destImage.getWidth()/ROW_LEN-15;
		
		if(width>2500) {
			fontsize=width/20;
		}else if(width>1000&&width<=2500) {
			fontsize=width/20;
		}else if(width<=1000&&width>500) {
			fontsize=width/20;
		}else if(width<=500) {
			fontsize=width/20;
		}
		Font font = new Font(FONT_STYLE, Font.CENTER_BASELINE, fontsize);
		graphics.setColor(new Color(51, 51, 51));
		MyFont myFont = new MyFont();
		Font definedFont2 = myFont.getDefinedFont(Font.BOLD, fontsize);
		graphics.setFont(definedFont2);
		int dwidth = destImage.getWidth();

		StringBuffer content=new StringBuffer();
		boolean  isAdd =false;

		for(int n =0; n<strContent.length(); n++){
			// 直接绘制
			String str = strContent.substring(n,n+1);
			if (regEx(str) || isAdd){
				isAdd =true;
				content.append(str);
			}else {
				content.append(str + " ");
				//System.out.println(strContent.substring(n,n+1));
			}
		}
		int strWidth = getStringLength(graphics, content.toString());

		int startX = 0;

		if (dwidth > strWidth) {
			startX = (dwidth - strWidth) / 2;
		}
		graphics.drawString(content.toString(), startX, y);

	}

	private static boolean regEx(String content){
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		return content.matches(regEx);
	}

	public static void addContentToBufferImageSimpleTips(BufferedImage destImage, int y, String strContent) {
		int size = destImage.getWidth()/ROW_LEN-20;
		int width = destImage.getWidth();
		if(width>2500) {
			size=width/20-5;
		}else if(width>1000&&width<=2500) {
			size=width/20-5;
		}else if(width<=1000&&width>500) {
			size=width/20-5;
		}else if(width<=500) {
			size=width/20-5;
		}
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		Font font = new Font(FONT_STYLE, Font.ROMAN_BASELINE, size);
		graphics.setColor(Color.gray);
		TipsFont tipsFont = new TipsFont();
		Font definedFont = tipsFont.getDefinedFont(Font.ROMAN_BASELINE, size);
		graphics.setFont(definedFont);
		int dwidth = destImage.getWidth();
		int strWidth = getStringLength(graphics, strContent);
		int startX = 0;
		if (dwidth > strWidth) {
			startX = (dwidth - strWidth) / 2;
		}
		// 直接绘制
		graphics.drawString(strContent, startX, y);
	}

	// 字符串总宽度
	private static int getStringLength(Graphics g, String str) {
		char[] strcha = str.toCharArray();
		int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
		// System.out.println("字符总宽度:" + strWidth);
		return strWidth;
	}

	// 字符高度
	private static int getStringHeight(Graphics2D g) {
		int height = g.getFontMetrics().getHeight();
		// System.out.println("字符高度:" + height);
		return height;
	}
	// 每一行字符串宽度 自定义 也就是画布中一行的宽度
	// int rowWidth=1110;

	// 字符串字符的个数
	// int strnum= str.length();
	// 每一行字符的个数
	private static int getRowStrNum(int strnum, int rowWidth, int strWidth) {
		int rowstrnum = 0;
		rowstrnum = (rowWidth * strnum) / strWidth;
		// System.out.println("每行的字符数:" + rowstrnum);
		return rowstrnum;
	}

	// 字符行数
	private static int getRows(int strWidth, int rowWidth) {
		int rows = 0;
		if (strWidth % rowWidth > 0) {
			rows = strWidth / rowWidth + 1;
		} else {
			rows = strWidth / rowWidth;
		}
		// System.out.println("行数:" + rows);
		return rows;
	}

	public static void main(String[] args) throws Exception {
		String biaozhun="http://resource.puxinwangxiao.com/df1b56c2921f96f451ef52cfe8e0697b.jpg";
		// 小尺寸
		String imageUrl1 = "http://resource.puxinwangxiao.com/80b13cef71142a71a62592cf2849f559.png";
		// 大尺寸
		String imageUrl = "http://resource.puxinwangxiao.com/6da35722df3ec498c978e061c942e1b8.jpg";
		String imageUrl3 = "http://resource.puxinwangxiao.com/b15c342a71be306e50f95eb9f71cea5d.jpg";
		String content3 = "2019一年级春季数学直播";
		String codeUrl3 = "https://topic.puxinwangxiao.com/class_details?product_id=51714470863740928&recommend_id=53211186754527232";
		String imageUrl_err = "http://resource.puxinwangxiao.com/844e270875538ff985f6aaeb18da7c37.jpg";
		String content = "2019新四年级数学暑期同步班二期（特价）";
		String codeUrl = "https://topic.puxinwangxiao.com/class_details?product_id=40816869821947904&recommend_id=undefined";
		String testURL="http://resource.puxinwangxiao.com/19fbcc24c747781f3027883d772b782a.png";
		
		String bj="http://resource.puxinwangxiao.com/df1b56c2921f96f451ef52cfe8e0697b.jpg";
		String buildImageInsertLogo1 = buildImageInsertLogo(bj, content, codeUrl,"￥699.00");


		System.out.println(buildImageInsertLogo1);

 
		    	  
	}
}
