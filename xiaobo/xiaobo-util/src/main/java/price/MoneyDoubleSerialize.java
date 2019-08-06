package price;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MoneyDoubleSerialize extends JsonSerializer<Double> {
    //原本这里是  ##.00 ,带来的问题是如果数据库数据为0.00返回“ .00 “经评论指正，改为0.00
    private DecimalFormat df = new DecimalFormat("0.00");
    @Override
    public void serialize(Double arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        if(arg0 != null) {
            String format = df.format(arg0);
            if (format.endsWith(".00")) {
                arg1.writeString(format.substring(0, format.length() - 3));
            } else {
                arg1.writeString(df.format(arg0));
            }
        }
    }

}

