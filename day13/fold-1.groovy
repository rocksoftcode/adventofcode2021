import java.util.concurrent.ConcurrentHashMap

List<String> parts = new File('input.txt').text.split('\n\n')
Set<Tuple<Integer>> markings = ConcurrentHashMap.newKeySet()
markings.addAll(parts[0].split('\n').collect {new Tuple(it.split(',')[0].toInteger(), it.split(',')[1].toInteger())})
List<String> fold = parts[1].split('\n')[0].split('fold along ')[1].split('=')
String axis = fold[0]
Integer value = fold[1].toInteger()
for (marking in markings) {
	int x = marking[0]; int y = marking[1]; boolean folded = false
	if (axis == 'x' && x > value) {
		x = value - (x - value)
		folded = true
	} else if (axis == 'y' && y > value) {
		y = value - (y - value)
		folded = true
	}
	if (folded) {
		markings.remove(marking)
		markings << new Tuple(x,y)
	}
}
println markings.size()