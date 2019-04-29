package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		
		Long [] ls=new Long[] {1553837659L,
		1555646166L,
		1555646316L,
		1555653021L,
		1555653147L,
		1556245875L,
		1556246579L,
		1556246621L,
		1556246657L,
		1556246711L,
		1556246914L};
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(Long single:ls) {
			System.out.println(sdf.format(new Date(single*1000L)));
		}
		/*

		update `order` set order_status=4 where order_id in (		39318102660653056,
				39318732288598016,
				39346857684803584,
				39347385659596800)
		*/
		//SELECT * FROM `order` where order_status=1 AND create_time <1556336742
//		39318102660653056L,
//		39318732288598016L,
//		39346857684803584L,
//		39347385659596800L,
		
//		31733586366603264,
//		39318102660653056,
//		39318732288598016,
//		39346857684803584,
//		39347385659596800,
//		41833465738731520,
//		41836417400479744,
//		41836594337193984,
//		41836744233230336,
//		41836973078650880,
//		41837824975347712
		
		/*
		 * update `order` set order_status=4 where order_id in ( 31733586366603264,
		 * 39318102660653056, 39318732288598016, 39346857684803584, 39347385659596800,
		 * 41833465738731520, 41836417400479744, 41836594337193984, 41836744233230336,
		 * 41836973078650880, 41837824975347712)
		 */
		
	//	SELECT * FROM `order` where order_status=1 AND create_time <1556334942
		
		//update `order` SET order_status=4 where order_status=1 AND create_time <1556334942
//		1556336742
//		1556334942
		System.out.println(1556336742-30*60);
	}
}
