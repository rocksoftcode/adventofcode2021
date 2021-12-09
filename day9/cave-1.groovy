Integer[][] grid = new File('input.txt').text.split('\n')*.collect { it.toInteger() }
List<Tuple<Integer>> lows = []
final int NONE = 10
for (i in 0..<grid.length) {
	for (j in 0..<grid[i].length) {
		int curr = grid[i][j]
		int up = i == 0 ? NONE : grid[i-1][j]
		int down = i == grid.length - 1 ? NONE : grid[i+1][j]
		int left = j == 0 ? NONE : grid[i][j-1]
		int right = j == grid[i].length - 1 ? NONE : grid[i][j+1]
		if (curr < up && curr < down && curr < left && curr < right) lows << new Tuple(i, j)
	}
}
println lows.sum { grid[it[0]][it[1]]+1 }