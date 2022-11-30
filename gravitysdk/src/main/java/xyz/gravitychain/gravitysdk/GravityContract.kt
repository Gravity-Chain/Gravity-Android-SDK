package xyz.gravitychain.gravitysdk

class GravityContract(private val contractAddress: String) {
    companion object : SingletonHolder<GravityContract, String>(::GravityContract)

}