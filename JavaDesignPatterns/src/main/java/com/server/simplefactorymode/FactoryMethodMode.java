package com.server.simplefactorymode;

/**
 * 工厂方法模式
 *
 * @author CYX
 * @date: 2018/12/25 17:52
 */
public class FactoryMethodMode {

    public static void main(String[] args) {

        //我要购买国产车和国产鱼
        ShopHeadOffice domesticStore = new DomesticStore();
        domesticStore.getCommodityHandle("DomesticCommodityCar").sayCommodityName();
        domesticStore.getCommodityHandle("DomesticCommodityFish").sayCommodityName();

        //他购买进口车和进口鱼
        ShopHeadOffice foreignStore = new ForeignStore();
        foreignStore.getCommodityHandle("ForeignCommodityCar").sayCommodityName();
        foreignStore.getCommodityHandle("ForeignCommodityFish").sayCommodityName();
    }

}

/**
 * 商品-抽象类
 */
abstract class AbstractCommodity {
    //商品名称
    protected String commodityName;

    public void sayCommodityName() {
        System.out.println(commodityName);
    }
}

/**
 * 国产车
 */
class DomesticCommodityCar extends AbstractCommodity {
    public DomesticCommodityCar() {
        commodityName = "国产车";
    }
}

/**
 * 国产鱼
 */
class DomesticCommodityFish extends AbstractCommodity {
    public DomesticCommodityFish() {
        commodityName = "国产鱼";
    }
}

/**
 * 进口车
 */
class ForeignCommodityCar extends AbstractCommodity {
    public ForeignCommodityCar() {
        commodityName = "进口车";
    }
}

/**
 * 进口鱼
 */
class ForeignCommodityFish extends AbstractCommodity {
    public ForeignCommodityFish() {
        commodityName = "进口鱼";
    }
}

/**
 * 商品总店
 * <p>
 * 创建商品的操作，由各个分店去完成
 */
abstract class ShopHeadOffice {

    /**
     * 获取商品
     *
     * @param commodityType
     * @return
     */
    public AbstractCommodity getCommodityHandle(String commodityType) {
        return createCommdity(commodityType);
    }

    /**
     * 具体实现获取商品，交给各个分店去实现
     *
     * @param commodityType
     * @return
     */
    abstract AbstractCommodity createCommdity(String commodityType);

}

/**
 * 国内分店
 */
class DomesticStore extends ShopHeadOffice {
    @Override
    AbstractCommodity createCommdity(String commodityType) {
        if ("DomesticCommodityCar".equals(commodityType)) {
            return new DomesticCommodityCar();
        } else if ("DomesticCommodityFish".equals(commodityType)) {
            return new DomesticCommodityFish();
        }
        return null;
    }
}

/**
 * 国外分店
 */
class ForeignStore extends ShopHeadOffice {
    @Override
    AbstractCommodity createCommdity(String commodityType) {
        if ("ForeignCommodityCar".equals(commodityType)) {
            return new ForeignCommodityCar();
        } else if ("ForeignCommodityFish".equals(commodityType)) {
            return new ForeignCommodityFish();
        }
        return null;
    }
}
