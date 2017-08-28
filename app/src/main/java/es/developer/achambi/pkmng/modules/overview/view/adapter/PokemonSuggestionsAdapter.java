package es.developer.achambi.pkmng.modules.overview.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;


public class PokemonSuggestionsAdapter extends SimpleCursorAdapter {
    public final static String POKEMON_KEY = "pokemonName";
    private final String[] data = new String[]{"Bulbasaur", "Ivysaur", "Venasaur", "Squirtle",
            "Warturtle","Blastoise", "Charmander", "Charmeleon", "Charizard"};

    public PokemonSuggestionsAdapter(Context context, int layout, Cursor c,
                                     String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    public PokemonSuggestionsAdapter(Context context) {
        this(context, android.R.layout.simple_spinner_dropdown_item, null,
                new String[] {POKEMON_KEY},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    public void onQueryTextChanged(String newText) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[] {
                BaseColumns._ID, POKEMON_KEY
        });
        for (int i = 0; i < data.length; i++){
            if(data[i].toLowerCase().startsWith(newText.toLowerCase())) {
                matrixCursor.addRow(new Object[]{i, data[i]});
            }
        }
        changeCursor(matrixCursor);
    }

    public String getValue(int position) {
        getCursor().moveToPosition(position);
        return getCursor().getString(getCursor().getColumnIndex(POKEMON_KEY));
    }
}
