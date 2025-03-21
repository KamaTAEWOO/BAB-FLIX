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

# This is generated automatically by the Android Gradle plugin.
-dontwarn android.os.ServiceManager*
-dontwarn com.bun.miitmdid.core.MdidSdkHelper*
-dontwarn com.bun.miitmdid.interfaces.IIdentifierListener*
-dontwarn com.bun.miitmdid.interfaces.IdSupplier*
-dontwarn com.google.firebase.iid.FirebaseInstanceId*
-dontwarn com.google.firebase.iid.InstanceIdResult*
-dontwarn com.huawei.hms.ads.identifier.AdvertisingIdClient$Info*
-dontwarn com.huawei.hms.ads.identifier.AdvertisingIdClient*
-dontwarn com.tencent.android.tpush.otherpush.OtherPushClient*

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class org.bouncycastle.jsse.** { *; }
-keep class org.bouncycastle.jsse.provider.** { *; }
-keep class org.conscrypt.** { *; }
-keep class org.openjsse.javax.net.ssl.** { *; }
-keep class org.openjsse.net.ssl.** { *; }

# KEEP design.theme.ThemeKt (Kotlin top-level object)
-keep class com.meronacompany.design.theme.ThemeKt { *; }

# KEEP feature.navigation.AppNavHostKt (Kotlin top-level object)
-keep class com.meronacompany.feature.navigation.AppNavHostKt { *; }

# KEEP all Composable functions (important for Compose apps)
-keep class ** {
    @androidx.compose.runtime.Composable *;
}

# Optional: Keep all classes in those packages if needed
# (useful if you use reflection or dynamic calls)
-keep class com.meronacompany.design.theme.ThemeKt { *; }
-keep class com.meronacompany.feature.navigation.AppNavHostKt { *; }

# Kotlin metadata 유지 (중요!)
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature, Exceptions, SourceFile, LineNumberTable, LocalVariableTable, LocalVariableTypeTable, RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations

# Compose 관련 기본 룰 추가
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Kotlin metadata 주석 포함
-keepclassmembers class ** {
    @kotlin.Metadata *;
}
-keep class com.meronacompany.feature.navigation.AppNavHostKt {
    public static void AppNavHost(...);
}