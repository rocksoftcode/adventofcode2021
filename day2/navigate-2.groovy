List<String> instructions = new File('instructions.txt').text.split('\n')
int horizon = 0
int depth = 0
int aim = 0
instructions.each {
	List<String> parts = it.split(/\s/)
	String instruction = parts[0]
	Integer value = parts[1].toInteger()
	if (instruction == 'forward') {
		horizon += value
		depth += aim * value
	}
	if (instruction == 'up') {
		aim -= value
	}
	if (instruction == 'down') {
		aim += value
	}
}
println horizon*depth