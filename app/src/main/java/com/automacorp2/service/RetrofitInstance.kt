package com.automacorp2.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import java.security.SecureRandom
import java.security.cert.CertificateException
import javax.net.ssl.X509TrustManager

object RetrofitInstance {
    private const val BASE_URL = "https://automacorp.devmind.cleverapps.io/api/"
    private const val API_USERNAME = "user" // Replace with actual username
    private const val API_PASSWORD = "password" // Replace with actual password

    val api: RoomsApiService by lazy {
        val client = getUnsafeOkHttpClient()
            .addInterceptor(BasicAuthInterceptor(API_USERNAME, API_PASSWORD))
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(RoomsApiService::class.java)
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            val trustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            val sslContext = SSLContext.getInstance("SSL").also {
                it.init(null, arrayOf(trustManager), SecureRandom())
            }
            sslSocketFactory(sslContext.socketFactory, trustManager)
            hostnameVerifier { hostname, _ -> hostname.contains("cleverapps.io") }
        }
}
