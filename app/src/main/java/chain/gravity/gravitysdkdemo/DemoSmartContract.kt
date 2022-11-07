package chain.gravity.gravitysdkdemo

import chain.gravity.gravitysdk.smartcontract.GravityEvents
import chain.gravity.gravitysdk.smartcontract.GravityTokenInterface
import chain.gravity.gravitysdk.smartcontract.annotations.TokenInfo
import java.math.BigDecimal

class DemoSmartContract(
    override val gravityEvents: GravityEvents,
    override val _balances: MutableMap<String, BigDecimal>,
    override val _allowed: MutableMap<String, MutableMap<String, BigDecimal>>,
) : GravityTokenInterface {
    override val tokenInfo = TokenInfo("Demo Token", "DEMO", BigDecimal("10000000000000000"))

    override fun totalSupply(): BigDecimal {
        return tokenInfo._totalSupply
    }

    override fun tokenName(): String {
        return tokenInfo._tokenName
    }

    override fun tokenSymbol(): String {
        return tokenInfo._tokenSymbol
    }

    override fun balanceOf(tokenOwner: String): BigDecimal? {
        return _balances[tokenOwner]
    }

    override fun allowance(
        tokenOwner: String,
        spender: String
    ): BigDecimal? {
        return _allowed[tokenOwner]?.get(spender)
    }

    override fun transfer(
        from: String,
        to: String,
        tokens: BigDecimal
    ): Boolean {
        require(to != "0x00000000000000")
        gravityEvents.transfer(from, to, tokens)
        return true
    }

    override fun approve(
        from: String,
        spender: String,
        tokens: BigDecimal
    ): Boolean {
        require(spender != "0x00000000000000")
        gravityEvents.approve(from, spender, tokens)
        return true
    }

    override fun mint(
        to: String,
        value: BigDecimal
    ) {
        require(to != "0x00000000000000")
        require(_balances[to] != null)
        gravityEvents.increaseTotalSupply(value)
        gravityEvents.transfer("0x00000000000000", to, value)
    }

    override fun burn(
        from: String,
        value: BigDecimal
    ) {
        require(from != "0x00000000000000")
        require(_balances[from] != null)
        gravityEvents.decreaseTotalSupply(value)
        gravityEvents.transfer(from, "0x00000000000000", value)
    }
}

fun main() {
}