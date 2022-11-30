package xyz.gravitychain.gravitysdk.retrofit

import xyz.gravitychain.gravitysdk.data.GravityContractCallRequest
import xyz.gravitychain.gravitysdk.data.GravityContractCallResponse
import xyz.gravitychain.gravitysdk.data.GravityRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface GravityAPI {
    @POST("/balanceOf")
    suspend fun balanceOf(@Body gravityRequest: GravityRequest): Result<GravityContractCallResponse>

    @POST("/contract")
    suspend fun contract(@Body gravityContractCallRequest: GravityContractCallRequest): Result<GravityContractCallResponse>
}