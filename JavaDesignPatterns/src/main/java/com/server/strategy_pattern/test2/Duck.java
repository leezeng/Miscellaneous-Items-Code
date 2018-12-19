package com.server.strategy_pattern.test2;

/**
 * @author CYX
 * @create 2018-12-19-11:55
 */

/*
    现在鸭子将飞行和鸭子叫的动作委托给'flyBehavior'和'quackBehavior'处理
 */
public abstract class Duck {

    public Duck() {
    }

    public FlyBehavior flyBehavior;

    public QuackBehavior quackBehavior;

    public abstract void display();

    public void swim() {
        System.out.println("all ducks can swim");
    }

    public void performFly() {
        flyBehavior.fly();
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}

/**
 * 每种鸭子继承抽象类Duck时，只需要在构造函数中设置对应的quackBehavior、flyBehavior实例变量即可
 */
class RedHeadDuck extends Duck {

    public RedHeadDuck() {
        this.quackBehavior = new Gua();
        this.flyBehavior = new FluWithWings();
    }

    @Override
    public void display() {

    }

    @Override
    public void performFly() {
        flyBehavior.fly();
    }

    @Override
    public void performQuack() {
        quackBehavior.quack();
    }
}

class ModelDuck extends Duck {

    public ModelDuck() {
        this.flyBehavior = new FlyNoWay();
        this.quackBehavior = new MuteQuac();
    }

    @Override
    public void display() {
        System.out.println("This is ModelDuck");
    }

    @Override
    public void performFly() {
        flyBehavior.fly();
    }

    @Override
    public void performQuack() {
        quackBehavior.quack();
    }
}