allprojects {
  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
  }
}
plugins {
  id("com.android.application") version "8.0.2" apply false
  id("com.android.library") version "8.0.2" apply false
  id("org.jetbrains.kotlin.android") version "1.8.10" apply false
  id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
  id("com.google.dagger.hilt.android") version "2.45" apply false
}