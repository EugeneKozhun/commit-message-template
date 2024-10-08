import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Versions
val mockkVersion = "1.13.12"
val junitVersion = "5.11.0"

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.20"
    id("org.jetbrains.intellij") version "1.17.4"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

group = "com.kozhun"
version = "1.9.0"

sourceSets["main"].java.srcDirs("src/main/gen")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
}

intellij {
    version.set("2023.1.5")
    type.set("IC")

    plugins.set(
        listOf(
            "Git4Idea"
        )
    )
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("243.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

detekt {
    source.setFrom("src/main/kotlin")
    config.setFrom("detekt-config.yml")
    buildUponDefaultConfig = true
}
