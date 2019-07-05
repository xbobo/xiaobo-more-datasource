package org.xiaobo.util;

public class FramUtil {

	public static void mysqlBuild() {
		/***
		 * 数据库表设计  id  status deleted_flag
		 */
		JDBCProperties prop = new JDBCProperties();
		//驱动com.mysql.cj.jdbc.Driver  com.mysql.jdbc.Driver
		prop.setDriver_class_name("com.mysql.cj.jdbc.Driver");
		// jdbc 链接
		prop.setUrl(TestFlowLayout.URL);
		
		//prop.setUrl("jdbc:mysql://localhost:3306/online2_db_local?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
		// 账号
		prop.setUser_name(TestFlowLayout.ACCOUNT);
		// 密码
		prop.setPassword(TestFlowLayout.PASSWORD);
		//包基础路径
		//prop.setPackage_path("com.puxinwangxiao.erp.core");
		prop.setPackage_path(TestFlowLayout.PACKAGE_NAME);
		//文件生成路径 默认根路径下
		String path=TestFlowLayout.FILE_PATH;
		path=FileUtil2.makeDir(path);
		prop.setFile_path(path);
		//类示例是否创建
		prop.setEntity_flag(true);
		//provider是否创建
		prop.setProvider_flag(true);
		//repository简单增删改查是否创建
		prop.setRepository_simple_flag(true);
		//repository 复杂增删改查 是否创建
		prop.setRepository_complex_flag(true);
		//sql 模板 是否创建
		prop.setSql_flag(true);
		//生成    数据库表数组   可不设
		if(TestFlowLayout.TABLE_EXCLUSIVES!=null&&TestFlowLayout.TABLE_EXCLUSIVES.length>0) {
			prop.setExclusive_tables(TestFlowLayout.TABLE_EXCLUSIVES);
		}
		//生成    数据库表数组   可不设
		//prop.setInclude_tables(new String[] {"product_material","live_code","channel_code"});
		if(TestFlowLayout.TABLE_INCLUDES!=null&&TestFlowLayout.TABLE_INCLUDES.length>0) {
			prop.setInclude_tables(TestFlowLayout.TABLE_INCLUDES);
		}
		
		System.out.println("连接数据库wei:"+prop.getLibrary());
		
		try {
			JDBCUtil3.buildJavaFile(prop);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
