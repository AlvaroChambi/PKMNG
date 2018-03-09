package es.developer.achambi.pkmng.core.ui.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.QuickDetailPopup;

public class ItemView extends ConstraintLayout {
    private TextView name;

    public ItemView(Context context) {
        super(context);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init( Context context ) {
        LayoutInflater inflater = LayoutInflater.from( context );
        inflater.inflate( R.layout.configuration_value_layout, this );
        this.name = findViewById(R.id.ability_name_text);
    }

    public void setItem( String name, final String descriptionText, boolean empty ) {
        if( !empty ) {
            this.name.setText( name );
            this.name.setBackgroundTintList( ContextCompat.getColorStateList( getContext(),
                    R.color.secondary_light ) );
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    View quickDetail = LayoutInflater.from(v.getContext())
                            .inflate( R.layout.configuration_value_quick_detail_view, null );
                    TextView description = quickDetail.findViewById(
                            R.id.configuration_value_quick_details_description_text);
                    description.setText( descriptionText );
                    QuickDetailPopup.displayDetails( quickDetail, ItemView.this );
                }
            });
        } else {
            this.name.setText( name );
            this.name.setBackgroundTintList( ContextCompat.getColorStateList( getContext(),
                    R.color.primary_innactive ) );
        }
    }
}
