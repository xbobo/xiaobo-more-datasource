package image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class FileUtil {
    /**
     * 上传文件
     *
     * @param bytes
     * @param path
     * @throws IOException
     */
    public static void upload(byte[] bytes, String path) throws IOException {
        File saveFile = new File(path);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));

        out.write(bytes);
        out.flush();
        out.close();
    }

    public static void qiniu(String filename,String fullPath) throws QiniuException {
        String ACCESS_KEY = "gaYfBm5tCOAYn8rBeG56F8XCYrhzRB2SZE8KsVL7";
        String SECRET_KEY = "XQo8a6pBox_azJ_IEjrHM-BwJpaw1Uc6ezmVcQX9";
        //要上传的空间
        String bucketname = "pxwx";

        //Zone z = Zone.autoZone();
        Zone z = new Zone.Builder().upHttp("http://up-z1.qiniup.com").upHttps("https://up-z1.qiniup.com").build();
        Configuration c = new Configuration(z);
        UploadManager uploadManager = new UploadManager(c);
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(bucketname);
        Response res = uploadManager.put(fullPath, filename, token);
    }
}
