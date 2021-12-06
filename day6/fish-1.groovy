List<Integer> fish = new File('input.txt').text.split(',')*.toInteger()
80.times {
	for (i in 0..<fish.size()) {
		if (fish[i] == 0) {
			fish[i] = 6
			fish << 8
		} else fish[i]--
	}
}
println fish.size()