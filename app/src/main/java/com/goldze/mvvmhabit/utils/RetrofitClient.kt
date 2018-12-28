package com.goldze.mvvmhabit.utils

import android.content.Context
import android.text.TextUtils

import com.goldze.mvvmhabit.BuildConfig

import java.io.File
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.goldze.mvvmhabit.http.cookie.CookieJarImpl
import me.goldze.mvvmhabit.http.cookie.store.PersistentCookieStore
import me.goldze.mvvmhabit.http.interceptor.BaseInterceptor
import me.goldze.mvvmhabit.http.interceptor.CacheInterceptor
import me.goldze.mvvmhabit.http.interceptor.logging.Level
import me.goldze.mvvmhabit.http.interceptor.logging.LoggingInterceptor
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.Utils
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by goldze on 2017/5/10.
 * RetrofitClient封装单例类, 实现网络请求
 */
class RetrofitClient private constructor(url: String = baseUrl, headers: Map<String, String>? = null) {


    companion object {
        //超时时间
        private val DEFAULT_TIMEOUT = 20
        //缓存时间
        private val CACHE_TIMEOUT = 10 * 1024 * 1024
        //服务端根路径
        var baseUrl = "https://www.oschina.net/"

        private val mContext = Utils.getContext()

        private lateinit var okHttpClient: OkHttpClient
        private lateinit var retrofit: Retrofit

        val instance: RetrofitClient = SingletonHolder.INSTANCE

    }
    private var cache: Cache? = null
    private var httpCacheDirectory: File? = null

    private object SingletonHolder {
        val INSTANCE = RetrofitClient()
    }

    init {
        var url = url

        if (TextUtils.isEmpty(url)) {
            url = baseUrl
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext.cacheDir, "goldze_cache")
        }

        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory, CACHE_TIMEOUT.toLong())
            }
        } catch (e: Exception) {
            KLog.e("Could not create http cache", e)
        }

        val sslParams = HttpsUtils.sslSocketFactory
        okHttpClient = OkHttpClient.Builder()
                .cookieJar(CookieJarImpl(PersistentCookieStore(mContext)))
                //                .cache(cache)
                .addInterceptor(BaseInterceptor(headers))
                .addInterceptor(CacheInterceptor(mContext))
                .sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
                .addInterceptor(LoggingInterceptor.Builder()//构建者模式
                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build()
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build()

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the `service` interface.
     */
    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit.create(service)
    }

}
