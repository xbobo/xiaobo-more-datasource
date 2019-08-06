package province;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xiaobo.util.FileUtil;

/**
 * 全国省市县镇村数据爬取
 *
 * @author liushaofeng
 * @date 2015-10-11 上午12:19:39
 * @version 1.0.0
 */
public class JsoupProviceTest {
    private static Map<Integer, String> cssMap = new HashMap<Integer, String>();

    static {
        cssMap.put(1, "provincetr");// 省
        cssMap.put(2, "citytr");// 市
        cssMap.put(3, "countytr");// 县
        cssMap.put(4, "towntr");// 镇
        cssMap.put(5, "villagetr");// 村
    }

    public static void main(String[] args) throws Exception {
        int level = 1;
//        TestConDataBase.initDataBase();

        // 获取全国各个省级信息
        Document connect = connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/");
        Elements rowProvince = connect.select("tr." + cssMap.get(level));
        for (Element provinceElement : rowProvince)// 遍历每一行的省份城市
        {
            Elements select = provinceElement.select("a");
            for (Element province : select)// 每一个省份(四川省)
            {
                Thread.sleep(1000L);
                String href = province.attr("href");
                String procode = href.substring(href.length()-7, href.length()-5)+"0000000000";
                System.out.println(level+1+","+procode+","+province.text());
                printProvince(province);
//                SysZone zone = new SysZone();
//                try {
//                    zone.set("zoneLevel", level+1)
//                            .set("zoneCode", procode.trim())
//                            .set("parentCode", "000000000000")
//                            .set("zoneName", province.text())
//                            .save();
//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//
//                }

              parseNextLevel(province, level + 1,procode,province.text());
            }
        }
//        for (int i = 3; i < rowProvince.size(); i++) {
//            Element provinceElement = rowProvince.get(i);
//            Elements select = provinceElement.select("a");
//            for (int j = 2; j < select.size(); j++) {
//                Element province = select.get(j);
//                System.out.println(province.text());
//                parseNextLevel(province, level + 1);
//            }
//        }
    }



    public void testa(){
        // 获取全国各个省级信息
        Document connect = connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/");
        Elements rowProvince = connect.select("tr." + cssMap.get(1));
        for (Element provinceElement : rowProvince)// 遍历每一行的省份城市
        {
            Elements select = provinceElement.select("a");
            for (Element province : select)// 每一个省份(四川省)
            {
                printProvince(province);
            }
        }
    }

    private static void parseNextLevel(Element parentElement, int level, String parent,String proName)
            throws IOException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document doc = connect(parentElement.attr("abs:href"));
        if(doc==null){
            doc = connect(parentElement.attr("abs:href"));
        }
        Elements newsHeadlines = doc.select("tr." + cssMap.get(level));//
        // 获取表格的一行数据
        for (Element element : newsHeadlines) {
            String parents = printInfo(element, level + 1,parent,proName);
            Elements select = element.select("a");
            if (select.size() != 0) {
                parseNextLevel(select.last(), level + 1,parents,proName);
            }
        }
    }

    private static void printProvince(Element province){
        BufferedWriter bufferedWriter = null;
        try {
            String pro =  province.text();
            String proFileName = "E:\\provinces\\pro-" + pro + ".txt";
            FileUtil.makeDirs(proFileName);
            bufferedWriter = new BufferedWriter(new FileWriter(new File(proFileName
                    ), true));

            String href = province.attr("href");
            String procode = href.substring(href.length()-7, href.length()-5)+"0000000000";
            String str=2+","+procode+",000000000000,"+pro;
            System.out.println(str);
            bufferedWriter.write(str);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bufferedWriter = null;
            }
        }
    }

    private static  String printInfo(Element element, int level,String parent,String proName) {
        BufferedWriter bufferedWriter = null;
        String code = "";
        code = element.select("td").first().text();
        String name =element.select("td").last().text();
        String str =  level + "," + code + ","+parent+","
                + name;

        System.out.println(str);
//        SysZone zone = new SysZone();
//        try {
//            zone.set("zoneLevel", level)
//                    .set("zoneCode", code.trim())
//                    .set("parentCode", parent.trim())
//                    .set("zoneName", name.trim())
//                    .save();
//        } catch (Exception e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//
//        }

        String cityFile = "E:\\provinces\\citys-" + proName + "citys.txt";
        FileUtil.makeDirs(cityFile);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(
                    cityFile), true));

            bufferedWriter.write(str);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bufferedWriter = null;
            }
        }

        return  code;
    }

    private static Document connect(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("The input url('" + url
                    + "') is invalid!");
        }
        String [] b = {
                "Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070309 Firefox/2.0.0.3",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1",
                "Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6",
                "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6",
                "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
                "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
                "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
                "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
                "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",
                "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24"
        };
        try {

            Random rand = new Random();
            return Jsoup.connect(url)
                    .header("User-Agent",b[rand.nextInt(19)])
                    .timeout(90 * 1000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
