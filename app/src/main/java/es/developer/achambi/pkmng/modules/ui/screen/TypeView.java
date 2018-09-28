package es.developer.achambi.pkmng.modules.ui.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.coreframework.ui.QuickDetailPopup;
import es.developer.achambi.pkmng.modules.ui.presentation.MoveTypePresentation;
import es.developer.achambi.pkmng.modules.ui.presentation.PokemonTypePresentation;

public class TypeView extends LinearLayout {
    private TextView typeFirst;
    private TextView typeSecond;

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
    }

    public void setType( final PokemonTypePresentation pokemonType ) {
        typeFirst.setText(pokemonType.first.name);
        typeFirst.setBackgroundTintList(pokemonType.first.backgroundColor);
        typeSecond.setVisibility(View.GONE);
        if( pokemonType.second != null ) {
            typeSecond.setText(pokemonType.second.name);
            typeSecond.setBackgroundTintList(pokemonType.second.backgroundColor);
            typeSecond.setVisibility(View.VISIBLE);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("InflateParams")
                View quickDetail = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.type_quick_detail_view, null);
                TextView effective = quickDetail.findViewById(R.id.type_quick_detail_top_text);
                TextView weak = quickDetail.findViewById(R.id.type_quick_details_bottom_text);
                effective.setText( pokemonType.resistantTo );
                weak.setText( pokemonType.weakAgainst );
                QuickDetailPopup.displayDetails( quickDetail, TypeView.this );
            }
        });
    }

    public void setType( final MoveTypePresentation moveType ) {
        typeFirst.setText(moveType.typePresentation.name);
        typeFirst.setBackgroundTintList(moveType.typePresentation.backgroundColor);
        typeSecond.setVisibility(GONE);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("InflateParams")
                View quickDetail = LayoutInflater.from(v.getContext())
                                .inflate(R.layout.type_quick_detail_view, null);
                TextView effective = quickDetail.findViewById(R.id.type_quick_detail_top_text);
                quickDetail.findViewById(R.id.type_quick_details_bottom_text).setVisibility(GONE);
                effective.setText( moveType.effectiveAgainst );
                QuickDetailPopup.displayDetails( quickDetail, TypeView.this );
            }
        });
    }
}
