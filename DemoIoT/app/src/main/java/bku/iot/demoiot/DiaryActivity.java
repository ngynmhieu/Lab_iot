package bku.iot.demoiot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiaryActivity extends AppCompatActivity{

    private TextView DiaryTable;
    String [] DiaryList;
    String [] DiaryTimeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_activity);
        DiaryList = getIntent().getStringArrayExtra("DiaryList");
        DiaryTimeList = getIntent().getStringArrayExtra("DiaryTimeList");

        List<DiaryEntry> entries = new ArrayList<>();
        for (int i = 0; i < DiaryList.length; i++) {
            entries.add(new DiaryEntry(DiaryTimeList[i], DiaryList[i]));
        }

        RecyclerView diaryRecyclerView = findViewById(R.id.diaryRecyclerView);
        diaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DiaryEntryAdapter adapter = new DiaryEntryAdapter(entries);
        diaryRecyclerView.setAdapter(adapter);
    }

}
