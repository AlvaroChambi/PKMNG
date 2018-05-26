package es.developer.achambi.pkmng.modules.create.screen;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
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
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class StatEVView extends ConstraintLayout implements SeekBar.OnSeekBarChangeListener,
        TextWatcher{
    public interface ProgressUpdateProvider {
        int requestValueIncrement( Stat stat, int progress );
        int requestTotalStatValuePreview( Stat stat, int value );
    }
    private TextView statName;
    private EditText valueEditText;
    private TextView totalValuePreviewText;
    private SeekBar seekBar;

    private int value;

    private Stat stat;
    private ProgressUpdateProvider progressProvider;

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
        totalValuePreviewText = findViewById(R.id.ev_stat_total_preview_value_text);
        seekBar = findViewById(R.id.ev_stat_seekbar);
        seekBar.setMax( StatsSet.MAX_STAT_EV );
        if(attrs != null) {
            final TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.StatEVView);
            if( typedArray.hasValue(R.styleable.StatEVView_stat_type) ) {
                int statNameValue = typedArray.getInt(R.styleable.StatEVView_stat_type, 0);
                stat = Stat.values()[statNameValue];
                setName();
            }

            typedArray.recycle();
        }
        if(!isInEditMode()) {
            seekBar.setOnSeekBarChangeListener(this);
            valueEditText.addTextChangedListener(this);

            valueEditText.setFilters(new InputFilter[]{ new InputFilterMax()});
        }
    }

    private void setName() {
        switch (stat) {
            case HP:
                statName.setText( getResources().getText(R.string.ev_bar_hp_text) );
                break;
            case ATTACK:
                statName.setText( getResources().getText(R.string.ev_bar_attack_text) );
                break;
            case DEFENSE:
                statName.setText( getResources().getText(R.string.ev_bar_defense_text) );
                break;
            case SP_ATTACK:
                statName.setText( getResources().getText(R.string.ev_bar_sp_attack_text) );
                break;
            case SP_DEFENSE:
                statName.setText( getResources().getText(R.string.ev_bar_sp_defense_text) );
                break;
            case SPEED:
                statName.setText( getResources().getText(R.string.ev_bar_speed_text) );
                break;
        }
    }

    public void setProgressUpdateProvider( ProgressUpdateProvider listener ) {
        this.progressProvider = listener;
    }

    public void setNatureModifier( Nature nature ) {
        if( nature.getIncreasedStat().equals(stat) ) {
            totalValuePreviewText.setTextColor(ContextCompat.getColor(
                    getContext(), R.color.nature_increased_stat_color ));
        } else if( nature.getDecreasedStat().equals(stat) ) {
            totalValuePreviewText.setTextColor(ContextCompat.getColor(
                    getContext(), R.color.nature_decreased_stat_color ));
        } else {
            totalValuePreviewText.setTextColor(ContextCompat.getColor(
                    getContext(), R.color.secondary_text_default_material_light ));
        }
        if( progressProvider != null ) {
            totalValuePreviewText.setText( String.valueOf(
                    progressProvider.requestTotalStatValuePreview( stat, value ) ) );
        }
    }

    public void setValue( int value ) {
        this.value = value;
        setEditTextValue( value );
        totalValuePreviewText.setText( String.valueOf(
                progressProvider.requestTotalStatValuePreview( stat, value )
        ) );
    }

    /**
     * Whenever a Zero value is provided the edit text will clear any content and display an
     * empty value
     * @param value
     */
    private void setEditTextValue( int value ) {
        String editValue = String.valueOf( value );
        if( editValue.equals("0") ) {
            valueEditText.setText( "" );
        } else {
            valueEditText.setText( editValue );
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int adjustedProgress = progressProvider.requestValueIncrement( stat, progress );
        if( adjustedProgress != progress ) {
            setValue( adjustedProgress );
        } else {
            setValue( progress );
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            syncEvViewValues( Integer.valueOf( s.toString() ) );
        } catch( NumberFormatException e ) {
            if(s.toString().equals("")) {
                syncEvViewValues( 0 );
            }
        }
    }

    private void syncEvViewValues( int value ) {
        this.value = value;
        totalValuePreviewText.setText( String.valueOf(
                progressProvider.requestTotalStatValuePreview( stat, value )
        ) );
        seekBar.setOnSeekBarChangeListener(null);
        seekBar.setProgress(value);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if( s.length() > 1 && s.toString().startsWith("0") ) {
            String trimmed = s.toString().substring(1);
            setValue( Integer.valueOf( trimmed ) );
        }
    }

    public class InputFilterMax implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            try {
                String inputText = dest.toString().substring(0, dstart) + source +
                        dest.toString().substring(dstart, dest.toString().length());
                int input = Integer.parseInt( inputText );
                int adjustedProgress = progressProvider.requestValueIncrement( stat, input );
                if (isInRange(0, StatsSet.MAX_STAT_EV, input) && adjustedProgress == input )
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
