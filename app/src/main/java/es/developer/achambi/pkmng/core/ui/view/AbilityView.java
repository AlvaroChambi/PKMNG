package es.developer.achambi.pkmng.core.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.QuickDetailPopup;

public class AbilityView extends ConstraintLayout {
    private TextView name;

    public AbilityView(Context context) {
        super(context);
        init( context );
    }

    public AbilityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init( context );
    }

    public AbilityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context );
    }

    private void init( Context context ) {
        LayoutInflater inflater = LayoutInflater.from( context );
        inflater.inflate( R.layout.configuration_value_layout, this );
        this.name = findViewById(R.id.ability_name_text);
    }

    public void setAbility( final String name, final String descriptionText,
                            final boolean empty ) {
        if( !empty ) {
            this.name.setText( name );
            this.name.setBackgroundTintList( ContextCompat.getColorStateList( getContext(),
                    R.color.secondary_light ) );
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("InflateParams")
                    View quickDetail = LayoutInflater.from(v.getContext())
                            .inflate( R.layout.configuration_value_quick_detail_view, null );
                    TextView description = quickDetail.findViewById(
                            R.id.configuration_value_quick_details_description_text);
                    description.setText( descriptionText );
                    QuickDetailPopup.displayDetails( quickDetail, AbilityView.this );
                }
            });
        } else {
            this.name.setText( name );
            this.name.setBackgroundTintList( ContextCompat.getColorStateList( getContext(),
                    R.color.primary_innactive ) );
        }
    }
}
