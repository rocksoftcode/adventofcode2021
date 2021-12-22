class Cuboid {
	BigInteger minX
	BigInteger maxX
	BigInteger minY
	BigInteger maxY
	BigInteger minZ
	BigInteger maxZ
	Boolean on

	Cuboid(List<BigInteger> xRange, List<BigInteger> yRange, List<BigInteger> zRange, boolean on = true) {
		this.minX = xRange[0]
		this.maxX = xRange[1]
		this.minY = yRange[0]
		this.maxY = yRange[1]
		this.minZ = zRange[0]
		this.maxZ = zRange[1]
		this.on = on
	}

	def max(BigInteger a, BigInteger b) {
		a > b ? a : b
	}
	def min(BigInteger a, BigInteger b) {
		a > b ? b : a
	}

	def overlaps(Cuboid other) {
		return other.minX <= this.maxX && this.minX <=  other.maxX
				&& other.minY <= this.maxY && this.minY <=  other.maxY
				&& other.minZ <= this.maxZ && this.minZ <=  other.maxZ
	}

	def split(Cuboid cuts) {
		List<Cuboid> splits = []
		if (cuts.minX > this.minX){
			splits << new Cuboid([this.minX, cuts.minX - 1], [this.minY, this.maxY], [this.minZ, this.maxZ])
		}
		if (cuts.maxX < this.maxX){
			splits << new Cuboid([cuts.maxX + 1, this.maxX], [this.minY, this.maxY], [this.minZ, this.maxZ])
		}

		List<BigInteger> middleXRange = [max(this.minX, cuts.minX), min(this.maxX, cuts.maxX)];
		if (cuts.minY > this.minY){
			splits << new Cuboid(middleXRange, [this.minY, cuts.minY - 1], [this.minZ, this.maxZ])
		}
		if (cuts.maxY < this.maxY){
			splits << new Cuboid(middleXRange, [cuts.maxY + 1, this.maxY], [this.minZ, this.maxZ])
		}

		List<BigInteger> middleYRange = [max(this.minY, cuts.minY), min(this.maxY, cuts.maxY)];
		if (cuts.minZ > this.minZ){
			splits << new Cuboid(middleXRange, middleYRange, [this.minZ, cuts.minZ - 1])
		}
		if (cuts.maxZ < this.maxZ){
			splits << new Cuboid(middleXRange, middleYRange, [cuts.maxZ + 1, this.maxZ])
		}

		return splits
	}

	def volume() {
		((1 + this.maxX - this.minX) * (1 + this.maxY - this.minY) * (1 + this.maxZ - this.minZ))
	}
}
def parseInput = { input ->
		input.collect { String line ->
			List<String> parts = line.split(/\s/)
			List<List<BigInteger>> axisRanges = parts[1].split(',').collect { axis ->
				axis.split('=')[1].split(/\.\./).collect { it.toBigInteger() }
			}
			new Cuboid(axisRanges[0], axisRanges[1], axisRanges[2], parts[0] == 'on')
		}
}
List<Cuboid> cuboids = parseInput(new File('input.txt').text.split(/\n/))
def execute = { List<Cuboid> steps ->
	List<Cuboid> lit = []
	steps.each { step ->
		List<Cuboid> newlyLit = []
		lit.each { cuboid ->
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
println execute(cuboids).inject(new BigInteger("0")) { BigInteger sum, c -> sum + c.volume() }
