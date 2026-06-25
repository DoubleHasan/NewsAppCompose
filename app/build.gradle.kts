    plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.kotlin.android)
        alias(libs.plugins.kotlin.compose)
        id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
        alias(libs.plugins.kotlinKsp)
        alias(libs.plugins.hilt.android)
    }

    android {
        namespace = "com.example.newsapp"
        compileSdk = 36

        defaultConfig {
            applicationId = "com.example.newsapp"
            minSdk = 24
            targetSdk = 36
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }
        buildFeatures {
            compose = true
            buildConfig = true
        }
    }

    dependencies {
        implementation("io.ktor:ktor-client-core:3.0.0")
        implementation("io.ktor:ktor-client-cio:3.0.0")
        implementation("io.ktor:ktor-client-content-negotiation:3.0.0")
        implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")
        implementation("io.ktor:ktor-client-okhttp:3.0.0")

        implementation("io.coil-kt.coil3:coil-compose:3.0.0")
        implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.0")
        implementation("androidx.appcompat:appcompat:1.6.1")

        implementation("androidx.navigation:navigation-compose:2.8.5")

        implementation(libs.hilt.android)
        implementation(libs.androidx.navigation.common.ktx)
        implementation(libs.androidx.room.ktx)
        ksp(libs.hilt.compiler)

        implementation(project(":feature:home"))
        implementation(project(":feature:detail"))
        implementation(project(":feature:saved"))
        implementation(project(":data"))
        implementation(project(":domain"))
        implementation(project(":feature:dropdown"))
        implementation(project(":core"))

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)

        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)
    }