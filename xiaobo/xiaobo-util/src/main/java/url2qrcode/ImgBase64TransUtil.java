package url2qrcode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 二维码转base64 工具
 * 
 * @author xiaobo
 * @date 2019年5月8日
 */
public class ImgBase64TransUtil {

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr base64编码字符串
	 * @param path   图片路径-具体到文件
	 * @return
	 */
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null)
			return false;
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author:
	 * @CreateTime:
	 * @return
	 */
	public static String getImageStr(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * 不过需要注意的是，一般插件返回的base64编码的字符串都是有一个前缀的。

"data:image/jpeg;base64," 解码之前这个得去掉。
	 * 示例
	 */
	public static void main(String[] args) {
		String strImg = getImageStr("C:\\Users\\DELL\\Desktop\\testQrcode\\temp.jpg");
		System.out.println("data:image/jpeg;base64,"+strImg);
		generateImage(strImg, "C:\\Users\\DELL\\Desktop\\testQrcode\\temp1.jpg");
	}
}
