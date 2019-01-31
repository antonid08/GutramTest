package com.gurtam.antonenkoid.test.primenumbers.history;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntity;
import com.gurtam.antonenkoid.test.utils.BaseRecyclerAdapter;
import com.gurtam.antonenkoid.test.utils.converters.DateToStringConverter;

import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * Adapter for history of prime numbers generation.
 *
 * @author antonenkoid
 */
public class GenerationsHistoryAdapter
    extends BaseRecyclerAdapter<GenerationEntity, BaseRecyclerAdapter.ItemViewHolder<GenerationEntity>> {

    @NonNull
    @Override
    public ItemViewHolder<GenerationEntity> onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new GenerationViewHolder(viewGroup);
    }

    static class GenerationViewHolder extends ItemViewHolder<GenerationEntity> {

        @BindView(R.id.timestamp)
        TextView timestamp;

        @BindView(R.id.limit)
        TextView limit;

        @BindView(R.id.elapsedTime)
        TextView elapsedTime;

        GenerationViewHolder(ViewGroup parent) {
            super(parent, R.layout.generations_history_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(GenerationEntity item) {
            timestamp.setText(
                new DateToStringConverter(DateToStringConverter.RU_LOCALE_TIME_PATTERN).convert(item.getTimestamp()));
            limit.setText(String.valueOf(item.getLimit()));
            elapsedTime.setText(String.valueOf(item.getElapsedTimeSeconds()));
        }

    }
}
