#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# 条件判断

age = 18

if age > 18:
    print("哈哈哈...")
    print("成年了...")

elif age == 18:
    print("吼吼吼...")
    print("刚好成年...")

else:
    print("嘻嘻嘻...")
    print("未成年...")

# if 条件还可以简写，比如：
if 1:
    print("True")

xx = []
if xx:
    print("非空...")
else:
    print("空...")

# -------------
birth = input('birth: ')

birth = int(birth)

if birth < 2000:
    print('00前')
else:
    print('00后')
