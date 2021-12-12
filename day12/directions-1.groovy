Map<String, List<String>> links = [:]
new File('input.txt').text.split('\n').each {
	List<String> parts = it.split('-')
	if (!links[parts[0]]) links[parts[0]] = [parts[1]]
	else links[parts[0]] << parts[1]
	if (!links[parts[1]]) links[parts[1]] = [parts[0]]
	else links[parts[1]] << parts[0]
}
def nav
def dirs = []
nav = { node, path ->
	List<String> curr = path.collect()
	curr << node
	if (node == 'end') dirs << curr
	else for (link in links[node]) if (link.toUpperCase() == link || !curr.contains(link)) nav(link, curr)
}
nav('start', [])
println dirs.size()