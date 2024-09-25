import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun Int?.isPositive(): Boolean {
    contract {
        returns(true) implies (this@isPositive != null)
    }

    return this?.let { it >= 0 } ?: false

}
