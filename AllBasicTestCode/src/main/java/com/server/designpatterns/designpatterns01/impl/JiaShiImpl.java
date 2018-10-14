package com.server.designpatterns.designpatterns01.impl;

import com.server.designpatterns.designpatterns01.inter.KindWomenInterface;

/**
 * 水浒中的贾氏
 *
 * @author CYX
 * @create 2018-06-24-19:07
 */
public class JiaShiImpl implements KindWomenInterface {

    @Override
    public void makeEyesWithMan() {
        System.out.println("贾氏和man眉来眼去...");
    }

    @Override
    public void happyWithMan() {
        System.out.println("贾氏和man出去happy...");
    }
}
