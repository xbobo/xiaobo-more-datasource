import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {
	public static void main (String[] args ) throws IOException {
        String a ="{\"NaMe\":\"hello\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Label label = objectMapper.readValue(a, Label.class);
        String labelString = objectMapper.writeValueAsString(label);
        System.out.println(labelString);
    }

    public static class Label{
    	//反序列化时两个都可用，都没有会报错
        @JsonAlias("NaMe")
        //@JsonProperty("NaMe")
        public String name;
        public Label(){
        }
    }
}
