List<String[]> input = new File('input.txt').text.split('\n')*.split(/\s\|\s/)
Map<Integer, Integer> key = [7: 3, 4: 4, 1: 2, 8: 7]
println input.sum { it[1].split(/\s/).count { it.length() in key.values() } }