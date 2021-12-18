import groovy.json.JsonSlurper

class PairNode {
		def depth = 0
		def value = null
		def left = null
		def right = null
		def parent = null

	def get() {
		if(this.value !== null)
			return this.value
		else return [this.left.get(), this.right.get()]
	}

	def rightmost() {
		if (this.value !== null)
			return this
		return this.right.rightmost()
	}

	def leftmost() {
		if (this.value !== null)
			return this
		return this.left.leftmost()
	}

	def firstLeft() {
		if(this.parent){
			if(this.parent.left !== this)
				return this.parent.left.rightmost()
			else
				return this.parent.firstLeft()
		} else {
			return null
		}
	}

	def firstRight() {
		if(this.parent){
			if(this.parent.right !== this)
				return this.parent.right.leftmost()
			else
				return this.parent.firstRight()
		} else {
			return null
		}
	}

	def next() {
		if(this.value !== null)
			return null
		if(this.depth === 4)
			return this
		def left = this.left.next()
		if(left !== null && left.value === null)
			return left
		def right = this.right.next()
		if(right !== null && right.value === null)
			return right
		return null
	}

	def nextSplit() {
		if (this.value !== null) {
			if(this.value >= 10)
				return this
		} else {
			def leftSplit = this.left.nextSplit()
			if (leftSplit !== null)
				return leftSplit
			def rightSplit = this.right.nextSplit()
			if (rightSplit !== null)
				return rightSplit
		}
		return null
	}

	def explode() {
		def left = this.firstLeft()
		def right = this.firstRight()
		if(left !== null)
			left.value += this.left.value
		if(right !== null)
			right.value += this.right.value
		this.left = null
		this.right = null
		this.value = 0
	}

	def split() {
		def left = new PairNode()
		left.parent = this
		left.depth = this.depth +1
		left.value = Math.floor(this.value/2)
		this.left = left

		def right = new PairNode()
		right.parent = this
		right.depth = this.depth +1
		right.value = Math.ceil(this.value/2)
		this.right = right

		this.value = null
	}

	def magnitude() {
		if(this.value !== null)
			return this.value
		return this.left.magnitude() * 3 + this.right.magnitude() * 2
	}

	def reduce() {
		def reduced = false
		while (!reduced){
			reduced = true
			def explode = this.next()
			def split = this.nextSplit()
			if(explode !== null) {
				reduced = false
				explode.explode()
			} else if(split !== null) {
				reduced = false
				split.split()
			}
		}
		return this
	}
}

def create
create = { depth, from ->
	PairNode current = new PairNode()
	current.depth = depth

	if (from instanceof Number) {
		current.value = from;
	} else {
		PairNode left = create(depth+1 , from[0])
		left.parent = current
		current.left = left
		PairNode right = create(depth+1, from[1])
		right.parent = current
		current.right = right
	}
	return current
}

List inputs = new File('input.txt').text.split('\n').collect { new JsonSlurper().parseText(it) }
def pairs = []
for (i in 0..<inputs.size()) {
	for (j in 0..<inputs.size()) {
		if (i != j) pairs.push([inputs[i], inputs[j]])
	}
}
println pairs.collect {create(0, it).reduce()}.collect {it.magnitude()}.flatten().max().toInteger()