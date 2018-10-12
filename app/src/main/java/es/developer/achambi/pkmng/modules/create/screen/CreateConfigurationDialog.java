package es.developer.achambi.pkmng.modules.create.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.coreframework.ui.BaseDialogFragment;

public class CreateConfigurationDialog extends BaseDialogFragment
    implements View.OnClickListener {
    private static final String CONFIGURATION_NAME_EXTRA = "CONFIGURATION_NAME_EXTRA";
    private EditText nameEditText;

    public static CreateConfigurationDialog newInstance( String name ) {
        CreateConfigurationDialog fragment = new CreateConfigurationDialog();
        Bundle args = new Bundle();
        args.putString( CONFIGURATION_NAME_EXTRA, name );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.create_configuration_dialog_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        nameEditText = view.findViewById(R.id.create_configuration_dialog_edit_text);
        view.findViewById(R.id.create_configuration_dialog_save_button).setOnClickListener(this);

        if( getArguments().containsKey( CONFIGURATION_NAME_EXTRA ) ) {
            nameEditText.setText( getArguments().getString( CONFIGURATION_NAME_EXTRA ) );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_configuration_dialog_save_button:
                Intent data = new Intent();
                data.putExtra( ConfigurationFragment.CONFIGURATION_NAME_DATA_KEY,
                        nameEditText.getText().toString() );
                getTargetFragment().onActivityResult( getTargetRequestCode(), Activity.RESULT_OK,
                        data );
                dismiss();
                break;
        }
    }
}
