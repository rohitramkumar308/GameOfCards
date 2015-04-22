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

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }

    ArrayList<Cards> cards;
    Context context;
    String cardBack;

    public CardHandAdapter(Context context, ArrayList<Cards> cards, String cardBack) {
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
//        holder.cardBack.setImageResource(context.getResources().getIdentifier(this.cardBack, "drawable",
//                context.getPackageName()));
//        if (currCard.cardFaceUp == true) {
//            holder.cardBack.setVisibility(View.VISIBLE);
//        } else {
//            holder.cardBack.setVisibility(View.GONE);
//        }
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
//            cardBack = (ImageView) v.findViewById(R.id.cardDesignBack);
//
//            cardBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mItemClickListener != null) {
//                        mItemClickListener.OnItemClick(v, getPosition());
//                    }
//                }
//            });
            cardFront.setOnClickListener(new View.OnClickListener() {
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
