package es.developer.achambi.pkmng.modules.calculator.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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

    public void setIsEmpty( boolean empty ) {
        if( !empty ) {
            findViewById(R.id.move_damage_empty_view).setVisibility(View.GONE);
        } else {
            findViewById(R.id.move_damage_empty_view).setVisibility(View.VISIBLE);
        }
    }

    public void setMoveName( String text ) {
        ((TextView)findViewById(R.id.move_damage_name_text)).setText(text);
    }

    public void setMoveType( String text ) {
        ((TextView)findViewById(R.id.move_damage_type_text)).setText(text);
    }

    public void setMoveCategory( String text ) {
        ((TextView)findViewById(R.id.move_damage_category_text)).setText(text);
    }

    public void setMovePower( String text ) {
        ((TextView)findViewById(R.id.move_damage_power_text)).setText(text);
    }

    public void setMoveEffect( String text ) {
        ((TextView)findViewById(R.id.move_damage_effect_text)).setText(text);
    }

    public void setMoveResult( String text ) {
        ((TextView)findViewById(R.id.move_damage_result_text)).setText(text);
    }
}
