package image;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import cn.hutool.core.img.Img;

public class PictureUtilQiniu {
	private static Logger logger = LoggerFactory.getLogger(PictureUtilQiniu.class);
	public static final Integer ROW_LEN = 16;
	public static final double SCAL_RATIO = 0.7;

	public static final int show_weight = 420;
	public static int show_height = 440;
	public static final int qrcode_weight = 98;
	public static final int qrcode_height = 98;
//	、将Font的创建从物理字体改为逻辑字体
//  Serif、SansSerif、Monospaced、Dialog 和 DialogInput 随意选择
	public static final String FONT_STYLE = "Serif";

	public static String buildImageInsertLogo1(String strurl, String content, String codeUrl, String price)
			throws IOException, MalformedURLException, Exception {
		BufferedImage srcImage = null;
		try {
			srcImage = ImageIO.read(new URL(strurl));
		} catch (Exception e) {
			srcImage = buildExceptionImage(strurl, srcImage, e);
		}
		System.out.println(srcImage.getWidth() + ":::" + srcImage.getHeight());
		int _width = 410;
		int _height = 200;
		int size = 10;
		srcImage = zoomImage(srcImage, _width, _height);
		System.out.println(srcImage.getWidth() + ":::" + srcImage.getHeight());
		srcImage = addPriceIcon(srcImage, price);

		BufferedImage destImage = new BufferedImage(show_weight, show_height, BufferedImage.TYPE_4BYTE_ABGR);
		initBackageColor(destImage);
		int curHeight = 16;
		int contentlength = content.length();
		BufferedImage codeBufferimage = ImageIO.read(new URL(codeUrl));//QrcodeUtil.imageInsertLogo(codeUrl, 120);

		// 添加图片
		addImageToBufferImageCommon(srcImage, destImage, 16, curHeight);
		curHeight = curHeight + _height + size;
		// 添加标题
		curHeight = addContentToBufferImageContents(destImage, curHeight, content, contentlength);
		curHeight = curHeight + size;
		// 添加二维码
		addImageToBufferImageMin(codeBufferimage, destImage, 0, curHeight);
		curHeight = curHeight + codeBufferimage.getHeight() + size + 10;
		// 添加二维码提示
		addContentToBufferImageSimpleTips(destImage, curHeight, "长按识别二维码");
		// 圆角处理
		destImage = (BufferedImage)Img.from(destImage).round(0.1).getImg();
		// BufferedImage zoomImage = zoomImage(destImage, 300, 400);
		// 缩放
		// BufferedImage resize = ResizeUtil.resize(destImage, allWeight, allHeight);
		// destImage=resize(destImage, show_weight, show_height);
//		FileOutputStream fo = new FileOutputStream(new File("E:/12.png"));
//		ImageIO.write(destImage, "png", fo);

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
		logger.info(fullPath);
		FileUtil.qiniu(fileName, fullPath);

		return "http://resource.puxinwangxiao.com/" + fileName;

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

	public static BufferedImage addPriceIcon(BufferedImage srcImage, String price) {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		int size = width / ROW_LEN / 2 + 2;
		// 画半透明 价格
		Graphics2D graphics = (Graphics2D) srcImage.createGraphics();
		// 1.0f为透明度 ，值从0-1.0，依次变得不透明
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
		graphics.setColor(Color.black);

		graphics.setStroke(new BasicStroke(1f));

		int len = price.length();
		graphics.fillRoundRect(width - width / 200 - len * size, height - size - 20, len * size / 2 + 25, 25, 10, 10);
		// 释放对象 透明度设置结束
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		Font font = new Font(PictureUtil.FONT_STYLE, Font.BOLD, size);
		graphics.setColor(Color.white);
		PriceFont priceFont = new PriceFont();
		Font definedFont = priceFont.getDefinedFont(Font.BOLD, size);
		graphics.setFont(definedFont);
		graphics.drawString(price, width - width / 200 - len * size + 5, height - 15);

		return srcImage;
	}

	public static String buildImageInsertLogo(String strurl, String content, String codeUrl, String price)
			throws IOException, MalformedURLException, Exception {
		BufferedImage srcImage = null;

		try {
			srcImage = ImageIO.read(new URL(strurl));
		} catch (Exception e) {
			srcImage = buildExceptionImage(strurl, srcImage, e);
		}

		int _width = 368;
		int _height = 200;
		int size = 10;
		srcImage = zoomImage(srcImage, _width, _height);
		srcImage = addPriceIcon(srcImage, price);

		BufferedImage destImage = new BufferedImage(show_weight, show_height, BufferedImage.TYPE_4BYTE_ABGR);
		initBackageColor(destImage);
		int curHeight = 16;
		int contentlength = content.length();
		BufferedImage codeBufferimage = QrcodeUtil.imageInsertLogo(codeUrl, 120);
		// codeBufferimage = zoomImage(codeBufferimage, 120,120);
		// System.out.println(codeBufferimage.getWidth()+":::"+codeBufferimage.getHeight());
		// 添加图片
		addImageToBufferImageCommon(srcImage, destImage, 16, curHeight);
		curHeight = curHeight + _height + size;
		// 添加标题
		curHeight = addContentToBufferImageContents(destImage, curHeight, content, contentlength);
		curHeight = curHeight + size;
		// 添加二维码
		addImageToBufferImageMin(codeBufferimage, destImage, 0, curHeight);
		curHeight = curHeight + codeBufferimage.getHeight() + size + 10;
		// 添加二维码提示
		addContentToBufferImageSimpleTips(destImage, curHeight, "长按识别二维码");
		// 圆角处理
		destImage = (BufferedImage)Img.from(destImage).round(0.1).getImg();
		// BufferedImage resize = ResizeUtil.resize(destImage, allWeight, allHeight);
		// destImage=resize(destImage, show_weight, show_height);
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
		logger.info(fullPath);
		FileUtil.qiniu(fileName, fullPath);

		return "http://resource.puxinwangxiao.com/" + fileName;
	}

	private static int addContentToBufferImageContents(BufferedImage destImage, int curHeight, String content,
			int contentlength) {
		if (contentlength > 0) {
			int size = destImage.getWidth() / ROW_LEN - 5;
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
				curHeight = curHeight + size + 5;
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
		g.dispose();
	}

	public static void addImageToBufferImageCommon(BufferedImage srcImage, BufferedImage destImage, int x, int y) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		srcImage = (BufferedImage)Img.from(srcImage).round(0.1).getImg();
		graphics.drawImage(srcImage, x, y, null);
		graphics.dispose();
	}

	public static void addImageToBufferImageMin(BufferedImage srcImage, BufferedImage destImage, int x, int y) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		// 消除文字锯齿
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int dwidth = destImage.getWidth();
		int swidth = srcImage.getWidth();
		int startX = 0;
		if (dwidth > swidth) {
			startX = (dwidth - swidth) / 2;
		}
		// 设置颜色和画笔粗细
//		graphics.setBackground(Color.black);
//		graphics.setColor(Color.black);
//		BasicStroke stroke = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f,
//				new float[] { 2, 1 }, 5f);
//		graphics.setStroke(stroke);
//		graphics.clearRect(startX, y, srcImage.getWidth(), srcImage.getHeight());// 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
//		graphics.drawRect(startX, y, srcImage.getWidth(), srcImage.getHeight());
		graphics.drawImage(srcImage, startX, y, null);
		graphics.dispose();
	}

	public static void addContentToBufferImage(BufferedImage destImage, int x, int y, String strContent, int rowWidth) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		// 消除文字锯齿
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		int size = destImage.getWidth() / ROW_LEN - 5;
		Font font = new Font(FONT_STYLE, Font.BOLD, size);
		graphics.setColor(new Color(51, 51, 51));
		MyFont myFont = new MyFont();
		Font definedFont2 = myFont.getDefinedFont(Font.BOLD, size);
		graphics.setFont(definedFont2);
		int dwidth = destImage.getWidth();
		int strWidth = getStringLength(graphics, strContent);
		int startX = 0;
		if (dwidth > strWidth) {
			startX = (dwidth - strWidth) / 2;
		}
		// 直接绘制
		graphics.drawString(strContent, startX, y);
		// 消除文字锯齿
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	public static void addContentToBufferImageSimpleTips(BufferedImage destImage, int y, String strContent) {
		int size = destImage.getWidth() / ROW_LEN - 8;
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		// 消除文字锯齿
		Font font = new Font(FONT_STYLE, Font.ROMAN_BASELINE, size);
		graphics.setColor(new Color(51, 51, 51));
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
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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

	/**
	 * 重调图片尺寸
	 *
	 * @param input     a {@link java.io.InputStream} object.
	 * @param output    a {@link java.io.OutputStream} object.
	 * @param width     a int.
	 * @param height    a int.
	 * @param maxWidth  a int.
	 * @param maxHeight a int.
	 */
	@SuppressWarnings("all")
	public static BufferedImage resize(BufferedImage img, int width, int height) throws Exception {

		try {

			boolean hasNotAlpha = !img.getColorModel().hasAlpha();
			double w = img.getWidth(null);
			double h = img.getHeight(null);
			int toWidth;
			int toHeight;
			double rate = w / h;

			if (width > 0 && height > 0) {
				rate = ((double) width) / ((double) height);
				toWidth = width;
				toHeight = height;
			} else if (width > 0) {
				toWidth = width;
				toHeight = (int) (toWidth / rate);
			} else if (height > 0) {
				toHeight = height;
				toWidth = (int) (toHeight * rate);
			} else {
				toWidth = ((Number) w).intValue();
				toHeight = ((Number) h).intValue();
			}

			BufferedImage tag = new BufferedImage(toWidth, toHeight,
					hasNotAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);

			// Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
			tag.getGraphics().drawImage(img.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH), 0, 0, null);

			return tag;

			// ImageIO.write(tag, hasNotAlpha ? "jpg" : "png", output);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
		}
	}

	/**
	 * 直接指定压缩后的宽高： (先保存原文件，再压缩、上传) 壹拍项目中用于二维码压缩
	 * 
	 * @param oldFile   要进行压缩的文件全路径
	 * @param width     压缩后的宽度
	 * @param height    压缩后的高度
	 * @param quality   压缩质量
	 * @param smallIcon 文件名的小小后缀(注意，非文件后缀名称),入压缩文件名是yasuo.jpg,则压缩后文件名是yasuo(+smallIcon).jpg
	 * @return 返回压缩后的文件的全路径
	 */
//	public static String zipImageFile(String oldFile, int width, int height,
//									  float quality, String smallIcon) {
//		if (oldFile == null) {
//			return null;
//		}
//		String newImage = null;
//		try {
//			/**对服务器上的临时文件进行处理 */
//			Image srcFile = ImageIO.read(new File(oldFile));
//			/** 宽,高设定 */
//			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//			tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
//			String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
//			/** 压缩后的文件名 */
//			newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
//			/** 压缩之后临时存放位置 */
//			FileOutputStream out = new FileOutputStream(newImage);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//			/** 压缩质量 */
//			jep.setQuality(quality, true);
//			encoder.encode(tag, jep);
//			out.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return newImage;
//	}

	/**
	 * 原始图像 倍数,比如0.5就是缩小一半,0.98等等double类型
	 * 
	 * @return 返回处理后的图像
	 */
	public static BufferedImage zoomImage(String src, int toWidth, int toHeight) {

		BufferedImage result = null;
		try {
			BufferedImage im = ImageIO.read(new URL(src));

			/* 原始图像的宽度和高度 */
			int width = im.getWidth();
			int height = im.getHeight();

			// 压缩计算
			// float resizeTimes = 0.3f; /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/

			/* 调整后的图片的宽度和高度 */
			// int toWidth = (int) (width * resizeTimes);
			// int toHeight = (int) (height * resizeTimes);

			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);

		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}

		return result;

	}

	public static BufferedImage zoomImage(BufferedImage src, int toWidth, int toHeight) {

		BufferedImage result = null;
		try {
			/* 原始图像的宽度和高度 */
			int width = src.getWidth();
			int height = src.getHeight();

			// 压缩计算
			// float resizeTimes = 0.3f; /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/

			/* 调整后的图片的宽度和高度 */
			// int toWidth = (int) (width * resizeTimes);
			// int toHeight = (int) (height * resizeTimes);

			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

			result.getGraphics().drawImage(src.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);

		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}

		return result;

	}

	public static void main(String[] args) throws Exception {
		String testImage="http://resource.puxinwangxiao.com/19fbcc24c747781f3027883d772b782a.png";
		String imageUrl1 = "http://resource.puxinwangxiao.com/80b13cef71142a71a62592cf2849f559.png";
		String imageUrl = "http://resource.puxinwangxiao.com/844e270875538ff985f6aaeb18da7c37.jpg";
		String content = "2019新四年级数学暑期同步班二期（特价）";
		String codeUrl = "https://topic.puxinwangxiao.com/class_details?product_id=40816869821947904&recommend_id=undefined";
		String bj="http://resource.puxinwangxiao.com/df1b56c2921f96f451ef52cfe8e0697b.jpg";
		String buildImageInsertLogo1 = buildImageInsertLogo(bj, content, codeUrl, "￥699.00");
		System.out.println(buildImageInsertLogo1);
	}
}
