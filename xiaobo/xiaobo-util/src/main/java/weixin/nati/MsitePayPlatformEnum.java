 package weixin.nati;


public class MsitePayPlatformEnum {
     
     public static enum PayTagType {
         NATIVE(1, "NATIVE"),
         APP_IOS(2, "APP"),
         APP_ANDROID(3, "APP"),
         MWEB(4, "MWEB"),
         BACK_SYSTEM(5, "BACK_SYSTEM"),
         JSAPI(6, "JSAPI");

         private Integer value;
         private String name;

         private PayTagType(Integer value, String name) {
             this.value = value;
             this.name = name;
         }

         public String getName() {
             return name;
         }
     }

     public static String getPayTagType(int value) {
         switch (value) {
             case 1: {
                 return PayTagType.NATIVE.getName();
             }
             case 2: {
                 return PayTagType.APP_IOS.getName();
             }
             case 3: {
                 return PayTagType.APP_ANDROID.getName();
             }
             case 4: {
                 return PayTagType.MWEB.getName();
             }
             case 5: {
                 return PayTagType.BACK_SYSTEM.getName();
             }
             case 6: {
                 return PayTagType.JSAPI.getName();
             }
         }
         return "";
     }
}
