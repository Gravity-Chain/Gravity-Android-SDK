package xyz.gravitychain.gravitysdkdemo

import xyz.gravitychain.gravitysdk.smartcontract.GravityEvents
import xyz.gravitychain.gravitysdk.smartcontract.GravityNFTInterface
import xyz.gravitychain.gravitysdk.smartcontract.annotations.TokenInfo
import java.math.BigDecimal

class DemoNFTContract(
    override val gravityEvents: GravityEvents,
    override var _totalSupply: BigDecimal,
    // tokenId <-> owner
    override val _allTokens: MutableMap<BigDecimal, String>,
    // owner <-> [tokenIds,..]
    override val _allTokenOwnersMap: MutableMap<String, MutableList<BigDecimal>>,
    // tokenId <-> token URI
    override val _tokenURIs: MutableMap<BigDecimal, String>,
    override val _allowed: MutableMap<String, MutableMap<String, BigDecimal>>
) : GravityNFTInterface {
    override val tokenInfo = TokenInfo("Demo Gravity NFT", "DEMGRVTY")

    override fun totalSupply(): BigDecimal {
        return _totalSupply
    }

    override fun tokenName(): String {
        return tokenInfo._tokenName
    }

    override fun tokenSymbol(): String {
        return tokenInfo._tokenSymbol
    }

    override fun balanceOf(tokenOwner: String): BigDecimal? {
        return _allTokenOwnersMap[tokenOwner]?.size?.toBigDecimal()
    }

    override fun ownerOf(tokenId: BigDecimal): String {
        require(_allTokens[tokenId] != null)
        return _allTokens[tokenId].orEmpty()
    }

    override fun tokenURI(tokenId: BigDecimal): String {
        require(_tokenURIs[tokenId] != null)
        return _tokenURIs[tokenId].orEmpty()
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
        tokenId: BigDecimal,
        tokens: BigDecimal
    ): Boolean {
        require(to != "0x00000000000000")
        require(_allTokens[tokenId] == null)
        require(_allTokens[tokenId] == from)

        if (_allTokenOwnersMap[from] != null) {
            _allTokenOwnersMap[from]?.remove(tokenId)
        }

        if (_allTokenOwnersMap[to] != null) {
            _allTokenOwnersMap[to]?.add(tokenId)
        } else {
            _allTokenOwnersMap[to] = mutableListOf(tokenId)
        }

        if (tokens != BigDecimal.ZERO) {
            // 5% royalty
            val royaltyAmount = (tokens * 5.0.toBigDecimal()) / 100.0.toBigDecimal()
            gravityEvents.transfer(from, to, tokens - royaltyAmount)
            gravityEvents.transfer(
                from,
                "0x2aa10805466ea0b93333dc96f4a477c8420834af",
                royaltyAmount
            )
        } else {
            gravityEvents.transfer(from, to, tokens)
        }
        return true
    }

    override fun approve(
        from: String,
        spender: String,
        tokenId: BigDecimal,
        tokens: BigDecimal
    ): Boolean {
        require(spender != "0x00000000000000")
        gravityEvents.approve(from, spender, tokens)
        return true
    }

    override fun _mint(
        tokenURI: String,
        value: BigDecimal
    ): Boolean {
        require(_allTokens.size.toBigDecimal() < _totalSupply)
        val tokenId = _allTokens.size.toBigDecimal().inc()
        require(_allTokens[tokenId] == null)
        _totalSupply++
        val contractOwner = "0x2aa10805466ea0b93333dc96f4a477c8420834af"
        _allTokens[tokenId] = contractOwner
        if (_allTokenOwnersMap[contractOwner] != null) {
            _allTokenOwnersMap[contractOwner]?.add(tokenId)
        } else {
            _allTokenOwnersMap[contractOwner] = mutableListOf(tokenId)
        }
        _tokenURIs[tokenId] = tokenURI
        gravityEvents.transfer("0x00000000000000", contractOwner, value)
        return true
    }

    override fun _burn(
        from: String,
        tokenId: BigDecimal
    ): Boolean {
        require(from != "0x00000000000000")
        require(_allTokens[tokenId] != null)
        require(_allTokens[tokenId] == from)
        _totalSupply--
        _allTokens.remove(tokenId)
        _allTokenOwnersMap[from]!!.remove(tokenId)
        gravityEvents.transfer(from, "0x00000000000000", tokenId)
        return true
    }
}

fun main() {
}