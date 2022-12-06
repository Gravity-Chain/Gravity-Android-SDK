package xyz.gravitychain.gravitysdk.smartcontract

import xyz.gravitychain.gravitysdk.smartcontract.annotations.Internal
import xyz.gravitychain.gravitysdk.smartcontract.annotations.TokenInfo
import java.math.BigDecimal

interface GravityNFTInterface {
    val gravityEvents: GravityEvents
    var _totalSupply: BigDecimal
    val _allTokens: MutableMap<BigDecimal, String>
    val _allTokenOwnersMap: MutableMap<String, MutableList<BigDecimal>>
    val _tokenURIs: MutableMap<BigDecimal, String>
    val _allowed: MutableMap<String, MutableMap<String, BigDecimal>>
    val tokenInfo: TokenInfo
    fun totalSupply(): BigDecimal
    fun tokenName(): String
    fun tokenSymbol(): String
    fun balanceOf(tokenOwner: String): BigDecimal?
    fun ownerOf(tokenId: BigDecimal): String
    fun tokenURI(tokenId: BigDecimal): String
    fun allowance(
        tokenOwner: String,
        spender: String
    ): BigDecimal?

    fun transfer(
        from: String,
        to: String,
        tokenId: BigDecimal,
        tokens: BigDecimal
    ): Boolean

    fun approve(
        from: String,
        spender: String,
        tokenId: BigDecimal,
        tokens: BigDecimal
    ): Boolean

    @Internal
    fun _setTokenURI(tokenId: BigDecimal, tokenURI: String): Boolean

    @Internal
    fun _mint(to: String, value: BigDecimal): Boolean

    @Internal
    fun _burn(from: String, tokenId: BigDecimal): Boolean
}