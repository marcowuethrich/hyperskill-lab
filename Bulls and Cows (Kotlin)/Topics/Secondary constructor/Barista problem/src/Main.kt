class EspressoMachine {
    var costPerServing: Float = 0f

    constructor(coffeeCapsulesCount: Int, totalCost: Float) {
        this.costPerServing = totalCost / coffeeCapsulesCount
    }

    constructor(coffeeBeansWeight: Float, totalCost: Float) {
        this.costPerServing = totalCost / (coffeeBeansWeight / 10)
    }
}
