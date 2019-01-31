package com.gurtam.antonenkoid.test.primenumbers.history;

import java.util.List;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Screen that contains history of prime number generations history.
 *
 * @author antonenkoid
 */
public class GenerationsHistoryActivity extends AppCompatActivity {

    @BindView(R.id.history)
    RecyclerView history;

    private GenerationsHistoryAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, GenerationsHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generations_history_activity);
        ButterKnife.bind(this);

        history.setLayoutManager(new LinearLayoutManager(this));
        history.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        history.setAdapter(adapter = new GenerationsHistoryAdapter());

        GenerationsHistoryViewModel viewModel = ViewModelProviders.of(this).get(GenerationsHistoryViewModel.class);
        viewModel.getHistory().observe(this, this::bindHistory);
    }

    private void bindHistory(List<GenerationEntity> history) {
        adapter.setData(history);
    }
}
