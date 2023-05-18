package algokelvin.app.circlecalculator.circle

class CircleCalculatorImpl: CircleCalculator {
    override fun calculateCircumference(radius: Double): Double {
        return 3.14 * 2 * radius
    }

    override fun calculateArea(radius: Double): Double {
        return 3.14 * radius * radius
    }
}