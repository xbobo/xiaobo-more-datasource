 package price;

public class DoubleUtil {
    /**
                 *       向上取整
     * @param num
     * @return
     */
     public static Double toTwoPlaceCeil(Double num) {
        if(num!=null) {
           return Math.ceil(num*100)/100; 
        }
        return null;
     }
     
     /**
                     *     向下取整
      * @param num
      * @return
      */
     public static Double toTwoPlaceFloor(Double num) {
         if(num!=null) {
            return Math.floor(num*100)/100; 
         }
         return null;
      }
     /**
      * 四舍五入
      * @param num
      * @return
      */
     public static Double toTwoPlaceRound(Double num) {
         if(num!=null) {
            return (double)Math.round(num*100)/100; 
         }
         return null;
      }
 
     public static void main(String[] args) {
        System.out.println(toTwoPlaceRound(0.015006));
    }
}
