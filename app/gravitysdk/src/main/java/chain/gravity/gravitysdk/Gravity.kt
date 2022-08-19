package chain.gravity.gravitysdk

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_ACTION
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_ACTION_AUTHENTICATE
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_ACTION_TRANSACTION
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_APP_NAME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_GRAVITY_SDK_TOKEN
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_GRAVITY_USER_ADDRESS
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_GRAVITY_USER_IDENTITY
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_PACKAGE_NAME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_TRANSACTION_AMOUNT
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_TRANSACTION_ID
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_TRANSACTION_STATUS
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_TRANSACTION_TO_ADDRESS
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_URI_SCHEME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_PACKAGE_NAME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_URI_SCHEME_OPEN
import chain.gravity.gravitysdk.data.*
import chain.gravity.gravitysdk.retrofit.GravityAPI
import chain.gravity.gravitysdk.retrofit.RetrofitHelper

class Gravity(private val activity: Activity) {
    companion object : SingletonHolder<Gravity, Activity>(::Gravity)

    fun authenticate(@NonNull gravityAuth: GravityAuth) {
        var intent: Intent? =
            activity.packageManager.getLaunchIntentForPackage(GRAVITY_WALLET_PACKAGE_NAME)
        if (intent == null) {
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(GRAVITY_WALLET_URI_SCHEME_OPEN)
        }

        val packageManager: PackageManager =
            activity.packageManager
        val packageName = activity.packageName
        val appName = packageManager.getApplicationLabel(
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
        ) as String


        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(GRAVITY_WALLET_KEY_ACTION, GRAVITY_WALLET_KEY_ACTION_AUTHENTICATE)
        intent.putExtra(GRAVITY_WALLET_KEY_PACKAGE_NAME, packageName)
        intent.putExtra(GRAVITY_WALLET_KEY_APP_NAME, appName)
        intent.putExtra(GRAVITY_WALLET_KEY_URI_SCHEME, gravityAuth.uri)
        activity.startActivity(intent)
    }

    fun fetchData(): GravityToken? {
        val bundle: Bundle = activity.intent?.extras ?: return null
        val authToken: String? = bundle?.getString(GRAVITY_WALLET_KEY_GRAVITY_SDK_TOKEN)
        val userAddress: String? = bundle?.getString(GRAVITY_WALLET_KEY_GRAVITY_USER_ADDRESS)
        val userIdentity: String? = bundle?.getString(GRAVITY_WALLET_KEY_GRAVITY_USER_IDENTITY)
        val walletTransactionStatus: String? = bundle?.getString(
            GRAVITY_WALLET_KEY_TRANSACTION_STATUS
        )
        val walletTransactionId: String? = bundle?.getString(
            GRAVITY_WALLET_KEY_TRANSACTION_ID
        )

        return if (authToken?.isNotEmpty() == true && userAddress?.isNotEmpty() == true) (GravityToken(
            authToken,
            userAddress,
            userIdentity,
            walletTransactionStatus,
            walletTransactionId
        )) else null
    }

    fun sendTransaction(gravityAuth: GravityAuth, gravityTransaction: GravityTransaction) {
        var intent: Intent? =
            activity.packageManager.getLaunchIntentForPackage(GRAVITY_WALLET_PACKAGE_NAME)
        if (intent == null) {
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(GRAVITY_WALLET_URI_SCHEME_OPEN)
        }

        val packageManager: PackageManager =
            activity.packageManager
        val packageName = activity.packageName
        val appName = packageManager.getApplicationLabel(
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
        ) as String


        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(GRAVITY_WALLET_KEY_ACTION, GRAVITY_WALLET_KEY_ACTION_TRANSACTION)
        intent.putExtra(GRAVITY_WALLET_KEY_PACKAGE_NAME, packageName)
        intent.putExtra(GRAVITY_WALLET_KEY_APP_NAME, appName)
        intent.putExtra(GRAVITY_WALLET_KEY_URI_SCHEME, gravityAuth.uri)
        intent.putExtra(GRAVITY_WALLET_KEY_GRAVITY_SDK_TOKEN, gravityTransaction.appAuthToken)
        intent.putExtra(GRAVITY_WALLET_KEY_TRANSACTION_TO_ADDRESS, gravityTransaction.toAddress)
        intent.putExtra(GRAVITY_WALLET_KEY_TRANSACTION_AMOUNT, gravityTransaction.amount)
        activity.startActivity(intent)
    }

    suspend fun balanceOf(gravityBalance: GravityBalance): Result<GravityMobileContractResponse> {
        val packageManager: PackageManager =
            activity.packageManager
        val packageName = activity.packageName
        val appName = packageManager.getApplicationLabel(
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
        ) as String

        return RetrofitHelper.getInstance().create(GravityAPI::class.java)
            .balanceOf(
                GravityMobileContractRequest(
                    GravityInfo(
                        gravityBalance.userAddress,
                        packageName,
                        appName,
                        gravityBalance.appAuthToken
                    )
                )
            )
    }
}