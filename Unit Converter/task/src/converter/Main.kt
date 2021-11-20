package converter

val uDistance = listOf(
    Pair(Pair(1.0, 0.0), listOf("meter", "m", "meters")),
    Pair(Pair(1000.0, 0.0), listOf("kilometer", "km", "kilometers")),
    Pair(Pair(0.01, 0.0), listOf("centimeter", "cm", "centimeters")),
    Pair(Pair(0.001, 0.0), listOf("millimeter", "mm", "millimeters")),
    Pair(Pair(1609.35, 0.0), listOf("mile", "mi", "miles")),
    Pair(Pair(0.9144, 0.0), listOf("yard", "yd", "yards")),
    Pair(Pair(0.3048, 0.0), listOf("foot", "ft", "feet")),
    Pair(Pair(0.0254, 0.0), listOf("inch", "in", "inches")),
)

val uWeight = listOf(
    Pair(Pair(1.0,  0.0), listOf( "gram", "g", "grams")),
    Pair(Pair(1000.0, 0.0), listOf("kilogram", "kg","kilograms")),
    Pair(Pair(0.001, 0.0), listOf("milligram", "mg", "milligrams")),
    Pair(Pair(453.592, 0.0), listOf("pound", "lb", "pounds")),
    Pair(Pair( 28.3495, 0.0), listOf("ounce", "oz", "ounces")),
)

val unitsTemperature = listOf(
    Pair(Pair(1.0, 0.0), listOf( "degree celsius", "c", "degrees celsius", "celsius", "dc")),
    Pair(Pair(5.0 / 9.0, 32.0), listOf( "degree fahrenheit", "f", "degrees fahrenheit", "fahrenheit", "df")),
    Pair(Pair(1.0, 273.15), listOf( "kelvin", "k", "kelvins")),
)

fun main() {
    while(true) {
        println("Enter what you want to convert (or exit): ")
        val toConvert = readLine()!!.also { if (it == "exit") return }
        toConvert.split(" ").map { it.lowercase() }.toMutableList().run {
            removeAll(listOf("degree", "degrees"))
            if (size in 4..5 && first().toDoubleOrNull() != null) {
                when {
                    first().toDouble() < 0.0 && findObject(this[1]) == uDistance -> println("Length shouldn't be negative")
                    first().toDouble() < 0.0 && findObject(this[1]) == uWeight -> println("Weight shouldn't be negative")
                    else -> getObject(this)
                }
            } else { println("Parse error") }
        }
    }
}

fun getObject(input: MutableList<String>) {
    val obj1 = findObject(input[1])
    val obj2 = findObject(input[3])
    val from = obj1?.find { it.second.contains(input[1]) }
    val to = obj2?.find { it.second.contains(input[3]) }
    when {
        obj1 == null && obj2 == null -> println("Conversion from ??? to ??? is impossible")
        obj1 == null -> println("Conversion from ??? to ${to!!.second[2]} is impossible")
        obj2 == null -> println("Conversion from ${from!!.second[2]} to ??? is impossible")
        obj1 != obj2 -> println("Conversion from ${from!!.second[2]} to ${to!!.second[2]} is impossible")
        else -> converter(input, from, to)
    }
}

fun converter(
    input: List<String>,
    from: Pair<Pair<Double, Double>, List<String>>?,
    to: Pair<Pair<Double, Double>, List<String>>?,
) {
    val inValue = input[0].toDouble()
    val outValueBase = (inValue - from!!.first.second) * from.first.first
    val outValue = (outValueBase / to!!.first.first) + to.first.second
    val uFrom = if (inValue == 1.0) from.second[0] else from.second[2]
    val uTo = if (outValue == 1.0) to.second[0] else to.second[2]
    println("$inValue $uFrom is $outValue $uTo")
}

fun findObject(s: String) = when {
    uDistance.find { it.second.contains(s) } != null -> uDistance
    uWeight.find { it.second.contains(s) } != null -> uWeight
    unitsTemperature.find { it.second.contains(s) } != null -> unitsTemperature
    else -> null
}
