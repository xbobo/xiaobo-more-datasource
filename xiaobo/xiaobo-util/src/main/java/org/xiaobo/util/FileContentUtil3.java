package org.xiaobo.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author xiaobo
 * @date 2019年4月25日
 */
public class FileContentUtil3 {
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
				+ "import "+PACKAGE_PATH+".vo."+fileStart+"VO;" + lineFeed 
				+ "import "+PACKAGE_PATH+".dto."+fileStart+"DTO;" + lineFeed 
				+ "import "+PACKAGE_PATH+".repository.provider."+fileStart+"Provider;" + lineFeed 
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
		repositoryStr += "	})" + lineFeed + "	List<" + fileStart + "VO> find(" + entity + "DTO dto);" + lineFeed + ""
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
				+ "import "+PACKAGE_PATH+".dto."+fileStart+"DTO;" + lineFeed 
				+ "public interface " + fileStart
				+ "RepositoryComplex {" + lineFeed 
				+ "" + lineFeed ;
		String baseSave= "	@InsertProvider(type = " + fileStart
				+ "Provider.class,method = \"save\")" + lineFeed
				+ "	@Options(useGeneratedKeys = true,keyColumn = \""+primaryKeyColumnName+"\",keyProperty = \""+transformFieldName(primaryKeyColumnName)+"\")" + lineFeed
				+ "	int save(" + entity + "DTO  entity);" + lineFeed + "" 
				+ lineFeed ;
		String baseUpdate= "	@UpdateProvider(type = " + fileStart+ "Provider.class,method = \"update\")" + lineFeed 
				+ "	int update(" + entity + "DTO entity);" + lineFeed + ""
				+ lineFeed ;
		String baseRemove= "	@DeleteProvider(type = " + fileStart + "Provider.class,method = \"remove\")" + lineFeed
				+ "	int remove(" + entity + "DTO entity);" + lineFeed + "" + lineFeed ;
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
		baseFind += "	})" + lineFeed + "	List<" + fileStart + "VO> find(" + entity + "DTO entity);" + lineFeed + "";
		
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
		saveBatchbuffer.append("	int saveBatch(List<" + fileStart + "DTO> list);"+ lineFeed);
		
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
		updateBatchbuffer.append("	int updateBatch(List<" + fileStart + "DTO> list);"+ lineFeed);	
		
		//removeBatch
		StringBuffer removeBatchbuffer=new StringBuffer();
		removeBatchbuffer.append("	@Delete({"+ lineFeed);
		removeBatchbuffer.append("		\"<script>\" "+ lineFeed);
		removeBatchbuffer.append("		+ \"DELETE FROM `"+tableName+"` WHERE "+primaryKeyColumnName+" in \""+ lineFeed);
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
	
	public static String transformNameFirstLower(String name) {
		if(name.length()>1){
			return ""+name.substring(0, 1).toLowerCase()+name.substring(1);
		}
		return "" ;
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
				+ "import "+PACKAGE_PATH+".dto.BaseDTO;" + lineFeed
				+ lineFeed 
				+ "import com.fasterxml.jackson.annotation.JsonAlias;" + lineFeed 
				+ "import com.fasterxml.jackson.annotation.JsonProperty;" + lineFeed 
				+ lineFeed 
				+ "import lombok.Data;" + lineFeed 
				+ lineFeed
				+ "@Data" + lineFeed 
				+ "public class " + fileStart + " extends BaseDTO implements Serializable{" + lineFeed;
		for (Entry<String, String> entry : map.entrySet()) {
			String comment = commentmap.get(entry.getKey());
			entityStr +=lineFeed;
			if(StringUtils.isNotEmpty(comment)) {
				//entityStr += "	@JsonAlias(\""+entry.getKey()+"\")" + lineFeed;
				entityStr += "	@JsonProperty(\""+entry.getKey()+"\")" + lineFeed;
				entityStr += "	private " + entry.getValue() + " " + transformFieldName(entry.getKey()) + " ; //"+comment+" " + lineFeed;
			}else {
				//entityStr += "	@JsonAlias(\""+entry.getKey()+"\")" + lineFeed;
				entityStr += "	@JsonProperty(\""+entry.getKey()+"\")" + lineFeed;
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

		updateBuffer.append("		if(entity.getIds()!=null&&entity.getIds().size()>0) {" + lineFeed);
		updateBuffer.append("			String ids=\"\";" + lineFeed);
		updateBuffer.append("			for(Long id:entity.getIds()) {" + lineFeed);
		updateBuffer.append("				if(id!=null) {" + lineFeed);
		updateBuffer.append("					ids+=id+\",\";" + lineFeed);
		updateBuffer.append("				}" + lineFeed);
		updateBuffer.append("			}" + lineFeed);
		updateBuffer.append("			if(ids.length()>0) {" + lineFeed);
		updateBuffer.append("				ids=ids.substring(0, ids.length()-1);" + lineFeed);
		updateBuffer.append("				sql.WHERE(\" " + primaryKeyColumnName + " in ( \"+ids+\" )  \");" + lineFeed);
		updateBuffer.append("			}" + lineFeed);
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
		
		findBuffer.append("		if(entity.getIds() != null &&entity.getIds().size()>0) {"+ lineFeed);
		findBuffer.append("			String idsStr=\"\";"+ lineFeed);
		findBuffer.append("			for(Long id:entity.getIds()) {"+ lineFeed);
		findBuffer.append("				idsStr+=id+\",\";"+ lineFeed);
		findBuffer.append("			}"+ lineFeed);
		findBuffer.append("			if(idsStr.length()>0) {"+ lineFeed);
		findBuffer.append("				idsStr=idsStr.substring(0,idsStr.length()-1);"+ lineFeed);
		findBuffer.append("			}"+ lineFeed);
		findBuffer.append("			sql.WHERE(\" "+primaryKeyColumnName+" in ( \"+idsStr+\" )\");"+ lineFeed);
		findBuffer.append("		}"+ lineFeed);
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

	public static String voContent(String tableName, LinkedHashMap<String, String> map,
			LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String entityStr = "" + "package "+PACKAGE_PATH+".vo;" + lineFeed + lineFeed 
				+ lineFeed 
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" + lineFeed 
				+ lineFeed 
				+ "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;"+ lineFeed 
				+ lineFeed 
				+ "import lombok.Data;" + lineFeed 
				+ lineFeed
				+ "@Data" + lineFeed 
				+ "@JsonIgnoreProperties({\"create_uid\",\"modify_uid\",\"create_time\",\"modify_time\"})" + lineFeed 
				+ "public class " + fileStart + "VO extends " + fileStart + " {" + lineFeed;
		 
		entityStr += "}";
		return entityStr;
	}

	public static String dtoContent(String tableName, LinkedHashMap<String, String> map,
			LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String entityStr = "" + "package "+PACKAGE_PATH+".dto;" + lineFeed + lineFeed 
				+ lineFeed 
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" + lineFeed 
				+ lineFeed 
				+ "import lombok.Data;" + lineFeed 
				+ lineFeed
				+ "@Data" + lineFeed 
				+ "public class " + fileStart + "DTO extends " + fileStart + " {" + lineFeed;
			entityStr += "}";
		return entityStr;
	}

	public static String basedtoContent(String tableName, LinkedHashMap<String, String> map,
			LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String entityStr = "" + "package "+PACKAGE_PATH+".dto;" + lineFeed + lineFeed 
				+ lineFeed 
				+ lineFeed 
				+ "import java.util.List;" + lineFeed
				+ lineFeed
				+ "import com.fasterxml.jackson.annotation.JsonAlias;" + lineFeed
				+ "import com.fasterxml.jackson.annotation.JsonProperty;" + lineFeed
				+ "import lombok.Data;" + lineFeed 
				+ lineFeed
				+ "@Data" + lineFeed 
				+ "public class  BaseDTO  {" + lineFeed;
		entityStr +=lineFeed;
		entityStr +="	private Long id;"+ lineFeed;
		entityStr +=lineFeed;
		entityStr +="	private List<Long> ids;"+ lineFeed;
		entityStr +=lineFeed;
		//entityStr +="	@JsonAlias(\"page_size\")"+ lineFeed;
		entityStr +="	@JsonProperty(\"page_size\")"+ lineFeed;
	    entityStr +="	private Integer pageSize;"+ lineFeed;
	    entityStr +=lineFeed;
		//entityStr +="	@JsonAlias(\"page_num\")"+ lineFeed;
		entityStr +="	@JsonProperty(\"page_num\")"+ lineFeed;
		entityStr +="	private Integer pageNum;"+ lineFeed;
		entityStr +=lineFeed;
		entityStr += "}";
		return entityStr;
	}

	public static String serviceContent(String tableName, LinkedHashMap<String, String> map,
			LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String entityStr = "" + "package "+PACKAGE_PATH+".service;" + lineFeed + lineFeed 
				+ lineFeed 
				+ lineFeed 
				+ "import java.util.List;" + lineFeed
				+ lineFeed
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" + lineFeed
				+ "import "+PACKAGE_PATH+".vo."+fileStart+"VO;" + lineFeed
				+ "import "+PACKAGE_PATH+".dto."+fileStart+"DTO;" + lineFeed
				+ lineFeed
				+ "import com.github.pagehelper.PageInfo;" + lineFeed
				+ lineFeed
				+ "public interface " + fileStart + "Service  {" + lineFeed;
		
		entityStr +=lineFeed;
		entityStr +="	int save("+fileStart+"DTO  dto);"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	int update("+fileStart+"DTO  dto);"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	int remove("+fileStart+"DTO  dto);"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	List<"+fileStart+"VO> find("+fileStart+"DTO  dto);"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	PageInfo<"+fileStart+"VO> findPage("+fileStart+"DTO  dto);"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	int updateBatch(List<"+fileStart+"DTO> list);"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	int saveBatch(List<"+fileStart+"DTO> list);"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	int removeBatch("+fileStart+"DTO  dto);"+ lineFeed;
		entityStr +=lineFeed;
 
		entityStr += "}";
		return entityStr;
	}

	public static String serviceImplContent(String tableName, LinkedHashMap<String, String> map,
			LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String firstLowerName = transformNameFirstLower(fileStart);
		String voName=fileStart+"VO";
		String dtoName=fileStart+"DTO";
		String entityStr = "" + "package "+PACKAGE_PATH+".service.impl;" + lineFeed + lineFeed 
				+ lineFeed 
				+ lineFeed 
				+ "import java.util.List;" + lineFeed
				+ lineFeed
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" + lineFeed
				+ "import "+PACKAGE_PATH+".vo."+fileStart+"VO;" + lineFeed
				+ "import "+PACKAGE_PATH+".dto."+fileStart+"DTO;" + lineFeed
				+ "import "+PACKAGE_PATH+".repository."+fileStart+"Repository;" + lineFeed
				+ "import "+PACKAGE_PATH+".repository."+fileStart+"RepositoryComplex;" + lineFeed
				+ "import "+PACKAGE_PATH+".service."+fileStart+"Service;" + lineFeed
				
				+ "import org.springframework.beans.factory.annotation.Autowired;" + lineFeed
				+ "import org.springframework.stereotype.Service;" + lineFeed
				+ lineFeed
				+ "import com.github.pagehelper.PageHelper;" + lineFeed
				+ "import com.github.pagehelper.PageInfo;" + lineFeed
				+ lineFeed
				+ "@Service" + lineFeed
				+ "public class " + fileStart + "ServiceImpl implements " + fileStart + "Service  {" + lineFeed;
		
		entityStr +=lineFeed;
		entityStr +="	@Autowired"+ lineFeed;
		entityStr +="	" + fileStart + "Repository "+firstLowerName+"Repository;"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@Autowired"+ lineFeed;
		entityStr +="	" + fileStart + "RepositoryComplex "+firstLowerName+"RepositoryComplex;"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public int save(" + dtoName + " dto) {"+ lineFeed;
		entityStr +="		return "+firstLowerName+"Repository.save(dto);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public int update(" + dtoName + " dto) {"+ lineFeed;
		entityStr +="		return "+firstLowerName+"Repository.update(dto);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;		 

		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public int remove(" + dtoName + " dto) {"+ lineFeed;
		entityStr +="		return "+firstLowerName+"Repository.remove(dto);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;	
		
		
		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public List<"+voName+"> find("+dtoName+" dto) {"+ lineFeed;
		entityStr +="		return "+firstLowerName+"Repository.find(dto);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;	
		
		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public PageInfo<" + voName + "> findPage(" + dtoName + " dto) {"+ lineFeed;
		entityStr +="		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());"+ lineFeed;
		entityStr +="		List<" + voName + "> find = "+firstLowerName+"Repository.find(dto);"+ lineFeed;
		entityStr +="		PageInfo<" + voName + "> page=new PageInfo<" + voName + ">(find);"+ lineFeed;
		entityStr +="		return page;"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public int removeBatch(" + dtoName + " dto) {"+ lineFeed;
		entityStr +="		return "+firstLowerName+"RepositoryComplex.removeBatch(dto.getIds());"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;	

		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public int updateBatch(List<" + dtoName + "> list) {"+ lineFeed;
		entityStr +="		return "+firstLowerName+"RepositoryComplex.updateBatch(list);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@Override"+ lineFeed;
		entityStr +="	public int saveBatch(List<" + dtoName + "> list) {"+ lineFeed;
		entityStr +="		return "+firstLowerName+"RepositoryComplex.saveBatch(list);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr += "}";
		return entityStr;
	}

	public static String controllerContent(String tableName, LinkedHashMap<String, String> map,
			LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String firstLowerName = transformNameFirstLower(fileStart);
		String voName=fileStart+"VO";
		String dtoName=fileStart+"DTO";
		String entityStr = "" + "package "+PACKAGE_PATH+".controller;" + lineFeed + lineFeed 
				+ lineFeed 
				+ lineFeed 
				+ "import java.util.List;" + lineFeed
				+ "import java.util.ArrayList;" + lineFeed
				+ lineFeed
				+ "import "+PACKAGE_PATH+".vo.Result;" + lineFeed
				+ "import "+PACKAGE_PATH+".entity."+fileStart+";" + lineFeed
				+ "import "+PACKAGE_PATH+".vo."+voName+";" + lineFeed
				+ "import "+PACKAGE_PATH+".dto."+dtoName+";" + lineFeed
				+ "import "+PACKAGE_PATH+".service."+fileStart+"Service;" + lineFeed
				+ lineFeed
				+ "import org.slf4j.Logger;" + lineFeed
				+ "import org.slf4j.LoggerFactory;" + lineFeed
				+ "import org.springframework.beans.factory.annotation.Autowired;" + lineFeed
				+ "import org.springframework.web.bind.annotation.PostMapping;" + lineFeed
				+ "import org.springframework.web.bind.annotation.RequestBody;" + lineFeed
				+ "import org.springframework.web.bind.annotation.RequestMapping;" + lineFeed
				+ "import org.springframework.web.bind.annotation.ResponseBody;" + lineFeed
				+ "import org.springframework.web.bind.annotation.RestController;" + lineFeed
				+ lineFeed
				+ "import com.github.pagehelper.PageInfo;" + lineFeed
				+ lineFeed
				+ "@RestController" + lineFeed
				+ "@RequestMapping(\"/api/"+tableName+"\")" + lineFeed
				+ "public class " + fileStart + "Controller   {" + lineFeed;
		entityStr +="	private  static Logger logger = LoggerFactory.getLogger(" + fileStart + "Controller.class);"+ lineFeed;
		entityStr +=lineFeed;
		entityStr +="	@Autowired"+ lineFeed;
		entityStr +="	" + fileStart + "Service "+firstLowerName+"Service;"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@PostMapping(\"/save\")"+ lineFeed;
		entityStr +="	@ResponseBody"+ lineFeed;
		entityStr +="	public Result save(@RequestBody "+dtoName+" dto) {"+ lineFeed;
		entityStr +="		int save = "+firstLowerName+"Service.save(dto);"+ lineFeed;
		entityStr +="		return Result.get(Result.OK, \"\", save>0);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@PostMapping(\"/update\")"+ lineFeed;
		entityStr +="	@ResponseBody"+ lineFeed;
		entityStr +="	public Result update(@RequestBody "+dtoName+" dto) {"+ lineFeed;
		entityStr +="		if(dto.getId()==null) {"+ lineFeed;
		entityStr +="			return Result.get(Result.ERROR, \"id不能为空\", null);"+ lineFeed;
		entityStr +="		}"+ lineFeed;
		entityStr +="		int update = "+firstLowerName+"Service.update(dto);"+ lineFeed;
		entityStr +="		return Result.get(Result.OK, \"\", update>0);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@PostMapping(\"/update_batch\")"+ lineFeed;
		entityStr +="	@ResponseBody"+ lineFeed;
		entityStr +="	public Result update_batch(@RequestBody "+dtoName+" dto) {"+ lineFeed;
		entityStr +="		if(dto.getIds()==null) {"+ lineFeed;
		entityStr +="			return Result.get(Result.ERROR, \"ids不能为空\", null);"+ lineFeed;
		entityStr +="		}"+ lineFeed;
		
		entityStr +="		List<"+dtoName+"> list=new ArrayList<"+dtoName+">();"+ lineFeed;
		entityStr +="		for(Long id:dto.getIds()) {"+ lineFeed;
		entityStr +="			"+dtoName+" updateDTO=new "+dtoName+"();"+ lineFeed;
		entityStr +="			updateDTO.setId(id);"+ lineFeed;
		entityStr +="			list.add(updateDTO);"+ lineFeed;
		entityStr +="		}"+ lineFeed;
		entityStr +="		int update = "+firstLowerName+"Service.updateBatch(list);"+ lineFeed;
		entityStr +="		return Result.get(Result.OK, \"\", update>0);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@PostMapping(\"/remove\")"+ lineFeed;
		entityStr +="	@ResponseBody"+ lineFeed;
		entityStr +="	public Result remove(@RequestBody "+dtoName+" dto) {"+ lineFeed;
		entityStr +="		if(dto.getIds()!=null&&dto.getIds().size()>0){"+ lineFeed;
		entityStr +="			int remove = "+firstLowerName+"Service.removeBatch(dto);"+ lineFeed;
		entityStr +="			return Result.get(Result.OK, \"\", remove>0);"+ lineFeed;
		entityStr +="		}else  if(dto.getId()!=null){"+ lineFeed;
		entityStr +="			int remove = "+firstLowerName+"Service.remove(dto);"+ lineFeed;
		entityStr +="			return Result.get(Result.OK, \"\", remove>0);"+ lineFeed;
		entityStr +="		}"+ lineFeed;
		entityStr +="		return Result.get(Result.OK, \"\", null);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@PostMapping(\"/find_page\")"+ lineFeed;
		entityStr +="	@ResponseBody"+ lineFeed;
		entityStr +="	public Result findPage(@RequestBody "+dtoName+" dto) {"+ lineFeed;
		entityStr +="		PageInfo<"+voName+"> page = "+firstLowerName+"Service.findPage(dto);"+ lineFeed;
		entityStr +="		return Result.get(Result.OK, \"\", page);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +="	@PostMapping(\"/find\")"+ lineFeed;
		entityStr +="	@ResponseBody"+ lineFeed;
		entityStr +="	public Result find(@RequestBody "+dtoName+" dto) {"+ lineFeed;
		entityStr +="		List<"+voName+"> list = "+firstLowerName+"Service.find(dto);"+ lineFeed;
		entityStr +="		return Result.get(Result.OK, \"\", list);"+ lineFeed;
		entityStr +="	}"+ lineFeed;
		entityStr +=lineFeed;
		
		entityStr +=lineFeed;
		entityStr += "}";
		return entityStr;
	}

	public static String resultContent(String tableName, LinkedHashMap<String, String> map,
			LinkedHashMap<String, String> commentmap) {
		String fileStart = transformTableName(tableName);
		String entityStr = "" + "package "+PACKAGE_PATH+".vo;" + lineFeed + lineFeed 
				+ lineFeed 
				+ "import lombok.Data;" + lineFeed 
				+ lineFeed
				+ "@Data" + lineFeed 
				+ "public class Result {" + lineFeed;
		entityStr += "	public static final Integer OK = 1;"+ lineFeed;
		entityStr += "	public static final Integer ERROR = 0;"+ lineFeed;
		entityStr += "	public static final Integer NOAUTH = -1;"+ lineFeed;
		entityStr += "	public static final Integer INVALID = -3;"+ lineFeed;
		entityStr += "	public static final String ERROR_MESSAGE = \"服务器错误\";"+ lineFeed;
		entityStr += lineFeed;
		entityStr += "	private Integer status;"+ lineFeed;
		entityStr += "	private String message;"+ lineFeed;
		entityStr += "	private Object data;"+ lineFeed;
		
		entityStr += "	public Result(int status, String message, Object data) {"+ lineFeed;
		entityStr += "		this.status = status;"+ lineFeed;
		entityStr += "		this.message = message;"+ lineFeed;
		entityStr += "		this.data = data;"+ lineFeed;
		entityStr += "	};"+ lineFeed;
		
		entityStr += "	public Result() {}"+ lineFeed;
		
		entityStr += "	public static Result get(int status, String message, Object data) {"+ lineFeed;
		entityStr += "		return new Result(status, message, data);"+ lineFeed;
		entityStr += "	}"+ lineFeed;
		
		entityStr += "	public static Result createServerError() {"+ lineFeed;
		entityStr += "		Result result = new Result(ERROR, ERROR_MESSAGE, null);"+ lineFeed;
		entityStr += "		return result;"+ lineFeed;
		entityStr += "	}"+ lineFeed;
		
		entityStr += "	@Override"+ lineFeed;
		entityStr += "	public String toString() {"+ lineFeed;
		entityStr += "	return \"{ status:\" + status + \", message:\" + message + \", data:\" + data + \"}\";"+ lineFeed;
		entityStr += "	}"+ lineFeed;
		
		entityStr += "}";
		return entityStr;
	}
 
}
