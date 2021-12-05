class Line {
	int x1
	int y1
	int x2
	int y2

	int maxX() {
		Math.max(x1, x2)
	}

	int maxY() {
		Math.max(y1, y2)
	}
}

List<Line> lines = new File('input.txt').text.split('\n').collect {
	List<String> parts = it.split(/\s->\s/)
	List<Integer> line1 = parts[0].split(',')*.toInteger()
	List<Integer> line2 = parts[1].split(',')*.toInteger()
	new Line(x1: line1[0], y1: line1[1], x2: line2[0], y2: line2[1])
}
int x = lines.max {Math.max(it.x1, it.x2)}.maxX() + 1
int y = lines.max {Math.max(it.y1, it.y2)}.maxY() + 1
int[][] grid = new int[y][x]
lines.each {
	if (it.x1 == it.x2 || it.y1 == it.y2) {
		int minY = Math.min(it.y1, it.y2)
		int maxY = Math.max(it.y1, it.y2)
		int minX = Math.min(it.x1, it.x2)
		int maxX = Math.max(it.x1, it.x2)
		for (i in minY..maxY) {
			for (j in minX..maxX) {
				grid[i][j]++
			}
		}
	}
}
println grid.collect().sum { it.collect().count { it > 1 } }
