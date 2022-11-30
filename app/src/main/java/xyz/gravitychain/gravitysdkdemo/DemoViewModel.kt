package xyz.gravitychain.gravitysdkdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import xyz.gravitychain.gravitysdk.Gravity
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {
    companion object {
        val balanceResponse = MutableLiveData<String?>()
        val totalSupplyRez = MutableLiveData<String?>()
        val tokenNameRez = MutableLiveData<String?>()
        val tokenSymbolRez = MutableLiveData<String?>()
        val balanceOfRez = MutableLiveData<String?>()
    }

    fun balanceOf(owner: String) {
        viewModelScope.launch {
            Gravity.getInstance()
                ?.balanceOf(owner)?.onSuccess {
                    balanceResponse.postValue("${it.response}")
                }?.onFailure {
                    balanceResponse.postValue(null)
                }
        }
    }

    fun getTokenSymbol(contractAddress: String) {
        viewModelScope.launch {
            Gravity.getInstance()
                ?.getTokenSymbol(
                    contractAddress
                )?.onSuccess {
                    tokenSymbolRez.postValue("${it.response}")

                }?.onFailure {
                    println("failed: ${it.message}")
                    tokenSymbolRez.postValue(null)
                }
        }
    }

    fun getTotalSupply(contractAddress: String) {
        viewModelScope.launch {
            Gravity.getInstance()
                ?.getTotalSupply(
                    contractAddress
                )?.onSuccess {
                    totalSupplyRez.postValue("${it.response}")

                }?.onFailure {
                    totalSupplyRez.postValue(null)
                }
        }
    }

    fun getTokenName(contractAddress: String) {
        viewModelScope.launch {
            Gravity.getInstance()
                ?.getTokenName(
                    contractAddress
                )?.onSuccess {
                    tokenNameRez.postValue("${it.response}")

                }?.onFailure {
                    tokenNameRez.postValue(null)
                }
        }
    }

    fun getBalanceOf(contractAddress: String, owner: String) {
        viewModelScope.launch {
            Gravity.getInstance()
                ?.getBalanceOf(
                    contractAddress,
                    owner
                )?.onSuccess {
                    balanceOfRez.postValue("${it.response}")

                }?.onFailure {
                    balanceOfRez.postValue(null)
                }
        }
    }
}