plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("androidx.room")
}

android {
    namespace = "com.example.recipefinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.recipefinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true"
                    )
                )
            }
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation (libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // AdapterDelegates
    implementation (libs.hannesdorfmann.adapterdelegates4.kotlin.dsl)
    implementation (libs.adapterdelegates4.kotlin.dsl.viewbinding)
    implementation (libs.delegateadapter)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)

    // Dagger
    implementation (libs.dagger)
    kapt (libs.google.dagger.compiler)
    implementation(libs.javax.inject)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Glide
    implementation (libs.glide)

    // Room

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // Annotation processor
    ksp(libs.androidx.lifecycle.compiler)

    // retrofit
    implementation (libs.retrofit)
    // gson converter
    implementation (libs.retrofit2.converter.gson)
    implementation (libs.logging.interceptor)

    //Cicerone
    implementation(libs.cicerone)

    // OkHttp
    implementation (libs.okhttp3.okhttp)
    implementation (libs.logging.interceptor)
}
kapt{
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
    }
    correctErrorTypes = true
}
