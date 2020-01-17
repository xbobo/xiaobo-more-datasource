package bitset;

import java.util.BitSet;

/**
 * @author xiaobo
 * @date 2019/8/8
 * @description:
 */
public class BitSet2 {
    public static void main(String[] args) {
//
        BitSet bitSet=new BitSet();
        int[] nums={1,2,3};

        for (int num : nums) {
            if (bitSet.get(num)) {
                System.out.println(num);
            } else {
                bitSet.set(num);
            }
        }
//
//        System.out.println(888>>6);
//        System.out.println((1L<<888));
//        System.out.println(72057594037927936L&(1L<<888));
//        //83886080 72057594037927936
//        System.out.println(Integer.toBinaryString(1<<6));
//        十进制转成十六进制：
//        Integer.toHexString(int i)
//        十进制转成八进制
//        Integer.toOctalString(int i)
//        十进制转成二进制
//        Integer.toBinaryString(int i)
//        十六进制转成十进制
//        Integer.valueOf("FFFF",16).toString()
//        八进制转成十进制
//        Integer.valueOf("876",8).toString()
//        二进制转十进制
//        Integer.valueOf("0101",2).toString()
        System.out.println((Integer.toBinaryString(1<<2)));
        System.out.println(2|4);
        System.out.println((Integer.toBinaryString(6)));

        String testnum="0000000000000000 0000000000000000 0000000000000000 0000000000000000";
        testnum=testnum.replaceAll("\\s","");
        System.out.println(testnum);
        System.out.println(Long.valueOf(testnum,2).toString());
        System.out.println(1<<1);

    }
}
