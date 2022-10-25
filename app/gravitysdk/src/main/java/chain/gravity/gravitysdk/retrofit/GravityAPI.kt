package chain.gravity.gravitysdk.retrofit

import chain.gravity.gravitysdk.data.GravityAPIResponse
import chain.gravity.gravitysdk.data.GravitySmartContractRequest
import chain.gravity.gravitysdk.data.GravitySmartContractResponse
import chain.gravity.gravitysdk.data.GravitytAPIRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface GravityAPI {
    @POST("/balanceOf")
    suspend fun balanceOf(@Body gravityMobileContractRequest: GravitytAPIRequest): Result<GravityAPIResponse>

    @POST("/totalSupply")
    suspend fun totalSupply(@Body gravitySmartContractRequest: GravitySmartContractRequest): Result<GravitySmartContractResponse>
}