def components = new File('input.txt').text.split(': ')[1].split(', ')
def px = components[0].split('x=')[1].split(/\.\./)*.toInteger()
def py = components[1].split('y=')[1].split(/\.\./)*.toInteger()
def minx = px[0]; maxx = px[1]; miny = py[0]; maxy = py[1]
def go = { v, min, max ->
	Tuple<Integer> pos = [0,0]
	boolean impact = false
	List<Tuple<Integer>> positions = []
	while (!impact && pos[0] <= max[0] && pos[1] >= min[1]) {
		pos = pos.withIndex().collect {
			p, i -> p + v[i]
		}
		positions << pos
		v[0] -= v[0] > 0 ? 1 : 0
		v[1]--
		impact = pos.withIndex().every { p, i -> p >= min[i] && p <= max[i] }
	}
	return impact ? positions.collect { it[1] }.inject { mx, mv -> Math.max(mx, mv) } : Integer.MIN_VALUE
}
Integer minvx = Math.floor(Math.sqrt(minx * 2));
def bin = []
(Math.abs(miny) << 1).times { y ->
	(1 + maxx - minvx).times { x ->
		bin << [x + minvx, miny + y]
	}
}
println bin.collect { go(it, [minx, miny], [maxx, maxy])}.findAll { it != Integer.MIN_VALUE }.size()

