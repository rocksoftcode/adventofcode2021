Integer[][] grid = new File('input.txt').text.split('\n').collect {it.split('')*.toInteger()}
long flashes = 0
def neighbors = {i, j -> [[i - 1, j - 1], [i - 1, j], [i - 1, j + 1], [i, j + 1], [i + 1, j + 1], [i + 1, j], [i + 1, j - 1], [i, j - 1]]}
def flash
def inc
flash = { i, j, flashed ->
	flashes++
	flashed[i][j] = true
	grid[i][j] = 0
	neighbors(i, j).each { inc(it[0], it[1], flashed) }
}
inc = {i, j, flashed ->
	if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
		if (grid[i][j] == 9) flash(i, j, flashed)
		else if (!flashed[i][j]) grid[i][j]++
	}
}
Integer.MAX_VALUE.times {
	Boolean[][] flashed = new Boolean[10][10]
	grid.eachWithIndex { line, i ->
		line.eachWithIndex { o, j -> inc(i, j, flashed)}
	}
	if (flashed.every { it.every { it }}) {
		println it+1
		System.exit 0
	}
}
