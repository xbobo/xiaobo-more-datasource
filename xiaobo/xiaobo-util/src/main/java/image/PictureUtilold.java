package image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import cn.hutool.core.img.Img;

public class PictureUtilold {
	public static final Integer ROW_LEN=18;
	public static final double SCAL_RATIO=0.7;
//	、将Font的创建从物理字体改为逻辑字体
//  Serif、SansSerif、Monospaced、Dialog 和 DialogInput 随意选择
	public static final String FONT_STYLE="Serif";
	public static void main(String[] args) throws Exception {
		String imageUrl="http://resource.puxinwangxiao.com/472730321956b39044457aea823bc024.png";
		String content = "高三特价班001高三特价班001高三特价班001高三特价班001";
		String codeUrl="http://192.168.0.22:8888/#/spelial_detail?activityId=2019051417002003";
		 buildImageInsertLogo1(imageUrl, content, codeUrl);
		System.out.println("");
	}
	public static String buildImage(String strurl, String content,
			String codeUrl) throws IOException, MalformedURLException, Exception {
		BufferedImage srcImage = ImageIO.read(new URL(strurl));
		srcImage = zoomByScale(srcImage, SCAL_RATIO);
		int allWeight = srcImage.getWidth()+16*2;
		int srcheight = srcImage.getHeight();
		int allHeight=srcheight+300+16*4+40+20;
		int coententLen=content.length();
		if(coententLen>0) {
			int num=coententLen/ROW_LEN;
			if(coententLen%ROW_LEN>0) {
				num=num+1;
			}
			allHeight=allHeight+40*num;
		}
		BufferedImage destImage = new BufferedImage(allWeight, allHeight, BufferedImage.TYPE_INT_RGB);
		initBackageColor(destImage);
		int curHeight=16;
		int contentlength = content.length();
		BufferedImage codeBufferimage = QrcodeUtilold
				.urlToBufferimage(codeUrl);

		//添加图片
		addImageToBufferImageCommon(srcImage, destImage, 16, curHeight);
		curHeight=curHeight+srcheight+16*2;
		//添加标题
		curHeight = addContentToBufferImageContents(destImage, curHeight, content, contentlength);
		curHeight=curHeight+16;
		//添加二维码
		addImageToBufferImageMin(codeBufferimage, destImage, 0, curHeight);
		curHeight=curHeight+codeBufferimage.getHeight()+16;
		//添加二维码提示
		addContentToBufferImageSimple(destImage,curHeight, "长按识别二维码");
		// 圆角处理
		destImage =(BufferedImage) Img.from(destImage).round(0.1).getImg();
		//缩放
		BufferedImage resize = ResizeUtil.resize(destImage, allWeight, allHeight);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        ImageIO.write(resize, "png", baos);//写入流中
        byte[] bytes = baos.toByteArray();//转换成字节
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String png_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return "data:image/png;base64,"+png_base64;
	}
	
	/**
     * 按比例对图片进行缩放.
     * @param scale 缩放比例
     * @throws IOException
     */
    public static BufferedImage zoomByScale(BufferedImage srcImage,double scale) throws IOException {
        //获取缩放后的长和宽
        int _width = (int) (scale * srcImage.getWidth());
        int _height = (int) (scale * srcImage.getHeight());
        //获取缩放后的Image对象
        Image _img = srcImage.getScaledInstance(_width, _height, Image.SCALE_DEFAULT);
        //新建一个和Image对象相同大小的画布
        BufferedImage image = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D graphics = image.createGraphics();
        //将Image对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
        graphics.drawImage(_img, 0, 0, null);
        //释放资源
        graphics.dispose();
        //使用ImageIO的方法进行输出,记得关闭资源
//        OutputStream out = new FileOutputStream(destFile);
//        ImageIO.write(image, ext, out);
//        out.close();
        return image;
    }
    /**
     * 指定长和宽对图片进行缩放
     * @param width 长
     * @param height 宽
     * @throws IOException
     */
    public static BufferedImage zoomBySize(BufferedImage srcImage,int width, int height) throws IOException {
        //与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
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
	public static String buildImageInsertLogo1(String strurl, String content,
			String codeUrl) throws IOException, MalformedURLException, Exception {
		BufferedImage srcImage = ImageIO.read(new URL(strurl));
		srcImage = zoomByScale(srcImage, SCAL_RATIO);
		int allWeight = srcImage.getWidth()+16*2;
		int srcheight = srcImage.getHeight();
		int allHeight=srcheight+300+16*4+40+20;
		int coententLen=content.length();
		if(coententLen>0) {
			int num=coententLen/ROW_LEN;
			if(coententLen%ROW_LEN>0) {
				num=num+1;
			}
			allHeight=allHeight+40*num;
		}
		
		BufferedImage destImage = new BufferedImage(allWeight, allHeight, BufferedImage.TYPE_INT_RGB);
		initBackageColor(destImage);
		int curHeight=16;
		int contentlength = content.length();
		BufferedImage codeBufferimage = QrcodeUtilold.imageInsertLogo(codeUrl);

		//添加图片
		addImageToBufferImageCommon(srcImage, destImage, 16, curHeight);
		curHeight=curHeight+srcheight+16*2;
		//添加标题
		curHeight = addContentToBufferImageContents(destImage, curHeight, content, contentlength);
		curHeight=curHeight+16;
		//添加二维码
		addImageToBufferImageMin(codeBufferimage, destImage, 0, curHeight);
		curHeight=curHeight+codeBufferimage.getHeight()+16;
		//添加二维码提示
		addContentToBufferImageSimple(destImage,curHeight, "长按识别二维码");
		// 圆角处理
		destImage = (BufferedImage)Img.from(destImage).round(0.1).getImg();
		//缩放
		BufferedImage resize = ResizeUtil.resize(destImage, allWeight, allHeight);
		FileOutputStream fo = new FileOutputStream(new File("E:/12.png"));
		ImageIO.write(destImage, "png", fo);
        
        return "";
	}
 

	public static String buildImageInsertLogo(String strurl, String content,
			String codeUrl) throws IOException, MalformedURLException, Exception {
		BufferedImage srcImage = ImageIO.read(new URL(strurl));
		srcImage = zoomByScale(srcImage, SCAL_RATIO);
		int allWeight = srcImage.getWidth()+16*2;
		int srcheight = srcImage.getHeight();
		int allHeight=srcheight+300+16*4+40+20;
		int coententLen=content.length();
		if(coententLen>0) {
			int num=coententLen/ROW_LEN;
			if(coententLen%ROW_LEN>0) {
				num=num+1;
			}
			allHeight=allHeight+40*num;
		}
		BufferedImage destImage = new BufferedImage(allWeight, allHeight, BufferedImage.TYPE_INT_RGB);
		initBackageColor(destImage);
		int curHeight=16;
		
		int contentlength = content.length();
		BufferedImage codeBufferimage = QrcodeUtilold.imageInsertLogo(codeUrl);

		//添加图片
		addImageToBufferImageCommon(srcImage, destImage, 16, curHeight);
		curHeight=curHeight+srcheight+16*2;
		//添加标题
		curHeight = addContentToBufferImageContents(destImage, curHeight, content, contentlength);
		curHeight=curHeight+16;
		//添加二维码
		addImageToBufferImageMin(codeBufferimage, destImage, 0, curHeight);
		curHeight=curHeight+codeBufferimage.getHeight()+16;
		//添加二维码提示
		addContentToBufferImageSimple(destImage,curHeight, "长按识别二维码");
		// 圆角处理
		destImage = (BufferedImage)Img.from(destImage).round(0.1).getImg();
		//缩放
		BufferedImage resize = ResizeUtil.resize(destImage, allWeight, allHeight);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        ImageIO.write(resize, "png", baos);//写入流中
        byte[] bytes = baos.toByteArray();//转换成字节
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String png_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return "data:image/png;base64,"+png_base64;
	}

	private static int addContentToBufferImageContents(BufferedImage destImage, int curHeight, String content,
			int contentlength) {
		if(contentlength>0) {
			int num=contentlength/ROW_LEN;
			if(contentlength%ROW_LEN>0) {
				num=num+1;
			}
			for(int x=0;x<num;x++) {
				int startIndex=x*ROW_LEN;
				int endIndex=ROW_LEN*(x+1);
				if(contentlength<endIndex) {
					endIndex=contentlength;
				}
				String curContent=content.substring(startIndex, endIndex);
				curHeight=curHeight+40;
				addContentToBufferImageSimple(destImage,curHeight, curContent);
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
		int startX=0;
		if(dwidth>swidth) {
			startX=(dwidth-swidth)/2;
		}
		graphics.drawImage(srcImage, startX, y, null);
	}

	public static void addContentToBufferImage(BufferedImage destImage, int x, int y, String strContent,int rowWidth) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		Font font = new Font(FONT_STYLE, Font.BOLD, 40);
		graphics.setColor(Color.black);
		graphics.setFont(font);
		// 获取字符串 字符的总宽度
		int strWidth = getStringLength(graphics, strContent);
		// 每一行字符串宽度
		//int rowWidth = 600;
		//System.out.println("每行字符宽度:" + rowWidth);
		// 获取字符高度
		int strHeight = getStringHeight(graphics);
		// 字符串总个数
		//System.out.println("字符串总个数:" + strContent.length());
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
	
	
	public static void addContentToBufferImageSimple(BufferedImage destImage,  int y, String strContent) {
		Graphics2D graphics = (Graphics2D) destImage.getGraphics();
		Font font = new Font(FONT_STYLE, Font.BOLD, 40);
		graphics.setColor(Color.black);
		graphics.setFont(font);
		int dwidth = destImage.getWidth();
		int strWidth = getStringLength(graphics, strContent);
		int startX=0;
		if(dwidth>strWidth) {
			startX=(dwidth-strWidth)/2;
		}
		// 直接绘制
		graphics.drawString(strContent, startX, y);
	}

	// 字符串总宽度
	private static int getStringLength(Graphics g, String str) {
		char[] strcha = str.toCharArray();
		int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
		//System.out.println("字符总宽度:" + strWidth);
		return strWidth;
	}

	// 字符高度
	private static int getStringHeight(Graphics2D g) {
		int height = g.getFontMetrics().getHeight();
		//System.out.println("字符高度:" + height);
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
		//System.out.println("每行的字符数:" + rowstrnum);
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
		//System.out.println("行数:" + rows);
		return rows;
	}
	 /**
     * 重调图片尺寸
     *
     * @param input
     *            a {@link java.io.InputStream} object.
     * @param output
     *            a {@link java.io.OutputStream} object.
     * @param width
     *            a int.
     * @param height
     *            a int.
     * @param maxWidth
     *            a int.
     * @param maxHeight
     *            a int.
     */
    @SuppressWarnings("all" )
    public static  BufferedImage  resize(BufferedImage img,  int width, int height ) throws Exception {
 
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
 
 
            BufferedImage tag = new BufferedImage(toWidth, toHeight, hasNotAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
 
            // Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
            tag.getGraphics().drawImage(img.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH), 0, 0, null);
 
            return  tag;
 
          //  ImageIO.write(tag, hasNotAlpha ? "jpg" : "png", output);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
        }
    }
}