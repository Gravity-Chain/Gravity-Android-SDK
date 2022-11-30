package xyz.gravitychain.gravitysdk.data

data class GravityContractCallRequest(
    val contractAddress: String,
    val method: String,
    val owner: String? = null
)