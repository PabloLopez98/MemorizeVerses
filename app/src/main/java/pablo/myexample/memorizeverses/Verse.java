package pablo.myexample.memorizeverses;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "verse_table")
public class Verse {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private String text;

    @NonNull
    private String location;

    public Verse(@NonNull String text, @NonNull String location) {
        this.text = text;
        this.location = location;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

