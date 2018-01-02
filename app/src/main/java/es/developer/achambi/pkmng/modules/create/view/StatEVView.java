package es.developer.achambi.pkmng.modules.create.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Stat;


public class StatEVView extends ConstraintLayout implements SeekBar.OnSeekBarChangeListener,
        TextWatcher{
    public interface OnValueChangedListener {
        void onValueChanged( Stat stat, int value );
    }
    private TextView statName;
    private EditText valueEditText;
    private TextView totalValueText;
    private SeekBar seekBar;

    private int baseValue;
    private int value;

    private Stat stat;
    private OnValueChangedListener listener;

    public StatEVView(Context context) {
        super(context);
        init(context, null);
    }

    public StatEVView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StatEVView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init( Context context, AttributeSet attrs ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.stat_ev_custom_view_layout, this);

        statName = findViewById(R.id.ev_stat_name_text);
        valueEditText = findViewById(R.id.ev_stat_value_text);
        totalValueText = findViewById(R.id.ev_stat_total_value_text);
        seekBar = findViewById(R.id.ev_stat_seekbar);
        valueEditText.setSaveEnabled(false);
        seekBar.setSaveEnabled(false);
        if(attrs != null) {
            final TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.StatEVView);
            if( typedArray.hasValue(R.styleable.StatEVView_stat_type) ) {
                int statNameValue = typedArray.getInt(R.styleable.StatEVView_stat_type, 0);
                stat = Stat.values()[statNameValue];
                updateName();
            }

            typedArray.recycle();
        }
        if(!isInEditMode()) {
            seekBar.setOnSeekBarChangeListener(this);
            valueEditText.addTextChangedListener(this);

            valueEditText.setFilters(new InputFilter[]{ new InputFilterMax(255)});
        }
    }

    private void updateName() {
        switch (stat) {
            case HP:
                statName.setText( "Hp" );
                break;
            case ATTACK:
                statName.setText( "Attack" );
                break;
            case DEFENSE:
                statName.setText( "Defense" );
                break;
            case SP_ATTACK:
                statName.setText( "Sp.Attk" );
                break;
            case SP_DEFENSE:
                statName.setText( "Sp.Def" );
                break;
            case SPEED:
                statName.setText( "Speed" );
                break;
        }
    }

    public void setOnValueChangedListener( OnValueChangedListener listener ) {
        this.listener = listener;
    }

    public void setValue( int value ) {
        this.value = value;
        valueEditText.setText( String.valueOf(value) );
        totalValueText.setText( String.valueOf(getTotalValue()) );

        if( listener != null ) {
            listener.onValueChanged( stat, value );
        }
    }

    public int getTotalValue() {
        return value + baseValue;
    }

    public void setBaseValue( int baseValue ) {
        this.baseValue = baseValue;
        totalValueText.setText( String.valueOf(getTotalValue()) );
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setValue( progress );
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            this.value = Integer.valueOf( s.toString() );
            totalValueText.setText( String.valueOf(getTotalValue()) );
            seekBar.setOnSeekBarChangeListener(null);
            seekBar.setProgress(value);
            seekBar.setOnSeekBarChangeListener(this);
        } catch( NumberFormatException e ) {
            setValue( 0 );
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if( s.length() > 1 && s.toString().startsWith("0") ) {
            String trimmed = s.toString().substring(1);
            setValue( Integer.valueOf( trimmed ) );
        } else if( s.length() == 0 ) {
            setValue( 0 );
        }
    }

    public class InputFilterMax implements InputFilter {
        private int max;

        public InputFilterMax( int max ) {
            this.max = max;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(0, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
