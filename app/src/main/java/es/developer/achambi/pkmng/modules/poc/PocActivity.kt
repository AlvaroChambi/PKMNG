package es.developer.achambi.pkmng.modules.poc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.developer.achambi.coreframework.ui.BaseActivity
import es.developer.achambi.coreframework.ui.BaseFragment
import es.developer.achambi.pkmng.R
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig

class PocActivity: BaseActivity() {
    companion object {
        fun getStartIntent(context: Context, config: PokemonConfig): Intent {
            val intent = Intent(context, PocActivity::class.java)
            val args = PocFragment.getArgs(config)
            intent.putExtra(BASE_ARGUMENTS, args)
            return intent
        }
    }
    override fun getFragment(args: Bundle): BaseFragment {
        return PocFragment.newInstance(args)
    }

    override fun getScreenTitle(): Int {
        return R.string.app_name
    }
}