package me.lazy_assedninja.plugins

object AndroidX {
    val appCompat = "androidx.appcompat:appcompat:${DependenciesVersions.appCompat}"
    val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${DependenciesVersions.constraintLayout}"
    val navigationFragmentKotlin =
        "androidx.navigation:navigation-fragment-ktx:${DependenciesVersions.navigation}"
    val navigationUIKotlin =
        "androidx.navigation:navigation-ui-ktx:${DependenciesVersions.navigation}"
    val recyclerview = "androidx.recyclerview:recyclerview:${DependenciesVersions.recyclerview}"
    val core = "androidx.core:core-ktx:${DependenciesVersions.core}"
    val lifecycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${DependenciesVersions.lifecycle}"

    val archCoreTesting = "androidx.arch.core:core-testing:${DependenciesVersions.archCore}"
    val testCore = "androidx.test:core-ktx:${DependenciesVersions.testCore}"
    val testJunit = "androidx.test.ext:junit:${DependenciesVersions.testJunit}"
    val testEspresso = "androidx.test.espresso:espresso-core:${DependenciesVersions.testEspresso}"
}