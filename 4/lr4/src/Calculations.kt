import kotlin.math.abs
import kotlin.math.pow

fun calculateMaclaurin(argument: Double, precision: Double): Double {
    var result = 0.0
    var prevResult: Double;
    var n = 0.0;

    do {
        prevResult = result;
        result += (-1.0).pow(n) * argument.pow(n);
        n++
    } while (abs(prevResult - result) >= precision)
    return result
}

fun calculateReference(argument: Double): Double {
    return 1.0/(1+argument);
}