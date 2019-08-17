package pablo.myexample.memorizeverses;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Verse.class}, version = 2)
public abstract class VerseRoomDatabase extends RoomDatabase {


    public abstract VerseDao verseDao();

    private static volatile VerseRoomDatabase INSTANCE;

    static VerseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VerseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VerseRoomDatabase.class, "verse_database").addCallback(sRoomDatabaseCallback).allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final VerseDao verseDao;

        PopulateDbAsync(VerseRoomDatabase db) {
            verseDao = db.verseDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            //This code deletes all the data after every startup, we dont want that in the release!!!
            //verseDao.deleteAll();

            return null;
        }

    }
}
