List<Integer> depths = new File('depths.txt').text.split('\n').collect { it.toInteger() }
int prev = 0
int increases = 0
(2..depths.size() -1).each {i ->
	int sum = depths[i-2] + depths[i-1] + depths[i]
	if(prev != 0 && sum > prev) increases++
	prev = sum
}
println increases