package com.thinkgem.jeesite.modules.test;

import com.thinkgem.jeesite.common.utils.Encodes;

/**
 * Created by xiaowu on 2017/2/10.
 */
public class TestEncodes {
    public static void main(String[] args) {
        System.out.println(Encodes.encodeHex("sys_default_user".getBytes()));
        System.out.println(new String(Encodes.decodeHex("4162633132337e")));
    }
}
