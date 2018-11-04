#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# list 和Java中的list集合差不多
# 有序、可以有重复数据、随时添加和删除其中的元素
# list 查找和插入的时间随着元素的增加而增加
# list 占用空间小，浪费内存很少

# 列出所有的同学
classmates = ['cyx1', 'cyx2', 'cyx3', "cyx1"]
print('全部元素', classmates)

# 列出集合中元素个数
print('元素数量', len(classmates))

# 用索引来访问list集合中每一个元素的位置,索引从0开始
# 超出索引范围,会报错
# 集合中第一个元素
print('集合中第一个元素', classmates[0])

# 集合中第二个元素
print('集合中第二个元素', classmates[1])

# 集合中第三个元素
print('集合中第三个元素', classmates[2])

# 集合中最后一个元素
print('集合中最后一个元素', classmates[len(classmates) - 1])

# ----------

# 追加元素至list集合末尾
classmates.append("Adam")
print(classmates)

# 插入元素至指定位置
classmates.insert(1, "Jack")
print(classmates)

# 删除list集合末尾元素
classmates.pop()
print(classmates)

# 删除list集合指定位置的元素
classmates.pop(1)
print(classmates)

# 替换指定位置的元素
classmates[0] = "Sarah"
print(classmates)

# list集合中的元素可以是不同类型的元素
L = ["Apple", 9521, True]
print("集合中存储不同类型的元素", L)

# list集合中也可以是另一个list集合
LL = [1, 2, 3, ["Apple", 9521, True], 4, 5, 6]
print(LL)
print('元素个数', len(LL))

# 拆开来写
MM = [1, 2, 3, 4]
KK = [5, 6, 7, MM, 8, 9]
print(KK)
print("元素个数", len(KK))

# 集合中没有元素，就是一个空集合，长度为0
PP = []
print(len(PP))
