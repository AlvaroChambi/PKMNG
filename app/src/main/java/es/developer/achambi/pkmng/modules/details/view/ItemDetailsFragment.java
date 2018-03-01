package es.developer.achambi.pkmng.modules.details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.modules.create.view.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.item.view.representation.SearchItemPresentation;

public class ItemDetailsFragment extends BaseDialogFragment implements View.OnClickListener{
    private static final String ITEM_ARGUMENT_KEY = "ITEM_ARGUMENT_KEY";

    private Item item;
    private SearchItemPresentation viewRepresentation;

    public static ItemDetailsFragment newInstance( Item item ) {
        Bundle bundle = new Bundle();
        bundle.putParcelable( ITEM_ARGUMENT_KEY, item );

        ItemDetailsFragment fragment = new ItemDetailsFragment();
        fragment.setArguments( bundle );

        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.item_details_fragment_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = getArguments().getParcelable(ITEM_ARGUMENT_KEY);
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if( !isViewRecreated() ) {
            viewRepresentation = new ItemDetailsRepresentationDataBuilder()
                    .buildViewRepresentation( item );
        }

        populateView( view );
        view.findViewById(R.id.details_choose_item_action_button).setOnClickListener(this);
    }

    private void populateView( View rootView ) {
        TextView itemName = rootView.findViewById(R.id.item_name_text);
        TextView itemDescription = rootView.findViewById(R.id.item_description_text);

        itemName.setText( viewRepresentation.name );
        itemDescription.setText( viewRepresentation.description);
    }

    private class ItemDetailsRepresentationDataBuilder {
        public SearchItemPresentation buildViewRepresentation( Item item ) {
            return SearchItemPresentation.Builder.buildPresentation( item );
        }
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.details_choose_item_action_button:
                Intent dataIntent = getActivity().getIntent();
                dataIntent.putExtra(ConfigurationFragment.ITEM_ACTIVITY_RESULT_DATA_KEY,
                        item);
                getActivity().setResult(Activity.RESULT_OK, dataIntent);
                getActivity().finish();
                dismiss();
                break;
        }
    }
}
