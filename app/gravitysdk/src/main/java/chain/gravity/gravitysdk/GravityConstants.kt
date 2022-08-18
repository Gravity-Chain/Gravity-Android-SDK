package chain.gravity.gravitysdk

class GravityConstants {
    companion object {
        const val GRAVITY_WALLET_PACKAGE_NAME: String = "chain.gravity.app"
        const val GRAVITY_WALLET_URI_SCHEME_OPEN: String = "gravitywallet://open"

        const val GRAVITY_WALLET_KEY_ACTION: String = "action"
        const val GRAVITY_WALLET_KEY_PACKAGE_NAME: String = "packageName"
        const val GRAVITY_WALLET_KEY_APP_NAME: String = "appName"
        const val GRAVITY_WALLET_KEY_URI_SCHEME: String = "uri"
        const val GRAVITY_WALLET_KEY_GRAVITY_SDK_TOKEN: String = "gravitySDKToken"
        const val GRAVITY_WALLET_KEY_GRAVITY_USER_ADDRESS: String = "gravityUserAddress"
        const val GRAVITY_WALLET_KEY_GRAVITY_USER_IDENTITY: String = "gravityUserIdentity"
        const val GRAVITY_WALLET_KEY_TRANSACTION_TO_ADDRESS: String = "toAddress"
        const val GRAVITY_WALLET_KEY_TRANSACTION_AMOUNT: String = "amount"
        const val GRAVITY_WALLET_KEY_TRANSACTION_STATUS: String = "walletTransactionStatus"

        const val GRAVITY_WALLET_KEY_ACTION_AUTHENTICATE: String = "authenticate"
        const val GRAVITY_WALLET_KEY_ACTION_TRANSACTION: String = "transaction"
    }
}