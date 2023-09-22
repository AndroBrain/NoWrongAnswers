plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
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

//    Insets
    implementation(rootProject.extra.get("insetter") as String)

//    Card Stack
    implementation(rootProject.extra.get("cardStackView") as String)

//    Navigation
    implementation(rootProject.extra.get("navigationFragment") as String)
    implementation(rootProject.extra.get("navigationUi") as String)

//    Dagger Hilt
    implementation(rootProject.extra.get("daggerHilt") as String)
    ksp(rootProject.extra.get("daggerHiltCompiler") as String)

    implementation(rootProject.extra.get("androidCore") as String)
    implementation(rootProject.extra.get("appcompact") as String)
    implementation(rootProject.extra.get("material") as String)
    implementation(rootProject.extra.get("constraintLayout") as String)
    testImplementation(rootProject.extra.get("junit") as String)
    androidTestImplementation(rootProject.extra.get("espressoCore") as String)
    androidTestImplementation(rootProject.extra.get("androidJunit") as String)
}
