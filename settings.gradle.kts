pluginManagement {
	repositories {
		jcenter()   // shadow
		mavenCentral()
		maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
	}

	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "kotlin") {
				useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
			}
			if (requested.id.id == "shadow") {
				useModule("com.github.jengelman.gradle.plugins:shadow:${requested.version}")
			}
			if (requested.id.id == "junit-platform-gradle-plugin") {
				useModule("org.junit.platform:junit-platform-gradle-plugin:${requested.version}")
			}
		}
	}
}


rootProject.buildFileName = "build.gradle.kts"

include("glm")
include("glm-test")
