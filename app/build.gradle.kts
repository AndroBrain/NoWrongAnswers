plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.androbrain"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.androbrain"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions += "variant"

    productFlavors {
        create("noWrongAnswers") {
            dimension = "variant"
            applicationIdSuffix = ".no.wrong.answers"
        }
    }

    sourceSets.getByName("noWrongAnswers") {
        java.srcDir("src/noWrongAnswers/java")
        res.srcDir("src/noWrongAnswers/res")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
//    Android Core
    implementation(rootProject.extra.get("androidCore") as String)
    implementation(rootProject.extra.get("appcompact") as String)
    implementation(rootProject.extra.get("material") as String)
    implementation(rootProject.extra.get("constraintLayout") as String)

//    Insets
    implementation(rootProject.extra.get("insetter") as String)

//    Card Stack
    implementation(rootProject.extra.get("cardStackView") as String)

//    Navigation
    implementation(rootProject.extra.get("navigationFragment") as String)
    implementation(rootProject.extra.get("navigationUi") as String)

//    Dagger Hilt
    implementation(rootProject.extra.get("daggerHilt") as String)
    implementation("com.google.android.play:review-ktx:2.0.1")
    ksp(rootProject.extra.get("daggerHiltCompiler") as String)

//    Complex Recycler
    implementation("com.airbnb.android:epoxy:5.1.3")

//    Data storage
    implementation(rootProject.extra.get("datastore") as String)

//    Serialization
    implementation(rootProject.extra.get("serialization") as String)

    testImplementation(rootProject.extra.get("junit") as String)
    androidTestImplementation(rootProject.extra.get("espressoCore") as String)
    androidTestImplementation(rootProject.extra.get("androidJunit") as String)
}
