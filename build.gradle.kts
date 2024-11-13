buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev" )
    }

    dependencies {
        classpath(libs.plugin.maven)
        classpath(libs.plugin.multiplatform.compose)
        classpath(libs.plugin.kotlin)
    }
}

allprojects {
    group = "xyz.wingio.stencil"
    version = "1.0.0-SNAPSHOT"
}