package idv.kuan.flashcard5.adapters;

import idv.kuan.flashcard5.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idv.kuan.flashcard5.database.modle.Word;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<Word> wordList;

    public WordAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public WordAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.WordViewHolder holder, int position) {
        Word word = wordList.get(position);

        holder.txtvWordTerm.setText(word.getTerm());
        holder.txtvWordTranslation.setText(word.getTranslation());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        public TextView txtvWordTerm;
        public TextView txtvWordTranslation;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvWordTerm = itemView.findViewById(R.id.item_word_txtv_term);
            txtvWordTranslation = itemView.findViewById(R.id.item_word_txtv_translation);
        }
    }
}
