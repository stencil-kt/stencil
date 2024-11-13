import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.task
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.Platform
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.jvm.tasks.Jar

// Lets us use `=` for assignments
private fun <T> Property<T>.assign(value: T) = set(value)

fun Project.setupAndroid(name: String) {
    val androidExtension: LibraryExtension = extensions.findByType<LibraryExtension>()
        ?: error("Could not found Android library plugin applied on module $name")

    androidExtension.apply {
        namespace = "xyz.wingio.${name.replace("-", ".")}"
        compileSdk = 35
        defaultConfig {
            minSdk = 21
        }
    }
}

@Suppress("UnstableApiUsage")
fun Project.setup(
    libName: String,
    moduleName: String,
    moduleDescription: String,
    androidOnly: Boolean = false
) {
    setupAndroid(moduleName)

    val mavenPublishing = extensions.findByType<MavenPublishBaseExtension>() ?: error("Couldn't find maven publish plugin")

    mavenPublishing.apply {
        if(androidOnly) {
            configure(AndroidSingleVariantLibrary("release"))
        } else {
            configure(KotlinMultiplatform(JavadocJar.Empty()))
        }
        publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
        signAllPublications()

        coordinates("xyz.wingio.stencil", moduleName, System.getenv("LIBRARY_VERSION") ?: version.toString())

        pom {
            name = libName
            description = moduleDescription
            inceptionYear = "2024"
            url = "https://github.com/wingio/stencil"

            licenses {
                license {
                    name = "MIT License"
                    url = "https://opensource.org/license/mit/"
                }
            }
            developers {
                developer {
                    id = "wingio"
                    name = "Wing"
                    url = "https://wingio.xyz"
                }
            }
            scm {
                url = "https://github.com/wingio/stencil"
                connection = "scm:git:github.com/wingio/stencil.git"
                developerConnection = "scm:git:ssh://github.com/wingio/stencil.git"
            }
        }
    }
}