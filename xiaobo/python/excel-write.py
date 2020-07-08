
import xlrd
import xlwt
from datetime import date
from datetime import datetime

def set_style(name,height,bold=False):
    # 初始化样式
    style = xlwt.XFStyle()

    font = xlwt.Font() # 创建字体
    font.bold = bold
    font.color_index = 4
    font.height = height
    font.name = name
    style.font = font
    return style

def write_execl():
    f = xlwt.Workbook()

    sheet1 = f.add_sheet(u'sheet1',cell_overwrite_ok=True)
    row0 = [u'业务',u'状态',u'北京',u'上海',u'广州',u'深圳',u'状态小计',u'合计']
    column0 = [u'机票', u'船票', u'火车票', u'汽车票', u'其它']
    status = [u'预订', u'出票', u'退票', u'业务小计']
    for i in range(0, len(row0)):
        sheet1.write(0,i,row0[i],set_style('Times New Roman',220,True))

    i ,j = 1,0
    while i < 4*len(column0) and j < len(column0):
        sheet1.write_merge(i,i+3,0,0,column0[j],set_style('Arial',220,True)) #第一列
        sheet1.write_merge(i, i + 3, 7, 7)  # 最后一列"合计"
        i += 4
        j +=1
    sheet1.write_merge(21, 21, 0, 1, u'合计', set_style('Times New Roman', 220, True))


    i =0
    while i < 4*len(column0):
        for j in range(0,len(status)):
            sheet1.write(j+i+1,1,status[j])
        i+=4

    sheet2 = f.add_sheet(u'sheet2',cell_overwrite_ok=True) #创建sheet2
    row0 = [u'姓名',u'年龄',u'出生日期',u'爱好',u'关系']
    column0 = [u'小杰',u'小胖',u'小明',u'大神',u'大仙',u'小敏',u'无名']

    #生成第一行
    for i in range(0,len(row0)):
        sheet2.write(0,i,row0[i],set_style('Times New Roman',220,True))

    #生成第一列
    for i in range(0,len(column0)):
        sheet2.write(i+1,0,column0[i],set_style('Times New Roman',220))

    sheet2.write(1,2,'1991/11/11')
    sheet2.write_merge(7,7,2,4,u'暂无') #合并列单元格
    sheet2.write_merge(1,2,4,4,u'好朋友') #合并行单元格


    f.save('test11.xlsx')

write_execl()