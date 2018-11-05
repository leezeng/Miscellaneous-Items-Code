#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# -----tuple元组
classmates = ("Michael", "Bob", "Tracy")
print(classmates)

print("第一个元素", classmates[0])

print("第二个元素", classmates[1])

# 当定义一个tuple时，在定义时，tuple元素就必须被确定下来
t = (1, 2)
print(t)

# 定义一个空的tuple
tt = ()
print(tt)

k = (1)
print(k)

U = (1,)
print(U)

UU = (5 + 1)
print(UU)

JJ = ("a", "b", ["A", "B"])
print("JJ", JJ)
JJ[2][0] = "X"
print("JJ", JJ)
JJ[2][1] = "Y"

print("JJ", JJ)
