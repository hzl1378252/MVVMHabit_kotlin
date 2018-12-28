package com.goldze.mvvmhabit.service

import com.goldze.mvvmhabit.entity.DemoEntity

import io.reactivex.Observable
import me.goldze.mvvmhabit.http.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by 曾宪泽 on 2017/6/15.
 */

interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    fun demoGet(): Observable<BaseResponse<DemoEntity>>

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    fun demoPost(@Field("catalog") catalog: String): Observable<BaseResponse<DemoEntity>>
}
