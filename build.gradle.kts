buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("tech.skot:plugin:1.0.0-alpha17")
    }
}

allprojects {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://raw.github.com/MathieuScotet/skot/repository")
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }

}
