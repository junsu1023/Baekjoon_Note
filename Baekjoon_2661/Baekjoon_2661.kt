private var n = 0
private var str = ""

fun main() {
    input()
    dfs(0)
}

private fun input() {
    n = readln().toInt()
}

private fun dfs(depth: Int): Boolean {
    if(depth == n) {
        println(str)
        return true
    }

    for(i in 1 .. 3) {
        str += ('0' + i)
        if(check() && dfs(depth + 1)) return true
        str = str.substring(0, str.length - 1)
    }

    return false
}

private fun check(): Boolean {
    val size = str.length

    for(i in 1 .. size / 2) {
        if(str.substring(size - 2 * i, size - 2 * i + i) == str.substring(size - i, size)) return false
    }

    return true
}