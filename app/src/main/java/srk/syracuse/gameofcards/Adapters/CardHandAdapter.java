package srk.syracuse.gameofcards.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import srk.syracuse.gameofcards.Model.CardCustomize;
import srk.syracuse.gameofcards.Model.Cards;
import srk.syracuse.gameofcards.R;

/**
 * Created by kunalshrivastava on 4/21/15.
 */
public class CardHandAdapter extends RecyclerView.Adapter<CardHandAdapter.ViewHolder> {

    public static OnItemClickListener mItemClickListener;
    ArrayList<Cards> cards;
    Context context;
    String cardBack;

    public CardHandAdapter(Context context, ArrayList<Cards> cards, String cardBack) {
        this.context=context;
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
        if (currCard.cardFaceUp == true) {
            //context.getResources().getIdentifier(currCard.imageID, "drawable", context.getPackageName());
            holder.imageView.setImageResource(context.getResources().getIdentifier(currCard.imageID, "drawable",
                    context.getPackageName()));
        } else {
            holder.imageView.setImageResource(context.getResources().getIdentifier(this.cardBack, "drawable",
                    context.getPackageName()));
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
        }

    }

    public interface OnItemClickListener {
        public void OnItemClick(View v, int position);
    }

    public void setOnItemCLickListener(final OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

}
