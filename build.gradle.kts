import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

fun getMapkitApiKey(): String {
    val properties = Properties()
    project.file("local.properties").inputStream().use { properties.load(it) }
    return properties.getProperty("MAPKIT_API_KEY", "")
}
