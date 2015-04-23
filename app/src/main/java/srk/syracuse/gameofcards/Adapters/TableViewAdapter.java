package srk.syracuse.gameofcards.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import srk.syracuse.gameofcards.Model.Cards;
import srk.syracuse.gameofcards.R;


public class TableViewAdapter extends RecyclerView.Adapter<TableViewAdapter.ViewHolder> {

    public static OnItemClickListener mItemClickListener;

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }

    ArrayList<Cards> cards;
    Context context;
    int cardBack;

    public TableViewAdapter(Context context, ArrayList<Cards> cards, int cardBack) {
        this.context = context;
        this.cards = cards;
        this.cardBack = cardBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_design_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cards currCard = cards.get(position);
        if (currCard.cardFaceUp) {
            holder.imageView.setImageResource(context.getResources().getIdentifier(currCard.imageID, "drawable",
                    context.getPackageName()));
        } else {
            holder.imageView.setImageResource(this.cardBack);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

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
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, getPosition());
                    }
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemLongClick(view, getPosition());
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);

        void OnItemLongClick(View v, int position);
    }

    public void setOnItemCLickListener(final OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }
}
