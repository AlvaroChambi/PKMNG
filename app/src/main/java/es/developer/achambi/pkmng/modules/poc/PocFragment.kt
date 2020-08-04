package es.developer.achambi.pkmng.modules.poc

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.view.View
import es.developer.achambi.coreframework.threading.Error
import es.developer.achambi.coreframework.threading.Response
import es.developer.achambi.coreframework.threading.ResponseHandler
import es.developer.achambi.coreframework.ui.BaseFragment
import es.developer.achambi.coreframework.ui.Presenter
import es.developer.achambi.coreframework.ui.Screen
import es.developer.achambi.pkmng.R
import es.developer.achambi.pkmng.core.AppWiring
import es.developer.achambi.pkmng.modules.overview.model.Pokemon
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccess
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess
import kotlinx.android.synthetic.main.poc_layout.*

class PocFragment: BaseFragment(), PocScreen {
    private lateinit var config: PokemonConfig
    private lateinit var presenter: PocPresenter
    companion object {
        val CONFIGURATION_KEY = "CONFIGURATION_KEY"
        fun newInstance(args: Bundle): PocFragment {
            val fragment = PocFragment()
            fragment.arguments = args
            return fragment
    }

        fun getArgs(config: PokemonConfig): Bundle {
            val args = Bundle()
            args.putParcelable(CONFIGURATION_KEY, config)
            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = arguments!!.getParcelable(CONFIGURATION_KEY)!!
        presenter = PocPresenter(AppWiring.executor, this,
                AppWiring.searchPokemonAssembler.pokemonDataAssembler.pokemonDataAccess as PokemonDataAccess,
                AppWiring.searchNatureAssembler.natureDataAssembler.natureDataAccess as NatureDataAccess)
    }

    override fun onViewSetup(view: View?, savedInstanceState: Bundle?) {
        pokemon_name_text.text = config.pokemon.name
        perform_query_button.setOnClickListener { presenter.buildMatrix() }
        presenter.onViewSetup()
    }

    override fun getLayoutResource(): Int {
        return R.layout.poc_layout
    }

    override fun screenLifecycle(): Lifecycle {
        return  lifecycle
    }

    override fun showNumberOfPokemons(total: Int) {
        pokemons_total_number_text.text = total.toString()
    }

    override fun showNumberOfNature(total: Int) {
        natures_number_text.text = total.toString()
    }
}

interface PocScreen: Screen {
    fun showNumberOfPokemons(total: Int)
    fun showNumberOfNature(total: Int)
}