package xyz.gravitychain.gravitysdk.smartcontract

import java.math.BigDecimal

class GravityEvents() {
    fun transfer(
        from: String,
        to: String,
        tokens: BigDecimal
    ) {
    }

    fun increaseTotalSupply(
        tokens: BigDecimal
    ) {
    }

    fun decreaseTotalSupply(
        tokens: BigDecimal
    ) {
    }

    fun approve(
        from: String,
        spender: String,
        tokens: BigDecimal
    ) {
    }
}