import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
//    kotlin("plugin.serialization") version "1.9.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
    id("app.cash.sqldelight") version "2.0.1"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    //Step1
    js(IR) {
        moduleName ="GameList"
        browser() {
            //Tool bundler for converting kotlin code to js code
            commonWebpackConfig() {
                outputFileName ="GameList.js"
                devServer = (devServer
                    ?: org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer()).copy()
            }
            binaries.executable() // it will generate executable js files
        }

    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName ="ComposeApp"
            isStatic = true

            export("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            export("com.arkivanov.essenty:lifecycle:1.3.0")
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            //decompose step3
            implementation ("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            implementation ("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)


            //https://github.com/Kotlin/kotlinx.serialization
            implementation(libs.kotlinx.serialization.json)

            //https://github.com/Kotlin/kotlinx.coroutines
            implementation(libs.kotlinx.coroutines.core)


            //https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-ktor-sqldelight.html#add-dependencies-to-the-multiplatform-library

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            //https://github.com/icerockdev/moko-mvvm
            implementation(libs.mvvm.core)

            //https://github.com/qdsfdhvh/compose-imageloader
            api(libs.image.loader)

            implementation ("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            implementation ("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")

            //koin step1
            implementation("io.insert-koin:koin-core:3.5.3")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)

            //koin step2
            implementation("io.insert-koin:koin-android:3.5.3")

            implementation("app.cash.sqldelight:android-driver:2.0.1")

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            api("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            api("com.arkivanov.essenty:lifecycle:1.3.0")

            implementation("app.cash.sqldelight:native-driver:2.0.1")
        }

        jsMain.dependencies {


            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.1"))
            implementation(npm("sql.js", "1.8.0"))
            implementation("app.cash.sqldelight:web-worker-driver:2.0.1")
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }

    }
}

android {
    namespace ="org.thiago.gamelist"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId ="org.thiago.GameList"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.palette.ktx)
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.thiago.gamelist"
            packageVersion = "1.0.0"
        }
    }
}
//Step3
compose.experimental {
    web.application {}
}

//sqldelight step2
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.thiago.gamelist")
            generateAsync.set(true)
        }
    }
}