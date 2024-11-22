plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.vanniktech.maven.publish.base")
    alias(libs.plugins.binary.compatibility)
}

setup(
    libName = "Stencil Core",
    moduleName = "stencil-core",
    moduleDescription = "Core functionality for the Stencil libraries"
)

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }
    jvm()

    jvmToolchain(17)
    explicitApi()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }

        commonTest.dependencies {
            implementation(libs.junit)
        }
    }
}
