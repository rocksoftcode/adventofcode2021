List<Integer> positions = new File('input.txt').text.split(',')*.toInteger()
int bestCost = Integer.MAX_VALUE
for (position in 0..positions.max()) {
	int cost = 0
	for (i in 0..<positions.size()) {
		cost += Math.abs(position - positions[i])
	}
	bestCost = Math.min(bestCost, cost)
}
println bestCost