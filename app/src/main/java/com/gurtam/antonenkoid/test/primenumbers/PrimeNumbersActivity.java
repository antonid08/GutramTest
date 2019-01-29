package com.gurtam.antonenkoid.test.primenumbers;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersChunk;
import com.gurtam.antonenkoid.test.utils.Dialogs;
import com.gurtam.antonenkoid.test.utils.Status;
import com.gurtam.antonenkoid.test.utils.UiUtils;
import com.gurtam.antonenkoid.test.utils.views.ProgressPanel;
import com.gurtam.antonenkoid.test.utils.views.pagination.Page;
import com.gurtam.antonenkoid.test.utils.views.pagination.PaginationView;

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

    private static final int NUMBERS_PAGE_SIZE = 100;

    @BindView(R.id.progress)
    ProgressPanel progress;

    @BindView(R.id.limitInput)
    EditText limitInput;

    @BindView(R.id.pagination)
    PaginationView pagination;

    @BindView(R.id.primeNumbers)
    RecyclerView primeNumbers;

    private PrimeNumbersViewModel viewModel;

    private PrimeNumbersAdapter adapter;

    private Page currentNumbersPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime_numbers_activity);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(PrimeNumbersViewModel.class);
        viewModel.getPrimeNumbersChunk().observe(this, this::bindPrimeNumbersChunk);
        viewModel.getStatus().observe(this, this::bindGeneratingStatus);

        currentNumbersPage = new Page(0, NUMBERS_PAGE_SIZE);
        pagination.setOnPageChangedListener(new NumbersPageChangeListener());
        UiUtils.setVisibility(false, pagination);

        primeNumbers.setLayoutManager(new LinearLayoutManager(this));
        primeNumbers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        primeNumbers.setAdapter(adapter = new PrimeNumbersAdapter());
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
        currentNumbersPage = primeNumbersChunk.getPage();
        adapter.setData(primeNumbersChunk.getPrimeNumbers());
    }

    private void bindGeneratingStatus(Status status) {
        if (status.isInProgress()) {
            progress.show();
        } else {
            viewModel.receivePrimeNumbers(new Page(0, NUMBERS_PAGE_SIZE));
            progress.hide();
        }
    }

    private class NumbersPageChangeListener implements PaginationView.OnPageChangedListener {

        @Override
        public void onPreviousPage() {

        }

        @Override
        public void onNextPage() {

        }
    }

}
