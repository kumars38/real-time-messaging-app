plugins {
    alias(libs.plugins.androidApplication)
    //id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.messagingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.messagingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core);

    //Import Firebase
    implementation ("com.google.firebase:firebase-core:16.0.8");
    implementation ("com.google.firebase:firebase-inappmessaging:17.0.3");
    implementation(libs.firebase.messaging)
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
}
