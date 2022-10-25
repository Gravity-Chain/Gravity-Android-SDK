package chain.gravity.gravitysdk.data

import androidx.annotation.Nullable
import java.math.BigDecimal

data class GravitySmartContractResponse(
    @Nullable val tokens: BigDecimal,
    @Nullable val requestSuccess: Boolean
)