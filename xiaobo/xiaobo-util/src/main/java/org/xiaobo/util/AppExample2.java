package org.xiaobo.util;
/**
 * 
 * @author xiaobo
 * @date 2019年4月25日
 */
public class AppExample2 {

	public static void main(String[] args) throws Exception {
		
		JDBCProperties prop = new JDBCProperties();
		//驱动com.mysql.cj.jdbc.Driver  com.mysql.jdbc.Driver
		prop.setDriver_class_name("com.mysql.cj.jdbc.Driver");
		// jdbc 链接
		prop.setUrl("jdbc:mysql://localhost:3306/xiabo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
		// 账号
		prop.setUser_name("root");
		// 密码
		prop.setPassword("123456");
		//包基础路径
		prop.setPackage_path("com.puxinwangxiao.distribution.frontent");
		//文件生成路径 默认根路径下
		prop.setFile_path("E:\\entity-java");
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
		prop.setExclusive_tables(new String[] {"account"});
		//生成    数据库表数组   可不设
		prop.setInclude_tables(new String[] {"demo"});
		
		System.out.println("连接数据库wei:"+prop.getLibrary());
		
		JDBCUtil2.buildJavaFile(prop);
		
//		System.out.println(FileUtil.getCurrentPath());
//		System.out.println(FileUtil.getCurrentRootPath());
//		System.out.println(FileUtil.getCurrentSystemPath());

		
		 
		   
	}
}
