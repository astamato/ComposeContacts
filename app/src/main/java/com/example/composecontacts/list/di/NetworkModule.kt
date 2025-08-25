package com.example.composecontacts.list.di

import com.example.composecontacts.core.network.KtorReqResService
import com.example.composecontacts.core.network.ReqResService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideHttpClient(): HttpClient =
    HttpClient(OkHttp) {
      install(ContentNegotiation) {
        json(
          Json {
            ignoreUnknownKeys = true
            isLenient = true
          }
        )
      }
      install(Logging) {
        logger = object : Logger {
          override fun log(message: String) {
            android.util.Log.d("Ktor", message)
          }
        }
        level = LogLevel.INFO
      }
    }

  @Provides
  @Singleton
  fun provideReqResService(httpClient: HttpClient): ReqResService = KtorReqResService(httpClient)
}
