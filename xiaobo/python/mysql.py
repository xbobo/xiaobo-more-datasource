#!/usr/bin/python3

# PyMySQL 安装
# 在使用 PyMySQL 之前，我们需要确保 PyMySQL 已安装。
#
# PyMySQL 下载地址：https://github.com/PyMySQL/PyMySQL。
#
# 如果还未安装，我们可以使用以下命令安装最新版的 PyMySQL：
#
# $ pip3 install PyMySQL
# 如果你的系统不支持 pip 命令，可以使用以下方式安装：
#
# 1、使用 git 命令下载安装包安装(你也可以手动下载)：
#
# $ git clone https://github.com/PyMySQL/PyMySQL
# $ cd PyMySQL/
# $ python setup.py install


import pymysql

# 打开数据库连接
db = pymysql.connect("localhost","root","123456","xiabo" )

# 使用 cursor() 方法创建一个游标对象 cursor
cursor = db.cursor()

# 使用 execute()  方法执行 SQL 查询
cursor.execute("SELECT VERSION()")

# 使用 fetchone() 方法获取单条数据.
data = cursor.fetchone()

print ("Database version : %s " % data)

# 关闭数据库连接
db.close()