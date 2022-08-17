package chain.gravity.gravitysdk.retrofit

import chain.gravity.gravitysdk.data.GravityMobileContractRequest
import chain.gravity.gravitysdk.data.GravityMobileContractResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GravityAPI {
    @POST("/balanceOf")
    suspend fun balanceOf(@Body gravityMobileContractRequest: GravityMobileContractRequest): Result<GravityMobileContractResponse>
}