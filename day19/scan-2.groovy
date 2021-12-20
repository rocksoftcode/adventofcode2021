def permute = { l ->
	def length = l.size()
	def result = [l.collect()]
	def c = [0] * length
	def i = 1
	def k
	def p

	while (i < length) {
		if (c[i] < i) {
			k = i % 2 != 0 ? c[i] : 0
			p = l[i]
			l[i] = l[k]
			l[k] = p
			c[i]++
			i = 1
			result << l.collect()
		} else {
			c[i] = 0;
			i++
		}
	}
	return result
}
def binarySearch = { arr, val, cmpFn ->
	int m = 0
	int n = arr.size() - 1
	while (m <= n) {
		int k = (n + m) >> 1
		int cmp = cmpFn(val, arr[k])
		if (cmp > 0) m = k + 1
		else if (cmp < 0) n = k - 1
		else return true
	}
	return false
}
List<String> groups = new File('input.txt').text.split(/\n{2,}/)
List<List<Integer>> scanners = []
groups.eachWithIndex { it, i ->
	List<String> lines = it.split(/\n/)
	lines.remove(0)
	scanners[i] = lines.collect { it.split(',').collect { it.toInteger() } }
}
List<List<Integer>> fullMap = scanners.remove(0)
fullMap.sort{ a, b -> (a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]) }
List<List<Integer>> permCoord = permute([0, 1, 2])
List<List<Integer>> multCoord = [
		[1, 1, 1],
		[1, 1, -1],
		[1, -1, 1],
		[-1, 1, 1],
		[1, -1, -1],
		[-1, -1, 1],
		[-1, 1, -1],
		[-1, -1, -1],
]
println "This will take a while.  Heat up some tea or something."
println ""

def scannerPos = [[0, 0, 0]];
outer: while (scanners.size() > 0) {
	for (int i = 0; i < scanners.size(); i++) {
		List<Integer> s = scanners[i];
		for (perm in permCoord) {
			for (mult in multCoord) {
				def rotated = s.collect { c -> [c[perm[0]] * mult[0], c[perm[1]] * mult[1], c[perm[2]] * mult[2]] };
				rotated.sort { a, b -> (a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]) }

				for (fullMapPoint in fullMap) {
					for (point in rotated) {
						def displacement = point.withIndex().collect { c, j -> fullMapPoint[j] - c }
						def pointsFound = []
						def pointsNotFound = []
						for (check in rotated) {
							def fixed = check.withIndex().collect { v, k -> v + displacement[k] }
							def found = binarySearch(fullMap, fixed, {a, b ->
								a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]
							})
							if (!found) {
								pointsNotFound << fixed
							} else {
								pointsFound << fixed
							}
							if (pointsNotFound.size() > s.size() - 12) break
						}

						if (pointsNotFound.size() <= s.size() - 12) {
							fullMap.addAll(pointsNotFound)
							fullMap.sort { a, b -> (a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]) }
							scanners.removeAt(i)
							scannerPos << displacement
							continue outer
						}
					}
				}
			}
		}
	}
}

def maxDist = 0
for (int i = 0; i < scannerPos.size() - 1; i++) {
	for (int j = i + 1; j < scannerPos.size(); j++) {
		def dist =
				Math.abs(scannerPos[i][0] - scannerPos[j][0]) +
						Math.abs(scannerPos[i][1] - scannerPos[j][1]) +
						Math.abs(scannerPos[i][2] - scannerPos[j][2])
		maxDist = Math.max(maxDist, dist)
	}
}
println maxDist