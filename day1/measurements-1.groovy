List<Integer> measurements = new File('depths.txt').text.split('\n').collect {it.toInteger() }
int increases = 0
(0..measurements.size() - 2).each {i ->
	if (measurements[i+1] > measurements[i]) increases++
}
println increases