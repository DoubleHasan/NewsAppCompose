package com.example.newsapp.di

import com.newsapp2.core.NewsInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient(): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                addInterceptor(NewsInterceptor())
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
//
//    private const val API_KEY = "9424855db1824f37ac3dd48103fa829d"
//
//    private val Api_Key_Plugin = createClientPlugin("ApiKeyPlugin") {
//        onRequest { request, _ ->
//            request.url.parameters.append("apiKey", API_KEY)
//        }
//    }
}