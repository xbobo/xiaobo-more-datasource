import xlrd
import xlwt
from datetime import date
from datetime import datetime


def read_excel():
    # 打开文件
    workbook = xlrd.open_workbook('test.xlsx')
    # sheet1,索引是从0开始,得到sheet1表的句柄.用这个可以操作表
    sheet1 = workbook.sheet_by_index(0)

    # 循环得到sheet1表的内容
    for i in range(sheet1.nrows):
        for j in range(sheet1.ncols):
            # 当表格中的内容是date的时间类型是换成python的时间类型
            if sheet1.cell(i,j).ctype == 3:
                d = xlrd.xldate_as_tuple(sheet1.cell_value(i, j), workbook.datemode)
                print(date(*d[:3]),end=' ')
            else:
                print(sheet1.cell(i,j).value,end=' ')

        print()

read_excel()