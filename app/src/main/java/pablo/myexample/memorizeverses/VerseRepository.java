package pablo.myexample.memorizeverses;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class VerseRepository {

    private VerseDao verseDao;
    private List<Verse> mAllVerses;

    VerseRepository(Application application) {
        VerseRoomDatabase db = VerseRoomDatabase.getDatabase(application);
        verseDao = db.verseDao();
        mAllVerses = verseDao.getAllVerses();
    }

    List<Verse> getmAllVerses() {
        return mAllVerses;
    }


    public void insert (Verse verse) {
        new insertAsyncTask(verseDao).execute(verse);
    }


    private static class insertAsyncTask extends AsyncTask<Verse, Void, Void> {

        private VerseDao mAsyncTaskDao;

        insertAsyncTask(VerseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Verse... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
