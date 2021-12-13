package edu.miracostacollege.cs134.pokedex.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;

/**
 * The <code>Pokemon</code> class represents a single character in the full Pokedex.
 * All information sourced from pokemon.com and pokedex.co.
 *
 * @author Michael Paulding
 * @version 1.0
 */
public class Pokemon implements Parcelable {

    private long mId;
    private String mName;
    private Uri mImgUri;
    private String[] mType;
    private String mHeight;
    private String mWeight;
    private int mCandyCount;
    private String mEgg;
    private double mSpawnChance;
    private double mAverageSpawns;
    private String mSpawnTime;
    private String[] mWeaknesses;

    public Pokemon(long id, String name, Uri imgUri, String[] type, String height, String weight,
                   int candyCount, String egg, double spawnChance, double averageSpawns,
                   String spawnTime, String[] weaknesses) {
        mId = id;
        mName = name;
        mImgUri = imgUri;
        mType = type;
        mHeight = height;
        mWeight = weight;
        mCandyCount = candyCount;
        mEgg = egg;
        mSpawnChance = spawnChance;
        mAverageSpawns = averageSpawns;
        mSpawnTime = spawnTime;
        mWeaknesses = weaknesses;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Uri getImgUri() {
        return mImgUri;
    }

    public void setImgUri(Uri imgUri) {
        mImgUri = imgUri;
    }

    public String[] getType() {
        return mType;
    }

    public void setType(String[] type) {
        mType = type;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public int getCandyCount() {
        return mCandyCount;
    }

    public void setCandyCount(int candyCount) {
        mCandyCount = candyCount;
    }

    public String getEgg() {
        return mEgg;
    }

    public void setEgg(String egg) {
        mEgg = egg;
    }

    public double getSpawnChance() {
        return mSpawnChance;
    }

    public void setSpawnChance(double spawnChance) {
        mSpawnChance = spawnChance;
    }

    public double getAverageSpawns() {
        return mAverageSpawns;
    }

    public void setAverageSpawns(double averageSpawns) {
        mAverageSpawns = averageSpawns;
    }

    public String getSpawnTime() {
        return mSpawnTime;
    }

    public void setSpawnTime(String spawnTime) {
        mSpawnTime = spawnTime;
    }

    public String[] getWeaknesses() {
        return mWeaknesses;
    }

    public void setWeaknesses(String[] weaknesses) {
        mWeaknesses = weaknesses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return mId == pokemon.mId &&
                mCandyCount == pokemon.mCandyCount &&
                Double.compare(pokemon.mSpawnChance, mSpawnChance) == 0 &&
                Double.compare(pokemon.mAverageSpawns, mAverageSpawns) == 0 &&
                Objects.equals(mName, pokemon.mName) &&
                Objects.equals(mImgUri, pokemon.mImgUri) &&
                Arrays.equals(mType, pokemon.mType) &&
                Objects.equals(mHeight, pokemon.mHeight) &&
                Objects.equals(mWeight, pokemon.mWeight) &&
                Objects.equals(mEgg, pokemon.mEgg) &&
                Objects.equals(mSpawnTime, pokemon.mSpawnTime) &&
                Arrays.equals(mWeaknesses, pokemon.mWeaknesses);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mId, mName, mImgUri, mHeight, mWeight, mCandyCount, mEgg, mSpawnChance, mAverageSpawns, mSpawnTime);
        result = 31 * result + Arrays.hashCode(mType);
        result = 31 * result + Arrays.hashCode(mWeaknesses);
        return result;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "Id=" + mId +
                ", Name='" + mName + '\'' +
                ", ImgUri=" + mImgUri +
                ", Type=" + Arrays.toString(mType) +
                ", Height=" + mHeight +
                ", Weight=" + mWeight +
                ", CandyCount=" + mCandyCount +
                ", Egg='" + mEgg + '\'' +
                ", SpawnChance=" + mSpawnChance +
                ", AverageSpawns=" + mAverageSpawns +
                ", SpawnTime='" + mSpawnTime + '\'' +
                ", Weaknesses=" + Arrays.toString(mWeaknesses) +
                '}';
    }

    private Pokemon(Parcel parcel) {
        mId = parcel.readLong();
        mName = parcel.readString();
        mImgUri = Uri.parse(parcel.readString());

        // First read the length of the array, then create the String[] from the parcel
        mType = new String[parcel.readInt()];
        parcel.readStringArray(mType);

        mHeight = parcel.readString();
        mWeight = parcel.readString();
        mCandyCount = parcel.readInt();
        mEgg = parcel.readString();
        mSpawnChance = parcel.readDouble();
        mAverageSpawns = parcel.readDouble();
        mSpawnTime = parcel.readString();
        // Again, read the length of the array, then create the String[] from the parcel
        mWeaknesses = new String[parcel.readInt()];
        parcel.readStringArray(mWeaknesses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(mId);
        parcel.writeString(mName);
        parcel.writeString(mImgUri.toString());

        // First write the length of the array, then write the String[] to the parcel
        parcel.writeInt(mType.length);
        parcel.writeStringArray(mType);

        parcel.writeString(mHeight);
        parcel.writeString(mWeight);
        parcel.writeInt(mCandyCount);
        parcel.writeString(mEgg);
        parcel.writeDouble(mSpawnChance);
        parcel.writeDouble(mAverageSpawns);
        parcel.writeString(mSpawnTime);

        // Again, write the length of the array, then write the String[] to the parcel
        parcel.writeInt(mWeaknesses.length);
        parcel.writeStringArray(mWeaknesses);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };
}
