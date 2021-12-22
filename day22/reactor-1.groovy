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

	def overlaps(Cuboid otherCuboid) {
		return otherCuboid.minX <= this.maxX && this.minX <= otherCuboid.maxX
				&& otherCuboid.minY <= this.maxY && this.minY <= otherCuboid.maxY
				&& otherCuboid.minZ <= this.maxZ && this.minZ <= otherCuboid.maxZ
	}

	def split(Cuboid cuttingCuboid) {
		List<Cuboid> splitCuboids = []
		if (cuttingCuboid.minX > this.minX) {
			splitCuboids << new Cuboid([this.minX, cuttingCuboid.minX - 1], [this.minY, this.maxY], [this.minZ, this.maxZ])
		}
		if (cuttingCuboid.maxX < this.maxX) {
			splitCuboids << new Cuboid([cuttingCuboid.maxX + 1, this.maxX], [this.minY, this.maxY], [this.minZ, this.maxZ])
		}

		List<Integer> middleXRange = [Math.max(this.minX, cuttingCuboid.minX), Math.min(this.maxX, cuttingCuboid.maxX)];
		if (cuttingCuboid.minY > this.minY) {
			splitCuboids << new Cuboid(middleXRange, [this.minY, cuttingCuboid.minY - 1], [this.minZ, this.maxZ])
		}
		if (cuttingCuboid.maxY < this.maxY) {
			splitCuboids << new Cuboid(middleXRange, [cuttingCuboid.maxY + 1, this.maxY], [this.minZ, this.maxZ])
		}

		List<Integer> middleYRange = [Math.max(this.minY, cuttingCuboid.minY), Math.min(this.maxY, cuttingCuboid.maxY)];
		if (cuttingCuboid.minZ > this.minZ) {
			splitCuboids << new Cuboid(middleXRange, middleYRange, [this.minZ, cuttingCuboid.minZ - 1])
		}
		if (cuttingCuboid.maxZ < this.maxZ) {
			splitCuboids << new Cuboid(middleXRange, middleYRange, [cuttingCuboid.maxZ + 1, this.maxZ])
		}

		return splitCuboids
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
