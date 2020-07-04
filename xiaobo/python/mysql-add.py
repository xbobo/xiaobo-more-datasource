#!/usr/bin/python3

import pymysql

# 打开数据库连接
db = pymysql.connect("localhost","root","123456","xiabo" )

# 使用cursor()方法获取操作游标
cursor = db.cursor()

# SQL 插入语句
sql = "INSERT INTO test(id,name) \
       VALUES ('%s', '%s')" % \
      ('2', 'Mohan')
try:
    # 执行sql语句
    cursor.execute(sql)
    # 执行sql语句
    db.commit()
except:
    # 发生错误时回滚
    db.rollback()

# 关闭数据库连接
db.close()