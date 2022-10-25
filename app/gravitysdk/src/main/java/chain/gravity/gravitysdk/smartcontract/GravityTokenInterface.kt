package chain.gravity.gravitysdk.smartcontract

import chain.gravity.gravitysdk.smartcontract.annotations.TokenInfo
import java.math.BigDecimal

interface GravityTokenInterface {
    val gravityEvents: GravityEvents
    val _balances: MutableMap<String, BigDecimal>
    val _allowed: MutableMap<String, MutableMap<String, BigDecimal>>
    val tokenInfo: TokenInfo
    fun totalSupply(): BigDecimal
    fun tokenName(): String
    fun tokenSymbol(): String
    fun balanceOf(tokenOwner: String): BigDecimal?
    fun allowance(
        tokenOwner: String,
        spender: String
    ): BigDecimal?

    fun transfer(
        from: String,
        to: String,
        tokens: BigDecimal
    ): Boolean

    fun approve(
        from: String,
        spender: String,
        tokens: BigDecimal
    ): Boolean

    fun mint(to: String, value: BigDecimal)
    fun burn(from: String, value: BigDecimal)
}