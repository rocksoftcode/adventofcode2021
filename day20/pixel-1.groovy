List<String> groups = new File('input.txt').text.split(/\n{2,}/)
List<Boolean> algo = groups[0].split('').collect { it == '#' }
List<String> lines = groups[1].split('\n')
int width = lines[0].size()
int height = lines.size()

Set map = []
lines.eachWithIndex{String line, int i ->
	line.eachWithIndex{String chr, int j ->
		if (chr == "#") map << j+','+i
	}
}

def neighbors = { x, y, p ->
	List<String> res = []
	for (dy in [-1, 0, 1]) {
		for (dx in [-1, 0, 1]) {
			if (x + dx < p.minX || x + dx > p.maxX || y + dy < p.minY || y + dy > p.maxY) res << "void"
			else res << (x + dx) + ',' + (y + dy)
		}
	}
	return res
}

def pixel = { x, y, p, _void ->
	String bin = ""
	for (key in neighbors(x, y, [minX:p.minX, minY:p.minY, maxX:p.maxX, maxY:p.maxY])) {
		bin += key == "void" ? _void : map.contains(key) ? '1' : '0'
	}

	return algo[Integer.parseInt(bin, 2)];
}

Set next
2.times { i ->
	next = []
	for (int x = -1 - i; x < width + i + 1; x++)
		for (int y = -1 - i; y < height + i + 1; y++)
			if (pixel(x, y, [minX:0 - i, minY:0 - i, maxX:width + i - 1, maxY: height + i - 1], algo[0] ? i % 2 : 0)) next << x+','+y

	map = next
}
println map.size()