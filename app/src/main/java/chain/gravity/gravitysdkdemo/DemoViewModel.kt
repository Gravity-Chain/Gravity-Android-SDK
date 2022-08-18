package chain.gravity.gravitysdkdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chain.gravity.gravitysdk.Gravity
import chain.gravity.gravitysdk.data.GravityBalance
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {
    companion object {
        val balanceResponse = MutableLiveData<Double?>()
    }

    fun balanceOf(userAddress: String, appAuthToken: String) {
        viewModelScope.launch {
            Gravity.getInstance()
                ?.balanceOf(
                    GravityBalance(
                        userAddress,
                        appAuthToken
                    )
                )?.onSuccess {
                    balanceResponse.postValue(it.balanceResponse?.balance)

                }?.onFailure {
                    balanceResponse.postValue(null)
                }
        }
    }
}