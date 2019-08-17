package pablo.myexample.memorizeverses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private VerseViewModel verseViewModel;

    private RequestQueue queue;
    private String chosenBook;
    private TextView foundVerses, foundLocation;
    private Spinner book;
    private EditText chapter, startverse, endverse;
    private String[] bookList = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("NET Verse Search");

        verseViewModel = ViewModelProviders.of(this).get(VerseViewModel.class);

        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        foundLocation = findViewById(R.id.displayVerseLocation);
        foundVerses = findViewById(R.id.displayVerse);
        book = findViewById(R.id.spinnerBook);
        book.setOnItemSelectedListener(this);
        chapter = findViewById(R.id.inputChapter);
        startverse = findViewById(R.id.inputVerseStart);
        endverse = findViewById(R.id.inputVerseEnd);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        book.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chosenBook = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Save(View view) {
        if(foundVerses.getText().toString().matches("") || foundLocation.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
        }else{
            Verse verse = new Verse(foundVerses.getText().toString(), foundLocation.getText().toString());
            verseViewModel.insert(verse);
            Intent intent = new Intent(this, SavedVerses.class);
            intent.putExtra("verse",foundVerses.getText().toString());
            intent.putExtra("location",foundLocation.getText().toString());
            startActivity(intent);}
    }

    public void GetVerse(View view) {

        final String ch = chapter.getText().toString();
        final String v1 = startverse.getText().toString();
        final String v2 = endverse.getText().toString();

        String URL = "https://labs.bible.org/api/?passage=" + chosenBook + "%20" + ch + ":" + v1 + "-" + v2;

        if (ch.matches("")) {
            Toast.makeText(getApplicationContext(), "Input a chapter.", Toast.LENGTH_SHORT).show();
        } else if (v1.matches("")) {
            Toast.makeText(getApplicationContext(), "Input the starting verse.", Toast.LENGTH_SHORT).show();
        } else if (v2.matches("")) {
            Toast.makeText(getApplicationContext(), "Input the ending verse.", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.replace("<b>", "").replace("</b>", "");
                    foundVerses.setText(result);
                    foundLocation.setText("(NET) " + chosenBook + " " + ch + " : " + v1 + " - " + v2);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Could not load.", Toast.LENGTH_SHORT).show();
                }
            });

            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    public void Verses(View view) {
        Intent intent = new Intent(this, SavedVerses.class);
        startActivity(intent);
    }
}