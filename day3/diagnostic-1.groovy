List<String> reports = new File('diagnostic.txt').text.split('\n')
String gamma = ''
String epsilon = ''
(0..reports[0].length()-1).each {i ->
	int zeroes
	int ones
	reports.each {
		if (it[i] == '0') zeroes++
		if (it[i] == '1') ones++
	}
	if (zeroes > ones) {
		gamma += '0'
		epsilon += '1'
	} else {
		gamma += '1'
		epsilon += '0'
	}
}
println Integer.parseInt(gamma,2) * Integer.parseInt(epsilon,2)
