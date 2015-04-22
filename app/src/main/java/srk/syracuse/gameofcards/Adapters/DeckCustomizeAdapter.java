package srk.syracuse.gameofcards.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import srk.syracuse.gameofcards.Model.CardCustomize;
import srk.syracuse.gameofcards.R;


public class DeckCustomizeAdapter extends BaseAdapter {
    List<CardCustomize> cardList;
    Context context;
    static PlaceHolder holder = null;

    public DeckCustomizeAdapter(List<CardCustomize> cardList, Context con) {
        if (cardList != null) {
            this.cardList = cardList;
            context = con;
        }
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < cardList.size()) {
            return cardList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class PlaceHolder {
        TextView cardTitle;
        ImageView cardImage;
        CheckBox selected;
        CardCustomize cardCustom;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View adaptView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            adaptView = inflater.inflate(R.layout.customize_deck_item, parent, false);
            holder = new PlaceHolder();
            holder.cardTitle = (TextView) adaptView.findViewById(R.id.cardTitle);
            holder.selected = (CheckBox) adaptView.findViewById(R.id.cardSelect);
            holder.cardImage = (ImageView) adaptView.findViewById(R.id.cardImage);
            adaptView.setTag(holder);
            holder.cardCustom = cardList.get(position);
        } else {
            adaptView = convertView;
            holder = (PlaceHolder) convertView.getTag();
            holder.cardCustom = cardList.get(position);
        }
        holder.cardTitle.setText(String.valueOf(holder.cardCustom.getCardTitle()));
        holder.cardImage.setImageResource(holder.cardCustom.getCardImage());
        holder.selected.setChecked(holder.cardCustom.isSelected());
        return adaptView;
    }

}
