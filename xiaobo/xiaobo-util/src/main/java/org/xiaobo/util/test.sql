SELECT
	cm.`year` AS `year`,
	cm.stage AS stage,
	cm.detail AS detail,
	cm.start_time AS start_time,
	cm.end_time AS end_time,
	cm.teacher_code AS teacher_code,
	cm.assistant_code AS assistant_code,
	p.class_id AS class_id,
	p.course_id AS course_id,
	p.product_id AS product_id,
	p.product_id_main AS product_id_main,
	p.product_name AS product_name,
	p.product_pc_img AS product_pc_img,
	p.old_price AS old_price,
	p.price AS price,
	p.course_price AS course_price,
	p.product_num AS product_num,
	p.cardinal_number AS cardinal_number,
	p.product_detail AS product_detail,
	p.product_other_img AS product_other_img,
	p.product_trait AS product_trait,
	p.buy_num AS buy_num,
	p.postage AS postage,
	p. STATUS AS STATUS,
	p.stuff_price AS stuff_price,
	p.need_post AS need_post,
	u1.user_id AS teacher_id,
	u1.user_login AS teacher_name,
	u1.teacher_img AS teacher_img,
	u1.wechat_code AS teacher_wechat_code,
	u1.wechat_num AS teacher_wechat_num,
	u2.user_id AS assistant_id,
	u2.user_login AS assistant_name,
	u2.teacher_img AS assistant_img,
	u2.wechat_code AS assistant_wechat_code,
	u2.wechat_num AS assistant_wechat_num,
	c.grade_mode AS grade_mode,
	c.subject_mode AS subject_mode,
	c.part_mode AS part_mode,
	c.quarter_mode AS quarter_mode,
	c.course_hour_num AS course_hour_num,
	c.course_times_num AS course_times_num
FROM
	product p
LEFT JOIN class_model cm ON cm.class_id = p.class_id
LEFT JOIN course c ON c.course_id = p.course_id
LEFT JOIN `user` u1 ON cm.teacher_id = u1.user_id
LEFT JOIN `user` u2 ON cm.assistant_id = u2.user_id
where p.product_id in (
  SELECT od.product_id from `order` o LEFT JOIN order_detail od on o.order_id =od.order_id where student_id = 20963366932881408 and o.order_status in (2,7,8,9) and od.`status` in (1,3)
) and cm.`year` =2019  and cm.stage ='01' and c.quarter_mode='C' and c.grade_mode ='09' and c.subject_mode='Y'