package test;

public class SingleTest {
	public static void main(String[] args) {
		int a=3;
		System.out.println(3*3);
		System.out.println(System.currentTimeMillis() / 1000);
		
		// 孟美红 18600026488
		
//		select ut.user_id AS teacher_id,
// 		ut.user_nickname AS teacher_name,
// 		ut.teacher_img AS teacher_img,
// 	   ut.wechat_num AS teacher_wechat_num,
// 	   ut.wechat_code AS teacher_wechat_code,
// 		ua.user_id AS assistant_id,
// 		ua.user_nickname AS assistant_name,
// 		ua.teacher_img AS assistant_img,
// 	   ua.wechat_num AS assistant_wechat_num,
// 	   ua.wechat_code AS assistant_wechat_code,
//
// 		c.course_name AS course_name,
// 		c.quarter_mode AS quarter_mode,
// 		c.part_mode AS part_mode,
// 		c.subject_mode AS subject_mode,
// 		c.grade_mode AS grade_mode,
// 		c.course_times_num AS course_times_num,
// 		c.course_hour_num AS course_hour_num,
// 		cm.class_name AS class_name,
// 		cm.start_time AS class_start_time,
// 		cm.end_time AS class_end_time,
// 		cm.stage AS stage,
// 	    p.need_post AS need_post,
// 	    p.stuff_mode AS stuff_mode,
// 		od.order_detail_id,
// 		od.order_id,
// 		od.product_id,
// 		od.ori_price,
// 		od.price,
// 		od. STATUS,
// 		od.product_name,
// 		od.product_img,
// 		od.teacher_name,
// 		od.assistant_code,
// 		od.validity_time,
// 		od.quarter_code,
// 		od.grade_name,
// 		od.subject_name,
// 		od.course_price,
// 		od.stuff_price,
// 		od.postage,
// 		od.discount_price,
// 		od.payment  from
//        `order` o
//        	JOIN order_detail od ON o.order_id = od.order_id
//        	LEFT JOIN product p ON p.product_id = od.product_id
//        	LEFT JOIN class_model cm ON p.class_id = cm.class_id
//        	LEFT JOIN course c ON cm.course_id = c.course_id
//        	LEFT JOIN `user` ut ON cm.teacher_id = ut.user_id
//        	LEFT JOIN `user` ua ON cm.assistant_id = ua.user_id 
	}
}
