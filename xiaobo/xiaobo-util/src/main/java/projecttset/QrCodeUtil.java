/*package com.puxinwangxiao.erp.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QrCodeUtil {
    private static Logger logger = LogManager.getLogger(QrCodeUtil.class.getName());

    *//**
        * 生成二维码
        * 
        * @param appid
        * @param appsecret
        * @param page
        * @return
        */
/*
public static String createQrcode(String appid, String appsecret, String page, String scene) {
 String result = "";
 try {
     String tokenurl =
         "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
     tokenurl = tokenurl.replace("APPID", appid).replace("SECRET", appsecret);
     logger.error("生成二维码的appid:" + appid);
     String dd = HttpUtil.sendGet(tokenurl);
     System.out.println(dd);
     String access_token = (String)JSONObject.fromObject(dd).get("access_token");
     String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN"
         .replace("ACCESS_TOKEN", access_token);
     Map<String, Object> map = new HashMap<String, Object>();
     map.put("scene", scene);
     map.put("page", page);
     map.put("width", 430);
     map.put("auto_color", false);
     result = httpPostWithJSON(url, JSONObject.fromObject(map).toString(), "QRcode");
 } catch (Exception e) {
     logger.error("创建二维码error" + e);
 }
 return result;
}

public static String httpPostWithJSON(String url, String json, String id) throws Exception {
 String result = null;
 try {
     CloseableHttpClient httpClient = HttpClients.createDefault();

     HttpPost httpPost = new HttpPost(url);
     RequestConfig requestConfig = null;
     *//**
         * 启用代理
         */
/*
String proxyIp = PropUtil.getPropertyValue("ods.http.proxy.server", "10.74.46.21:8002").replace("\"", "");
String[] proxIpArray = proxyIp.split(":");
String proxyHost = proxIpArray[0];
int proxyPort = Integer.valueOf(proxIpArray[1]);
HttpHost proxy = new HttpHost(proxyHost, proxyPort);
requestConfig = RequestConfig.custom().setProxy(proxy).build();
httpPost.setConfig(requestConfig);
httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

StringEntity se = new StringEntity(json);
se.setContentType("application/json");
se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
httpPost.setEntity(se);
// httpClient.execute(httpPost);
HttpResponse response = httpClient.execute(httpPost);
logger.error("生成非车代付二维码的响应response:" + response);
if (response != null) {
 HttpEntity resEntity = response.getEntity();
 if (resEntity != null) {
     InputStream instreams = resEntity.getContent();
     File uploadSysUrl = new File(".\\" + "tempFile");
     File saveFile = new File(uploadSysUrl.getCanonicalPath() + id + ".png"); // 判断这个文件（saveFile）是否存在
     if (!saveFile.getParentFile().exists()) { // 如果不存在就创建这个文件夹
         saveFile.getParentFile().mkdirs();
     }
     saveToImgByInputStream(instreams, uploadSysUrl.getCanonicalPath(), id + ".png");
     // 建立数据的上传通道
     FileInputStream fileInputStream = new FileInputStream(saveFile);
     String returnStr = ComFileManager.FileUpload(fileInputStream, "scanimeg.png", "wii", "car_quote");
     JSONObject objectscanurl = JSONObject.fromObject(returnStr);
     result = (String)objectscanurl.get("id");
     deleteUploadFile(saveFile.toString());
 }
}
httpPost.abort();
} catch (Exception e) {
logger.error("httpPost" + e);
}
return result;
}


* @param instreams 二进制流
* 
* @param imgPath 图片的保存路径
* 
* @param imgName 图片的名称
* 
* @return 1：保存正常 0：保存失败

public static int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName) {

int stateInt = 1;
if (instreams != null) {
try {
 File file = new File(imgPath + imgName);// 可以是任何图片格式.jpg,.png等
 FileOutputStream fos = new FileOutputStream(file);

 byte[] b = new byte[1024];
 int nRead = 0;
 while ((nRead = instreams.read(b)) != -1) {
     fos.write(b, 0, nRead);
 }
 fos.flush();
 fos.close();
} catch (Exception e) {
 stateInt = 0;
 e.printStackTrace();
} finally {
 try {
     instreams.close();
 } catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
 }
}
}
return stateInt;
}

*//**
    * 删除临时二维码
    * 
    * @Description (TODO这里用一句话描述这个方法的作用)
    * @param address
    * @return
    *//*
      private static String deleteUploadFile(String address) {
       File file = new File(address);
       if (file.isFile() && file.exists()) {
           file.delete();
           return "success";
       } else {
           return "false";
       }
      }
      }*/