package pablo.myexample.memorizeverses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedVerses extends AppCompatActivity implements CardAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private Intent intent;
    private ArrayList<CardItem> theList;
    private VerseViewModel verseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_verses);
        setTitle("Saved Verses");

        intent = getIntent();
        String answer = intent.getStringExtra("saving?");
        theList = new ArrayList<>();

        verseViewModel = ViewModelProviders.of(this).get(VerseViewModel.class);

        for (int i = 0; i < verseViewModel.getmAllVerses().size(); i++) {
            String v = verseViewModel.getmAllVerses().get(i).getText();
            String l = verseViewModel.getmAllVerses().get(i).getLocation();
            CardItem cardItem = new CardItem(v, l);
            theList.add(cardItem);
        }


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(this, theList);
        cardAdapter.setClickListener(this);
        recyclerView.setAdapter(cardAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + cardAdapter.getItem(position).getLocation().toString() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
