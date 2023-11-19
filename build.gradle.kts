// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id("com.android.application") version "8.1.3" apply false
//    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
//}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
    }
}

plugins {
    id("com.diffplug.spotless") version "6.4.1"
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

//spotless {
//    kotlin {
//        target("**/*.kt")
//        ktlint("0.40.0").userData(mapOf("max_line_length" to "100"))
//    }
//}