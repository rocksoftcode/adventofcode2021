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

	List<List<List<Integer>>> rowMatch = boards.findAll {board -> board.any {row -> row[0] + row[1] + row[2] + row[3] + row[4] == -5 } }
	List<List<List<Integer>>> colMatch = boards.findAll {board -> for (col in 0..<5) if (board[0][col] + board[1][col] + board[2][col] + board[3][col] + board[4][col] == -5) return true; return false }

	if (boards.size() == 1) {
		println addAll(boards[0]) * call
		System.exit 0
	} else if (rowMatch || colMatch) {
		boards.removeAll(rowMatch)
		boards.removeAll(colMatch)
	}
}
println boards.size()
