#!/usr/bin/env bash

#进行数学计算


#使用普通变量赋值，定义数值，虽然会被存储为字符串。但可以使用let命令直接进行基本的算术操作。
number1=10;
number2=20;
let number3=number1+number2;
echo "普通变量相加 : ${number3}"


#自增操作
num01=01;
let num01++;
echo "自增操作 : ${num01}";


#自减操作
num02=04;
let num02--;
echo "自减操作 : ${num02}";



#'+='运算符
num03=03;
let num03+=3;
echo "'+='运算符操作 : ${num03}";


#'-='运算符
num04=06;
let num04-=5;
echo "'-='运算符操作 : ${num04}";



######################################


#高精度运算/数学运算高级工具
#bc用于数据运算的高级工具，精密计算包含了大量选项。

#注:由于在Windows平台，使用Cygwin模拟Linux，bc命令无法使用，暂时搁置。

