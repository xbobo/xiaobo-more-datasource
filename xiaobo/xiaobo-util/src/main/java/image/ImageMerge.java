package image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import weixin.nati.UrlToQrcode;

public class ImageMerge {
	/**
	 * @param srcFile源图片、targetFile截好后图片全名、startAcross 开始截取位置横坐标、StartEndlong开始截图位置纵坐标、width截取的长，hight截取的高
	 * @Description:截图
	 * @author:liuyc
	 * @time:2016年5月27日 上午10:18:23
	 */
	public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width,
			int hight) throws Exception {
		// 取得图片读入器
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
		ImageReader reader = readers.next();
		// 取得图片读入流
		InputStream source = new FileInputStream(srcFile);
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		// 图片参数对象
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
	}

	/**
	 * @param files 要拼接的文件列表
	 * @param type1 横向拼接， 2 纵向拼接
	 * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
	 * @author:liuyc
	 * @time:2016年5月27日 下午5:52:24
	 */
	public static void mergeImage(String[] files, int type, String targetFile) {
		int len = files.length;
		if (len < 1) {
			throw new RuntimeException("图片数量小于1");
		}
		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(files[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];
			ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
		}
		int newHeight = 0;
		int newWidth = 0;
		for (int i = 0; i < images.length; i++) {
			// 横向
			if (type == 1) {
				newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
				newWidth += images[i].getWidth();
			} else if (type == 2) {// 纵向
				newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
				newHeight += images[i].getHeight();
			}
		}
		if (type == 1 && newWidth < 1) {
			return;
		}
		if (type == 2 && newHeight < 1) {
			return;
		}

		// 生成新图片
		try {
			BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			int width_i = 0;
			for (int i = 0; i < images.length; i++) {
				if (type == 1) {
					ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
							images[i].getWidth());
					width_i += images[i].getWidth();
				} else if (type == 2) {
					ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
					height_i += images[i].getHeight();
				}
			}
			// 输出想要的图片
			ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:小图片贴到大图片形成一张图(合成)
	 * @author:liuyc
	 * @time:2016年5月27日 下午5:51:20
	 */
	public static final void overlapImage(String bigPath, String smallPath, String outFile) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//字符串总宽度
	private static int  getStringLength(Graphics g,String str) {
        char[]  strcha=str.toCharArray();
        int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
        System.out.println("字符总宽度:"+strWidth);
        return strWidth;
    }
	//每一行字符串宽度  自定义  也就是画布中一行的宽度
	//int rowWidth=1110;  

	//字符串字符的个数 
	//int strnum= str.length();
	//每一行字符的个数
	private static int getRowStrNum(int strnum,int rowWidth,int strWidth){
		int rowstrnum=0;
		rowstrnum=(rowWidth*strnum)/strWidth;
		System.out.println("每行的字符数:"+rowstrnum);
		return rowstrnum;
		}
	//字符行数
	private static int  getRows(int strWidth,int rowWidth){
        int rows=0;
        if(strWidth%rowWidth>0){
            rows=strWidth/rowWidth+1;
        }else{
            rows=strWidth/rowWidth;
        }
        System.out.println("行数:"+rows);
        return rows;
    }
	//字符高度
	private static int  getStringHeight(Graphics g) {
        int height = g.getFontMetrics().getHeight();
        System.out.println("字符高度:"+height);
        return height;
    }
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://resource.puxinwangxiao.com/6cfe3267ec057cd3b4cb2b0db7aefc0c.jpg");
		BufferedImage courseBufferedImage = ImageIO.read(url);

		String[] urls = { "http://resource.puxinwangxiao.com/6cfe3267ec057cd3b4cb2b0db7aefc0c.jpg",
				"https://www.cnblogs.com/junrong624/p/4121656.html" };
		BufferedImage[] images = new BufferedImage[urls.length];
		int[][] ImageArrays = new int[urls.length][];
		for (int i = 0; i < urls.length; i++) {
			if (i == 0) {
				// 课程图片
				images[i] = ImageIO.read(new URL(urls[i]));
				int width = images[i].getWidth();
				int height = images[i].getHeight();
				ImageArrays[i] = new int[width * height];
				ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
			} else if (i == 1) {
				// url 生成二维码
				images[i] = UrlToQrcode.urlToBufferimage(urls[i]);
		        
				int width = images[i].getWidth();
				int height = images[i].getHeight();
				ImageArrays[i] = new int[width * height];
				ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
			}
		}
		int newHeight = 20*3;
		int newWidth = 10*2;
		for (int i = 0; i < images.length; i++) {
			newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
			newHeight += images[i].getHeight();
		}

		BufferedImage imageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) imageNew.getGraphics();
		//设置背景色 并赋予背景色
        g.setBackground(Color.WHITE);//设置背景色
        g.clearRect(0, 0, imageNew.getWidth(), imageNew.getHeight());//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        
        //开始绘制图片
       // g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.9f));
        BufferedImage currentbufferedImage = images[0];
        int startX=10;
        g.drawImage(currentbufferedImage, 0, 0, currentbufferedImage.getWidth(), currentbufferedImage.getHeight(), null);
        int previousWeight = currentbufferedImage.getWidth();
        int previousHeight = currentbufferedImage.getHeight();
        int fontNum=40;
		Font font = new Font("宋体", Font.BOLD, 40);
        g.setColor(Color.black);
		g.setFont(font);
        previousHeight=previousHeight+20+fontNum;
        String strContent="特价班活动分享(上)特价班活动分享(上)特价班活动分享(上)特价班活动分享(上)特价班活动分享";
        //获取字符串 字符的总宽度
        int strWidth =getStringLength(g,strContent);
        //每一行字符串宽度
        int rowWidth=1110;
        System.out.println("每行字符宽度:"+rowWidth);
        //获取字符高度
        int strHeight=getStringHeight(g);
        //字符串总个数
        System.out.println("字符串总个数:"+strContent.length());
        int  rows=1;
        if(strWidth>rowWidth){
            int rowstrnum=getRowStrNum(strContent.length(),rowWidth,strWidth);
            rows= getRows(strWidth,rowWidth);
            String temp="";
            for (int i = 0; i < rows; i++) {
                //获取各行的String 
                if(i==rows-1){
                    //最后一行
                    temp=strContent.substring(i*rowstrnum,strContent.length());
                }else{
                    temp=strContent.substring(i*rowstrnum,i*rowstrnum+rowstrnum);
                }
                if(i>0){
                    //第一行不需要增加字符高度，以后的每一行在换行的时候都需要增加字符高度
                	previousHeight=previousHeight+strHeight;
                }
                g.drawString(temp, 100, previousHeight);
            }
        }else{
            //直接绘制
            g.drawString(strContent, 100, previousHeight);
        }
        
        currentbufferedImage = images[1];
        int qrcodeWeight = currentbufferedImage.getWidth();
        int qrcodeStartX=10;
        if(qrcodeWeight<previousHeight) {
        	qrcodeStartX=(previousHeight-qrcodeWeight)/2;
        }
        g.drawImage(currentbufferedImage, qrcodeStartX, previousHeight, currentbufferedImage.getWidth(), currentbufferedImage.getHeight(), null);
        g.dispose();
        
        
		FileOutputStream fo = new FileOutputStream(new File("E:/1.png"));
		ImageIO.write(imageNew, "png", fo);
 
		System.out.println("over");
	}
}
