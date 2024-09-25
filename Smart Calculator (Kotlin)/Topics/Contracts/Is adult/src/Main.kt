import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun Any?.isPerson(): Boolean {
    contract {
        returns(true) implies (this@isPerson is Person)
    }

    return this is Person
}
