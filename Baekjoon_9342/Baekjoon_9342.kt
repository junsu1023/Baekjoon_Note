fun main() {
    repeat(readLine()!!.toInt()) {
        println(if(readLine()!!.check()) "Infected!" else "Good")
    }
}

private fun String.check(): Boolean {
    val reg = "[A-F]?A+F+C+[A-F]?$".toRegex()
    return if(this.matches(reg)) true
    else false
}