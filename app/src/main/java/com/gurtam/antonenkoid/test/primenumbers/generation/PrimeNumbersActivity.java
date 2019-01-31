package com.gurtam.antonenkoid.test.primenumbers.generation;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.PrimeNumbersChunk;
import com.gurtam.antonenkoid.test.primenumbers.history.GenerationsHistoryActivity;
import com.gurtam.antonenkoid.test.utils.Dialogs;
import com.gurtam.antonenkoid.test.utils.Status;
import com.gurtam.antonenkoid.test.utils.UiUtils;
import com.gurtam.antonenkoid.test.utils.pagination.PaginationView;
import com.gurtam.antonenkoid.test.utils.views.ProgressPanel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private boolean isMenuEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime_numbers_activity);
        ButterKnife.bind(this);

        pagination.setOnPageChangedListener(new NumbersPageChangeListener());
        pagination.setPreviousPageAvailable(false);
        pagination.setNextPageAvailable(false);

        numbers.setLayoutManager(new LinearLayoutManager(this));
        numbers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        numbers.setAdapter(adapter = new PrimeNumbersAdapter());

        viewModel = ViewModelProviders.of(this).get(PrimeNumbersViewModel.class);
        viewModel.getPrimeNumbersChunk().observe(this, this::bindPrimeNumbersChunk);
        viewModel.getStatus().observe(this, this::bindStatus);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return isMenuEnabled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.numbers_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.clear_cache:
                viewModel.clearPrimeNumbersCache();
                return true;
            case R.id.history:
                GenerationsHistoryActivity.start(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.generate)
    void onGenerateClick() {
        if (!TextUtils.isEmpty(limitInput.getText())) {
            viewModel.getGeneratingTimeTracker().start();
            viewModel.generatePrimeNumbers(Integer.valueOf(limitInput.getText().toString()));
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

        Status generatingStatus = viewModel.getStatus().getValue();

        if (viewModel.getGeneratingTimeTracker().isStarted() && generatingStatus != null && !generatingStatus.isInProgress()) {
            float generatingTime = (float) viewModel.getGeneratingTimeTracker().finish() / 1000;
            viewModel.addGenerationToHistory(generatingTime);
            Dialogs.showOkDialog(this, String.format(getString(R.string.generating_time_pattern), generatingTime));
        }
    }

    private void bindStatus(Status status) {
        if (status.isInProgress()) {
            progress.show();
            setMenuEnabled(false);
        } else if (status.isSuccess()) {
            //todo save state of pagination after orientation changing
            progress.hide();
            viewModel.receiveFirstPrimeNumbersPage();
            setMenuEnabled(true);
        } else {
            processCommonError(status.getError());
            setMenuEnabled(true);
        }
    }

    private void processCommonError(String errorText) {
        progress.hide();
        adapter.clear();
        pagination.setPreviousPageAvailable(false);
        pagination.setNextPageAvailable(false);
        Dialogs.showOkDialog(this, errorText);
    }

    private boolean isMenuEnabled() {
        return isMenuEnabled;
    }

    private void setMenuEnabled(boolean enabled) {
        this.isMenuEnabled = enabled;
        invalidateOptionsMenu();
    }

    private class NumbersPageChangeListener implements PaginationView.OnPageChangedListener {

        @Override
        public void onPreviousPage() {
            viewModel.receivePreviousPrimeNumbersPage();
        }

        @Override
        public void onNextPage() {
            viewModel.receiveNextPrimeNumbersPage();
        }
    }

}
