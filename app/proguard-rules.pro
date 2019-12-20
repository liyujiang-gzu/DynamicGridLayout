#################
#项目自定义混淆配置
#################

# Android支持包
-dontwarn android.**
-keep class android.** { *; }
-keep interface androidx.** { *; }
-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep public class * extends androidx.**
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-keep class com.google.android.material.** { *; }

# 保持R文件不被混淆，否则你的反射是获取不到资源的
-keep class **.R$* { *; }
-keep class **.R$** { *; }

# Apache HttpClient (如QQ互联会用到)
-dontwarn org.apache.http.**
-keep class org.apache.http.** { *; }

# 微信SDK && QQ SDK
-dontwarn com.tencent.**
-keep class com.tencent.** {  *; }
-keep interface com.tencent.** {  *; }

# gson && protob
-dontwarn com.google.gson.**
-dontwarn com.google.protobuf.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.protobuf.** {*;}

# okhttp && okio
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# sqlite
-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }
