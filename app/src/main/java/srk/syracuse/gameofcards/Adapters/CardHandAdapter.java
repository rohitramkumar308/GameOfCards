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

/**
 * Created by kunalshrivastava on 4/21/15.
 */
public class CardHandAdapter extends RecyclerView.Adapter<CardHandAdapter.ViewHolder> {

    public static OnItemClickListener mItemClickListener;
    public static OnItemLongClickListener mItemLongClickListener;

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }

    ArrayList<Cards> cards;
    Context context;
    int cardBack;

    public CardHandAdapter(Context context, ArrayList<Cards> cards, int cardBack) {
        this.context = context;
        this.cards = cards;
        this.cardBack = cardBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_back_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cards currCard = cards.get(position);
        holder.cardFront.setImageResource(context.getResources().getIdentifier(currCard.imageID, "drawable",
                context.getPackageName()));
        holder.cardBack.setImageResource(this.cardBack);
        if (currCard.cardFaceUp == true) {
            holder.cardBack.setVisibility(View.INVISIBLE);
        } else {
            holder.cardBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardFront;
        ImageView cardBack;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            cardFront = (ImageView) v.findViewById(R.id.cardDesign);
            cardBack = (ImageView) v.findViewById(R.id.cardDesignBack);

            cardBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, getPosition());
                    }
                }
            });

            cardFront.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mItemLongClickListener != null) {
                        mItemLongClickListener.OnItemLongClick(view, getPosition());
                    }
                    return true;
                }
            });
        }

    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean OnItemLongClick(View v, int position);
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener onItemLongClickListener) {
        this.mItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }
}
