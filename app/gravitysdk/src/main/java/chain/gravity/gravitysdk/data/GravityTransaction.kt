package chain.gravity.gravitysdk.data

data class GravityTransaction(
    val toAddress: String,
    val amount: Double,
    val appAuthToken: String
)
