List<String> parts = new File('input.txt').text.split(/\n\n/)
Map<String, String> rules = parts[1].split('\n').collectEntries { def p = it.split(' -> '); [(p[0]): p[1]] }
String template = parts[0]
Map<String, Long> countLetters = [(template[0].toString()):1L]
Map<String, Long> countPairs = [:]
for (i in 1..<template.length()) {
	countPairs[template[i-1]+template[i]] = 1L + (countPairs[template[i-1]+template[i]] ?: 0L)
	countLetters[template[i]] = 1L + (countLetters[template[i]] ?: 0L)
}
40.times {
	Map<String, Long> newPairCount = [:]
	rules.each {
		if (countPairs[it.key]) {
			String insert = it.value
			String pairLeft = it.key[0].toString() + insert
			String pairRight = insert + it.key[1]
			Long count = countPairs[it.key]
			countPairs[it.key] = 0L
			countLetters[insert] = count + (countLetters[insert] ?: 0L)
			[pairLeft,pairRight].each{ newPairCount[it] = count + (newPairCount[it] ?: 0L) }
		}
	}
	newPairCount.each {countPairs[it.key] = it.value + (countPairs[it.key] ?: 0) }
}
println countLetters.max { it.value }.value - countLetters.min { it.value }.value