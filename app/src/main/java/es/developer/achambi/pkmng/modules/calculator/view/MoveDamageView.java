package es.developer.achambi.pkmng.modules.calculator.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import es.developer.achambi.pkmng.R;

public class MoveDamageView extends CardView {
    public MoveDamageView(Context context) {
        super(context);
        init(context);
    }

    public MoveDamageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MoveDamageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init( Context context ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.move_damage_view_layout, this );
    }
}
