package pablo.myexample.memorizeverses;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VerseDao {

    @Insert
    void insert(Verse verse);

    @Query("SELECT * from verse_table ORDER BY id ASC")
    List<Verse> getAllVerses();

    @Query("DELETE FROM verse_table")
    void deleteAll();
}
