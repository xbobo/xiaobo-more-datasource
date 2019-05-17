package image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class G3 {
	//你可以了解下thumbnailator这个图片处理库https://github.com/coobird/thumbnailator
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://resource.puxinwangxiao.com/6cfe3267ec057cd3b4cb2b0db7aefc0c.jpg");
		BufferedImage image = ImageIO.read(url);
	    int w = image.getWidth(); 
	        int h = image.getHeight(); 
	        BufferedImage output = new BufferedImage(w, h, 
	                BufferedImage.TYPE_INT_ARGB); 

	        Graphics2D g2 = output.createGraphics(); 
	g2.setColor(new Color(0,0,0,0)); //用全透明颜色
	g2.fillRect(0,0,w,h);//覆盖整个画布

	        // This is what we want, but it only does hard-clipping, i.e. aliasing 
	        // g2.setClip(new RoundRectangle2D ...) 

	        // so instead fake soft-clipping by first drawing the desired clip shape 
	        // in fully opaque white with antialiasing enabled... 
	        g2.setComposite(AlphaComposite.SrcOut); 
	        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST, 0.0f)); 
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
	                RenderingHints.VALUE_ANTIALIAS_ON); 
	        g2.setColor(new Color(0,0,0)); 
	        g2.setBackground(Color.black);  
	            g2.setPaint(new Color(0,0,0));  
	        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 60, 
	                60)); 
	        // ... then compositing the image on top, 
	        // using the white shape from above as alpha source 
	        g2.setComposite(AlphaComposite.SrcAtop); 
	        g2.drawImage(image, 0, 0, null); 

	        g2.dispose(); 
	        
	        
	        
	        FileOutputStream fo = new FileOutputStream(new File("E:/1.png"));
			ImageIO.write(image, "png", fo);
	}
}
