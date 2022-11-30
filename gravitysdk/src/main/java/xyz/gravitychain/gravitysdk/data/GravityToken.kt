package xyz.gravitychain.gravitysdk.data

data class GravityToken(
    val authToken: String,
    val userAddress: String,
    val userIdentity: String? = null,
    val walletTransactionStatus: String? = null,
    val walletTransactionId: String? = null
)
