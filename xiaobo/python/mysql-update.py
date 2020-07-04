#!/usr/bin/python3

import pymysql

# 打开数据库连接
db = pymysql.connect("localhost","root","123456","xiabo" )

# 使用cursor()方法获取操作游标
cursor = db.cursor()

# SQL 更新语句
sql = "UPDATE test SET name = name + 1 WHERE id = '%c'" % ('1')
try:
    # 执行SQL语句
    cursor.execute(sql)
    # 提交到数据库执行
    db.commit()
except:
    # 发生错误时回滚
    db.rollback()

# 关闭数据库连接
db.close()