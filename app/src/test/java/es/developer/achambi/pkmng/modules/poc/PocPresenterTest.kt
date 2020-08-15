package es.developer.achambi.pkmng.modules.poc

import es.developer.achambi.coreframework.threading.MainExecutor
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccess
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class PocPresenterTest {
    @Mock
    lateinit var executor: MainExecutor
    @Mock
    lateinit var screen: PocScreen
    @Mock
    lateinit var dataAccess: PokemonDataAccess
    @Mock
    lateinit var natureAccess: NatureDataAccess

    val initialMatrix = ArrayList<ArrayList<Int>>()
    lateinit var graph: Graph

    lateinit var presenterTest: PocPresenter
    @Before
    fun setUp() {
        presenterTest = PocPresenter(executor, screen, dataAccess, natureAccess)
        initialMatrix.clear()
        graph = Graph(initialMatrix)
    }

    @Test
    fun test() {
        repeat(2) {
            presenterTest.addEmptyNode(initialMatrix)
        }

        assertEquals(2, initialMatrix.size)
        assertEquals( 2, initialMatrix[0].size)
        assertEquals( 2, initialMatrix[1].size)
    }

    @Test
    fun testLinkToRoot() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        assertEquals(0, initialMatrix[0][0])
        assertEquals(10, initialMatrix[1][0])
        assertEquals(15, initialMatrix[2][0])
    }

    @Test
    fun linkFirstStepToNew() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,20)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,30)

        assertEquals(20, initialMatrix[3][1] )
        assertEquals(30, initialMatrix[3][2] )
    }

    @Test
    fun dijkstra() {
        val path = Path()
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,20)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,30)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,40)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,50)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 0, 1)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 1, 1)

        presenterTest.shortestPath(Graph(initialMatrix),0, 5, path)
        assertEquals(5, path.path[3])
        assertEquals(4, path.path[2])
        assertEquals(2, path.path[1])
        assertEquals(0, path.path[0])
        assertEquals(66, path.totalWeight)
    }

    @Test
    fun `list nodes when nothing has been removed`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        val result = graph.getAllNodes()

        assertEquals(0, result[0])
        assertEquals(1, result[1])
        assertEquals(2, result[2])
    }

    @Test
    fun `list nodes when node is removed`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        graph.removeNode(1)
        val result = graph.getAllNodes()

        assertEquals(0, result[0])
        assertEquals(2, result[1])
    }

    @Test
    fun `list nodes on removed not in the graph`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        graph.removeNode(5)
        val result = graph.getAllNodes()

        assertEquals(0, result[0])
        assertEquals(1, result[1])
        assertEquals(2, result[2])
    }

    @Test
    fun `neighbours on nothing removed`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        val result = graph.getAdjacencyList(0)

        assertEquals(1, result[0])
        assertEquals(2, result[1])
    }

    @Test
    fun `neighbours on node removed`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        graph.removeNode(1)
        val result = graph.getAdjacencyList(0)

        assertEquals(2, result[0])
        assertEquals(2, graph.getAllNodes().size)
    }

    @Test
    fun `neighbours on edge removed`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        graph.removeEdge(0, 1)
        val result = graph.getAdjacencyList(0)

        assertEquals(2, result[0])
        assertEquals(1, result.size)
    }

    @Test(expected = Exception::class)
    fun `neighbours on owner removed`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        graph.removeNode(0)
        graph.getAdjacencyList(0)
    }

    @Test
    fun `edge restored`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        graph.removeEdge(0, 1)
        graph.restore()
        val result = graph.getAdjacencyList(0)

        assertEquals(1, result[0])
        assertEquals(2, result[1])
        assertEquals(2, result.size)
    }

    @Test
    fun `node restored`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        graph.removeNode(1)
        graph.restore()
        val result = graph.getAdjacencyList(0)

        assertEquals(1, result[0])
        assertEquals(2, result[1])
        assertEquals(3, graph.getAllNodes().size)
    }

    @Test
    fun `no path removed things`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,20)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,20)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,30)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,30)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 0, 1)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 1, 1)

        graph.removeNode(0)
        graph.removeNode(1)
        graph.removeEdge(4, 5)

        val path = Path()
        presenterTest.shortestPath(graph,4, 5, path)

        assertEquals(0, path.path.size)
        assertEquals(0, path.totalWeight)
    }

    @Test
    fun `yen test`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)

        //first layer
        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,1)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,1)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,2)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,2)

        //second layer
        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 0,1)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 1,1)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 0,2)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 1,2)

        //sink
        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 0, 1)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 1, 1)

        val result = presenterTest.yens(graph, 7, 9)
        assertEquals(4, result.size)
    }

    @Test
    fun `dijkstra with nature multiplier`() {
        repeat(3) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)
        presenterTest.linkRootToNode(initialMatrix, 15, 1)
        //offset: number of nodes that were added on the previous layers (accumulated) until the next to the immediately previous one
        //position: relative position of node of the previous layer that I want to link to my new node
        //value: weight of the link

        //first layer
        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,1)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,1)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 0,2)
        presenterTest.linkPosToNewNode(initialMatrix, 1, 1,2)

        //second layer
        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 0,1)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 1,1)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 0,2)
        presenterTest.linkPosToNewNode(initialMatrix, 3, 1,2)

        //nature multiplier layer
        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 0, PocPresenter.NATURE_NEUTRAL_KEY)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 1, PocPresenter.NATURE_NEUTRAL_KEY)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 0, PocPresenter.NATURE_POSITIVE_KEY)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 1, PocPresenter.NATURE_POSITIVE_KEY)

        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 0, PocPresenter.NATURE_NEGATIVE_KEY)
        presenterTest.linkPosToNewNode(initialMatrix, 5, 1, PocPresenter.NATURE_NEGATIVE_KEY)

        //sink
        presenterTest.addEmptyNode(initialMatrix)
        presenterTest.linkPosToNewNode(initialMatrix, 7, 0, 1)
        presenterTest.linkPosToNewNode(initialMatrix, 7, 1, 1)
        presenterTest.linkPosToNewNode(initialMatrix, 7, 2, 1)

        presenterTest.printMatrix(initialMatrix)

        val result = Path()
        presenterTest.shortestPath(graph, 0, 10, result)

        assertEquals(6, result.path.size)
        assertEquals(10, result.path[5])
        assertEquals(8, result.path[4])
        assertEquals(6, result.path[3])
        assertEquals(4, result.path[2])
        assertEquals(2, result.path[1])
        assertEquals(0, result.path[0])
        assertEquals(21, result.totalWeight)
    }
}