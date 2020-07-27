plugins {
    id("org.jetbrains.kotlin.js") version "1.3.72"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    //React, React DOM
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.110-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.110-kotlin-1.3.72")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))

    // sql.js
    implementation(npm("sql.js"))
    implementation(npm("copy-webpack-plugin"))

    // css
    implementation(npm("style-loader"))
    implementation(npm("css-loader"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
}

kotlin {
    target {
        browser {}
        useCommonJs()
    }
}