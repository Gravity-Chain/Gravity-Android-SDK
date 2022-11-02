package chain.gravity.gravitysdk.retrofit

import chain.gravity.gravitysdk.data.GravityAPIResponse
import chain.gravity.gravitysdk.data.GravityContractCallRequest
import chain.gravity.gravitysdk.data.GravityContractCallResponse
import chain.gravity.gravitysdk.data.GravitytAPIRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface GravityAPI {
    @POST("/balanceOf")
    suspend fun balanceOf(@Body owner: String): Result<GravityContractCallResponse>

    @POST("/contract")
    suspend fun contract(@Body gravityContractCallRequest: GravityContractCallRequest): Result<GravityContractCallResponse>
}