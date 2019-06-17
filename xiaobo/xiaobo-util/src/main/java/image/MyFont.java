package image;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

public class MyFont {

	private static Font definedFont = null; 
	private static String FONT_NAME_WIN="Microsoft Yahei.ttf";
	private static String FONT_NAME_LIN="PingFang.ttc";
	
	public  Font getDefinedFont(int style,float size) {
		String curFontName=FONT_NAME_WIN;
		String os = System.getProperty("os.name");  
		if(!os.toLowerCase().startsWith("win")){  
			curFontName=FONT_NAME_LIN;
		}
		if (definedFont == null) {  
            BufferedInputStream bis = null;  
            try {  
                ClassPathResource resource=new ClassPathResource(curFontName);
                bis = new BufferedInputStream(resource.getInputStream());  
                definedFont = Font.createFont(java.awt.Font.TRUETYPE_FONT, bis);
                //设置字体大小，float型
               definedFont = definedFont.deriveFont(style, size);
            } catch (FontFormatException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally { 
               try {  
                    if (null != bis) {  
                        bis.close();  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }
            }  
        }  
        return definedFont;  
    }  
}
