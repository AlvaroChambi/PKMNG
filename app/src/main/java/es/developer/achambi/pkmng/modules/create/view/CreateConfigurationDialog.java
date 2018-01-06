package es.developer.achambi.pkmng.modules.create.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;

public class CreateConfigurationDialog extends BaseDialogFragment
    implements View.OnClickListener {
    private EditText nameEditText;

    public static CreateConfigurationDialog newInstance(  ) {
        CreateConfigurationDialog fragment = new CreateConfigurationDialog();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_configuration_dialog_save_button:
                Intent data = new Intent();
                data.putExtra( CreateConfigurationFragment.CONFIGURATION_NAME_DATA_KEY,
                        nameEditText.getText().toString() );
                getTargetFragment().onActivityResult( getTargetRequestCode(), Activity.RESULT_OK,
                        data );
                dismiss();
                break;
        }
    }
}
