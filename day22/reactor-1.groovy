class Cuboid {
	int minX
	int maxX
	int minY
	int maxY
	int minZ
	int maxZ
	boolean on

	Cuboid(List<Integer> xRange, List<Integer> yRange, List<Integer> zRange, boolean on = true) {
		this.minX = xRange[0]
		this.maxX = xRange[1]
		this.minY = yRange[0]
		this.maxY = yRange[1]
		this.minZ = zRange[0]
		this.maxZ = zRange[1]
		this.on = on
	}

	def overlaps(Cuboid other) {
		return other.minX <= this.maxX && this.minX <= other.maxX
				&& other.minY <= this.maxY && this.minY <= other.maxY
				&& other.minZ <= this.maxZ && this.minZ <= other.maxZ
	}

	def split(Cuboid cut) {
		List<Cuboid> splits = []
		if (cut.minX > this.minX) {
			splits << new Cuboid([this.minX, cut.minX - 1], [this.minY, this.maxY], [this.minZ, this.maxZ])
		}
		if (cut.maxX < this.maxX) {
			splits << new Cuboid([cut.maxX + 1, this.maxX], [this.minY, this.maxY], [this.minZ, this.maxZ])
		}

		List<Integer> middleXRange = [Math.max(this.minX, cut.minX), Math.min(this.maxX, cut.maxX)];
		if (cut.minY > this.minY) {
			splits << new Cuboid(middleXRange, [this.minY, cut.minY - 1], [this.minZ, this.maxZ])
		}
		if (cut.maxY < this.maxY) {
			splits << new Cuboid(middleXRange, [cut.maxY + 1, this.maxY], [this.minZ, this.maxZ])
		}

		List<Integer> middleYRange = [Math.max(this.minY, cut.minY), Math.min(this.maxY, cut.maxY)];
		if (cut.minZ > this.minZ) {
			splits << new Cuboid(middleXRange, middleYRange, [this.minZ, cut.minZ - 1])
		}
		if (cut.maxZ < this.maxZ) {
			splits << new Cuboid(middleXRange, middleYRange, [cut.maxZ + 1, this.maxZ])
		}

		return splits
	}

	def volume() {
		((1 + this.maxX - this.minX) * (1 + this.maxY - this.minY) * (1 + this.maxZ - this.minZ))
	}
}

def parseInput = {input ->
	input.collect {String line ->
		List<String> parts = line.split(/\s/)
		List<List<Integer>> axisRanges = parts[1].split(',').collect {axis ->
			axis.split('=')[1].split(/\.\./).collect {it.toInteger()}
		}
		new Cuboid(axisRanges[0], axisRanges[1], axisRanges[2], parts[0] == 'on')
	}
}
List<Cuboid> cuboids = parseInput(new File('input.txt').text.split(/\n/))
def execute = {List<Cuboid> steps ->
	List<Cuboid> lit = []
	steps.each {step ->
		List<Cuboid> newlyLit = []
		lit.each {cuboid ->
			if (cuboid.overlaps(step)) {
				newlyLit.addAll(cuboid.split(step))
			} else {
				newlyLit << cuboid
			}
		}
		if (step.on) {
			newlyLit << step
		}
		lit = newlyLit
	}
	lit
}
def lit = execute(cuboids.findAll {
	![it.maxX, it.minX, it.maxY, it.minY, it.maxZ, it.minZ].any {Math.abs(it) > 50}
})
println lit.inject(0) {sum, c -> sum + c.volume()}
