package com.server.strategy_pattern.test2;

/*
    从Duck中将quack和fly两个行为从Duck中分离出来，建立一组新类来代表每个行为。
 */

/**
 * 鸭叫行为-接口
 */
interface QuackBehavior {
    void quack();
}

/**
 * 动物叫行为的具体实现
 */
class MuteQuac implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("silence...");
    }
}

/**
 * 动物叫行为的具体实现
 */
class Gua implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("ga ga ga....");
    }
}

/**
 * 飞行行为-接口
 */
interface FlyBehavior {
    void fly();
}

/**
 * 飞行行为的具体实现
 */
class FluWithWings implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("fly fly fly...");
    }
}

/**
 * 飞行行为的具体实现
 */
class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("can't fly...");
    }
}