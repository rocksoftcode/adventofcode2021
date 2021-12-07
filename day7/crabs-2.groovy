List<Integer> positions = new File('input.txt').text.split(',')*.toInteger()
int mean = positions.inject(0) { sum, value -> sum + value } / positions.size()
int cost = 0
for (position in positions) {
	int distance = Math.abs(position - mean)
	cost += (distance * (distance + 1)) / 2
}
println cost
