package chain.gravity.gravitysdkdemo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import chain.gravity.gravitysdk.Gravity
import chain.gravity.gravitysdk.data.GravityAuth
import chain.gravity.gravitysdkdemo.databinding.FragmentFirstBinding
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

        binding.buttonFirst.setOnClickListener {
            testAuthentication()
        }

        sharedPreferences?.apply {
            if (sharedPreferences?.getString("gravityAuthToken", null) != null) {
                binding.buttonFirst.visibility = View.GONE
                binding.buttonTxn.visibility = View.VISIBLE
                binding.buttonBalance.visibility = View.VISIBLE
                binding.textviewBalance.visibility = View.VISIBLE

                binding.textviewFirst.text = "Connected to Wallet!"

                binding.buttonTxn.setOnClickListener {
                    sendTestTransaction(
                        sharedPreferences?.getString("gravityAuthToken", null).orEmpty()
                    )
                }

                binding.buttonBalance.setOnClickListener {
                    viewModel.balanceOf(
                        sharedPreferences?.getString("gravityUserAddress", "").orEmpty(),
                        sharedPreferences?.getString("gravityAuthToken", null).orEmpty()
                    )

                    DemoViewModel.balanceResponse.observe(viewLifecycleOwner) {
                        if (it != null) {
                            binding.textviewBalance.text =
                                "Balance: ${it.to2Decimal()} GRT"
                        } else {
                            binding.textviewBalance.text =
                                "Failed to fetch balance"
                        }
                    }
                }

            } else {
                binding.buttonFirst.visibility = View.VISIBLE
                binding.buttonTxn.visibility = View.GONE
                binding.buttonBalance.visibility = View.GONE
                binding.textviewBalance.visibility = View.GONE
            }
        }

    }

    private fun sendTestTransaction(thirdPartyAuthToken: String) {
        TODO("Not yet implemented")
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
            binding.textviewFirst.text = "Connected to Wallet!"

            val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
            editor?.putString("gravityAuthToken", gravityResponse.authToken)
            editor?.putString("gravityUserAddress", gravityResponse.userAddress)
            editor?.putString("gravityUserIdentity", gravityResponse.userAddress)
            editor?.apply()

            binding.buttonFirst.visibility = View.GONE
            binding.buttonTxn.visibility = View.VISIBLE
        }
    }

    fun Double.to2Decimal(): Double {
        val df = DecimalFormat("#.##")
        return df.format(this).toDouble()
    }
}