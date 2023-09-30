import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun main() {
    // Создаём поликлинику
    val polyclinic = Polyclinic()

    // Создаём пациентов
    val patients = mutableListOf(
        Patient("Кирилл"),
        Patient("Алексей"),
        Patient("Даниил"),
        Patient("Дмитрий"),
        Patient("Максим"),
        Patient("Андрей"),
        Patient("Артем"),
        Patient("Иван"),
        Patient("Михаил"),
    )

    println("Пациенты пришли в поликлинику: $patients")

    // Создаём пул потоков (по одному потоку на каждого пациента + один поток для поликлиники)
    val executor: ExecutorService =
        Executors.newFixedThreadPool(patients.size + 1)

    // Потоки пациентов, которые получают номера
    for (i in 0..<patients.size) {
        val patient = patients[i]
        executor.submit {
            val randomTime = Random.nextLong(0, 3000)
            Thread.sleep(randomTime)
            var number = 0
            for (j in 0..<patients.size) {
                if (patients[j].getNumber() > number) {
                    number = patients[j].getNumber()
                }
            }
            patient.setNumber(number + 1)
        }
    }

    // Поток поликлиники, который обрабатывает пациентов
    executor.submit {
        Thread.sleep(3000)
        while (patients.isNotEmpty()) {
            var minPat = patients[0]
            for (i in 0..<patients.size) {
                if (patients[i].getNumber() < minPat.getNumber()) {
                    minPat = patients[i]
                }
            }
            polyclinic.process(minPat)
            patients.remove(minPat)
            Thread.sleep(1000)
        }
    }

    // Ждём пока все потоки завершатся
    executor.shutdown()
    try {
        executor.awaitTermination(
            Long.MAX_VALUE,
            TimeUnit.NANOSECONDS
        )
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

}