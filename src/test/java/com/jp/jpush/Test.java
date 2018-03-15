package com.jp.jpush;

import org.apache.log4j.BasicConfigurator;

import java.util.Collections;


public class Test {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        PushUtils.push( "110" ,"单点string测试",null);
        PushUtils.push( Collections.singletonList( "110" ),"单点List测试",null);
        PushUtils.pushByTags( "120" ,"标签String测试",null );
        PushUtils.pushByTags( Collections.singletonList( "120" ),"标签List测试",null );
        PushUtils.pushToAll( "全体推送测试" ,null);
    }
}
