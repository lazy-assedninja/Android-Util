package me.lazy_assedninja.plugins

@Suppress("unused")
object ThirdParty {

    const val GLIDE = "com.github.bumptech.glide:glide:${DependenciesVersions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${DependenciesVersions.GLIDE}"
    const val TIMBER = "com.jakewharton.timber:timber:${DependenciesVersions.TIMBER}"

    // Test
    const val JUNIT = "junit:junit:${DependenciesVersions.JUNIT}"
    const val MOCKITO_CORE = "org.mockito:mockito-core:${DependenciesVersions.MOCKITO}"
    const val MOCKITO_ANDROID = "org.mockito:mockito-android:${DependenciesVersions.MOCKITO}"
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${DependenciesVersions.MOCK_WEB_SERVER}"
    const val ROBOLECTRIC = "org.robolectric:robolectric:${DependenciesVersions.ROBOLECTRIC}"
}