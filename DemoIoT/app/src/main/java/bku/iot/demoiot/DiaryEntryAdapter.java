package bku.iot.demoiot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder> {
    private List<DiaryEntry> diaryEntries;

    public DiaryEntryAdapter(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    @NonNull
    @Override
    public DiaryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_activity, parent, false);
        return new DiaryEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryViewHolder holder, int position) {
        DiaryEntry diaryEntry = diaryEntries.get(position);
        holder.timeView.setText(diaryEntry.getTime());
        holder.actionView.setText(diaryEntry.getAction());
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }

    static class DiaryEntryViewHolder extends RecyclerView.ViewHolder {
        TextView timeView;
        TextView actionView;

        DiaryEntryViewHolder(View view) {
            super(view);
            timeView = view.findViewById(R.id.diary_time);
            actionView = view.findViewById(R.id.diary_action);
        }
    }
}