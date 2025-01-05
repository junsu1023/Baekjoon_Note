fun main() {
    val checked = IntArray(10001)

    val list = mutableListOf<Triple<Int, Int, Int>>()
    repeat(readLine()!!.toInt()) {
        val (y, x1, x2) = readLine()!!.split(" ").map { it.toInt() }
        list.add(Triple(y, x1, x2))
    }

    list.sortBy { it.first }

    var total = 0
    for((y, x1, x2) in list) {
        total += y - checked[x1] + y - checked[x2 - 1]
        (x1 until x2).forEach { checked[it] = y }
    }

    println(total)
}