#!/usr/bin/python3

import pymysql

# 打开数据库连接
db = pymysql.connect("localhost","root","123456","xiabo" )

# 使用cursor()方法获取操作游标
cursor = db.cursor()

# SQL 查询语句
sql = "SELECT * FROM test \
       WHERE id > %s" % (0)
try:
    # 执行SQL语句
    cursor.execute(sql)
    # 获取所有记录列表
    results = cursor.fetchall()
    for row in results:
        id = row[0]
        name = row[1]
        # 打印结果
        print ("id=%s,name=%s" % \
               (id, name))
except:
    print ("Error: unable to fetch data")

# 关闭数据库连接
db.close()