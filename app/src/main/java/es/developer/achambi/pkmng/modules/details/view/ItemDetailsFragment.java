package es.developer.achambi.pkmng.modules.details.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.core.utils.GlideApp;
import es.developer.achambi.pkmng.core.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.create.screen.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class ItemDetailsFragment extends BaseDialogFragment implements View.OnClickListener{
    private static final String ITEM_ARGUMENT_KEY = "ITEM_ARGUMENT_KEY";

    private Item item;
    private ItemDetailsPresentation viewRepresentation;

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
        ImageView itemIcon = rootView.findViewById(R.id.item_image_view);
        GlideApp.with(this).load(viewRepresentation.image)
                .placeholder(R.drawable.icon_placeholder)
                .into(itemIcon);

        itemName.setText( viewRepresentation.name );
        itemDescription.setText( viewRepresentation.description);
    }

    private class ItemDetailsRepresentationDataBuilder {
        public ItemDetailsPresentation buildViewRepresentation( Item item ) {
            return new ItemDetailsPresentation( item.getName(),
                    item.getDescription(),
                    ImageResourceBuilder.buildItemImageAssetPath(item.getName()));
        }
    }

    private class ItemDetailsPresentation {
        public final String name;
        public final String description;
        public final Uri image;

        public ItemDetailsPresentation(String name, String description, Uri image) {
            this.name = name;
            this.description = description;
            this.image = image;
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
