package com.example.trackerexpenses

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenFoodFactsApi {
    @GET("api/v2/search")
    suspend fun searchProducts(
        @Query("search") query: String,
        @Query("page") page: Int = 1
    ): Response<FoodSearchResponse>
}
