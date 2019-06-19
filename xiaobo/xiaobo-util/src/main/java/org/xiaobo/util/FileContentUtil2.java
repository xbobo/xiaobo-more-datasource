package org.xiaobo.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author xiaobo
 * @date 2019年4月25日
 */
public class FileContentUtil2 {
	public static String lineFeed = "\r\n";
	
	public static String PACKAGE_PATH = "org";
	
	/**
	 * sql 生成
	 * @param tableName
	 * @param commentmap
	 * @return
	 */
	public static String sqlContent(String tableName,LinkedHashMap<String, String> map) {
		StringBuffer bufferContent=new StringBuffer();
		for(Entry<String, String> entry:map.entrySet()) {
			bufferContent.append(" t.`"+entry.getKey()+"`,");
		}
		String content = bufferContent.toString();
		return " SELECT "+content.substring(0, content.length()-1)+" FROM `"+tableName+"` t ;";
	}

	/***
	 * reponsitory 生成  提供简单增删改查接口
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static String repositoryContentSimple(String tableName,String primaryKeyColumnName, LinkedHashMap<String, LinkedHashMap<String, String>> map) {
		String fileStart = transformTableName(tableName);
		String entity = fileStart + "";
		String repositoryStr = "" + "package "+PACKAGE_PATH+".repository;" + lineFeed + lineFeed + "import java.util.List;" + lineFeed
				+ "import org.apache.ibatis.annotations.DeleteProvider;" + lineFeed
				+ "import org.apache.ibatis.annotations.InsertProvider;" + lineFeed
				+ "import org.apache.ibatis.annotations.Options;" + lineFeed
				+ "import org.apache.ibatis.annotations.Result;" + lineFeed
				+ "import org.apache.ibatis.annotations.Results;" + lineFeed
				+ "import org.apache.ibatis.annotations.SelectProvider;" + lineFeed
				+ "import org.apache.ibatis.annotations.UpdateProvider;" + lineFeed
				+ "import org.apache.ibatis.type.JdbcType;" + lineFeed
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" + lineFeed 
				+ "public interface " + fileStart
				+ "Repository {" + lineFeed + "" + lineFeed + "	@InsertProvider(type = " + fileStart
				+ "Provider.class,method = \"save\")" + lineFeed
				+ "	@Options(useGeneratedKeys = true,keyColumn = \""+primaryKeyColumnName+"\",keyProperty = \""+transformFieldName(primaryKeyColumnName)+"\")" + lineFeed
				+ "	int save(" + entity + "  entity);" + lineFeed + "" + lineFeed + "	@UpdateProvider(type = " + fileStart
				+ "Provider.class,method = \"update\")" + lineFeed + "	int update(" + entity + " entity);" + lineFeed + ""
				+ lineFeed + "	@DeleteProvider(type = " + fileStart + "Provider.class,method = \"remove\")" + lineFeed
				+ "	int remove(" + entity + " entity);" + lineFeed + "" + lineFeed + "	@SelectProvider(type = " + fileStart
				+ "Provider.class,method = \"find\")" + lineFeed + "	@Results(id = \"findResultList\",value = {"
				+ lineFeed;
		for (Entry<String, LinkedHashMap<String, String>> entry : map.entrySet()) {
			for (Entry<String, String> innterEntry : entry.getValue().entrySet()) {
				repositoryStr += "		@Result(column =\"" + entry.getKey() + "\" ,property = \""
						+ transformFieldName(innterEntry.getKey()) + "\",jdbcType = JdbcType." + innterEntry.getValue() + ")," + lineFeed;
			}
		}
		// 去除逗号
		repositoryStr = repositoryStr.substring(0, repositoryStr.length() - 1);
		repositoryStr += "	})" + lineFeed + "	List<" + fileStart + "> find(" + entity + " entity);" + lineFeed + ""
				+ lineFeed + "}";
		return repositoryStr;
	}
	
	/***
	 * reponsitory 生成  复杂
	 *  批量添加
	 *  批量 修改   
	 *  批量删除
	 * 注意：批量操作需要开启数据库 批量操作命令  &allowMultiQueries=true
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static String repositoryContentComplex(String tableName,String primaryKeyColumnName, LinkedHashMap<String, LinkedHashMap<String, String>> map) {
		String fileStart = transformTableName(tableName);
		String entity = fileStart + "";
		String repositoryStr = "" 
				+ "package "+PACKAGE_PATH+".repository;" 
					+ lineFeed 
					+ lineFeed 
				+ "import java.util.List;" + lineFeed
				+ "import org.apache.ibatis.annotations.Delete;" + lineFeed 
				+ "import org.apache.ibatis.annotations.DeleteProvider;" + lineFeed 
				+ "import org.apache.ibatis.annotations.Insert;" + lineFeed 
				+ "import org.apache.ibatis.annotations.InsertProvider;" + lineFeed 
				+ "import org.apache.ibatis.annotations.Lang;" + lineFeed 
				+ "import org.apache.ibatis.annotations.Options;" + lineFeed 
				+ "import org.apache.ibatis.annotations.Param;" + lineFeed 
				+ "import org.apache.ibatis.annotations.Result;" + lineFeed 
				+ "import org.apache.ibatis.annotations.Results;" + lineFeed 
				+ "import org.apache.ibatis.annotations.SelectProvider;" + lineFeed 
				+ "import org.apache.ibatis.annotations.Update;" + lineFeed 
				+ "import org.apache.ibatis.annotations.UpdateProvider;" + lineFeed 
				+ "import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;" + lineFeed 
				+ "import org.apache.ibatis.type.JdbcType;" + lineFeed 
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" + lineFeed 
				+ "public interface " + fileStart
				+ "RepositoryComplex {" + lineFeed 
				+ "" + lineFeed ;
		String baseSave= "	@InsertProvider(type = " + fileStart
				+ "Provider.class,method = \"save\")" + lineFeed
				+ "	@Options(useGeneratedKeys = true,keyColumn = \""+primaryKeyColumnName+"\",keyProperty = \""+transformFieldName(primaryKeyColumnName)+"\")" + lineFeed
				+ "	int save(" + entity + "  entity);" + lineFeed + "" 
				+ lineFeed ;
		String baseUpdate= "	@UpdateProvider(type = " + fileStart+ "Provider.class,method = \"update\")" + lineFeed 
				+ "	int update(" + entity + " entity);" + lineFeed + ""
				+ lineFeed ;
		String baseRemove= "	@DeleteProvider(type = " + fileStart + "Provider.class,method = \"remove\")" + lineFeed
				+ "	int remove(" + entity + " entity);" + lineFeed + "" + lineFeed ;
		String baseFind="	@SelectProvider(type = " + fileStart
				+ "Provider.class,method = \"find\")" + lineFeed + "	"
						+ "@Results(id = \"findResultList\",value = {"
				+ lineFeed;
		StringBuffer saveItemBuffer=new StringBuffer();
		StringBuffer updateItemBuffer=new StringBuffer();
		
		StringBuffer saveFieldBuffer=new StringBuffer();
		
		for (Entry<String, LinkedHashMap<String, String>> entry : map.entrySet()) {
			for (Entry<String, String> innterEntry : entry.getValue().entrySet()) {
				saveFieldBuffer.append(" `"+entry.getKey()+"`,");
				baseFind += "		@Result(column =\"" + entry.getKey() + "\" ,property = \""
						+ innterEntry.getKey() + "\",jdbcType = JdbcType." + innterEntry.getValue() + ")," + lineFeed;
				
				//saveItemBuffer 
				saveItemBuffer.append("			+\"#{item."+transformFieldName(entry.getKey())+"} ,\""+ lineFeed);
				//updateItemBuffer
//				+ \"<if test='item.content != null and item.content.length>0 '>\"
//	              + \" `content` = #{item.content} ,\"
//	             + \"</if>\"
				String javaType = TypeEnum.JavaType.getJavaType(innterEntry.getValue());
				if(primaryKeyColumnName.equals(entry.getKey())) {
					//TODO 是主键 不赋值
				}else if(javaType.equals(TypeEnum.JavaType.getJavaType(""))){
					updateItemBuffer.append("			+ \"<if test='item."+transformFieldName(entry.getKey())+" != null and item."+transformFieldName(entry.getKey())+".length>0 '>\""+ lineFeed);
					updateItemBuffer.append("			+ \" `"+entry.getKey()+"` = #{item."+transformFieldName(entry.getKey())+"} ,\""+ lineFeed);
					updateItemBuffer.append("			+ \"</if>\""+ lineFeed);
				}else {
					updateItemBuffer.append("			+ \"<if test='item."+transformFieldName(entry.getKey())+" != null '>\""+ lineFeed);
					updateItemBuffer.append("			+ \" `"+entry.getKey()+"` = #{item."+transformFieldName(entry.getKey())+"} ,\""+ lineFeed);
					updateItemBuffer.append("			+ \"</if>\""+ lineFeed);
				}
				
			}
		}
		
		// 去除逗号
		baseFind = baseFind.substring(0, baseFind.length() - 1);
		baseFind += "	})" + lineFeed + "	List<" + fileStart + "> find(" + entity + " entity);" + lineFeed + "";
		
		//批处理方法添加
		//saveBatch
		String saveFields = saveFieldBuffer.toString();
		if(saveFields.length()>0) {
			saveFields=saveFields.substring(0, saveFields.length()-1);
		}
		StringBuffer saveBatchbuffer=new StringBuffer();
		saveBatchbuffer.append("	@Insert({"+ lineFeed);
		saveBatchbuffer.append("	\"<script>\"  "+ lineFeed);
		saveBatchbuffer.append("		  +  \" INSERT INTO `"+tableName+"` ("+saveFields+") VALUES  \" "+ lineFeed);
		saveBatchbuffer.append("			+\"<foreach collection='list' item='item' index='index' open='' separator=',' close=';' > \"  "+ lineFeed);
	          
		
		saveBatchbuffer.append("			+\"( \""+ lineFeed);
		//saveBatchbuffer.append("+\"( #{item.name}, #{item.content} ) \" ");
		String saveItemBufferStr = saveItemBuffer.toString();
		String substring = saveItemBufferStr.substring(0, saveItemBufferStr.length()-4);
		saveBatchbuffer.append(substring+"\""+ lineFeed);
		saveBatchbuffer.append("			+\") \" "+ lineFeed);
		
	    saveBatchbuffer.append("			+\"</foreach>\""+ lineFeed);
	    saveBatchbuffer.append("			+\";\" "+ lineFeed);
	    saveBatchbuffer.append("	+\"</script>\" "+ lineFeed);
		saveBatchbuffer.append("	})"+ lineFeed);
		saveBatchbuffer.append("	@Options(useGeneratedKeys = true,keyColumn = \""+primaryKeyColumnName+"\",keyProperty = \""+transformFieldName(primaryKeyColumnName)+"\")"+ lineFeed);
		saveBatchbuffer.append("	int saveBatch(List<" + fileStart + "> list);"+ lineFeed);
		
		//updateBatch
		StringBuffer updateBatchbuffer=new StringBuffer();		
		updateBatchbuffer.append("	@Update({"+ lineFeed);
		updateBatchbuffer.append("		\"<script>\""+ lineFeed);
		updateBatchbuffer.append("			+\"<foreach item='item' index='index'  collection='list' open='' separator=';' close=''  > \""+ lineFeed);
		updateBatchbuffer.append("			+ \" update `"+tableName+"` \""+ lineFeed);
		updateBatchbuffer.append("			+ \"<set>\""+ lineFeed);
	              
//	             + \"<if test='item.name != null and item.name.length>0 '>\"
//	             + \" `name` = #{item.name} "
//	             + \"</if>\"
		String updateItemStr = updateItemBuffer.toString();
		
		//System.out.println(updateItemStr.substring(0, updateItemStr.length()-18));
		updateBatchbuffer.append(updateItemStr.substring(0, updateItemStr.length()-18)+" \"  + \"</if>\" "+ lineFeed);
	    updateBatchbuffer.append("			+ \"</set>\""+ lineFeed);
	    updateBatchbuffer.append("			+ \" WHERE "+primaryKeyColumnName+" = #{item."+transformFieldName(primaryKeyColumnName)+"}   \""+ lineFeed);
	    updateBatchbuffer.append("			+\"</foreach>\""+ lineFeed);
	    updateBatchbuffer.append("		+\"</script>\" "+ lineFeed);
		updateBatchbuffer.append("	})"+ lineFeed);
		updateBatchbuffer.append("	@Lang(XMLLanguageDriver.class)"+ lineFeed);
		updateBatchbuffer.append("	int updateBatch(List<" + fileStart + "> list);"+ lineFeed);	
		
		//removeBatch
		StringBuffer removeBatchbuffer=new StringBuffer();
		removeBatchbuffer.append("	@Delete({"+ lineFeed);
		removeBatchbuffer.append("		\"<script>\" "+ lineFeed);
		removeBatchbuffer.append("		+ \"DELETE FROM message WHERE "+primaryKeyColumnName+" in \""+ lineFeed);
		removeBatchbuffer.append("			+ \"<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>\""+ lineFeed);
		removeBatchbuffer.append("			+       \"#{item}\""+ lineFeed);
		removeBatchbuffer.append("			+ \"</foreach>\" "+ lineFeed);
		removeBatchbuffer.append("		+ \" ;\""+ lineFeed);
		removeBatchbuffer.append("		+\"</script>\" "+ lineFeed);
		removeBatchbuffer.append("	})"+ lineFeed);
		removeBatchbuffer.append("	int removeBatch(@Param(value = \"ids\") List ids);"+ lineFeed);
		
		String endStr= lineFeed + "}";
		return repositoryStr + lineFeed 
				+ saveBatchbuffer.toString() + lineFeed 
				+ updateBatchbuffer.toString() + lineFeed
				+ removeBatchbuffer.toString() + lineFeed 
				+ endStr;
	}

	/**
	 * entity 名称转换 user_id --> UserId
	 * 
	 * @param name
	 * @return
	 */
	public static String transformTableName(String name) {
		StringBuffer buffer = new StringBuffer();
		if (name.contains("_")) {
			String[] split = name.toLowerCase().split("_");
			for (String str : split) {
				buffer.append(str.substring(0, 1).toUpperCase() + str.substring(1));
			}
		} else {
			buffer.append(name.substring(0, 1).toUpperCase() + name.substring(1));
		}
		return buffer.toString();
	}
	
	/**
	 *   字段转换 user_id --> userId
	 * @param name
	 * @return
	 */
	public static String transformFieldName(String name) {
		StringBuffer buffer = new StringBuffer();
		if (name.contains("_")) {
			String[] split = name.toLowerCase().split("_");
			for (int i=0;i<split.length;i++){ 
				String str = split[i];
				if(StringUtils.isNotEmpty(str)) {
					if(i>0) {
						buffer.append(str.substring(0, 1).toUpperCase() + str.substring(1));
					}else {
						buffer.append(str);
					}
				}
			}
		}else {
			buffer.append(name);
		}
		return buffer.toString();
	}

	/**
	 * 实体生成
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static String entityContent(String tableName, LinkedHashMap<String, String> map,LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String entityStr = "" + "package "+PACKAGE_PATH+".entity;" + lineFeed + lineFeed 
				+ "import java.io.Serializable;" + lineFeed
				+ lineFeed 
				+ lineFeed 
				+ "import lombok.Data;" + lineFeed 
				+ lineFeed
				+ "@Data" + lineFeed 
				+ "public class " + fileStart + " implements Serializable{" + lineFeed;
		for (Entry<String, String> entry : map.entrySet()) {
			String comment = commentmap.get(entry.getKey());
			entityStr +=lineFeed;
			if(StringUtils.isNotEmpty(comment)) {
				entityStr += "	private " + entry.getValue() + " " + transformFieldName(entry.getKey()) + " ; //"+comment+" " + lineFeed;
			}else {
				entityStr += "	private " + entry.getValue() + " " + transformFieldName(entry.getKey()) + " ; " + lineFeed;
			}
		}
		entityStr += "}";
		return entityStr;
	}

	/**
	 * provider 生成
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static String providerContent(String tableName,String primaryKeyColumnName, String cloumns, LinkedHashMap<String, String> map) {
		String fileStart = transformTableName(tableName);
		String entityStr = "" + "package "+PACKAGE_PATH+".repository.provider;" + lineFeed + lineFeed 
				+ "import org.apache.commons.lang3.StringUtils;"
				+ lineFeed 
				+ "import org.apache.ibatis.jdbc.SQL;" 
				+ lineFeed 
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" 
				+ lineFeed
				+ lineFeed
				+ "public class "+fileStart+"Provider {" + lineFeed
				+ lineFeed
				+ "	public static final String TABLE=\" `" + tableName + "` \";" + lineFeed
				+ lineFeed
				+ "	public static final String CLOUMNS=\" " + cloumns + "\";" + lineFeed;

		StringBuffer saveBuffer = new StringBuffer("	/**"+ lineFeed+"	* 添加方法"+ lineFeed+"	*/"+ lineFeed);
		StringBuffer updateBuffer = new StringBuffer("	/**"+ lineFeed+"	* 修改方法"+ lineFeed+"	*/"+ lineFeed);
		StringBuffer removeBuffer = new StringBuffer("	/**"+ lineFeed+"	* 删除方法"+ lineFeed+"	*/"+ lineFeed);
		StringBuffer whereBuffer = new StringBuffer();
		StringBuffer findBuffer = new StringBuffer("	/**"+ lineFeed+"	* 查询方法"+ lineFeed+"	*/"+ lineFeed);
		saveBuffer.append("	public String save(" + fileStart + " entity) {" + lineFeed );
		saveBuffer.append("		SQL sql=new SQL();"+ lineFeed);
		saveBuffer.append("		sql.INSERT_INTO(TABLE);"+ lineFeed);

		updateBuffer.append("	public String update(" + fileStart + " entity) {" + lineFeed);
		updateBuffer.append("		SQL sql=new SQL();" + lineFeed);
		updateBuffer.append("		sql.UPDATE(TABLE);" + lineFeed);

		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			key = transformFieldName(entry.getKey());
			System.out.println(key);
			if (key.length() > 1) {
				key = key.substring(0, 1).toUpperCase() + key.substring(1);
			} else {
				key = key.substring(0, 1).toUpperCase();
			}
			String cond = "";
			if (TypeEnum.JavaType.getJavaType("").equals(entry.getValue())) {
				cond += "		if(StringUtils.isNotEmpty(entity.get" + key + "())) {" + lineFeed;
			} else {
				cond += "		if(entity.get" + key + "() != null ) {" + lineFeed;
			}
			saveBuffer.append(cond);
			saveBuffer.append("			sql.VALUES(\"" + entry.getKey() + "\", \"#{" + transformFieldName(entry.getKey()) + "}\");" + lineFeed);
			saveBuffer.append("		}" + lineFeed);

			updateBuffer.append(cond);
			updateBuffer.append("			sql.SET(\"" + entry.getKey() + " =#{" + transformFieldName(entry.getKey()) + "}\");" + lineFeed);
			updateBuffer.append("		}" + lineFeed);

			whereBuffer.append(cond);
			whereBuffer.append("			sql.WHERE(\" " + entry.getKey() + " = #{" + transformFieldName(entry.getKey()) + "}\");" + lineFeed);
			whereBuffer.append("		}" + lineFeed);
		}
		saveBuffer.append("		return sql.toString();" + lineFeed);
		saveBuffer.append("	}" + lineFeed);

		//updateBuffer.append(whereBuffer.toString());
		String prikey = transformFieldName(primaryKeyColumnName);
		System.out.println(prikey);
		if (prikey.length() > 1) {
			prikey = prikey.substring(0, 1).toUpperCase() + prikey.substring(1);
		} else {
			prikey = prikey.substring(0, 1).toUpperCase();
		}
		updateBuffer.append("		if(entity.get" + prikey + "() != null ) {" + lineFeed);
		updateBuffer.append("			sql.WHERE(\" " + primaryKeyColumnName + " = #{" + transformFieldName(primaryKeyColumnName) + "}\");" + lineFeed);
		updateBuffer.append("		}" + lineFeed);

		updateBuffer.append("		return sql.toString();" + lineFeed);
		updateBuffer.append("	}" + lineFeed);

		removeBuffer.append("	public String remove(" + fileStart + " entity) {" + lineFeed);
		removeBuffer.append( "		SQL sql=new SQL();"+ lineFeed );
		removeBuffer.append("		sql.DELETE_FROM(TABLE);" + lineFeed);
		
		removeBuffer.append(whereBuffer);
		removeBuffer.append("		return sql.toString();" + lineFeed);
		removeBuffer.append("	}" + lineFeed);

		findBuffer.append("	public String find(" + fileStart + " entity) {" + lineFeed );
		findBuffer.append("		SQL sql=new SQL();" + lineFeed);
		findBuffer.append("		sql.SELECT(CLOUMNS);" + lineFeed );
		findBuffer.append("		sql.FROM(TABLE);" + lineFeed);
		findBuffer.append(whereBuffer);
		findBuffer.append("		return sql.toString();" + lineFeed);
		findBuffer.append("	}" + lineFeed);

		String endStr = "}";
		return entityStr + lineFeed+ saveBuffer.toString() 
						+ lineFeed+ updateBuffer.toString() 
						+ lineFeed+ removeBuffer.toString()
						+ lineFeed+ findBuffer.toString() 
						+ lineFeed+ endStr;
	}

	public static void main(String[] args) {

		// reponsitory test
//		Java
//		String table="user_id";
//		Map<String, Map<String,String>> map=new HashMap<String, Map<String,String>>();
//		HashMap<String, String> idmap = new HashMap<String, String>();
//		idmap.put("id", "BIGINT");
//		map.put("id",idmap);
//		HashMap<String, String> namemap = new HashMap<String, String>();
//		namemap.put("name", "VARCHAR");
//		map.put("name", namemap);
//		HashMap<String, String> valuemap = new HashMap<String, String>();
//		valuemap.put("value", "BIGINT");
//		map.put("value", valuemap);
//		FileUtil.createJavaFile(transformTableName(table)+"Repository", repositoryContent(table, map));
//		System.out.println(transformTableName("use_id_name_sss"));

		// entity test
//		Map<String,String> map=new HashMap<String, String>();
//		map.put("id", "Long");
//		map.put("age", "Integer");
//		map.put("name", "String");
//		FileUtil.createJavaFile("user", entityContent("user",map));
		// System.out.println(transformTableName("user"));

		// provider test
//		LinkedHashMap<String,String> map=new LinkedHashMap<String, String>();
//		map.put("id", "Long");
//		map.put("age", "Integer");
//		map.put("name", "String");
//		FileUtil.createJavaFile("UserProvider", providerContent("user","id,name,age",map));
		String name="user___";
		
		String transformFieldName = transformFieldName(name);
		System.out.println(transformFieldName);
	}
}
