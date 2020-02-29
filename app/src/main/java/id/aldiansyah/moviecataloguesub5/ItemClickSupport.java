package id.aldiansyah.moviecataloguesub5;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {
    private RecyclerView mRecyclerView;
    private OnitemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onitemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private final View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongclicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private ItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click, this);
        RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View v) {
                if (mOnItemClickListener != null) {
                    v.setOnClickListener(mOnClickListener);
                }

                if (mOnItemLongClickListener != null) {
                    v.setOnLongClickListener(mOnLongClickListener);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        };
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public void setOnItemClickListener(OnitemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnitemClickListener {
        void onitemClicked(RecyclerView recyclerView, int position, View v);
    }

    interface OnItemLongClickListener {
        boolean onItemLongclicked(RecyclerView recyclerView, int position, View v);
    }
}
