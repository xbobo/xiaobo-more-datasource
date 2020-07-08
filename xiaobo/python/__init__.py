#!/usr/bin/python
import sys;
import re;
print('Python %s on %s' % (sys.version, sys.platform))
print(re.match('www', 'www.runoob.com').span())  # 在起始位置匹配
print(re.match('com', 'www.runoob.com'))         # 不在起始位置匹配

# 这是一个注释
print("Hello, World!")

a=5;
b=2;
print(a+b);
print(a-b);
print(a*b);
print(a/b);
print(a%b);
print(a//b);
print(a**b)

import random

# 第一个随机数
print ("random() : ", random.random())

# 第二个随机数
print ("random() : ", random.random())


var1="0123456789";
print(var1[:1])
print(var1[2:5])
print(var1[5:])
print(var1[:])
print("1" in var1[:])

print(len(var1))

list=["1","2","3","4","5","6"];
del list[:1]
print(len(list))
print(list)



sys.exit()