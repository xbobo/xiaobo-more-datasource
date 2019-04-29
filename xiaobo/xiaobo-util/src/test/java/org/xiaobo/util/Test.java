package org.xiaobo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(new Date(1546272000000l)));
	}
}
