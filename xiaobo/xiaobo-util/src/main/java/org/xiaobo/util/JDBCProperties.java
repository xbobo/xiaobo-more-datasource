package org.xiaobo.util;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author xiaobo
 * @date 2019年4月25日
 */
public class JDBCProperties {
	//驱动
	private String driver_class_name = "com.mysql.jdbc.Driver"; // 驱动
	// jdbc 链接
	private String url = "jdbc:mysql://localhost:3306/base?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	// 账号
	private String user_name = "root";
	// 密码
	private String password = "123456"; 
	//类示例是否创建
	private boolean entity_flag=true;
	//provider是否创建
	private boolean provider_flag=true;
	//repository简单增删改查是否创建
	private boolean repository_simple_flag=true;
	//repository 复杂增删改查 是否创建
	private boolean repository_complex_flag=true;
	//sql 模板 是否创建
	private boolean sql_flag=true;
	// 生成包路径
	private String package_path = "org.example.com";
	// 生成文件  默认根路径下
	private String file_path="";

	@SuppressWarnings("unused")
	private String library;// 数据库库名
	//生成    数据库表数组   可不设
	private String[] include_tables=new String[] {};// 包含表名
	//不生成  数据库表数组  可不设
	private String[] exclusive_tables=new String[] {};

	public String getDriver_class_name() {
		return driver_class_name;
	}

	public void setDriver_class_name(String driver_class_name) {
		this.driver_class_name = driver_class_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPackage_path() {
		return package_path;
	}

	public void setPackage_path(String package_path) {
		this.package_path = package_path;
	}

	public String getLibrary() {
		 
		return buildLibaryName(url);
	}

	public String[] getInclude_tables() {
		return include_tables;
	}

	public void setInclude_tables(String[] include_tables) {
		this.include_tables = include_tables;
	}

	public String[] getExclusive_tables() {
		return exclusive_tables;
	}

	public void setExclusive_tables(String[] exclusive_tables) {
		this.exclusive_tables = exclusive_tables;
	}

	public String getFile_path() {
		if(StringUtils.isEmpty(file_path)) {
			return FileUtil.getCurrentRootPath();
		}
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public boolean isEntity_flag() {
		return entity_flag;
	}

	public void setEntity_flag(boolean entity_flag) {
		this.entity_flag = entity_flag;
	}

	public boolean isProvider_flag() {
		return provider_flag;
	}

	public void setProvider_flag(boolean provider_flag) {
		this.provider_flag = provider_flag;
	}

	public boolean isRepository_simple_flag() {
		return repository_simple_flag;
	}

	public void setRepository_simple_flag(boolean repository_simple_flag) {
		this.repository_simple_flag = repository_simple_flag;
	}

	public boolean isRepository_complex_flag() {
		return repository_complex_flag;
	}

	public void setRepository_complex_flag(boolean repository_complex_flag) {
		this.repository_complex_flag = repository_complex_flag;
	}

	public boolean isSql_flag() {
		return sql_flag;
	}

	public void setSql_flag(boolean sql_flag) {
		this.sql_flag = sql_flag;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	private static String buildLibaryName(String url) {
		if(url!=null) {
			int doubleSlashIndex = url.lastIndexOf("//");
			int singleSlashindex = url.indexOf("/", doubleSlashIndex+2);
			int questionMarkIndex = url.indexOf("?");
			return url.substring(singleSlashindex+1,questionMarkIndex);
		}
		return "";
	}
	
	public static void main(String[] args) {
		String str="sdfsdf/xiaobo?fsdf";
		int doubleSlashIndex = str.lastIndexOf("//");
		int singleSlashindex = str.indexOf("/", doubleSlashIndex+2);
		int questionMarkIndex = str.indexOf("?");
		System.out.println(str.substring(singleSlashindex+1,questionMarkIndex));
		 
	}
}
