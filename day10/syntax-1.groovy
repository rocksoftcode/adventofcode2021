/*): 3 points.
]: 57 points.
}: 1197 points.
>: 25137 points.*/
List<String> lines = new File('input.txt').text.split('\n')
long score = 0
def opens = /[\(\[\{<]/
def closes = /[\)\]\}>]/
Map<String, String> match = [')': '(', ']': '[', '}': '{', '>': '<']
Map<String, Integer> scores = [')': 3, ']': 57, '}': 1197, '>': 25137]
Stack<String> t = [] as Stack
lines.each {
	for (i in 0..<it.length()) {
		if (it[i] ==~ opens) t << it[i]
		if (it[i] ==~ closes) {
			String next = t.size() == 0 ? '' : t.peek()
			if (next != match[it[i]]) { score += scores[it[i]]; break }
			else t.pop()
		}
	}
}
println score