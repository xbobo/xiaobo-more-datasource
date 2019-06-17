package org.xiaobo.util;
/**
 * 
 * @author xiaobo
 * @date 2019年4月25日
 */
public class TypeEnum {
	/**
	 * java 类型
	 */
	public enum JavaType{
		
//		CHAR String
//		VARCHAR String
//		LONGVARCHAR String
//		NUMERIC java.math.BigDecimal
//		DECIMAL java.math.BigDecimal
//		BIT Boolean
//		TINYINT Integer
//		SMALLINT Integer
//		INTEGER Integer
//		BIGINT Long
//		REAL Float
//		FLOAT Double
//		DOUBLE Double
//		BINARY byte[]
//		VARBINARY byte[]
//		LONGVARBINARY byte[]
//		DATE java.sql.Date
//		TIME java.sql.Time
//		TIMESTAMP java.sql.Timestamp
		
		CHAR("CHAR", "String"),
		VARCHAR("VARCHAR", "String"),
		LONGVARCHAR("LONGVARCHAR", "String"),
		NUMERIC("NUMERIC", "java.math.BigDecimal"),
		DECIMAL("DECIMAL", "java.math.BigDecimal"),
		BIT("BIT", "Boolean"),
		TINYINT("TINYINT", "Integer"),
		SMALLINT("SMALLINT", "Integer"),
		INTEGER("INTEGER", "Integer"),
		BIGINT("BIGINT", "Long"),
		REAL("REAL", "Float"),
		FLOAT("FLOAT", "Double"),
		DOUBLE("DOUBLE", "Double"),
		BINARY("BINARY", "byte[]"),
		VARBINARY("VARBINARY", "byte[]"),
		DATE("DATE", "java.sql.Date"),
		TIME("TIME", "java.sql.Time"),
		TIMESTAMP("TINYINT", "java.sql.Timestamp");
		
		private String value;
		
        private String name;

        private JavaType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getName() {
            return name;
        }
        
        public String getValue() {
            return value;
        }

        public static String getJavaType(String intValue) {
            switch (intValue) {
                case "CHAR":
                    return JavaType.CHAR.getName();
                case "VARCHAR":
                    return JavaType.VARCHAR.getName();
                case "LONGVARCHAR":
                    return JavaType.LONGVARCHAR.getName();
                case "NUMERIC":
                    return JavaType.NUMERIC.getName();
                case "DECIMAL":
                    return JavaType.DECIMAL.getName();
                case "BIT":
                    return JavaType.BIT.getName();
                case "TINYINT":
                    return JavaType.TINYINT.getName();
                case "SMALLINT":
                    return JavaType.SMALLINT.getName();
                case "INTEGER":
                    return JavaType.INTEGER.getName();
                case "BIGINT":
                    return JavaType.BIGINT.getName();
                case "REAL":
                    return JavaType.REAL.getName();
                case "FLOAT":
                    return JavaType.FLOAT.getName();
                case "DOUBLE":
                    return JavaType.DOUBLE.getName();
                case "BINARY":
                    return JavaType.BINARY.getName();
                case "VARBINARY":
                    return JavaType.VARBINARY.getName();
                case "DATE":
                    return JavaType.DATE.getName();
                case "TIME":
                    return JavaType.TIME.getName();
                case "TIMESTAMP":
                    return JavaType.TIMESTAMP.getName();
                default:
                	 return JavaType.CHAR.getName();
            }
        }
	}
	public static void main(String[] args) {
		//System.out.println(JavaType.getJavaType("CHAR"));
	}
}
