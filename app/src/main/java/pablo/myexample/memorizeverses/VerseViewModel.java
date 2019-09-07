package pablo.myexample.memorizeverses;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class VerseViewModel extends AndroidViewModel {
    private VerseRepository mRepository;
    private List<Verse> mAllVerses;

    public VerseViewModel (Application application) {
        super(application);
        mRepository = new VerseRepository(application);
        mAllVerses = mRepository.getmAllVerses();
    }

    List<Verse> getmAllVerses() { return mAllVerses; }

    public void insert(Verse verse) { mRepository.insert(verse); }

    public void deleteByLocation(String location){mRepository.deleteByLocation(location);}

}
