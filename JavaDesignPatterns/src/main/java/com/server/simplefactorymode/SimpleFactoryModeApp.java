package com.server.simplefactorymode;

/**
 * 简单工厂模式
 *
 * @author CYX
 * @date: 2018/12/21 16:55
 */
public class SimpleFactoryModeApp {

    public static void main(String[] args) {

        //普通方式构建对象
        //FreshCommodity freshCommodity = new FreshCommodity();
        //GameCommodity gameCommodity = new GameCommodity();

        //使用工厂方法构建对象
        Commodity freshCommodity = CommodityFactory.getCommodityFactory("freshCommodity");
        freshCommodity.saySomething();

        Commodity gameCommodity = CommodityFactory.getCommodityFactory("gameCommodity");
        gameCommodity.saySomething();
    }

}

/**
 * 商品-抽象类
 */
abstract class Commodity {

    public abstract void saySomething();
}

/**
 * 生鲜商品
 */
class FreshCommodity extends Commodity {

    @Override
    public void saySomething() {
        System.out.println("生鲜商品...");
    }
}

/**
 * 游戏商品
 */
class GameCommodity extends Commodity {

    @Override
    public void saySomething() {
        System.out.println("游戏商品...");
    }
}

/**
 * 获取商品对象工程方法
 */
class CommodityFactory {

    public static Commodity getCommodityFactory(String commodityType) {

        Commodity commodity = null;

        if ("freshCommodity".equals(commodityType)) {
            commodity = new FreshCommodity();
        } else if ("gameCommodity".equals(commodityType)) {
            commodity = new GameCommodity();
        }

        return commodity;
    }

}
