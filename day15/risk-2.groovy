Integer[][] paths = new File('input.txt').text.split('\n').collect {it.split('')*.toInteger()}
Integer[][] expanded = [[0] * (paths[0].length * 5)] * (paths.length * 5)
for (i in 0..<expanded.length) {
	for (j in 0..<expanded[0].length) {
		expanded[i][j] = (((paths[i % paths.length][j % paths[0].length] + Math.floor(i / paths.length)
				+ Math.floor(j / paths[0].length) - 1) % 9) + 1).toInteger()
	}
}
final def steps = [
		[x: -1, y: 0],
		[x: 0, y: -1],
		[x: 1, y: 0],
		[x: 0, y: 1],
]
def calc = { grid ->
	Integer height = grid.size()
	Integer width = grid[0].size()

	def queue = [ v: [risk: 0, x: 0, y: 0] ]
	Map<String, Integer> risks = [:]

	while (true) {
		Integer risk = queue.v.risk
		Integer x = queue.v.x
		Integer y = queue.v.y
		if (x == width - 1 && y == height - 1) return risk

		for (step in steps) {
			Integer stepX = x + step.x
			Integer stepY = y + step.y
			if (stepX < 0 || stepX >= width || stepY < 0 || stepY >= height) continue
			Integer newRisk = risk + grid[stepY][stepX];
			String key = stepX + ':' + stepY
			if (!risks[key] || risks[key] > newRisk) {
				risks[key] = newRisk
				Map<String, ?> p = queue;
				while (p.n && p.n.v.risk < newRisk) p = p.n
				p.n = [ v: [risk: newRisk, x: stepX, y: stepY], n: p.n ]
			}
		}
		queue = queue.n
	}
}
println calc(expanded)