plugins {
    glass(JAVA)
    glass(PUBLISHING)
    glass(SIGNING)
    spotless(GRADLE)
    spotless(JAVA)
    alias(libs.plugins.jreleaser)
}

group = "team.idealstate.sugar"
version = "0.1.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.AZUL)
    }
}

glass {
    release.set(8)

    application {
        agent {
            val mainClass = "team.idealstate.sugar.agent.Javaagent"
            premain.set(mainClass)
            agentmain.set(mainClass)
            canRedefineClasses.set(true)
            canRetransformClasses.set(true)
            canSetNativeMethodPrefix.set(true)
        }
    }

    withCopyright()
    withMavenPom()

    withSourcesJar()
    withJavadocJar()

    withInternal()
    withShadow()

    withJUnitTest()
}

repositories {
    mavenLocal()
    aliyun()
    sonatype()
    sonatype(SNAPSHOT)
    maven {
        url = uri("https://maven.nustar.top/repository/nustar-public/")
    }
    mavenCentral()
}

dependencies {
    compileOnly(java(project, "tools"))

    internal(libs.asm)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

publishing {
    repositories {
        project(project)
        maven {
            name = "nustar-snapshots"
            url = uri("https://maven.nustar.top/repository/nustar-snapshots/")
            properties(project).login()
        }
    }
}
