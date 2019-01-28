package com.gurtam.antonenkoid.test.primenumbers;

import android.view.ViewGroup;
import android.widget.TextView;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.utils.BaseRecyclerAdapter;

import java.math.BigInteger;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * Adapter for prime numbers list.
 *
 * @author antonenkoid
 */
public class PrimeNumbersAdapter extends BaseRecyclerAdapter<BigInteger, BaseRecyclerAdapter.ItemViewHolder<BigInteger>> {

    @NonNull
    @Override
    public ItemViewHolder<BigInteger> onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new NumberViewHolder(viewGroup);
    }

    static class NumberViewHolder extends BaseRecyclerAdapter.ItemViewHolder<BigInteger> {

        @BindView(R.id.number)
        TextView number;

        NumberViewHolder(ViewGroup parent) {
            super(parent, R.layout.prime_number_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(BigInteger item) {
            number.setText(String.valueOf(item));
        }

    }
}
