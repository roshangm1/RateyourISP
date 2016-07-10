package roshan.info.np.rateyourisp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import roshan.info.np.rateyourisp.R;
import roshan.info.np.rateyourisp.interfaces.ClickListener;
import roshan.info.np.rateyourisp.models.MyList;


public class ISPListAdapter extends RecyclerView.Adapter<ISPListAdapter.VH> {

    private ArrayList<MyList> list = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    ClickListener clickListener;

    public ISPListAdapter(Context context) {
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    public void addToTop(MyList value) {
        list.add(value);
        notifyItemInserted(getItemCount()-1);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(inflater.inflate(R.layout.each_isp_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.name.setText(list.get(position).name);
        holder.desp.setText(list.get(position).desp);
        holder.address.setText(list.get(position).address);
        holder.number.setText(list.get(position).number);
        holder.email.setText(list.get(position).email);
        holder.website.setText(list.get(position).website);

        Picasso.with(context).load(list.get(position).logo).into(holder.logo);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClicked(v,position,list.get(position).name,list.get(position).desp);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name, desp,address,number,email,website;
        ImageView logo;
        public VH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_of_isp);
            desp = (TextView) itemView.findViewById(R.id.short_desp);
            address = (TextView) itemView.findViewById(R.id.address);
            number = (TextView) itemView.findViewById(R.id.phone);
            email = (TextView) itemView.findViewById(R.id.email);
            website = (TextView) itemView.findViewById(R.id.website);
            cardView = (CardView) itemView.findViewById(R.id.core_view);
            logo = (ImageView) itemView.findViewById(R.id.logo_isp);
        }
    }

    public void setOnRateClickListener(ClickListener listener) {
        this.clickListener=listener;
    }
}
