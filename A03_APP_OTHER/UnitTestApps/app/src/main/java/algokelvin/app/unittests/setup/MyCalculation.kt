package algokelvin.app.unittests.setup

class MyCalculation: Calculations {
    private val phi = 3.14

    override fun calculateCircumference(radius: Double): Double {
        return 2 * phi * radius
    }

    override fun calculateArea(radius: Double): Double {
        return phi * radius * radius
    }
}