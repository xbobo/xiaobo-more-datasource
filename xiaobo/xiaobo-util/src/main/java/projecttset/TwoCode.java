package projecttset;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class TwoCode {

    /*
     * 获取 token
    　　　* 普通的 get 可获 token
     */
    public static String getToken() {
        try {

            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("grant_type", "client_credential");
            map.put("appid", "Contants.appId");// 改成自己的appid
            map.put("secret", "Contants.secret");

            String rt = UrlUtil.sendPost("https://api.weixin.qq.com/cgi-bin/token", map);

            System.out.println("what is:" + rt);
            JSONObject json = JSONObject.parseObject(rt);

            if (json.getString("access_token") != null || json.getString("access_token") != "") {
                return json.getString("access_token");
            } else {
                return null;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

    /*
     * 获取 二维码图片
    　　 * 
     */
    public static String getminiqrQr(String accessToken, HttpServletRequest request) {
        String p = request.getSession().getServletContext().getRealPath("/");
        String codeUrl = p + "/twoCode.png";
        String twoCodeUrl = "twoCode.png";
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", "1234567890");
            paramJson.put("path", "pages/index?query=1");
            paramJson.put("width", 430);
            paramJson.put("is_hyaline", true);
            paramJson.put("auto_color", true);
            /**
             * line_color生效 paramJson.put("auto_color", false); JSONObject lineColor = new JSONObject();
             * lineColor.put("r", 0); lineColor.put("g", 0); lineColor.put("b", 0); paramJson.put("line_color",
             * lineColor);
             */

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            // 开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new FileOutputStream(new File(codeUrl));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twoCodeUrl;
    }
}