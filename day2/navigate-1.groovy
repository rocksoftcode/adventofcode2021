List<String> instructions = new File('instructions.txt').text.split('\n')
int horizon = 0
int depth = 0
instructions.each {
	List<String> parts = it.split(/\s/)
	String instruction = parts[0]
	Integer value = parts[1].toInteger()
	if (instruction == 'forward') horizon+=value
	if (instruction == 'up') depth-=value
	if (instruction == 'down') depth+=value
}
println horizon*depth