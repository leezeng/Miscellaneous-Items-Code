#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# 将str转换为bytes
print('ABC'.encode("ascii"))
print("中文".encode("UTF-8"))

# 将bytes转换为str
print(b'ABC'.decode("ascii"))
print(b'\xe4\xb8\xad\xe6\x96\x87'.decode("UTF-8"))
print('字符串长度', len(b'\xe4\xb8\xad\xe6\x96\x87'.decode("UTF-8")))
print('字节长度', len("中文".encode("UTF-8")))


# 格式化
print('Hello , %s' % 'World')
print('Hi , %s you have $%d.' % ('CYX', 10000))
print('growth rate : %d %%' % 7)