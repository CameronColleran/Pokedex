package edu.miracostacollege.cs134.pokedex.model;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Class loads Pokemon data from a formatted JSON (JavaScript Object Notation) file.
 * Populates data model (Pokemon) with data.
 */

public class JSONLoader
{

    public static final String JSON_URI = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";
    //FROM GITHUB: https://github.com/Biuni/PokemonGO-Pokedex/blob/master/pokedex.json";
    public static final String IMG_URI_BASE = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/";

    private static List<Pokemon> allPokemonList;

    // TODO: Add a static method that reads the notusedpokedex.json file directly from the web
    // TODO: instead of using local AssetManager.  The pokedex can be found here:
    // TODO: https://github.com/Biuni/PokemonGO-Pokedex/blob/master/pokedex.json
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static List<Pokemon> loadJSONFromHTTP()
    {
        allPokemonList = new ArrayList<>();
        // Android enforces that HTTP/HTTPS requests happen in the BACKGROUND thread (not UI thread)
        // Background thread is called asynchronous task

        DownloadJSONTask task = new DownloadJSONTask();
        task.execute(JSON_URI);

        return allPokemonList;
    }



    private static String readAll(Reader rd) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJSONFromUrl(String url) throws IOException, JSONException
    {
        InputStream is = new URL(url).openStream();
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(reader);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally
        {
            is.close();
        }
    }

    // Asynchronous task = private inner class
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class DownloadJSONTask extends AsyncTask<String, Void, JSONObject>
    {

        // Connect to the URL (source of JSON)
        // Parse it and construct a JSON object
        @Override
        protected JSONObject doInBackground(String... strings)
        {
            try
            {
                return readJSONFromUrl(JSON_URI);
            } catch (Exception e)
            {
                Log.e("JSONLoader", "Error loading JSON from " + JSON_URI + e.getMessage());
                return null;
            }
        }

        // After JSONObject has been retrieved, parse it, create Pokemon object and add object to List
        // Ctrl + o
        // OnPostExecute automatically gets called after background process finishes
        @Override
        protected void onPostExecute(JSONObject jsonObject)
        {
            try
            {
                // shift + f6 rename all instances of variable
                JSONArray allPokemonJSON = jsonObject.getJSONArray("pokemon");
                int numberOfEvents = allPokemonJSON.length();
                Pokemon pokemon;
                long id;
                int candyCount;
                String height, weight, name, egg, spawnTime;
                Uri imgUri;
                double spawnChance, averageSpawns;
                JSONArray typeJSON, weaknessesJSON;
                String[] type, weaknesses;

                for (int i = 0; i < numberOfEvents; i++)
                {
                    JSONObject pmJSON = allPokemonJSON.getJSONObject(i);
                    id = pmJSON.getLong("id");
                    name = pmJSON.getString("name");
                    // Rather than use the .png files from JSON (too small), I decided to grab the best quality
                    // images from the source: assets.pokemon.com
                    // The schema being used is take the Pokemon id (e.g. 1) and pad it with zeros to make a
                    // 3-digit file name, e.g. 001.png
                    imgUri = Uri.parse(IMG_URI_BASE + String.format("%03d", id) + ".png");

                    typeJSON = pmJSON.getJSONArray("type");
                    type = new String[typeJSON.length()];
                    for (int j = 0; j < typeJSON.length(); j++)
                        type[j] = typeJSON.getString(j);

                    height = pmJSON.getString("height");
                    weight = pmJSON.getString("weight");

                    // Not all Pokemon have a candy count  (Who knew?!?)
                    candyCount = (pmJSON.has("candy_count")) ?
                            pmJSON.getInt("candy_count") : 0;

                    egg = pmJSON.getString("egg");
                    spawnChance = pmJSON.getDouble("spawn_chance");
                    averageSpawns = pmJSON.getDouble("avg_spawns");
                    spawnTime = pmJSON.getString("spawn_time");

                    weaknessesJSON = pmJSON.getJSONArray("weaknesses");
                    weaknesses = new String[weaknessesJSON.length()];
                    for (int j = 0; j < weaknessesJSON.length(); j++)
                        weaknesses[j] = weaknessesJSON.getString(j);

                    pokemon = new Pokemon(id, name, imgUri, type, height, weight, candyCount, egg, spawnChance, averageSpawns, spawnTime, weaknesses);
                    allPokemonList.add(pokemon);
                }
            } catch (Exception e)
            {
                Log.e("Pokedex", e.getMessage());
            }
        }
    }
}
