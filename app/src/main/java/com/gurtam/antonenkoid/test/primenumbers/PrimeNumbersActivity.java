package com.gurtam.antonenkoid.test.primenumbers;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersChunk;
import com.gurtam.antonenkoid.test.utils.Dialogs;
import com.gurtam.antonenkoid.test.utils.Status;
import com.gurtam.antonenkoid.test.utils.UiUtils;
import com.gurtam.antonenkoid.test.utils.pagination.PaginationManager;
import com.gurtam.antonenkoid.test.utils.pagination.PaginationView;
import com.gurtam.antonenkoid.test.utils.views.ProgressPanel;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The screen which allows the user generate prime numbers.
 *
 * @author antonenkoid
 */
public class PrimeNumbersActivity extends AppCompatActivity {

    @BindView(R.id.progress)
    ProgressPanel progress;

    @BindView(R.id.limitInput)
    EditText limitInput;

    @BindView(R.id.pagination)
    PaginationView pagination;

    @BindView(R.id.numbers)
    RecyclerView numbers;

    private PrimeNumbersViewModel viewModel;

    private PrimeNumbersAdapter adapter;

    private PaginationManager paginationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime_numbers_activity);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(PrimeNumbersViewModel.class);
        viewModel.getPrimeNumbersChunk().observe(this, this::bindPrimeNumbersChunk);
        viewModel.getStatus().observe(this, this::bindGeneratingStatus);

        paginationManager = new PaginationManager();
        pagination.setOnPageChangedListener(new NumbersPageChangeListener());
        UiUtils.setVisibility(false, pagination);

        numbers.setLayoutManager(new LinearLayoutManager(this));
        numbers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        numbers.setAdapter(adapter = new PrimeNumbersAdapter());
    }

    @OnClick(R.id.generate)
    void onGenerateClick() {
        if (!TextUtils.isEmpty(limitInput.getText())) {
            int limit = Integer.valueOf(limitInput.getText().toString());
            viewModel.generatePrimeNumbers(limit);
            UiUtils.hideKeyboard(this);
        } else {
            Dialogs.showOkDialog(this, R.string.invalid_limit_error);
        }
    }

    private void bindPrimeNumbersChunk(PrimeNumbersChunk primeNumbersChunk) {
        UiUtils.setVisibility(true, pagination);
        adapter.setData(primeNumbersChunk.getNumbers());
        numbers.scrollToPosition(0);

        pagination.setNextPageAvailable(primeNumbersChunk.isNextPageAvailable());
        pagination.setPreviousPageAvailable(primeNumbersChunk.getPage().getIndex() > 0);
    }

    private void bindGeneratingStatus(Status status) {
        if (status.isInProgress()) {
            progress.show();
        } else {
            paginationManager = new PaginationManager();
            viewModel.receivePrimeNumbers(paginationManager.getCurrentPage());
            progress.hide();
        }
    }

    private class NumbersPageChangeListener implements PaginationView.OnPageChangedListener {

        @Override
        public void onPreviousPage() {
            viewModel.receivePrimeNumbers(paginationManager.previousPage());
        }

        @Override
        public void onNextPage() {
            viewModel.receivePrimeNumbers(paginationManager.nextPage());
        }
    }

}
