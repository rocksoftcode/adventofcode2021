import java.util.concurrent.ConcurrentHashMap

List<String> parts = new File('input.txt').text.split('\n\n')
Set<Tuple<Integer>> markings = ConcurrentHashMap.newKeySet()
markings.addAll(parts[0].split('\n').collect {new Tuple(it.split(',')[0].toInteger(), it.split(',')[1].toInteger())})
List<Tuple<String>> folds = parts[1].split('\n').collect { def p = it.split('fold along ')[1].split('='); return new Tuple(p[0], p[1]) }
def fold = { points, fold ->
	String axis = fold[0]
	Integer value = fold[1].toInteger()
	for (marking in markings) {
		int x = marking[0]; int y = marking[1]; boolean folded = false
		if (axis == 'x' && x > value) {
			x = value - (x - value)
			folded = true
		} else if (axis == 'y' && y > value) {
			y = value - (y - value)
			folded = true
		}
		if (folded) {
			markings.remove(marking)
			markings << new Tuple(x,y)
		}
	}
}
folds.each {fold(markings, it) }
List<List<String>> grid = []
(0..<8).each { y ->
	grid[y] = []
	(0..<40).each {x ->
		grid[y][x] = (markings.find { it[0] == x && it[1] == y } ? '#' : ' ')
	}
}
grid.each { row ->
	row.each { print it }
	print '\n'
}