package es.developer.achambi.pkmng.core.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;

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

    public void setType( Pair<TypePresentation, TypePresentation> type ) {
        typeFirst.setText(type.first.name);
        typeFirst.setBackgroundTintList(type.first.backgroundColor);
        typeSecond.setVisibility(View.GONE);
        if( type.second != null ) {
            typeSecond.setText(type.second.name);
            typeSecond.setBackgroundTintList(type.second.backgroundColor);
            typeSecond.setVisibility(View.VISIBLE);
        }
    }

    public void setType( TypePresentation type ) {
        setType( new Pair<TypePresentation, TypePresentation>( type, null ) );
    }
}
