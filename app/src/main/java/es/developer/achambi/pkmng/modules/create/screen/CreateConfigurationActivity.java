package es.developer.achambi.pkmng.modules.create.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public class CreateConfigurationActivity extends BaseActivity {

    public static Intent getStartIntent( Context context, Pokemon pokemon ) {
        Intent intent = new Intent( context, CreateConfigurationActivity.class );
        Bundle args = ConfigurationFragment.getFragmentArgs( pokemon );
        intent.putExtra( BASE_ARGUMENTS, args );
        return intent;
    }

    @Override
    public int getScreenTitle() {
        return R.string.create_config_activity_title;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return ConfigurationFragment.newInstance(args);
    }
}
