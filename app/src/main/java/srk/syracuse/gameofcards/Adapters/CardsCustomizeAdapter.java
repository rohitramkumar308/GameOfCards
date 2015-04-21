package srk.syracuse.gameofcards.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import srk.syracuse.gameofcards.R;

public class CardsCustomizeAdapter extends RecyclerView.Adapter<CardsCustomizeAdapter.PlaceHolder> {

    OnItemClickListener mOnItemClickListner;
    List<Map<String, ?>> cardList;
    Context context;
    PlaceHolder pH;

    public CardsCustomizeAdapter(List<Map<String, ?>> cardList, Context con) {
        context = con;
        this.cardList = cardList;
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {
        TextView cardTitle;
        ImageView cardImage;
        CheckBox cardSelected;

        public PlaceHolder(final View itemView) {
            super(itemView);
            cardTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            cardSelected = (CheckBox) itemView.findViewById(R.id.movieSelect);
            cardImage = (ImageView) itemView.findViewById(R.id.movieImage);
            cardSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final HashMap<String, Boolean> selectedMap = (HashMap<String, Boolean>) cardList.get(getPosition());
                    selectedMap.put("selection", isChecked);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListner != null) {
                        mOnItemClickListner.onItemClick(itemView, getPosition());
                    }
                }
            });

        }


        void bindMovieData(Map<String, ?> movie) {
            cardTitle.setText(String.valueOf(movie.get("name")));
            cardSelected.setChecked((Boolean) movie.get("selection"));
        }

    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View adaptView;
        adaptView = inflater.inflate(R.layout.customize_deck_item, parent, false);
        pH = new PlaceHolder(adaptView);
        return pH;
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        holder.bindMovieData(cardList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if ((Double) cardList.get(position).get("rating") < 8)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public List<Map<String, ?>> getList() {
        if (cardList != null) {
            return cardList;
        }
        return null;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListner = mOnItemClickListener;
    }

    public Object getItem(int position) {
        if (position >= 0 && position < cardList.size()) {
            return cardList.get(position);
        }
        return null;
    }
}
