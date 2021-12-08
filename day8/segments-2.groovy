List<String[]> input = new File('input.txt').text.split('\n')*.split(/\s\|\s/)
def sort = {it.split('').sort().join('')}
def containsAll = {a, b -> a.split('').every {b.split('').contains(it)}}
println input.sum {
	List<String> signal = it[0].split(/\s/).sort { it.length() }
	Map<Integer, String> decode = [1: sort(signal[0]), 7: sort(signal[1]), 4: sort(signal[2]), 8: sort(signal[9])]
	Map<String, Integer> recode = [(decode[1]): 1, (decode[4]): 4, (decode[7]): 7, (decode[8]): 8]
	(3..<9).each {
		def seq = sort(signal[it])
		if (seq.length() == 6) {
			if (containsAll(decode[4], seq)) {
				decode[9] = seq
				recode[seq] = 9
			} else if (containsAll(decode[7], seq)) {
				decode[0] = seq
				recode[seq] = 0
			} else {
				decode[6] = seq
				recode[seq] = 6
			}
		} else {
			if (containsAll(decode[7], seq)) {
				decode[3] = seq
				recode[seq] = 3
			} else if (decode[4].findAll("[$seq]").size() == 3) {
				decode[5] = seq
				recode[seq] = 5
			} else {
				decode[2] = seq
				recode[seq] = 2
			}
		}
	}
	it[1].split(/\s/).collect {recode[sort(it)] }.join('').toLong()
}
