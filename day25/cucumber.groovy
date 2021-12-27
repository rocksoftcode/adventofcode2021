List<List<String>> grid = new File('input.txt').text.split(/\n/).collect { it.split('') }
int height = grid.size()
int width = grid[0].size()

boolean moved = true
int steps = 0

while (moved) {
	steps++
	moved = false

	List<List<String>> gridCopy = grid.collect { it.collect() }
	for (i in 0..<height) {
		for (j in 0..<width) {
			if (grid[i][j] == '>' && grid[i][(j + 1) % width] == '.') {
				gridCopy[i][j] = '.'
				gridCopy[i][(j + 1) % width] = '>'
				moved = true
			}
		}
	}
	grid = gridCopy.collect { it.collect() }
	for (i in 0..<height) {
		for (j in 0..<width) {
			if (gridCopy[i][j] == 'v' && gridCopy[(i + 1) % height][j] == '.') {
				grid[i][j] = '.'
				grid[(i + 1) % height][j] = 'v'
				moved = true
			}
		}
	}
}

println steps