import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    id("kotlin") version("1.3.21") apply false
    id("shadow") version("4.0.4") apply false
    id("junit-platform-gradle-plugin") version("1.2.0") apply false
}

val moduleName = "com.github.kotlin_graphics.glm"
val kotlintest_version = "3.2.1"
val unsigned_version = "0e3cc3ec155986f2100fee03cc143cbd3dfd0d84"
val kool_version = "98ffadafd81f8d69b21bf374f54f83a8e1e539f2"
val lwjgl_version = "3.2.2-SNAPSHOT"

val os = OperatingSystem.current()!!

val lwjgl_natives = when {
    os.isWindows -> "windows"
    os.isLinux -> "linux"
    os.isMacOsX -> "macos"
    else -> TODO()
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "com.github.johnrengelman.shadow")

    // jitpack
    apply(plugin = "maven")

    group = "com.github.kotlin-graphics"

    repositories {
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://jitpack.io")
    }

    dependencies {
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib")
        "testImplementation"("io.kotlintest:kotlintest-runner-junit5:$kotlintest_version")
    }

    val sourcesJar by tasks.registering(Jar::class) {
        dependsOn("classes")

        archiveClassifier.set("sources")

        configure<KotlinProjectExtension> {
            // OLD: from sourceSets.main.allSource
            // from(sourceSets["main"].allSource)
        }
    }

    val javadocJar by tasks.registering(Jar::class) {
        val javadoc by tasks.getting(Javadoc::class)

        dependsOn(javadoc)

        archiveClassifier.set("javadoc")

        from(javadoc.destinationDir)
    }

    artifacts {
        add("archives", sourcesJar)
        add("archives", javadocJar)
    }

    // jar {
    //     inputs.property("moduleName", moduleName)
    //     manifest.attributes("Automatic-Module-Name": moduleName)
    // }
}

project(":glm") {
    apply(plugin = "java-library")

    dependencies {
        "api"("com.github.kotlin-graphics:kotlin-unsigned:$unsigned_version")
        "api"("com.github.kotlin-graphics:kool:$kool_version")

        "testImplementation"(project(":glm-test"))

        "testImplementation"("io.kotlintest:kotlintest-runner-junit5:$kotlintest_version")

        listOf("", "-glfw", "-jemalloc", "-openal", "-opengl", "-stb").forEach {
            "implementation"("org.lwjgl:lwjgl$it:$lwjgl_version")
            "implementation"("org.lwjgl:lwjgl$it:$lwjgl_version:natives-$lwjgl_natives")
        }
    }

    configure<KotlinProjectExtension> {
        // test.useJUnitPlatform()
    }

    // configure<ShadowJar> {
    //     archiveClassifier.set("all")
    // }
}

project(":glm-test") {

    dependencies {
        "implementation"(project(":glm"))

        "implementation"("io.kotlintest:kotlintest-core:$kotlintest_version")
        "implementation"("io.kotlintest:kotlintest-assertions:$kotlintest_version")
    }
}