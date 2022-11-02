package chain.gravity.gravitysdk.retrofit

import chain.gravity.gravitysdk.data.GravityContractCallRequest
import chain.gravity.gravitysdk.data.GravityContractCallResponse
import chain.gravity.gravitysdk.data.GravityRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface GravityAPI {
    @POST("/balanceOf")
    suspend fun balanceOf(@Body gravityRequest: GravityRequest): Result<GravityContractCallResponse>

    @POST("/contract")
    suspend fun contract(@Body gravityContractCallRequest: GravityContractCallRequest): Result<GravityContractCallResponse>
}