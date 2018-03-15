package com.jp.jpush;

import java.util.Collections;

public class Test {
    public static void main(String[] args) {
        PushUtils.push( "110" ,"IOS信息发送测试",null);
//        PushUtils.push( Collections.singletonList( "110" ),"IOS信息发送测试",null);
        PushUtils.pushByTags( "120" ,"IOS标签组测试",null );
//        PushUtils.pushByTags( Collections.singletonList( "120" ),"IOS标签组测试",null );
//        PushUtils.pushToAll( "IOS全体推送测试" ,null);
    }
}
