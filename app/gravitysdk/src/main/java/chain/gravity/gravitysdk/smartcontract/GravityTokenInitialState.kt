package chain.gravity.gravitysdk.smartcontract

import java.math.BigDecimal

data class GravityTokenInitialState(
    val name: String,
    val symbol: String,
    val totalSupply: BigDecimal
)