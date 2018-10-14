package com.server.designpatterns.designpatterns01.impl;

import com.server.designpatterns.designpatterns01.inter.KindWomenInterface;

/**
 * 定义潘金莲，实现KindWomenInterface接口
 *
 * @author CYX
 * @create 2018-06-24-18:58
 */
public class PanJinLianImpl implements KindWomenInterface {

    @Override
    public void makeEyesWithMan() {
        System.out.println("潘金莲和man眉来眼去...");
    }

    @Override
    public void happyWithMan() {
        System.out.println("潘金莲和man出去happy...");
    }
}
