package chain.gravity.gravitysdk

import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_CONTRACT_METHOD_TOKEN_NAME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_CONTRACT_METHOD_TOKEN_SYMBOL
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_CONTRACT_METHOD_TOTAL_SUPPLY
import chain.gravity.gravitysdk.data.GravityContractCallRequest
import chain.gravity.gravitysdk.data.GravityContractCallResponse
import chain.gravity.gravitysdk.retrofit.GravityAPI
import chain.gravity.gravitysdk.retrofit.RetrofitHelper

class GravityContract(private val contractAddress: String) {
    companion object : SingletonHolder<GravityContract, String>(::GravityContract)

}