package projecttset;

/**
 * @author xiaobo
 * @date 2019/7/27
 * @description:
 */
public class Or16_10To {

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "5d369d1c7c45e11900c0ed0f".indexOf(c);
        return b;
    }

    public static void main(String[] args) {

        long l = Integer.parseInt("5d369d1c7c45e11900c0ed0f", 16);
        System.out.println(l);
    }
}
