package chain.gravity.gravitysdk

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_ACTION
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_ACTION_AUTHENTICATE
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_APP_NAME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_GRAVITY_SDK_TOKEN
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_GRAVITY_USER_ADDRESS
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_GRAVITY_USER_IDENTITY
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_PACKAGE_NAME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_KEY_URI_SCHEME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_PACKAGE_NAME
import chain.gravity.gravitysdk.GravityConstants.Companion.GRAVITY_WALLET_URI_SCHEME
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
            intent.data = Uri.parse(GRAVITY_WALLET_URI_SCHEME)
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
        val bundle: Bundle? = activity.intent?.extras
        val authToken: String? = bundle?.getString(GRAVITY_WALLET_KEY_GRAVITY_SDK_TOKEN)
        val userAddress: String? = bundle?.getString(GRAVITY_WALLET_KEY_GRAVITY_USER_ADDRESS)
        val userIdentity: String? = bundle?.getString(GRAVITY_WALLET_KEY_GRAVITY_USER_IDENTITY)

        return if (authToken?.isNotEmpty() == true && userAddress?.isNotEmpty() == true && userIdentity?.isNotEmpty() == true) (GravityToken(
            authToken,
            userAddress,
            userIdentity
        )) else null
    }

    fun sendTransaction(toAddress: String, amount: Double, thirdPartAuthToken: String) {
        // make txn send api

        // return the response
    }

    suspend fun balanceOf(
        userAddress: String,
        appAuthToken: String
    ): Result<GravityMobileContractResponse> {
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
                        userAddress,
                        packageName,
                        appName,
                        appAuthToken
                    )
                )
            )
    }
}