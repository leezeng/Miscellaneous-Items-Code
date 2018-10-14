package com.server.designpatterns.designpatterns01.impl;

import com.server.designpatterns.designpatterns01.inter.KindWomenInterface;

/**
 * 定义王婆，实现KindWomenInterface接口
 * 王婆人老、聪明，作为代理...
 *
 * @author CYX
 * @create 2018-06-24-19:00
 */
public class WangPoImpl implements KindWomenInterface {

    private KindWomenInterface kindWomenInterface;

    /**
     * 王婆实现类的默认构造方法，是潘金莲的代理
     */
    public WangPoImpl() {
        this.kindWomenInterface = new PanJinLianImpl();
    }

    /**
     * 王婆实现类的有参构造方法，她可以是KindWomenInterface类型的任何女人的代理
     */
    public WangPoImpl(KindWomenInterface kindWomenInterface) {
        this.kindWomenInterface = kindWomenInterface;
    }

    @Override
    public void makeEyesWithMan() {
        //并不是王婆，而是让别人，比如潘金莲
        this.kindWomenInterface.makeEyesWithMan();
    }

    @Override
    public void happyWithMan() {
        //并不是王婆，而是让别人，比如潘金莲
        this.kindWomenInterface.happyWithMan();
    }
}
