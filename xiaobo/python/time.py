
import sys;
import time;
import calendar;

print(time.time());
print(time.localtime(time.time()))
print(time.strftime("%Y-%m-%d %H:%M:%S",time.localtime()));
print(time.time()*1000//1)
print(calendar.month(2020,1))


def fun(str="aaaa"):
    print("2222")
    return str;


print(fun())

tuple=("1","2","3");
print(tuple)
a="test";
a="1111";
print(a)

print(tuple);

# 可写函数说明
def printinfo( arg1, *et ):
    "打印任何传入的参数"
    print("输出: ")
    print(arg1)
    for var in et:
        print(var)
    return

# 调用printinfo 函数
printinfo( 10 )
printinfo( 70, 60, 50 );

# 可写函数说明
sum = lambda arg1, arg2: arg1 + arg2

# 调用sum函数
print("相加后的值为 : ", sum( 10, 20 ))
print("相加后的值为 : ", sum( 20, 20 ))

import math

content = dir(math)

print(content);

sys.exit();