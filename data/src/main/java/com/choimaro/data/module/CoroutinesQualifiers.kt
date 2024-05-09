package com.choimaro.data.module

import javax.inject.Qualifier

class CoroutinesQualifiers {
    @Retention(AnnotationRetention.RUNTIME) // Scope를 제한하는데 사용되는 annotation
    @Qualifier // Autowiring conflict를 해결하기 위해 사용되는 annotation
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class IoDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class MainDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MainImmediateDispatcher
}