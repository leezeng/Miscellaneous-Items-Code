package com.server.designpatterns.designpatterns01;

import com.server.designpatterns.designpatterns01.impl.JiaShiImpl;
import com.server.designpatterns.designpatterns01.impl.WangPoImpl;
import com.server.designpatterns.designpatterns01.inter.KindWomenInterface;

/**
 * 代理模式
 * 西门庆
 *
 * @author CYX
 * @create 2018-06-24-18:54
 */
public class ProxyModeApp01 {

    public static void main(String[] args) {

        //西门庆找来潘金莲
        WangPoImpl wangPo = new WangPoImpl();

        //西门庆通过王婆找到潘金莲，表面是和王婆，其实是和潘金莲
        wangPo.makeEyesWithMan();
        wangPo.happyWithMan();

        //------------------------------------------

        //改变下历史，让西门庆去找贾氏
        KindWomenInterface womenInterface = new JiaShiImpl();
        WangPoImpl wangPo2 = new WangPoImpl(womenInterface);
        wangPo2.makeEyesWithMan();
        wangPo2.happyWithMan();

    }

}
