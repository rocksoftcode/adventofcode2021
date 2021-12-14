List<String> parts = new File('input.txt').text.split(/\n\n/)
String template = parts[0]
Map<String, String> rules = parts[1].split('\n').collectEntries { def p = it.split(' -> '); [(p[0]): p[1]] }
def replace = { polymer ->
	Queue<String> elements = polymer.toCharArray()*.toString() as Queue
	StringBuilder out = new StringBuilder()
	while (elements) {
		String c1 = elements.remove()
		out << c1
		if (elements) {String c2 = elements.peek(); if (rules["$c1$c2"]) out << rules["$c1$c2"] }
	}
	out
}
String polymer = template
10.times {polymer = replace(polymer) }
Set<Integer> counts = polymer.toCharArray()*.toString().groupBy {polymer.count(it) }.keySet()
println counts.max() - counts.min()