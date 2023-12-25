package viewModel.map

class MapCheck {

    fun checkSandConnectivity(tileMap: MutableMap<Pair<Int, Int>, String>): Boolean {
        val sandTiles = tileMap.filterValues { it == "/map/sand.png" }.keys.toMutableSet()
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = mutableListOf<Pair<Int, Int>>()

        val startTile = sandTiles.firstOrNull()
        if (startTile != null) {
            queue.add(startTile)
            visited.add(startTile)
            sandTiles.remove(startTile)

            while (queue.isNotEmpty()) {
                val currentTile = queue.removeAt(0)
                println("Tile at position (${currentTile.second}, ${currentTile.first})")


                // Check neighbors
                val neighbors = getSandNeighbors(tileMap, currentTile)
                for (neighbor in neighbors) {
                    if (neighbor !in visited) {
                        visited.add(neighbor)
                        queue.add(neighbor)
                        sandTiles.remove(neighbor)
                    }
                }
            }
        }
        return if (sandTiles.isNotEmpty()) {
            println("Not all sand tiles are connected.")
            false
        } else {
            println("All sand tiles are connected.")
            true
        }
    }

    private fun getSandNeighbors(tileMap: MutableMap<Pair<Int, Int>, String>, tile: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (row, col) = tile
        val neighbors = mutableListOf<Pair<Int, Int>>()

        // Check top, bottom, left, and right neighbors
        val possibleNeighbors = listOf(Pair(row - 1, col), Pair(row + 1, col), Pair(row, col - 1), Pair(row, col + 1))

        for (neighbor in possibleNeighbors) {
            if (neighbor in tileMap.keys && tileMap[neighbor] == "/map/sand.png") {
                neighbors.add(neighbor)
            }
        }

        return neighbors
    }

    //------------------------------------------------------------------------

    fun checkCity(tileMap: MutableMap<Pair<Int, Int>, String>): Boolean {
        val cityTiles = tileMap.filterValues { it == "/map/city.jpg" }.keys.toMutableSet()
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = mutableListOf<Pair<Int, Int>>()

        val startTile = cityTiles.firstOrNull()
        if (startTile != null) {
            queue.add(startTile)
            visited.add(startTile)
            cityTiles.remove(startTile)

            while (queue.isNotEmpty()) {
                val currentTile = queue.removeAt(0)
                println("Tile at position (${currentTile.second}, ${currentTile.first})")

                val neighbors = getCityNeighbors(tileMap, currentTile)
                for (neighbor in neighbors) {
                    if (neighbor !in visited) {
                        visited.add(neighbor)
                        queue.add(neighbor)
                        cityTiles.remove(neighbor)
                    }
                }
            }
        }
        if (cityTiles.isNotEmpty()) {
            println("Not all city tiles are connected.")
            return false
        } else {
            println("All city tiles are connected.")
            return true
        }
    }

    private fun getCityNeighbors(tileMap: MutableMap<Pair<Int, Int>, String>, tile: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (row, col) = tile
        val neighbors = mutableListOf<Pair<Int, Int>>()

        val possibleNeighbors = listOf(Pair(row - 1, col), Pair(row + 1, col), Pair(row, col - 1), Pair(row, col + 1))

        for (neighbor in possibleNeighbors) {
            if (neighbor in tileMap.keys && tileMap[neighbor] == "/map/city.jpg") {
                neighbors.add(neighbor)
            }
        }
        return neighbors
    }

    fun checkCityConnectedToRoad(tileMap: MutableMap<Pair<Int, Int>, String>): Boolean {
        val cityTiles = tileMap.filterValues { it == "/map/city.jpg" }.keys.toMutableSet()
        val sandTiles = tileMap.filterValues { it == "/map/sand.png" }.keys.toMutableSet()

        for (cityTile in cityTiles) {
            for(sandTile in sandTiles){
                val row = sandTile.first
                val col = sandTile.second
                val possibleNeighbors = listOf(Pair(row - 1, col), Pair(row + 1, col), Pair(row, col - 1), Pair(row, col + 1))
                if ( cityTile in possibleNeighbors) {
                    return true
                }
            }
        }
        return false
    }

}