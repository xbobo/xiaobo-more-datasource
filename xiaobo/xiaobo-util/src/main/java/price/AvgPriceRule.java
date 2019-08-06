 package price;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvgPriceRule {
    
     public static Map<String, ActivityConsumerProductInfoVO> priceSharing(Map<String, ActivityConsumerProductInfoVO> activiyProductsMap,
         List<ValidateExtraProductVO> productList, 
         Double sumPayPrice, Double sumProductPrice, Double sumDiscountPrice, Double balanceDiscountPrice) {
    	 Map<String, ActivityConsumerProductInfoVO> tempactiviyProductsMap=new HashMap<String, ActivityConsumerProductInfoVO>();
         productList.sort(Comparator.comparing(ValidateExtraProductVO::getPrice));
         for(int i=0;i<productList.size();i++) {
              ValidateExtraProductVO validateExtraProductVO = productList.get(i);
              Double curProPrice = DoubleUtil.toTwoPlaceRound(validateExtraProductVO.getPrice().doubleValue());
             ActivityConsumerProductInfoVO activityConsumerProductInfoVO = activiyProductsMap.get(validateExtraProductVO.getProduct_id()+"");
              if(i==(productList.size()-1)) {
                  if(balanceDiscountPrice>curProPrice) {
                      balanceDiscountPrice=DoubleUtil.toTwoPlaceRound(balanceDiscountPrice-curProPrice);
                      activityConsumerProductInfoVO.setAvg_discount_price(curProPrice);
                      activityConsumerProductInfoVO.setSurplus_price(curProPrice-curProPrice);
                  }else {
                      activityConsumerProductInfoVO.setAvg_discount_price(balanceDiscountPrice);
                      activityConsumerProductInfoVO.setSurplus_price(DoubleUtil.toTwoPlaceRound(curProPrice-balanceDiscountPrice));
                      balanceDiscountPrice=DoubleUtil.toTwoPlaceRound(balanceDiscountPrice-balanceDiscountPrice);
                  }
              }else {
                  Double curDiscountPrice=validateExtraProductVO.getPrice().doubleValue()/sumProductPrice*sumDiscountPrice;
                  if(curDiscountPrice>0) {
                      curDiscountPrice = DoubleUtil.toTwoPlaceFloor(curDiscountPrice);
                  }
                  if(curDiscountPrice>0) {
                      activityConsumerProductInfoVO.setAvg_discount_price(curDiscountPrice);
                      balanceDiscountPrice=balanceDiscountPrice-curDiscountPrice;
                      double surplus_price=DoubleUtil.toTwoPlaceRound(curProPrice-curDiscountPrice); 
                      activityConsumerProductInfoVO.setSurplus_price(surplus_price);
                  }else {
                      activityConsumerProductInfoVO.setAvg_discount_price(0D);
                      activityConsumerProductInfoVO.setSurplus_price(curProPrice);
                  }
              }
              //同步商品价格
              activityConsumerProductInfoVO.setOri_price(validateExtraProductVO.getPrice().doubleValue());
              sumPayPrice=sumPayPrice+activityConsumerProductInfoVO.getOri_price();
              tempactiviyProductsMap.put(validateExtraProductVO.getProduct_id()+"", activityConsumerProductInfoVO);
          }
//         System.out.println("第0次均分");
//         for(Entry<String, ActivityConsumerProductInfoVO> entry :tempactiviyProductsMap.entrySet()) {
//             System.out.println(entry.getKey()+"::"+entry.getValue().getOri_price()+"::"+entry.getValue().getSurplus_price()+";;"+entry.getValue().getAvg_discount_price());
//         }
          // 初次均分仍有 优惠金额
          //优惠未分完继续分
         int x=1;
          while(balanceDiscountPrice>0&&x<productList.size()) {
              //去除优惠完的商品  还可以再次进行优惠的商品
            //TODO  根据剩余价格均分
              double leavePaySum=0;
              List<ValidateExtraProductVO> leaveproductList = new ArrayList<ValidateExtraProductVO>();
              for(int i=0;i<productList.size();i++) {
                  ValidateExtraProductVO validateExtraProductVO = productList.get(i);
                  String key = validateExtraProductVO.getProduct_id()+"";
                  ActivityConsumerProductInfoVO activityConsumerProductInfoVO = tempactiviyProductsMap.get(key);
                  if(activityConsumerProductInfoVO!=null) {
                      if(activityConsumerProductInfoVO.getSurplus_price()>0) {
                          leaveproductList.add(validateExtraProductVO);
                          leavePaySum=leavePaySum+activityConsumerProductInfoVO.getSurplus_price();
                      }
                  }
              }
              //再次均摊金额
              for(int i=0;i<leaveproductList.size();i++) {
                  ValidateExtraProductVO validateExtraProductVO = leaveproductList.get(i);
                  String key = validateExtraProductVO.getProduct_id()+"";
                  ActivityConsumerProductInfoVO activityConsumerProductInfoVO = tempactiviyProductsMap.get(key);
                  if(activityConsumerProductInfoVO!=null) {
                      Double surplus_price = activityConsumerProductInfoVO.getSurplus_price();
                      if(i==leaveproductList.size()-1) {
                          if(balanceDiscountPrice>surplus_price) {
                              activityConsumerProductInfoVO.setAvg_discount_price(activityConsumerProductInfoVO.getAvg_discount_price()+surplus_price);
                              activityConsumerProductInfoVO.setSurplus_price(activityConsumerProductInfoVO.getSurplus_price()-surplus_price);
                              balanceDiscountPrice=balanceDiscountPrice-surplus_price;
                          }else {
                              activityConsumerProductInfoVO.setAvg_discount_price(activityConsumerProductInfoVO.getAvg_discount_price()+balanceDiscountPrice);
                              activityConsumerProductInfoVO.setSurplus_price(surplus_price-balanceDiscountPrice);
                              balanceDiscountPrice=balanceDiscountPrice-balanceDiscountPrice;
                          }
                      }else {
                          double cur_discount_price=activityConsumerProductInfoVO.getSurplus_price()/leavePaySum*balanceDiscountPrice;
                          if(cur_discount_price>0) {
                              cur_discount_price=Math.floor(cur_discount_price*100)/100;
                          }
                          activityConsumerProductInfoVO.setAvg_discount_price(activityConsumerProductInfoVO.getAvg_discount_price()+cur_discount_price);
                          balanceDiscountPrice=balanceDiscountPrice-cur_discount_price;
                          activityConsumerProductInfoVO.setSurplus_price(surplus_price-cur_discount_price);
                      }
                  }
                  tempactiviyProductsMap.put(key, activityConsumerProductInfoVO);
              }
              balanceDiscountPrice=DoubleUtil.toTwoPlaceRound(balanceDiscountPrice); 
//              for(Entry<String, ActivityConsumerProductInfoVO> entry :tempactiviyProductsMap.entrySet()) {
//                  System.out.println(entry.getKey()+"::"+entry.getValue().getOri_price()+"::"+entry.getValue().getSurplus_price()+";;"+entry.getValue().getAvg_discount_price());
//              }
              x++;
          }
         return tempactiviyProductsMap;
     }
}
