ext.apply {
//    Kotlin
    val coroutinesVersion = "1.7.3"
    set(
        "coroutinesCore",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    )
    set("serialization", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    set("datastore", "androidx.datastore:datastore:1.0.0")

//    Android Platform
    val navigationVersion = "2.7.2"
    val daggerHiltVersion = "2.48"
    set("daggerHiltVersion", daggerHiltVersion)
    set("cardStackView", "com.yuyakaido.android:card-stack-view:2.3.4")
    set("insetter", "dev.chrisbanes.insetter:insetter:0.6.1")
    set("androidCore", "androidx.core:core-ktx:1.12.0")
    set("appcompact", "androidx.appcompat:appcompat:1.6.1")
    set("material", "com.google.android.material:material:1.9.0")
    set("constraintLayout", "androidx.constraintlayout:constraintlayout:2.1.4")
    set(
        "coroutinesAndroid",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    )
    set("navigationFragment", "androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    set("navigationUi", "androidx.navigation:navigation-ui-ktx:$navigationVersion")
    set("daggerHilt", "com.google.dagger:hilt-android:$daggerHiltVersion")
    set("daggerHiltCompiler", "com.google.dagger:hilt-android-compiler:$daggerHiltVersion")

//    Data

//    Android Testing
    set("espressoCore", "androidx.test.espresso:espresso-core:3.5.1")
    set("androidJunit", "androidx.test.ext:junit:1.1.5")

//    Testing
    set("junit", "junit:junit:4.13.2")
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.2")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.0")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}