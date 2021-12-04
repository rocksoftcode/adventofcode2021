String raw = new File('input.txt').text
List<String> lines = raw.split('\n')
List<Integer> calls = lines[0].split(',')*.toInteger()
List<List<List<Integer>>> boards = []
int line = 1
while (line++ < lines.size()) {
	List<List<Integer>> board = []
	board << lines[line++].trim().split(/\s+/)*.toInteger()
	board << lines[line++].trim().split(/\s+/)*.toInteger()
	board << lines[line++].trim().split(/\s+/)*.toInteger()
	board << lines[line++].trim().split(/\s+/)*.toInteger()
	board << lines[line++].trim().split(/\s+/)*.toInteger()
	boards << board
}
def addAll = { List<List<Integer>> board ->
	board.sum { it.sum {Math.max(it, 0) }}
}
calls.each {call ->
	boards.each {board ->
		board.each {row -> Collections.replaceAll(row, call, -1)}
	}
	List<List<Integer>> rowMatch = boards.find {board -> board.any {row -> row.sum() == -5}}
	List<List<Integer>> colMatch = boards.find {board ->
		for (col in 0..<5) {
			int sum = 0
			for (row in 0..<5) {
				sum += board[row][col]
			}
			if (sum == -5) return true
		}
		return false
	}
	if (rowMatch || colMatch) {
		println addAll(rowMatch ?: colMatch) * call
		System.exit 0
	}
}
