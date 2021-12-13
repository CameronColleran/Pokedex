package edu.miracostacollege.cs134.pokedex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.Arrays;

import edu.miracostacollege.cs134.pokedex.model.Pokemon;


public class PokemonDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        ImageView pokemonDetailsImageView = findViewById(R.id.pokemonDetailsImageView);
        TextView pokemonDetailsNameTextView = findViewById(R.id.pokemonDetailsNameTextView);
        TextView pokemonDetailsNumberTextView = findViewById(R.id.pokemonDetailsNumberTextView);
        TextView pokemonDetailsTypesTextView = findViewById(R.id.pokemonDetailsTypesTextView);
        TextView pokemonDetailsWeaknessesTextView = findViewById(R.id.pokemonDetailsWeaknessesTextView);
        TextView pokemonDetailsHeightTextView = findViewById(R.id.pokemonDetailsHeightTextView);
        TextView pokemonDetailsWeightTextView = findViewById(R.id.pokemonDetailsWeightTextView);
        TextView pokemonDetailsCandyCountTextView = findViewById(R.id.pokemonDetailsCandyCountTextView);
        TextView pokemonDetailsAverageSpawnsTextView = findViewById(R.id.pokemonDetailsAverageSpawnsTextView);


        Pokemon selectedPokemon = getIntent().getExtras().getParcelable("SelectedPokemon");

        // Download the Pokemon image from the web using the Picasso library
        // given only its URI.
        Picasso.get()
                .load(selectedPokemon.getImgUri())
                .into(pokemonDetailsImageView);

        pokemonDetailsNameTextView.setText(selectedPokemon.getName());
        pokemonDetailsNumberTextView.setText("#" + String.format("%03d", selectedPokemon.getId()));
        pokemonDetailsTypesTextView.setText("Type: " + Arrays.toString(selectedPokemon.getType()));
        pokemonDetailsWeaknessesTextView.setText("Weaknesses: " + Arrays.toString(selectedPokemon.getWeaknesses()));
        pokemonDetailsHeightTextView.setText("Height: " + selectedPokemon.getHeight());
        pokemonDetailsWeightTextView.setText("Height: " + selectedPokemon.getWeight());
        pokemonDetailsCandyCountTextView.setText("Candy Count: " + selectedPokemon.getCandyCount());
        pokemonDetailsAverageSpawnsTextView.setText("Average Spawns: " + selectedPokemon.getAverageSpawns());


    }
}
