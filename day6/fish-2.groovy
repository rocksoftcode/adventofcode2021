LinkedList<Integer> fish = new File('input.txt').text.split(',')*.toInteger()
List<Map<Integer, Long>> stages = [fish.collectEntries { [it, fish.count(it) as long] }]
(0..8).each {  if (!stages[0][it]) stages[0][it] = 0L }
(1..256).each {
	Map<Integer, Long> stage = [8:0]
	(0..<8).each { day -> stage[day] = stages[it-1][day+1] }
	stage[6] += stages[it-1][0]
	stage[8] += stages[it-1][0]
	stages << stage
}
println stages.last().values().sum()