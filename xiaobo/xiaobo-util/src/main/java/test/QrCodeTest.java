package test;

import org.xiaobo.util.QRCodeUtil;

public class QrCodeTest {

	public static void main(String[] args) throws Exception {
		// 存放在二维码中的内容
		String text = "id=123&type=456";
		// 嵌入二维码的图片路径
		String imgPath = "E:/1.jpg";
		// 生成的二维码的路径及名称
		String destPath = "E:/test.jpg";
		//生成二维码
		QRCodeUtil.encode(text, imgPath, destPath, true);
		// 解析二维码
		String str = QRCodeUtil.decode(destPath);
		// 打印出解析出的内容
		System.out.println(str);
 
	}
}
