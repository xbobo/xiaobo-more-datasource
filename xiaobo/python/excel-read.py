import xlrd
data = xlrd.open_workbook('D:/github/xb-python/test/test.xlsx')
# 获取sheets数目：
print (data.nsheets)
# 获取sheets列表：
print(data.sheets())

# 获取sheets name列表：
print(data.sheet_names())
# #sheets返回一个sheet列表
sheet = data.sheets()[0]
sheet = data.sheet_by_index(0)    #通过索引顺序获取
sheet = data.sheet_by_name(u'Sheet1')#通过名称获取
# 第几行的数据内容,索引是从0开始的
print(sheet.row_values(0))
# 第几列的数据,这列所有的数据
print(sheet.col_values(3))
# 获取列数
print(sheet.ncols)
# 获取行数
print(sheet.nrows)
# 获取名字
print(sheet.name)

# 可以循环获取行内容
for i  in range(sheet.nrows):
    print(sheet.row_values(i))