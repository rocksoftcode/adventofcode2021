List<Integer> positions = new File('input.txt').text.split(',')*.toInteger()
int bestCost = Integer.MAX_VALUE
for (position in positions) {
	int cost = 0
	for (i in 0..<positions.size()) {
		int p = Math.abs(position - positions[i])
		cost += (p*(p+1))/2
	}
	bestCost = Math.min(bestCost, cost)
}
println bestCost
