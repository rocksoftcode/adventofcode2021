List<String> lines = new File('input.txt').text.split('\n')
def opens = /[\(\[\{<]/
def closes = /[\)\]\}>]/
Map<String, String> match = [')': '(', ']': '[', '}': '{', '>': '<']
List<Stack<String>> lineStacks = [] as Stack
lines.eachWithIndex {it, n ->
	Stack t = []; boolean ok = true
	for (i in 0..<it.length()) {
		if (it[i] ==~ opens) t << it[i]
		if (it[i] ==~ closes) {
			String next = t.size() == 0 ? '' : t.peek()
			if (next != match[it[i]]) { ok = false; break }
			else t.pop()
		}
	}
	if (ok) lineStacks << t
}
List<Long> scores = []
Map<String, Integer> scoreMap = ['(': 1, '[': 2, '{': 3, '<': 4]
lineStacks.each {
	long score = 0
	it.reverse().each {score = (score * 5) + scoreMap[it] }
	scores << score
}
println scores.sort()[Math.floor(scores.size() / 2) as int]