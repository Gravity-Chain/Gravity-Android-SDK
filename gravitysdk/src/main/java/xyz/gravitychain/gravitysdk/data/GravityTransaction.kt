package xyz.gravitychain.gravitysdk.data

import java.math.BigDecimal

data class GravityTransaction(
    val toAddress: String,
    val amount: BigDecimal,
    val appAuthToken: String,
    val transactionMeta: TransactionMeta? = null
)
