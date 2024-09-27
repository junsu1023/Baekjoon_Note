private var n = 0
private lateinit var board: Array<IntArray>
private var max = Int.MIN_VALUE

fun main() {
    input()
    solve()
}

private fun input() {
    n = readLine()!!.toInt()

    board = Array(n) { IntArray(n) }
    repeat(n) {
        board[it] = readLine()!!.split(" ").map { it.toInt() }.toIntArray()
    }
}

private fun solve() {
    for(i in 0 until 4) {
        move(board.copy(), i, 0)
    }

    println(max)
}

private fun Array<IntArray>.copy(): Array<IntArray> {
    val copyBoard = Array(n) { IntArray(n) }
    for(i in 0 until n) {
        copyBoard[i] = this[i].clone()
    }

    return copyBoard
}

private fun move(board: Array<IntArray>, dir: Int, moveCnt: Int) {
    if(moveCnt == 5) {
        board.findMaxValue()
        return
    }

    val afterMoveBoard = when(dir) {
        0 -> board.moveUp()
        1 -> board.moveDown()
        2 -> board.moveLeft()
        else -> board.moveRight()
    }

    for(i in 0 until 4) {
        move(afterMoveBoard.copy(), i, moveCnt + 1)
    }
}

private fun Array<IntArray>.moveUp(): Array<IntArray> {
    for(j in 0 until n) {
        for(i in 0 until n) {
            if(this[i][j] == 0) continue

            var k = i + 1
            while(k < n && this[k][j] == 0) k++

            if(k >= n) continue
            if(this[i][j] != this[k][j]) {
                val temp = this[k][j]
                this[k][j] = 0
                this[i+1][j] = temp
                continue
            }

            this[i][j] *= 2
            this[k][j] = 0
        }
    }

    for(j in 0 until n) {
        for(i in 1 until n) {
            if(this[i][j] == 0) continue

            var k = i - 1
            while(k >= 0 && this[k][j] == 0) k--

            val temp = this[i][j]
            this[i][j] = 0
            this[k+1][j] = temp
        }
    }

    return this
}

private fun Array<IntArray>.moveDown(): Array<IntArray> {
    for(j in 0 until n) {
        for(i in n-1 downTo 0) {
            if(this[i][j] == 0) continue

            var k = i - 1
            while(k >= 0 && this[k][j] == 0) k--

            if(k < 0) continue
            if(this[i][j] != this[k][j]) {
                val temp = this[k][j]
                this[k][j] = 0
                this[i-1][j] = temp
                continue
            }

            this[i][j] *= 2
            this[k][j] = 0
        }

        for(j in 0 until n) {
            for(i in n-2 downTo 0) {
                if(this[i][j] == 0) continue

                var k = i + 1
                while(k < n && this[k][j] == 0) k++

                val temp = this[i][j]
                this[i][j] = 0
                this[k-1][j] = temp
            }
        }
    }

    return this
}

private fun Array<IntArray>.moveLeft(): Array<IntArray> {
    for(i in 0 until n) {
        for(j in 0 until n) {
            if(this[i][j] == 0) continue

            var k = j + 1
            while(k < n && this[i][k] == 0) k++

            if(k >= n) continue
            if(this[i][j] != this[i][k]) {
                val temp = this[i][k]
                this[i][k] = 0
                this[i][j+1] = temp
                continue
            }

            this[i][j] *= 2
            this[i][k] = 0
        }
    }

    for(i in 0 until n) {
        for(j in 0 until n) {
            if(this[i][j] == 0) continue

            var k = j - 1
            while(k >= 0 && this[i][k] == 0) k--

            val temp = this[i][j]
            this[i][j] = 0
            this[i][k+1] = temp
        }
    }

    return this
}

private fun Array<IntArray>.moveRight(): Array<IntArray> {
    for(i in 0 until n) {
        for(j in n-1 downTo 0) {
            if(this[i][j] == 0) continue

            var k = j -1
            while(k >= 0 && this[i][k] == 0) k--

            if(k < 0) continue
            if(this[i][j] != this[i][k]) {
                val temp = this[i][k]
                this[i][k] = 0
                this[i][j-1] = temp
                continue
            }

            this[i][j] *= 2
            this[i][k] = 0
        }
    }

    for(i in 0 until n) {
        for(j in n-2 downTo 0) {
            if(this[i][j] == 0) continue

            var k = j + 1
            while(k < n && this[i][k] == 0) k++

            val temp = this[i][j]
            this[i][j] = 0
            this[i][k-1] = temp
        }
    }

    return this
}

private fun Array<IntArray>.findMaxValue() {
    for(i in this.indices) {
        for(j in this[i].indices) {
            max = maxOf(max, this[i][j])
        }
    }
}