package es.developer.achambi.pkmng.modules.speed_calculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.developer.achambi.coreframework.ui.BaseActivity
import es.developer.achambi.coreframework.ui.BaseFragment
import es.developer.achambi.pkmng.R
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig

class SpeedConfigurationActivity: BaseActivity() {
    companion object{
        fun getStartIntent(context: Context, config: PokemonConfig): Intent {
            val intent = Intent(context, SpeedConfigurationActivity::class.java)
            val args = SpeedConfigurationFragment.getArgs(config)
            intent.putExtra(BASE_ARGUMENTS, args)
            return intent
        }
    }

    override fun getScreenTitle(): Int {
        return R.string.speed_calculator_title
    }

    override fun getFragment(args: Bundle): BaseFragment {
        return SpeedConfigurationFragment.newInstance(args)
    }
}