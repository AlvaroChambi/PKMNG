package es.developer.achambi.pkmng.modules.calculator.screen;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.ui.presentation.MoveTypePresentation;
import es.developer.achambi.pkmng.modules.ui.screen.TypeView;

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

    public void setIsEmpty( boolean empty ) {
        if( !empty ) {
            findViewById(R.id.move_damage_empty_view).setVisibility(View.GONE);
        } else {
            findViewById(R.id.move_damage_empty_view).setVisibility(View.VISIBLE);
        }
    }

    public void setLeftBackgroundColor( ColorStateList color ) {
        findViewById(R.id.move_damage_left_background).setBackgroundTintList( color );
    }

    public void setMoveName( CharSequence text ) {
        ((TextView)findViewById(R.id.move_damage_name_text)).setText(text);
    }

    public void setMoveType( MoveTypePresentation type ) {
        ((TypeView)findViewById(R.id.move_damage_type_text)).setType( type );
    }

    public void setMoveCategory( CharSequence text ) {
        ((TextView)findViewById(R.id.move_damage_category_text)).setText(text);
    }

    public void setMovePower( CharSequence text ) {
        ((TextView)findViewById(R.id.move_damage_power_text)).setText(text);
    }

    public void setMoveEffect( CharSequence text ) {
        ((TextView)findViewById(R.id.move_damage_effect_text)).setText(text);
    }

    public void setMoveResult( CharSequence text ) {
        ((TextView)findViewById(R.id.move_damage_result_text)).setText(text);
    }
}
