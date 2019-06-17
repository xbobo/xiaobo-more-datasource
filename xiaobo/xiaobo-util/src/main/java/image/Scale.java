package image;

import net.coobird.thumbnailator.Thumbnails;

public class Scale {
	public static void main(String[] args) throws Exception {
		 
	 Thumbnails.of("E:/logo1024.png").size(60, 60).toFile("E:/logo-100.png");;
	}
}
