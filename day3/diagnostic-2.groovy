def removeIfMoreThanOne = { col, i, ch -> if (col.size() > 1) col.removeAll { it[i] == ch } }
List<String> reports = new File('diagnostic.txt').text.split('\n')
List<String> oxy = reports.collect()
List<String> co2 = reports.collect()
int length = reports[0].length() - 1
def criteria = [[oxy, 1, 0], [co2, 0, 1]]
(0..length).each {i ->
	criteria.each {c ->
		int count0 = c[0].count {it[i] == '0'}
		int count1 = c[0].count {it[i] == '1'}
		if (count0 > count1) {
			removeIfMoreThanOne(c[0], i, c[1].toString())
		}
		else if (count1 > count0 || count1 == count0) {
			removeIfMoreThanOne(c[0], i, c[2].toString())
		}
	}
}
println Integer.parseInt(oxy[0], 2) * Integer.parseInt(co2[0], 2)