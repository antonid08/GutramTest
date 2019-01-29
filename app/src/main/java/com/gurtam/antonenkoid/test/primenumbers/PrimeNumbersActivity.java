package com.gurtam.antonenkoid.test.primenumbers;

import java.math.BigInteger;
import java.util.List;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.utils.Dialogs;
import com.gurtam.antonenkoid.test.utils.Status;
import com.gurtam.antonenkoid.test.utils.UiUtils;
import com.gurtam.antonenkoid.test.utils.views.ProgressPanel;

import android.graphics.drawable.GradientDrawable;
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

    @BindView(R.id.primeNumbers)
    RecyclerView primeNumbers;

    private PrimeNumbersViewModel viewModel;

    private PrimeNumbersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime_numbers_activity);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(PrimeNumbersViewModel.class);
        viewModel.getPrimeNumbers().observe(this, this::bindPrimeNumbers);
        viewModel.getStatus().observe(this, this::bindGeneratingStatus);

        primeNumbers.setLayoutManager(new LinearLayoutManager(this));
        primeNumbers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        primeNumbers.setAdapter(adapter = new PrimeNumbersAdapter());
    }

    @OnClick(R.id.generate)
    void onGenerateClick() {
        if (!TextUtils.isEmpty(limitInput.getText())) {
            BigInteger limit = BigInteger.valueOf(Long.valueOf(limitInput.getText().toString()));
            viewModel.generatePrimeNumbers(limit);
            UiUtils.hideKeyboard(this);
        } else {
            Dialogs.showOkDialog(this, R.string.invalid_limit_error);
        }
    }

    private void bindPrimeNumbers(List<BigInteger> primeNumbers) {
        adapter.setData(primeNumbers);
    }

    private void bindGeneratingStatus(Status status) {
        if (status.isInProgress()) {
            progress.show();
        } else {
            progress.hide();
        }
    }

}
