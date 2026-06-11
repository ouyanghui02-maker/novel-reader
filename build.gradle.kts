buildscript {
    extra.apply {
        set("compose_version", "1.6.0")
        set("hilt_version", "2.50")
        set("room_version", "2.6.1")
        set("retrofit_version", "2.9.0")
        set("okhttp_version", "4.12.0")
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}