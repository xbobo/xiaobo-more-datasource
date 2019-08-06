package price;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Test {

    public static void main(String[] args) {
        Map<String, ActivityConsumerProductInfoVO> activiyProductsMap =
            new HashMap<String, ActivityConsumerProductInfoVO>();
        ActivityConsumerProductInfoVO vo1 = new ActivityConsumerProductInfoVO();
        vo1.setKed_id(1L);
        activiyProductsMap.put(vo1.getKed_id() + "", vo1);

        ActivityConsumerProductInfoVO vo2 = new ActivityConsumerProductInfoVO();
        vo2.setKed_id(2L);
        activiyProductsMap.put(vo2.getKed_id() + "", vo2);

        ActivityConsumerProductInfoVO vo3 = new ActivityConsumerProductInfoVO();
        vo3.setKed_id(3L);
        activiyProductsMap.put(vo3.getKed_id() + "", vo3);

        ActivityConsumerProductInfoVO vo4 = new ActivityConsumerProductInfoVO();
        vo4.setKed_id(4L);
        activiyProductsMap.put(vo4.getKed_id() + "", vo4);

        ActivityConsumerProductInfoVO vo5 = new ActivityConsumerProductInfoVO();
        vo5.setKed_id(5L);
        activiyProductsMap.put(vo5.getKed_id() + "", vo5);

        ActivityConsumerProductInfoVO vo6 = new ActivityConsumerProductInfoVO();
        vo6.setKed_id(6L);
        activiyProductsMap.put(vo6.getKed_id() + "", vo6);

        List<ValidateExtraProductVO> productList = new ArrayList<ValidateExtraProductVO>();
        ValidateExtraProductVO vvo1 = new ValidateExtraProductVO();
        vvo1.setProduct_id(1L);
        vvo1.setPrice(new BigDecimal(0.01D));
        productList.add(vvo1);

        ValidateExtraProductVO vvo2 = new ValidateExtraProductVO();
        vvo2.setProduct_id(2L);
        vvo2.setPrice(new BigDecimal(0.01D));
        productList.add(vvo2);

        ValidateExtraProductVO vvo3 = new ValidateExtraProductVO();
        vvo3.setProduct_id(3L);
        vvo3.setPrice(new BigDecimal(0.01D));
        productList.add(vvo3);

        ValidateExtraProductVO vvo4 = new ValidateExtraProductVO();
        vvo4.setProduct_id(4L);
        vvo4.setPrice(new BigDecimal(0.01D));
        productList.add(vvo4);

        ValidateExtraProductVO vvo5 = new ValidateExtraProductVO();
        vvo5.setProduct_id(5L);
        vvo5.setPrice(new BigDecimal(0.01D));
        productList.add(vvo5);

        ValidateExtraProductVO vvo6 = new ValidateExtraProductVO();
        vvo6.setProduct_id(6L);
        vvo6.setPrice(new BigDecimal(0.02D));
        productList.add(vvo6);
        
        Double sumPayPrice = 0D;
        Double sumProductPrice = 0.07d;
        Double sumDiscountPrice = 0.05d;
        Double balanceDiscountPrice = 0.05d;

        Map<String, ActivityConsumerProductInfoVO> tempactiviyProductsMap = AvgPriceRule.priceSharing(activiyProductsMap,
            productList, sumPayPrice, sumProductPrice, sumDiscountPrice, balanceDiscountPrice);

        for (Entry<String, ActivityConsumerProductInfoVO> entry : tempactiviyProductsMap.entrySet()) {
            System.out.println(entry.getKey() + "::" + entry.getValue().getOri_price() + "::"
                + entry.getValue().getSurplus_price() + ";;" + entry.getValue().getAvg_discount_price());
        }

    }

}
