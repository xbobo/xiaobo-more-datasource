package org.xiaobo.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSONObject;
/**
 * 
 * @author xiaobo
 * @date 2019年4月25日
 */
public class JDBCUtil3 {
	public static boolean RUN_FLAG=true;

	public static void buildJavaFile(JDBCProperties prop) throws Exception {
		
		FileUtil3.FILE_PATH = prop.getFile_path();
		
		FileContentUtil3.PACKAGE_PATH=prop.getPackage_path();
		
		List<String> inncludeList = Arrays.asList(prop.getInclude_tables());
		
		List<String> exclusList = Arrays.asList(prop.getExclusive_tables());
		
		Class.forName(prop.getDriver_class_name());

		Connection con = DriverManager.getConnection(prop.getUrl(), prop.getUser_name(), prop.getPassword());
		System.out.println("成功获取连接");
		DatabaseMetaData dbMetaData = con.getMetaData();
		StringBuffer sbTables = new StringBuffer();
		List<String> tables = new ArrayList<String>();
		ResultSet tablers = dbMetaData.getTables(null, null, null, new String[] { "TABLE" });
		while (tablers.next()) {// ///TABLE_TYPE/REMARKS
			// 过滤库
			String current_lib = tablers.getString("TABLE_CAT");
			//System.out.println(current_lib);
			if (prop.getLibrary().equals(current_lib)) {
				String current_table = tablers.getString("TABLE_NAME");
				if (exclusList != null && exclusList.size() > 0 && exclusList.contains(current_table)) {
					continue;
				}
				if (inncludeList == null || inncludeList.size() == 0 || inncludeList.contains(current_table)) {
					tables.add(current_table);
				}
				sbTables.append("表名：" + tablers.getString("TABLE_NAME") + "<br/>");
				sbTables.append("表类型：" + tablers.getString("TABLE_TYPE") + "<br/>");
				sbTables.append("表所属数据库：" + tablers.getString("TABLE_CAT") + "<br/>");
				sbTables.append("表所属用户名：" + tablers.getString("TABLE_SCHEM") + "<br/>");
				sbTables.append("表备注：" + tablers.getString("REMARKS") + "<br/>");
				sbTables.append("------------------------------<br/>");
			}
		}
		System.out.println(JSONObject.toJSON(tables).toString());
		// 2、遍历数据库表，获取各表的字段等信息
		StringBuffer sbCloumns = new StringBuffer();
		for (String tableName : tables) {
			if(!RUN_FLAG) {
				System.exit(0);
			}
			LinkedHashMap<String, String> commentmap = new LinkedHashMap<String, String>();

			// TODO 查询sql注释信息
			String fullSql = "show full columns from `" + tableName+"`";
			PreparedStatement fullps = con.prepareStatement(fullSql);
			ResultSet fullinfo = fullps.executeQuery();
			while (fullinfo.next()) {
				String field = fullinfo.getString("Field");
				String comment = fullinfo.getString("Comment");
				commentmap.put(field, comment);
			}

			// TODO 查询字段信息
			String sql = "select * from `" + tableName+"`";
			StringBuilder query_clomns = new StringBuilder();
			// sql.WHERE("address_id = #{addressId,jdbcType=BIGINT}");
			// sql.SET("modify_time = #{modifyTime,jdbcType=BIGINT}");
			// sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
			try {
				ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(con.getCatalog(), null, tableName);
				String primaryKeyColumnName = "id";
				while (primaryKeyResultSet.next()) {
					primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
					System.out.println("主键为：" + primaryKeyColumnName);
					break;
				}
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet infors = ps.executeQuery();
				ResultSetMetaData meta = infors.getMetaData();
				int columeCount = meta.getColumnCount();

				sbCloumns.append("表 " + tableName + "共有 " + columeCount + " 个字段。字段信息如下：<br/>");
				//key 为字段      value 为 Java 类型  String  Long
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				//key 为字段  value   map: (key 为字段      value 为 数据库 JdbcType 类型  VARCHAR  BIGINT)
				LinkedHashMap<String, LinkedHashMap<String, String>> maps = new LinkedHashMap<String, LinkedHashMap<String, String>>();
				String cloumns = "";
				for (int i = 1; i < columeCount + 1; i++) {
					// 字段 名称
					String columnName = meta.getColumnName(i);
					cloumns += " `"+columnName + "`,";
					query_clomns.append(meta.getColumnName(i) + ",");
					sbCloumns.append("字段名：" + meta.getColumnName(i) + "<br/>");
					sbCloumns.append("类型：" + meta.getColumnType(i) + "<br/>");
					// 转换类型
					//JdbcType forCode = JdbcType.forCode(meta.getColumnType(i));
					
					// JdbcType  VARCHAR  BIGINT
					String codejavaType = JdbcType.forCode(meta.getColumnType(i)).name();
					
					//key 为字段      value 为 Java 类型  String  Long
					map.put(meta.getColumnName(i), TypeEnum.JavaType.getJavaType(codejavaType));

					// System.out.println(columnName+"::"+tableName2+"::"+TypeEnum.JavaType.getJavaType(codejavaType));
					//tempMap: (key 为字段      value 为 数据库 JdbcType 类型  VARCHAR  BIGINT)
					LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap.put(meta.getColumnName(i), JdbcType.forCode(meta.getColumnType(i)).name());
					
					
					maps.put(meta.getColumnName(i), tempMap);

					// System.out.println(forCode);
					// System.out.println(meta.getColumnTypeName(i));
					sbCloumns.append("------------------------------<br/>");
				}
				cloumns = cloumns.substring(0, cloumns.length() - 1)+" ";
				//String string = query_clomns.toString();
				String entityName = FileContentUtil3.transformTableName(tableName);
				System.out.println(" 表格 " + tableName + " 转换 start...");

				// 创建Java 实体
				if(prop.isEntity_flag()) {
					FileUtil3.createJavaFile("entity"+File.separator+entityName, FileContentUtil3.entityContent(tableName, map, commentmap));
				}
				// 创建Repository simple
				if(prop.isRepository_simple_flag()) {
					FileUtil3.createJavaFile("repository"+File.separator+entityName + "Repository",
							FileContentUtil3.repositoryContentSimple(tableName, primaryKeyColumnName, maps));
				}
				// 创建 provider
				if(prop.isProvider_flag()) {
					FileUtil3.createJavaFile("repository"+File.separator+"provider"+File.separator+entityName + "Provider",
							FileContentUtil3.providerContent(tableName,primaryKeyColumnName, cloumns, map));
				}
				// 创建Repository complex
				if(prop.isRepository_complex_flag()) {
					FileUtil3.createJavaFile("repository"+File.separator+entityName + "RepositoryComplex",
							FileContentUtil3.repositoryContentComplex(tableName, primaryKeyColumnName, maps));
				}
				//创建sql 模板
				if(prop.isSql_flag()) {
					FileUtil3.createNewFile("sql"+File.separator+entityName + "-sql",
							FileContentUtil3.sqlContent(tableName, map));
				}
				FileUtil3.createJavaFile("vo"+File.separator+entityName + "VO",
						FileContentUtil3.voContent(tableName, map, commentmap));
				FileUtil3.createJavaFile("vo"+File.separator + "Result",
						FileContentUtil3.resultContent(tableName, map, commentmap));
				FileUtil3.createJavaFile("dto"+File.separator+entityName + "DTO",
						FileContentUtil3.dtoContent(tableName, map, commentmap));
				FileUtil3.createJavaFile("dto"+File.separator+"BaseDTO",
						FileContentUtil3.basedtoContent(tableName, map, commentmap));
				//service 生成
				FileUtil3.createJavaFile("service"+File.separator+entityName + "Service",
						FileContentUtil3.serviceContent(tableName, map, commentmap));
				
				FileUtil3.createJavaFile("service"+File.separator+"impl"+File.separator+entityName + "ServiceImpl",
						FileContentUtil3.serviceImplContent(tableName, map, commentmap));
				//controller 生成
				FileUtil3.createJavaFile("controller"+File.separator+entityName + "Controller",
						FileContentUtil3.controllerContent(tableName, map, commentmap));
			} catch (Exception e) {
				e.printStackTrace();
			}
			sbCloumns.append("------------------------------<br/>");
		}
		con.close();
	}
}
