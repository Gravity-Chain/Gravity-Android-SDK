package xyz.gravitychain.gravitysdkdemo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import chain.gravity.gravitysdkdemo.databinding.FragmentFirstBinding
import xyz.gravitychain.gravitysdk.Gravity
import xyz.gravitychain.gravitysdk.GravityConstants
import xyz.gravitychain.gravitysdk.data.GravityAuth
import xyz.gravitychain.gravitysdk.data.GravityTransaction
import xyz.gravitychain.gravitysdk.data.TransactionMeta
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: DemoViewModel

    private val sharedPrefFile = "demoPref"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        sharedPreferences = this.context?.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        viewModel = ViewModelProvider(this)[DemoViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConnectWallet.setOnClickListener {
            testAuthentication()
        }

        sharedPreferences?.apply {
            if (sharedPreferences?.getString("gravityAuthToken", null) != null) {
                binding.firstCl.visibility = View.GONE
                binding.secondCl.visibility = View.VISIBLE

                binding.textviewIdentity.text =
                    sharedPreferences?.getString("gravityUserIdentity", null)
                binding.textviewAddress.text =
                    sharedPreferences?.getString("gravityUserAddress", null)
                binding.textInitials.text =
                    sharedPreferences?.getString("gravityUserIdentity", null)?.substring(0, 1)

            } else {
                binding.firstCl.visibility = View.VISIBLE
                binding.secondCl.visibility = View.GONE
            }
        }

        binding.buttonTxn.setOnClickListener {
            sendTestTransaction(
                "teszterr", 5.0,
                sharedPreferences?.getString("gravityAuthToken", null).orEmpty()
            )
        }

        binding.buttonBalance.setOnClickListener {
            viewModel.balanceOf(
                sharedPreferences?.getString("gravityUserAddress", "").orEmpty()
            )

            DemoViewModel.balanceResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.textviewBalance.text =
                        "Balance: ${it.toDouble().to2Decimal()} GRT"
                } else {
                    binding.textviewBalance.text =
                        "Failed to fetch balance"
                }
            }
        }

        binding.cvSupply.button.setOnClickListener {
            viewModel.getTotalSupply("0x799e8759bb2727a637d4c64ecd3138e2fd562d57")
        }

        binding.cvTokenName.button.text = "execute tokenName"
        binding.cvTokenName.button.setOnClickListener {
            viewModel.getTokenName("0x799e8759bb2727a637d4c64ecd3138e2fd562d57")
        }

        binding.cvTokenSymbol.button.text = "execute tokenSymbol"
        binding.cvTokenSymbol.button.setOnClickListener {
            viewModel.getTokenSymbol("0x799e8759bb2727a637d4c64ecd3138e2fd562d57")
        }

        binding.cvBalanceOf.button.text = "execute balanceOf"
        binding.cvBalanceOf.tvB.text = "0xd5...c792"
        binding.cvBalanceOf.tvB.visibility = View.VISIBLE
        binding.cvBalanceOf.button.setOnClickListener {
            viewModel.getBalanceOf(
                "0x799e8759bb2727a637d4c64ecd3138e2fd562d57",
                "0xd59aec8166e6fdba0a0ad663cfa821319d8cc792"
            )
        }

        binding.cvSendTxnContract.button.text = "send contract transaction"
        binding.cvSendTxnContract.tvB.text = "amount: 1 GRT"
        binding.cvSendTxnContract.tvB.visibility = View.VISIBLE
        binding.cvSendTxnContract.button.setOnClickListener {
            sendContractTestTransaction(
                "teszterr", 1.0,
                sharedPreferences?.getString("gravityAuthToken", null).orEmpty(),
                "0x799e8759bb2727a637d4c64ecd3138e2fd562d57"
            )
        }

        DemoViewModel.totalSupplyRez.observe(viewLifecycleOwner) {
            println("rezz: $it")
            if (it != null) {
                binding.cvSupply.tvA.text =
                    "Total Supply: ${BigDecimal(it).toPlainString()}"
            } else {
                binding.cvSupply.tvA.text =
                    "Failed to fetch"
            }
        }

        DemoViewModel.tokenNameRez.observe(viewLifecycleOwner) {
            println("rezz: $it")
            if (it != null) {
                binding.cvTokenName.tvA.text =
                    "Token name: $it"
            } else {
                binding.cvTokenName.tvA.text =
                    "Failed to fetch"
            }
        }

        DemoViewModel.tokenSymbolRez.observe(viewLifecycleOwner) {
            println("rezz: $it")
            if (it != null) {
                binding.cvTokenSymbol.tvA.text =
                    "Total Supply: $it"
            } else {
                binding.cvTokenSymbol.tvA.text =
                    "Failed to fetch"
            }
        }

        DemoViewModel.balanceOfRez.observe(viewLifecycleOwner) {
            println("rezz: $it")
            if (it != null) {
                binding.cvBalanceOf.tvA.text =
                    "Balance: ${BigDecimal(it).toPlainString()}"
            } else {
                binding.cvBalanceOf.tvA.text =
                    "Failed to fetch"
            }
        }

//        binding.cvSupply.button.setOnClickListener {
//            viewModel.getTotalSupply("0x799e8759bb2727a637d4c64ecd3138e2fd562d57")
//
//            DemoViewModel.totalSupplyRez.observe(viewLifecycleOwner) {
//                if (it != null) {
//                    binding.cvSupply.tvA.text =
//                        "Total Supply: $it"
//                } else {
//                    binding.textviewBalance.text =
//                        "Failed to fetch"
//                }
//            }
//        }
    }

    private fun sendTestTransaction(
        toAddress: String,
        amount: Double,
        thirdPartyAuthToken: String
    ) {
        Gravity.getInstance()?.sendTransaction(
            GravityAuth("gravitysdkdemoapp://open"),
            GravityTransaction(toAddress, BigDecimal(amount), thirdPartyAuthToken)
        )
    }

    private fun sendContractTestTransaction(
        toAddress: String,
        amount: Double,
        thirdPartyAuthToken: String,
        contractAddress: String
    ) {
        Gravity.getInstance()?.sendTransaction(
            GravityAuth("gravitysdkdemoapp://open"),
            GravityTransaction(
                toAddress,
                BigDecimal(amount),
                thirdPartyAuthToken,
                TransactionMeta(contractAddress, GravityConstants.GRAVITY_CONTRACT_METHOD_TRANSFER)
            )
        )
    }

    private fun testAuthentication() {
        binding.textviewFirst.text = "Connecting to Wallet"
        Gravity.getInstance()
            ?.authenticate(GravityAuth("gravitysdkdemoapp://open"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val gravityResponse = Gravity.getInstance()?.fetchData()
        if (gravityResponse != null) {

            if (!gravityResponse.walletTransactionStatus.isNullOrEmpty()) {
                binding.firstCl.visibility = View.GONE
                binding.secondCl.visibility = View.VISIBLE

                binding.tvAddress.text =
                    "transaction status: ${gravityResponse.walletTransactionStatus.toBoolean()}"

                binding.tvAmount.text = "transactionId: ${gravityResponse.walletTransactionId}"


            } else {
                binding.textviewFirst.text = "Connected to Wallet!"

                val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                editor?.putString("gravityAuthToken", gravityResponse.authToken)
                editor?.putString("gravityUserAddress", gravityResponse.userAddress)
                editor?.putString("gravityUserIdentity", gravityResponse.userIdentity)
                editor?.apply()

                binding.firstCl.visibility = View.GONE
                binding.secondCl.visibility = View.VISIBLE

                binding.textviewIdentity.text = gravityResponse.userIdentity
                binding.textviewAddress.text = gravityResponse.userAddress
                binding.textInitials.text = gravityResponse.userIdentity?.substring(0, 1)
            }
        }
    }

    fun Double.to2Decimal(): Double {
        val df = DecimalFormat("#.##")
        return df.format(this).toDouble()
    }
}