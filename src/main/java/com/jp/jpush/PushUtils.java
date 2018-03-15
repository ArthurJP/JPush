package com.jp.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"deprecation", "restriction"})
public class PushUtils {
    private static final Logger log = LoggerFactory.getLogger( PushUtils.class );
    private static String masterSecret = "bcc44cde7082be22cdfd4c1b";
    private static String appKey = "a8952762678fb35173fd9130";

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     */
    private static PushPayload buildPushObject_android_ios_alias_alert(List<String> alias, String alert, Map<String, String> extras) {
        return PushPayload.newBuilder()
                .setPlatform( Platform.android_ios() )
                .setAudience( Audience.alias( alias ) )
                .setNotification( Notification.newBuilder()
                        .addPlatformNotification( AndroidNotification.newBuilder()
                                .addExtras( extras )
                                .setAlert( alert )
                                .build() )
                        .addPlatformNotification( IosNotification.newBuilder()
                                .addExtras( extras )
                                .setAlert( alert )
                                .build() )
                        .build() )
                .setOptions( Options.newBuilder()
                        .setApnsProduction( false )//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive( 90 )//消息在JPush服务器的失效时间（测试使用参数）
                        .build() )
                .build();
    }

    private static PushPayload buildPushObject_android_ios_tags_alert(List<String> tag, String alert, Map<String, String> extras) {
        return PushPayload.newBuilder()
                .setPlatform( Platform.android_ios() )
                .setAudience( Audience.tag( tag ) )
                .setNotification( Notification.newBuilder()
                        .addPlatformNotification( AndroidNotification.newBuilder()
                                .addExtras( extras )
                                .setAlert( alert )
                                .build() )
                        .addPlatformNotification( IosNotification.newBuilder()
                                .addExtras( extras )
                                .setAlert( alert )
                                .build() )
                        .build() )
                .setOptions( Options.newBuilder()
                        .setApnsProduction( false )//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive( 90 )//消息在JPush服务器的失效时间（测试使用参数）
                        .build() )
                .build();
    }

    private static PushPayload buildPushObject_android_ios_all_alert(String alert, Map<String, String> extras) {
        return PushPayload.newBuilder()
                .setPlatform( Platform.android_ios() )
                .setAudience( Audience.all() )
                .setNotification( Notification.newBuilder()
                        .addPlatformNotification( AndroidNotification.newBuilder()
                                .addExtras( extras )
                                .setAlert( alert )
                                .build() )
                        .addPlatformNotification( IosNotification.newBuilder()
                                .addExtras( extras )
                                .setAlert( alert )
                                .build() )
                        .build() )
                .setOptions( Options.newBuilder()
                        .setApnsProduction( false )//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive( 90 )//消息在JPush服务器的失效时间（测试使用参数）
                        .build() )
                .build();
    }

    /**
     * 单点推送方法(采用java SDK)
     */

    public static PushResult push(List<String> alias, String alert, Map<String, String> extras) {
        if (CheckUtils.isValidTagsOrAlias( alias )) {
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient( masterSecret, appKey, null, clientConfig );
            PushPayload payload = buildPushObject_android_ios_alias_alert( alias, alert, extras );
            return getPushResult( jpushClient, payload );
        }
        log.info( "非法的alias/tags" );
        return null;
    }

    public static PushResult push(String alia, String alert, Map<String, String> extras) {
        if (CheckUtils.isValidTagsOrAlias( alia )) {
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient( masterSecret, appKey, null, clientConfig );
            PushPayload payload = buildPushObject_android_ios_alias_alert( Collections.singletonList( alia ), alert, extras );
            return getPushResult( jpushClient, payload );
        }
        log.info( "非法的alias/tags" );
        return null;
    }


    /**
     * 标签推送（范围）
     */
    public static PushResult pushByTags(String tag, String alert, Map<String, String> extras) {
        if (CheckUtils.isValidTagsOrAlias( tag )) {
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient( masterSecret, appKey, null, clientConfig );
            PushPayload payload = buildPushObject_android_ios_tags_alert( Collections.singletonList( tag ), alert, extras );
            return getPushResult( jpushClient, payload );
        }
        return null;
    }

    public static PushResult pushByTags(List<String> tags, String alert, Map<String, String> extras) {
        if (CheckUtils.isValidTagsOrAlias( tags )) {
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient( masterSecret, appKey, null, clientConfig );
            PushPayload payload = buildPushObject_android_ios_tags_alert( tags, alert, extras );
            return getPushResult( jpushClient, payload );
        }
        return null;
    }

    /**
     * 全部推送
     */
    public static PushResult pushToAll(String alert, Map<String, String> extras) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient( masterSecret, appKey, null, clientConfig );
        PushPayload payload = buildPushObject_android_ios_all_alert( alert, extras );
        return getPushResult( jpushClient, payload );
    }

    private static PushResult getPushResult(JPushClient jpushClient, PushPayload payload) {
        try {
            return jpushClient.sendPush( payload );
        } catch (APIConnectionException e) {
            log.error( "Connection error. Should retry later. ", e );
            return null;
        } catch (APIRequestException e) {
            log.error( "Error response from JPush server. Should review and fix it. ", e );
            log.info( "HTTP Status: " + e.getStatus() );
            log.info( "Error Code: " + e.getErrorCode() );
            log.info( "Error Message: " + e.getErrorMessage() );
            log.info( "Msg ID: " + e.getMsgId() );
            return null;
        }
    }

}
