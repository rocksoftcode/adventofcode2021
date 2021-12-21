def wrap = { it > 10 ? it - 10 : it }
List<String> input = new File('input.txt').text.split(/\n/)
List<Integer> spots = [input[0].split(' ').last().toInteger(), input[1].split(' ').last().toInteger()]
Integer dice = 1
Integer score1 = 0
Integer score2 = 0
Integer rolls = 0
def move = {
	Integer d = dice + dice + 1 + dice + 2
	dice = dice + 3
	rolls += 3
	wrap(it + (d % 10))
}
while (true) {
	spots[0] = move(spots[0])
	score1 += spots[0]
	if (score1 >= 1000) { println score2 * rolls; break }
	spots[1] = move(spots[1])
	score2 += spots[1]
	if (score2 >= 1000) { println score1 * rolls; break }
}