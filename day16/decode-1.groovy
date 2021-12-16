int versionSum = 0
int pos = 0
def calculate
calculate = { bits ->
	int version = Integer.parseInt(bits.substring(pos, pos + 3), 2)
	int typeId = Integer.parseInt(bits.substring(pos + 3, pos + 6), 2)
	versionSum += version
	pos += 6
	if (typeId == 4) {
		boolean last = false
		StringBuilder data = new StringBuilder()
		while (!last) {
			String part = bits.substring(pos, pos + 5)
			if (part.startsWith("0")) last = true
			data << part.substring(1)
			pos += 5
		}
		return Long.parseLong(data.toString(), 2)
	} else {
		int lengthTypeId = Integer.parseInt(bits.substring(pos, pos + 1), 2)
		int l = lengthTypeId == 0 ? 15 : 11
		int length = Integer.parseInt(bits.substring(pos + 1, pos + 1 + l), 2)
		pos += l + 1
		List<Long> results = new ArrayList<>()
		if (lengthTypeId == 0) {
			while (length > 0) {
				int start = pos
				results.add(calculate(bits))
				length -= (pos - start)
			}
		} else {
			for (int i = 0; i < length; i++) {
				results.add(calculate(bits))
			}
		}
		return results
	}
}
String input = new File('input.txt').text
calculate(new BigInteger(input, 16).toString(2))
println versionSum