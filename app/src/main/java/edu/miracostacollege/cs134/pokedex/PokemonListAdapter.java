package edu.miracostacollege.cs134.pokedex;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import edu.miracostacollege.cs134.pokedex.model.Pokemon;

/**
 * Helper class to provide custom adapter for the <code>Pokemon</code> list.
 */
public class PokemonListAdapter extends ArrayAdapter<Pokemon> {

    private Context mContext;
    private List<Pokemon> mPokemonsList;
    private int mResourceId;



    /**
     * Creates a new <code>PokemonListAdapter</code> given a mContext, resource id and list of Pokemons.
     *  @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param Pokemons The list of Pokemons to display
     */
    public PokemonListAdapter(MainActivity c, int rId, List<Pokemon> Pokemons) {
        super(c, rId, Pokemons);
        mContext = c;
        mResourceId = rId;
        mPokemonsList = Pokemons;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the Pokemon selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        final Pokemon selectedPokemon = mPokemonsList.get(pos);

        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        LinearLayout PokemonListLinearLayout =
               view.findViewById(R.id.pokemonListLinearLayout);
        ImageView PokemonListImageView =
               view.findViewById(R.id.pokemonListImageView);
        TextView PokemonListNameTextView =
                view.findViewById(R.id.pokemonNameTextView);
        TextView pokemonNumberTextView =
                view.findViewById(R.id.pokemonNumberTextView);
        TextView pokemonTypesTextView  =
                view.findViewById(R.id.pokemonTypesTextView);

        // Download the Pokemon image from the web using the Picasso library
        // given only its URI.
        Picasso.get()
                .load(selectedPokemon.getImgUri())
                .into(PokemonListImageView);

        PokemonListLinearLayout.setTag(selectedPokemon);
        PokemonListNameTextView.setText(selectedPokemon.getName());
        pokemonNumberTextView.setText("#" + String.format("%03d", selectedPokemon.getId()));
        pokemonTypesTextView.setText("Type: " + Arrays.toString(selectedPokemon.getType()));






        return view;
    }
}
