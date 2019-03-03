package com.fstyle.library.helper;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.SharedPreferences;



public abstract class RoomAssetDatabaseFactory {
    public static <T extends RoomDatabase> T getInstance(Context context,T instance ,Class<T> tClass, String name , int version) {
        if (instance == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("room_asset_database_settings_" + name, Context.MODE_PRIVATE);
            if (sharedPreferences.getInt("database_version", 1) != version) {
                context.deleteDatabase(name);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("database_version", version);
                editor.commit();
            }
            instance = Room.databaseBuilder(context,
                    tClass , name).allowMainThreadQueries().openHelperFactory(new AssetSQLiteOpenHelperFactory()).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}

