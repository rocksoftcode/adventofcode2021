class Operation {
	String code
	String target
	def value
}
List<Operation> operations = new File('input.txt').text.split(/\n/).collect {
	List<String> parts = it.split(' ')
	new Operation(code: parts[0], target: parts[1], value: parts.size() > 2 ? parts[2] : 0d)
}
List<Double> options = []
for (def i = 0; i < 18*14; i += 18) {
	Double p1 = new Double(operations[i + 4].value)
	Double p2 = new Double(operations[i + 5].value)
	Double p3 = new Double(operations[i + 15].value)
	options << [p1, p2, p3]
}

def fn = { params, zv, w ->
	double z = zv.toDouble()
	if ((z%26d + params[1]) != w)
		return Math.floor(z / params[0]) * 26d + w + params[2]
	return Math.floor(z / params[0])
}
Map<Double, Double> zMax = [0d: 0d]

options.each { param ->
	Map<Double, Double> zMaxn = [:]
	for (def z in zMax.keySet()) {
		for (double inputDigit = 1; inputDigit <= 9; inputDigit++) {
			def newZ = fn(param, z, inputDigit);
			if (param[0] == 1d || (param[0] == 26d && newZ < z)) {
				if (!zMaxn[newZ]) {
					zMaxn[newZ] = zMax[z] * 10 + inputDigit;
				} else {
					zMaxn[newZ] = Math.max(zMaxn[newZ], zMax[z] * 10d + inputDigit);
				}
			}
		}
	}
	zMax = zMaxn.clone() as Map
}
println new BigDecimal(zMax[0d]).toPlainString()