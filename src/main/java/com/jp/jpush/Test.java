package com.jp.jpush;

import java.util.Collections;

public class Test {
    public static void main(String[] args) {
        PushUtils.push( Collections.singletonList( "123" ),"信息发送测试",null);
        PushUtils.pushByTags( Collections.singletonList( "tagX" ),"标签组测试",null );
        PushUtils.pushToAll( "全体推送测试" ,null);
    }
}
