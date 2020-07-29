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

    lateinit var presenterTest: PocPresenter
    @Before
    fun setUp() {
        presenterTest = PocPresenter(executor, screen, dataAccess, natureAccess)
        initialMatrix.clear()
    }

    @Test
    fun test() {
        repeat(2) {
            presenterTest.addEmptyNode(initialMatrix)
        }

        assertEquals(2, initialMatrix.size)
        assertEquals( 2, initialMatrix[0].size)
    }

    @Test
    fun testLinkToRoot() {
        repeat(2) {
            presenterTest.addEmptyNode(initialMatrix)
        }
        presenterTest.linkRootToNode(initialMatrix, 10, 0)

        assertEquals(0, initialMatrix[0][0])
        assertEquals(10, initialMatrix[0][1])
    }
}