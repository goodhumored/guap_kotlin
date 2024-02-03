import kotlin.math.abs
import kotlin.math.pow

// в подсчёте по маклорену считаем пока разница между предыдущим значением и сейчас не станет меньше точности
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

// в подсчёте эталонного значения просто считаем
fun calculateReference(argument: Double): Double {
    return 1.0/(1+argument);
}