package es.developer.achambi.pkmng.modules.speed_calculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import es.developer.achambi.coreframework.ui.BaseFragment
import es.developer.achambi.pkmng.R
import es.developer.achambi.pkmng.modules.create.screen.ConfigurationFragment
import es.developer.achambi.pkmng.modules.create.screen.StatEVView
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig
import es.developer.achambi.pkmng.modules.overview.model.Stat
import es.developer.achambi.pkmng.modules.search.configuration.screen.SearchConfigurationActivity
import es.developer.achambi.pkmng.modules.search.configuration.screen.presentation.ConfigurationPresentation
import es.developer.achambi.pkmng.modules.search.nature.model.Nature
import es.developer.achambi.pkmng.modules.search.nature.screen.SearchNatureActivity
import kotlinx.android.synthetic.main.speed_configuration_layout.*

class SpeedConfigurationFragment: BaseFragment() {
    private var config: PokemonConfig? = null

    companion object {
        val CONFIGURATION_KEY = "CONFIGURATION_KEY"
        val SEARCH_NATURE_CODE = 101

        fun newInstance(args: Bundle): SpeedConfigurationFragment {
            val fragment = SpeedConfigurationFragment()
            fragment.arguments = args
            return fragment
        }

        fun getArgs(config: PokemonConfig): Bundle {
            val args = Bundle()
            args.putParcelable(CONFIGURATION_KEY, config)
            return args
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.speed_configuration_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = arguments?.getParcelable(CONFIGURATION_KEY)
    }

    override fun onViewSetup(view: View?, savedInstanceState: Bundle?) {
        if(config != null) {
            speed_calculator_header.visibility = View.VISIBLE
            val presentation = ConfigurationPresentation.Builder.buildPresentation(activity, config)
            speed_calculator_pokemon_name.text = presentation.pokemon.name
            speed_calculator_base_speed.text = presentation.pokemon.stats.speed
            speed_calculator_total_speed.text = getString( R.string.speed_calculator_total_speed_tag,
                    config?.speed.toString())

            speed_calculator_nature.text = config?.nature?.name
            speed_calculator_nature.setOnClickListener {
                startActivityForResult(SearchNatureActivity.getStartIntent(activity, config?.nature),
                SEARCH_NATURE_CODE)

            }

            speed_calculator_speed_ev.setNatureModifier(config?.nature)
            val progressUpdate = object: StatEVView.ProgressUpdateProvider {
                override fun requestValueIncrement(stat: Stat?, progress: Int): Int {
                    return progress
                }

                override fun requestTotalStatValuePreview(stat: Stat?, value: Int): Int {
                    config?.configuration?.evsSet?.speed = value
                    return config?.speed!!
                }

            }
            speed_calculator_speed_ev.setProgressUpdateProvider(progressUpdate)
            config?.configuration?.evsSet?.speed?.let { speed_calculator_speed_ev.setValue(it) }
        } else {
            speed_calculator_add_image.visibility = View.VISIBLE
            speed_calculator_add_image.setOnClickListener {
                SearchConfigurationActivity.getStartIntent(activity, null) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SEARCH_NATURE_CODE && resultCode == Activity.RESULT_OK) {
            speed_calculator_nature.text = data?.getParcelableExtra<Nature>(
                    ConfigurationFragment.NATURE_ACTIVITY_RESULT_DATA_KEY)?.name
        }
    }
}