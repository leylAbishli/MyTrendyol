plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.mytrendyol"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mytrendyol"
        minSdk = 24
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {
      //circle imageview
    implementation ("de.hdodenhof:circleimageview:3.1.0")
      //material design
    implementation ("com.google.android.material:material:1.8.0")
      //country picker
    implementation ("com.hbb20:ccp:2.5.3")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-firestore")

    //ViewPager
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    //implementation("me.relax:circleindicator:2.1.4")
    implementation ("com.tbuonomo:dotsindicator:4.2")


    //RecyclerView (diffUtil implementation)
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    val lifecycle_version = "2.6.2"
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    // ViewModel utilities for Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    //Okhttp-logging
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")
   implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")
//
//    //Dagger2
//    val appcompat_version = "1.6.1"
//    implementation ("com.google.dagger:dagger:2.50")
//    kapt("com.google.dagger:dagger-compiler:2.50")

    //  Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")

    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("com.google.firebase:firebase-firestore:24.10.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
kapt {
    correctErrorTypes = true
}