#!/usr/bin/env python3
# -*- coding: utf-8 -*-

a = 100
if a >= 0:
    print(a)
else:
    print(a)

age = 22
if age >= 18:
    print("成年了")
else:
    print("未成年")

# -----字符串

print("Hello 'Python'")

# 如果字符串内部既包含'(单引号)、"(双引号)
# 可以使用转义字符\来标识
print('I\'m \"OK\"')

# 转义字符 \ 可以转义很多字符,比如 \n 表示换行、\t 表示制表符
# 字符 \ 本身也要转义,所以 \\ 表示的字符就是 \
# 下面我们打印看看...

# \n 换行符
print('I\'m learing \n Python')

print('\\ \n \\')

# -----------------------------

# 如果字符串里面有很多字符都需要转义,就需要加很多 \ ,为了简化,Python还允许使用r''表示''内部的字符串默认不转义.
print('\\\t\\')

print(r'\\\t\\')

# 如果字符串内部有很多换行,用 \n 写在一行里不好阅读,为了简化,Python允许'''...'''的格式表示多行内容
# 这个是命令行中的格式
print('''line1
...line2
...line3''')

# 如果是在编译器中,这样写
print('''line1
line2
line3''')

a = 'ABC'
b = a
a = 'XYZ'

print(b)

# ---------

abc = 'ABC'
print(abc)

abcd = b"ABCD"
print(abcd)
