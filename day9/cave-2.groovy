Integer[][] grid = new File('input.txt').text.split('\n')*.collect { it.toInteger() }
List<Tuple<Integer>> lows = []
def neighbors = { i, j ->
	[up: new Tuple(i-1, j),
	down: new Tuple(i+1, j),
	left: new Tuple(i, j-1),
	right: new Tuple(i, j+1)]
}
def oob = { i,j -> i < 0 || i == grid.length || j < 0 || j == grid[0].length }
def at = { c -> oob(c[0], c[1]) ? 10 : grid[c[0]][c[1]] }
for (i in 0..<grid.length) {
	for (j in 0..<grid[i].length) {
		int curr = grid[i][j]
		def n = neighbors(i, j)
		if (curr < at(n.up) && curr < at(n.down) && curr < at(n.left) && curr < at(n.right)) lows << new Tuple(i, j)
	}
}
def basinSize
basinSize = { y, x, t = [] ->
	def n = 1
	for (coord in neighbors(y, x).values()) {
		int i = coord[0]; int j = coord[1]
		if (oob(i, j) || t[i]?[j]) continue
		if (!t[i]) t[i] = []
		t[i][j] = true
		if (grid[i][j] < 9) n += basinSize(i, j, t)
	}
	return n
}
List<Integer> basins = []
for (low in lows) {
	int i = low[0]; int j = low[1]
	basins << basinSize(i, j)-1
}
def sorted = basins.sort { it * -1 }
println sorted[0]*sorted[1]*sorted[2]