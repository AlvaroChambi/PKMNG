package es.developer.achambi.pkmng.core.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;
import es.developer.achambi.pkmng.core.ui.presentation.TypesPresentation;

public class TypeView extends LinearLayout implements View.OnClickListener {
    private TextView typeFirst;
    private TextView typeSecond;
    private TypesPresentation presentation;

    public TypeView(Context context) {
        super(context);
        init(context);
    }

    public TypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.type_view_layout, this );
        typeFirst = findViewById(R.id.pokemon_type_0_text);
        typeSecond = findViewById(R.id.pokemon_type_1_text);
        if(!isInEditMode()) {
            setOnClickListener( this );
        }
    }

    public void setType( TypesPresentation pokemonType ) {
        this.presentation = pokemonType;
        typeFirst.setText(pokemonType.first.name);
        typeFirst.setBackgroundTintList(pokemonType.first.backgroundColor);
        typeSecond.setVisibility(View.GONE);
        if( pokemonType.second != null ) {
            typeSecond.setText(pokemonType.second.name);
            typeSecond.setBackgroundTintList(pokemonType.second.backgroundColor);
            typeSecond.setVisibility(View.VISIBLE);
        }
    }

    public void setType( TypePresentation moveType ) {
        setType( new TypesPresentation( moveType, null, null, null ) );
    }

    @Override
    public void onClick(View v) {
        View quickDetail =
                LayoutInflater.from(v.getContext()).inflate(R.layout.type_quick_detail_view, null);
        TextView effective = quickDetail.findViewById(R.id.type_quick_detail_effective_text);
        TextView weak = quickDetail.findViewById(R.id.type_quick_details_weak_text);
        effective.setText( presentation.effectiveAgainst );
        weak.setText( presentation.weakAgainst );
        PopupWindow popup = new PopupWindow( quickDetail, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT );
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.setOutsideTouchable( true );
        popup.showAsDropDown( v );
    }
}
