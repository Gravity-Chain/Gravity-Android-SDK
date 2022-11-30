package xyz.gravitychain.gravitysdk

open class SingletonHolder<out T, in A>(private val constructor: (A) -> T) {

    @Volatile
    private var instance: T? = null

    fun init(arg: A): T =
        instance ?: synchronized(this) {
            instance ?: constructor(arg).also { instance = it }
        }

    fun getInstance(): T? =
        synchronized(this) {
            instance
        }
}