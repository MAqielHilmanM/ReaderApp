package gits.helper.nfcapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gits.helpers.dao.PackStickerDao;
import gits.helpers.dao.StickerIdByPack;

/**
 * Created by root on 25/11/16.
 */

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder>{
    List<PackStickerDao.PackSticker> list = new ArrayList<>();

    public ScanAdapter(List<PackStickerDao.PackSticker> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_row, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id_sticker.setText(list.get(position).getId());
        holder.id_number.setText(String.valueOf(list.get(position).getNumber()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_sticker, id_number;
        public ViewHolder(View itemView) {
            super(itemView);
            id_sticker = (TextView) itemView.findViewById(R.id.id_sticker);
            id_number = (TextView) itemView.findViewById(R.id.id_number);
        }
    }
}
