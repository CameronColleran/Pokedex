package edu.miracostacollege.cs134.pokedex;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;



import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import edu.miracostacollege.cs134.pokedex.model.DBHelper;
import edu.miracostacollege.cs134.pokedex.model.JSONLoader;
import edu.miracostacollege.cs134.pokedex.model.Pokemon;

public class MainActivity extends AppCompatActivity {

    private List<Pokemon> mPokedex;
    private DBHelper mDB;
    private ListView mPokemonListView;
    private PokemonListAdapter mPokemonListAdapter;

    public static final String TAG = MainActivity.class.getSimpleName();


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new DBHelper(this);
        mPokedex = mDB.getAllPokemon();

        if (mPokedex.size() == 0) {
                // TODO: After creating new method in JSONLoader to load the notusedpokedex.json
                // TODO: from the web (http), use the new method here.
                mPokedex = JSONLoader.loadJSONFromHTTP();
                for (Pokemon p : mPokedex)
                    mDB.addPokemon(p);
        }

        mPokemonListAdapter = new PokemonListAdapter(this, R.layout.pokemon_list_item, mPokedex);
        mPokemonListView = findViewById(R.id.pokemonListView);
        mPokemonListView.setAdapter(mPokemonListAdapter);

    }

    // TODO: Implement the addPokemon method to create a new (relatively) empty Pokemon object
    // TODO: to the ListAdapter and the Database (DBHelper)
    public void addPokemon(View v)
    {
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Uri emptyUri = Uri.EMPTY;
        String[] emptyArr = {""};
        Pokemon pokemon = new Pokemon(0, name, emptyUri, emptyArr, "", "", 0, "", 0, 0, "", emptyArr);

        pokemon.setName(name);
        pokemon.setName(description);

        mPokemonListAdapter.add(pokemon);
        mPokemonListAdapter.notifyDataSetChanged();
        mDB.addPokemon(pokemon);

        nameEditText.setText("");
        descriptionEditText.setText("");

    }

    // TODO: Implement the clearAllPokemon method to remove all Pokemon objects
    // TODO: from the ListAdapter and the Database (DBHelper)
    public void clearAllPokemon(View v)
    {
        mPokemonListAdapter.clear();
        mDB.deleteAllPokemon();
        mPokemonListAdapter.notifyDataSetChanged();
    }

    public void viewPokemonDetails(View v)
    {
        Pokemon selectedPokemon = (Pokemon) v.getTag();
        Intent detailsIntent = new Intent(this, PokemonDetailsActivity.class);
        detailsIntent.putExtra("SelectedPokemon", selectedPokemon);
        startActivity(detailsIntent);
    }
}
