import groovy.json.JsonBuilder
def wrap = { it > 10 ? it - 10 : it }
List<String> input = new File('input.txt').text.split(/\n/)
List<Integer> spots = [input[0].split(' ').last().toInteger(), input[1].split(' ').last().toInteger()]
Map cache = [:]

def wins
wins = { pos, score, player ->
	String key = new JsonBuilder([pos, score, player]).toString()
	def cached = cache[key]
	if (cached) return cached

	if (score[0] >= 21) {
		cached = [1.toBigInteger(), 0.toBigInteger()]
	} else if (score[1] >= 21) {
		cached = [0.toBigInteger(), 1.toBigInteger()]
	} else {
		cached = [0, 0]
		for (d1 in [1, 2, 3]) {
			for (d2 in [1, 2, 3]) {
				for (d3 in [1, 2, 3]) {
					Integer d = d1 + d2 + d3
					List<BigInteger> dPos = pos.collect()
					List<BigInteger> dScore = score.collect()
					dPos[player] = wrap(dPos[player] + (d % 10))
					dScore[player] += dPos[player]
					List<BigInteger> w = wins(dPos, dScore, player == 0 ? 1 : 0)
					cached[0] += w[0]
					cached[1] += w[1]
				}
			}
		}
	}

	cache[key] = cached
	cached
}

List<BigInteger> ws = wins([spots[0], spots[1]], [0, 0], 0)
println ws[0] > ws[1] ? ws[0] : ws[1] // Math.max doesn't work with BigIntegers :(