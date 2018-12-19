package com.server.strategy_pattern.test1;

/**
 * 策略模式
 * <p>
 * http://www.cnblogs.com/greyzeng/p/5915202.html
 *
 * @author CYX
 * @create 2018-12-19-10:36
 */

/*
    模拟鸭子游戏，游戏中会出现各种鸭子，一边游泳戏水，一边呱呱叫。我们应该如何设计这个鸭子类呢？
 */

/**
 * Duck抽象类
 */
public abstract class Duck {

    public abstract void display();

    public abstract void quack();

    public void swim() {
        System.out.println("all ducks can swim");
    }

}

/**
 * Duck子类-红头鸭
 */
class RedHeadDuck extends Duck {

    @Override
    public void display() {
        System.out.println("This is RedHeadDuck");
    }

    @Override
    public void quack() {
        System.out.println("RedHeadDuck Quack");
    }
}

/**
 * Duck子类-模型鸭子
 */
class ModelDuck extends Duck {
    @Override
    public void display() {
        System.out.println("This is ModelDuck");
    }

    @Override
    public void quack() {
        System.out.println("ModelDuck Can not Quack");
    }
}

/*
    首先我们考虑设计一个鸭子的超类(抽象类)Duck，抽象出鸭子的公共方法(抽象方法)，然后其他种类的鸭子类型(子类)继承这个抽象类；
    子类对抽象方法有自己独有的实现

    但是，这种设计，会出现一个问题：
    如果要增加一个可以飞的鸭子，我们在抽闲类中加入一个fly()方法，所有继承Duck的子类都会有这个方法，导致代码在多个子类中重复。

    另一种方法：
    分开变化与不变化的部分，因为Duck类中，fly()和quack()会随着鸭子不同而改变，所以我们需要将这两个行为从Duck中分离出来，
    建立一组新类来代表每个行为。

    详见：com.server.strategy_pattern.test2

 */