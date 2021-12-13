package edu.miracostacollege.cs134.pokedex.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    //TASK: DEFINE THE DATABASE VERSION AND NAME  (DATABASE CONTAINS MULTIPLE TABLES)
    static final String DATABASE_NAME = "Pokedex";
    private static final int DATABASE_VERSION = 1;

    //TASK: DEFINE THE FIELDS (COLUMN NAMES) FOR THE CAFFEINE LOCATIONS TABLE
    private static final String POKEMON_TABLE = "Pokemon";
    private static final String POKEMON_KEY_FIELD_ID = "_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_IMG_URI = "img_uri";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_HEIGHT = "height";
    private static final String FIELD_WEIGHT = "weight";
    private static final String FIELD_CANDY_COUNT = "candy_count";
    private static final String FIELD_EGG = "egg";
    private static final String FIELD_SPAWN_CHANCE = "spawn_chance";
    private static final String FIELD_AVERAGE_SPAWNS = "average_spawns";
    private static final String FIELD_SPAWN_TIME = "spawn_time";
    private static final String FIELD_WEAKNESSES = "weaknesses";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createQuery = "CREATE TABLE " + POKEMON_TABLE + "("
                + POKEMON_KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_NAME + " TEXT, "
                + FIELD_IMG_URI + " TEXT, "
                + FIELD_TYPE + " TEXT,"
                + FIELD_HEIGHT + " TEXT,"
                + FIELD_WEIGHT + " TEXT,"
                + FIELD_CANDY_COUNT + " INTEGER,"
                + FIELD_EGG + " TEXT,"
                + FIELD_SPAWN_CHANCE + " REAL,"
                + FIELD_AVERAGE_SPAWNS + " REAL,"
                + FIELD_SPAWN_TIME + " TEXT,"
                + FIELD_WEAKNESSES + " TEXT"
                + ")";
        database.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + POKEMON_TABLE);

        onCreate(database);
    }

    //********** LOCATIONS TABLE OPERATIONS:  ADD, GETALL, DELETE

    public void addPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(POKEMON_KEY_FIELD_ID, pokemon.getId());
        values.put(FIELD_NAME, pokemon.getName());
        values.put(FIELD_IMG_URI, pokemon.getImgUri().toString());
        values.put(FIELD_TYPE, arrayToCSV(pokemon.getType()));
        values.put(FIELD_HEIGHT, pokemon.getHeight());
        values.put(FIELD_WEIGHT, pokemon.getWeight());
        values.put(FIELD_CANDY_COUNT, pokemon.getCandyCount());
        values.put(FIELD_EGG, pokemon.getEgg());
        values.put(FIELD_SPAWN_CHANCE, pokemon.getSpawnChance());
        values.put(FIELD_AVERAGE_SPAWNS, pokemon.getAverageSpawns());
        values.put(FIELD_SPAWN_TIME, pokemon.getSpawnTime());
        values.put(FIELD_WEAKNESSES, arrayToCSV(pokemon.getWeaknesses()));

        long id = db.insert(POKEMON_TABLE, null, values);
        pokemon.setId(id);
        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    private String arrayToCSV(String[] array)
    {
        String csv = "";
        for (int i = 0; i < array.length; i++)
            csv += ((i == array.length-1) ? array[i] : array[i] + ",");
        return csv;
    }

    private String[] csvToArray(String csv)
    {
        return csv.split(",");
    }

    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokemonList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                POKEMON_TABLE,
                new String[]{POKEMON_KEY_FIELD_ID,
                        FIELD_NAME,
                        FIELD_IMG_URI,
                        FIELD_TYPE,
                        FIELD_HEIGHT,
                        FIELD_WEIGHT,
                        FIELD_CANDY_COUNT,
                        FIELD_EGG,
                        FIELD_SPAWN_CHANCE,
                        FIELD_AVERAGE_SPAWNS,
                        FIELD_SPAWN_TIME,
                        FIELD_WEAKNESSES},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon =
                        new Pokemon(cursor.getLong(0),
                                cursor.getString(1),
                                Uri.parse(cursor.getString(2)),
                                csvToArray(cursor.getString(3)),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getInt(6),
                                cursor.getString(7),
                                cursor.getDouble(8),
                                cursor.getDouble(9),
                                cursor.getString(10),
                                csvToArray(cursor.getString(11)));

                pokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pokemonList;
    }

    public void deletePokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETE THE TABLE ROW
        db.delete(POKEMON_TABLE, POKEMON_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(pokemon.getId())});
        db.close();
    }

    public void deleteAllPokemon() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POKEMON_TABLE, null, null);
        db.close();
    }

    public Pokemon getPokemon(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                POKEMON_TABLE,
                new String[]{POKEMON_KEY_FIELD_ID,
                        FIELD_NAME,
                        FIELD_IMG_URI,
                        FIELD_TYPE,
                        FIELD_HEIGHT,
                        FIELD_WEIGHT,
                        FIELD_CANDY_COUNT,
                        FIELD_EGG,
                        FIELD_SPAWN_CHANCE,
                        FIELD_AVERAGE_SPAWNS,
                        FIELD_SPAWN_TIME,
                        FIELD_WEAKNESSES},
                POKEMON_KEY_FIELD_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Pokemon pokemon =
                new Pokemon(cursor.getLong(0),
                        cursor.getString(1),
                        Uri.parse(cursor.getString(2)),
                        csvToArray(cursor.getString(3)),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getDouble(8),
                        cursor.getDouble(9),
                        cursor.getString(10),
                        csvToArray(cursor.getString(11)));
        cursor.close();
        db.close();
        return pokemon;
    }




}
