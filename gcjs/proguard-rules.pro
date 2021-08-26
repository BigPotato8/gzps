# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class *
-keep class * extends RealmObject {*;}
-keep class * implements Serializable {*;}
-keep class * implements Parcelable {*;}

-keepclassmembers class * {
    public protected *;
}

-dontwarn java.lang.invoke.**

#agwater5
-dontwarn com.augurit.agmobile.busi.bpm.widget.view.AGOcrInput
-dontwarn com.augurit.agmobile.busi.bpm.widget.view.AGQrCodeInput
-keep class com.augurit.agmobile.agwater5.common.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.datarepair.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.eventlist.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.journal.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.patroldynamic.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.problem.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.publicaffair.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.track.model.** {*;}
-keep class com.augurit.agmobile.agwater5.drainage.uploadfacility.model.** {*;}
-keep class com.augurit.agmobile.agwater5.flood.dispatch.model.** {*;}
-keep class com.augurit.agmobile.agwater5.flood.monitor.model.** {*;}
-keep class com.augurit.agmobile.agwater5.im.model.** {*;}
-keep class com.augurit.agmobile.agwater5.mine.sign.model.** {*;}
-keep class com.augurit.agmobile.agwater5.psh.doorno.model.** {*;}
-keep class com.augurit.agmobile.agwater5.psh.well.model.** {*;}
-keep class com.augurit.agmobile.agwater5.sewage.cuberun.model.** {*;}
-keep class com.augurit.agmobile.agwater5.sewage.publicaffair.model.** {*;}
-keep class com.augurit.agmobile.agwater5.statistics.model.** {*;}

#rongim
-keepattributes Exceptions,InnerClasses
-keepattributes Signature
-keep class io.rong.** {*;}
-keep class cn.rongcloud.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent {*;}
-dontwarn io.rong.push.**
-dontnote com.xiaomi.**
-dontnote com.google.android.gms.gcm.**
-dontnote io.rong.**
-keep class io.agora.rtc.** {*;}
-keep class com.amap.api.**{*;}
-keep class com.amap.api.services.**{*;}
-keep class com.google.gson.** { *; }
-keep class com.uuhelper.Application.** {*;}
-keep class net.sourceforge.zbar.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.alipay.** {*;}
-keep class com.jrmf360.rylib.** {*;}
-ignorewarnings

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#common-lib
-keep class com.augurit.agmobile.common.lib.baidu.BaiduGeocodeResult {*;}
-keep class com.augurit.agmobile.common.lib.coordt.model.** {*;}
-keep class com.augurit.agmobile.common.lib.location.DetailAddress {*;}
-keep class com.augurit.agmobile.common.lib.model.FileBean {*;}
-keep class com.augurit.agmobile.common.lib.net.model.** {*;}

#common-view
-keep class android.databinding.** {*;}
-dontwarn cn.bingoogolapple.**
-keep class com.augurit.agmobile.busi.bpm.common.model.** {*;}
-keep class com.augurit.agmobile.common.view.filepicker.bean.** {*;}
-keep class com.augurit.agmobile.common.view.treeview.model.** {*;}

#common-arcgis
-keep class com.augurit.agmobile.common.arcgis.model.** {*;}
-keep class com.augurit.agmobile.common.arcgis.customlayer.model.** {*;}
-keep class com.augurit.agmobile.common.arcgis.util.wktutil.** {*;}
-keep class com.augurit.agmobile.common.arcgis.widget.geometrySelector.model.** {*;}

-keep class com.esri.** { *; }
-keep interface com.esri.** { *; }
-keep class org.codehaus.jackson.** { *; }
-dontwarn org.codehaus.jackson.map.ext.**
-dontwarn jcifs.http.**

-keep class * extends com.esri.**

-keep class com.esri.android.map.ags.AGFeatureLayer {*;}
-keep class com.esri.android.toolkit.map.AMSketchLayer {*;}
-dontwarn com.esri.android.map.ags.AGFeatureLayer
-dontwarn com.esri.android.toolkit.map.AMSketchLayer

#business-common
-keep class com.augurit.agmobile.busi.common.login.model.** {*;}
-keep class com.augurit.agmobile.busi.common.organization.model.** {*;}
-keep class com.augurit.agmobile.busi.common.update.model.** {*;}

#business-arcgis
-keep class com.augurit.agmobile.busi.map.bookmark.model.** {*;}
-keep class com.augurit.agmobile.busi.map.common.model.** {*;}
-keep class com.augurit.agmobile.busi.map.layerquery.model.** {*;}
-keep class com.augurit.agmobile.busi.map.map.model.** {*;}
-keep class com.augurit.agmobile.busi.map.mark.model.** {*;}

#business-ui
-keep class com.augurit.agmobile.busi.ui.mark.model.** {*;}

#business-bpm
-keep class com.augurit.agmobile.busi.bpm.common.model.** {*;}
-keep class com.augurit.agmobile.busi.bpm.dict.model.** {*;}
-keep class com.augurit.agmobile.busi.bpm.form.model.** {*;}
-keep class com.augurit.agmobile.busi.bpm.record.model.** {*;}
-keep class com.augurit.agmobile.busi.bpm.view.model.** {*;}
-keep class com.augurit.agmobile.busi.bpm.workflow.model.** {*;}

#lib-baidumap
-keep class com.augurit.agmobile.lib.baidumap.location.model.** {*;}
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**

#lib-baiduvoice
-keep class com.baidu.speech.**{*;}

#lib-im
-dontwarn demo.**
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**
-keep class tencent.**{*;}
-dontwarn tencent.**
-keep class qalsdk.**{*;}
-dontwarn qalsdk.**
-keep class com.augurit.agmobile.common.im.timchat.utils.MiPushMessageReceiver {*;}
-dontwarn com.xiaomi.push.**
-dontwarn com.huawei.**


#lib-ocridcard


#lib-qrcode

#etc
-dontwarn com.thoughtworks.**
-dontwarn net.sf.**
-dontwarn com.wintone.cipher.**
-dontwarn com.kernal.lisence.**


-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-dontwarn com.facebook.**
-dontwarn com.google.common.**

-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature-keepattributes
-keep class io.haobi.wallet.network.** { *; }
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.-KotlinExtensions
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
