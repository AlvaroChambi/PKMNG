package es.developer.achambi.pkmng.modules.details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.coreframework.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.modules.create.screen.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.screen.presentation.SearchAbilityPresentation;

public class AbilityDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private static final String ABILITY_ARGUMENT_KEY = "ABILITY_ARGUMENT_KEY";

    private Ability ability;
    private static SearchAbilityPresentation viewPresentation;

    public static AbilityDetailsFragment newInstance( Ability ability ) {
        Bundle args = new Bundle();
        args.putParcelable( ABILITY_ARGUMENT_KEY, ability );

        AbilityDetailsFragment fragment = new AbilityDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.ability_details_fragment_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ability = getArguments().getParcelable(ABILITY_ARGUMENT_KEY);
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if( !isViewRecreated() ) {
            viewPresentation = new AbilityDetailsPresentationBuilder().build( ability );
        }

        populateView(view);
        view.findViewById(R.id.details_choose_ability_action_button).setOnClickListener(this);
    }

    private void populateView( View view ) {
        TextView name = view.findViewById(R.id.ability_name_text);
        TextView description = view.findViewById(R.id.ability_description_text);

        name.setText(viewPresentation.name);
        description.setText(viewPresentation.description);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_choose_ability_action_button:
                Intent dataIntent = getActivity().getIntent();
                dataIntent.putExtra(ConfigurationFragment.ABILITY_ACTIVITY_RESULT_DATA_KEY,
                        ability );
                getActivity().setResult(Activity.RESULT_OK,dataIntent);
                getActivity().finish();
                dismiss();
                break;
        }
    }

    public class AbilityDetailsPresentationBuilder {
        public SearchAbilityPresentation build(Ability ability ) {
            return SearchAbilityPresentation.Builder.buildPresentation(ability);
        }
    }
}
