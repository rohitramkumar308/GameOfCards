package srk.syracuse.gameofcards.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import srk.syracuse.gameofcards.R;


public class DesignAdapter extends RecyclerView.Adapter<DesignAdapter.ViewHolder> {

    OnItemClickListener mOnItemClickListner;
    private int[] mDataSet;
    private boolean isCardType;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            imageView = (ImageView) v.findViewById(R.id.cardDesign);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListner != null) {
                        mOnItemClickListner.onItemClick(itemView, getPosition());
                    }
                }
            });
        }

    }

    public DesignAdapter(int[] dataSet, boolean isCardType) {
        mDataSet = dataSet;
        this.isCardType = isCardType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (isCardType) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_back_layout, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.table_design_layout, viewGroup, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.imageView.setImageResource(mDataSet[position]);
        viewHolder.imageView.setTag(mDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListner = mOnItemClickListener;
    }

}
