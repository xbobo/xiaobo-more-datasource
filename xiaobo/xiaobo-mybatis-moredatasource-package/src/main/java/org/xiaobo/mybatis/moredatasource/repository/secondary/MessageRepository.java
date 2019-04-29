package org.xiaobo.mybatis.moredatasource.repository.secondary;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.xiaobo.mybatis.moredatasource.entity.Message;

public interface MessageRepository {

	@InsertProvider(type = MessageProvider.class,method = "save")
	@Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
	int save(Message message);
	
	@Insert({
		"<script>"
            +"INSERT INTO message (name,content) VALUES  "
            	+"<foreach collection='list' item='item' index='index' open='' separator=',' close=';' > "
            		+"( #{item.name}, #{item.content} ) " 
               +"</foreach>"
            +";"
	    +"</script>" 
	})
	@Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
	int saveBatch(List<Message> list);

	@UpdateProvider(type = MessageProvider.class,method = "update")
	int update(Message message);
	
	@Update({
	    "<script>"
	      +"<foreach item='item' index='index'  collection='list' open='' separator=';' close=''  > "
              + " update `message` "
              + "<set>"
              + "<if test='item.content != null and item.content.length>0 '>"
              + " `content` = #{item.content} ,"
             + "</if>"
             + "<if test='item.name != null and item.name.length>0 '>"
             + " `name` = #{item.name} "
             + "</if>"
              + "</set>"
              + " WHERE id = #{item.id}   "
           +"</foreach>"
	    +"</script>" 
	    })
	@Lang(XMLLanguageDriver.class)
	int updateBatch(@Param("list")List<Message> list);
	
	@Update({
	    "<script>"
	        + "UPDATE message SET status = #{status} WHERE id in "
	        + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
	        +       "#{item}"
	        + "</foreach>" 
	        + " ;"
	    +"</script>" 
	    })
	int updateStatusByIds(@Param("status") Integer status,@Param("ids") Long[] ids);
	// int updateOrderStatus(@Param("orderStatus") Short orderStatus,@Param("orderId") String[] orderList);
	
	@DeleteProvider(type = MessageProvider.class,method = "remove")
	int remove(Message message);
	
	@Delete({
		"<script>"
		        + "DELETE FROM message WHERE id in "
		        + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
		        +       "#{item}"
		        + "</foreach>" 
		        + " ;"
		    +"</script>" 
	})
	int removeBatchByXML(@Param(value = "ids") List ids);
	
	@DeleteProvider(type = MessageProvider.class,method = "removeBatch")
	int removeBatch(@Param(value = "ids") List ids);
	
	@SelectProvider(type = MessageProvider.class,method = "find")
	@Results(id = "messageFindResultList",value = {
			@Result(column ="id" ,property = "id"),
			@Result(column ="name" ,property = "name"),
			@Result(column ="content" ,property = "content")
	})
	List<Message> findAll(Message message);
 

}
