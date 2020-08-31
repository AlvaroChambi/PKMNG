package es.developer.achambi.pkmng.modules.speed_calculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.developer.achambi.coreframework.ui.BaseActivity
import es.developer.achambi.coreframework.ui.BaseFragment
import es.developer.achambi.pkmng.R

class SpeedConfigurationActivity: BaseActivity() {
    companion object{
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SpeedConfigurationActivity::class.java)
        }
    }

    override fun getScreenTitle(): Int {
        return R.string.speed_calculator_title
    }

    override fun getFragment(args: Bundle?): BaseFragment {
        return SpeedConfigurationFragment()
    }
}