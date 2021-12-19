class Beacon {
	int x
	int y
	int z

	Beacon applyDelta(Beacon b) {
		return new Beacon(x: this.x + b.x, y: this.y + b.y, z: this.z + b.z)
	}

	Beacon rotate(String axis) {
		if (axis == 'x') return new Beacon(x: x, y: z, z: -y);
		if (axis == 'y') return new Beacon(x: -z, y: y, z: x);
		if (axis == 'z') return new Beacon(x: y, y: -x, z: z);
		return null
	}

	int distance(Beacon b) {
		return Math.abs(this.x - b.x) + Math.abs(this.y - b.y) + Math.abs(this.z - b.z);
	}
}

List<String> scannerTexts = new File('input.txt').text.split('\n\n')
Deque<Set<Beacon>> scanners = new LinkedList<>();
scannerTexts.each {
	List<String> scanner = it.split('\n')
	Set<Beacon> result = []
	for (i in 1..<scanner.size()) {
		def coords = scanner[i].split(',')*.toInteger()
		result << new Beacon(x: coords[0], y: coords[1], z: coords[2])
	}
	scanners << result
}

def scannerSet = new HashSet<Beacon>()
scannerSet.add(new Beacon(x: 0, y: 0, z: 0))
def nullSet = scanners.pop()
outer:
while (!scanners.isEmpty()) {
	def ns = scanners.pop();
	for (int j = 0; j < 6; j++) {
		for (int k = 0; k < 4; k++) {
			for (Beacon n : ns) {
				for (Beacon b : nullSet) {
					def delta = new Beacon(x: b.x - n.x, y: b.y - n.y, z: b.z - n.z)
					def shift = ns.collect {x -> x.applyDelta(delta)} as Set
					def count = 0
					for (Beacon beacon1 in shift) {
						if (nullSet.contains(beacon1)) count++
						if (count >= 12) {
							nullSet.addAll(shift)
							scannerSet << delta
							continue outer;
						}
					}
				}
			}
			ns = ns.collect {x -> x.rotate("x")} as Set
		}
		if (j < 5) {
			if (j < 3) ns = ns.collect {x -> x.rotate("y")} as Set
			if (j > 2) ns = ns.collect {x -> x.rotate("z")} as Set
			if (j == 4) ns = ns.collect {x -> x.rotate("z")} as Set
		}
	}
	scanners.add(ns);
}
def max = 0;
for (Beacon b1 : scannerSet) {
	for (Beacon b2 : scannerSet) {
		max = Math.max(max, b1.distance(b2));
	}
}
System.out.println(max);

