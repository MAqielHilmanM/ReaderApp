package gits.helper.nfcapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import gits.helpers.dao.PackDao;

/**
 * Created by root on 24/11/16.
 */

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.ViewHolder>{
    List<PackDao.AllPackDao> list = new ArrayList<>();
    Context context;

    public PackAdapter(List<PackDao.AllPackDao> dummy) {
        list = dummy;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_row, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTotal.setText("Total " + list.get(position).getTotal_sticker());
        holder.tvTitle.setText(list.get(position).getName());
        holder.tvStat.setText("Status "+ (list.get(position).getUsed() == 0 ? "Belum Terpakai" : "Terpakai"));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ScanActivity.class);
                i.putExtra("id", list.get(position).getId());
                i.putExtra("nama", list.get(position).getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle, tvStat, tvTotal;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_main);
            tvTitle = (TextView) itemView.findViewById(R.id.pack_name);
            tvStat = (TextView) itemView.findViewById(R.id.pack_stat);
            tvTotal = (TextView) itemView.findViewById(R.id.total_Sticker);
        }
    }
}
