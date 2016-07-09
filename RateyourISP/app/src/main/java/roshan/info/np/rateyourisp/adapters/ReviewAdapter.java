package roshan.info.np.rateyourisp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import roshan.info.np.rateyourisp.R;
import roshan.info.np.rateyourisp.models.Post;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.VH> {

    private ArrayList<Post> posts = new ArrayList<>();

    LayoutInflater inflater;
    Context context;

    public void addToTop(Post post) {
        posts.add(post);
        notifyItemInserted(getItemCount() - 1);
    }

    public ReviewAdapter(Context context) {
        this.context=context;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(inflater.inflate(R.layout.review_each,parent,false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.reviewer.setText(posts.get(position).reviewer);
        holder.review.setText(posts.get(position).review);
        holder.rA.setRating(posts.get(position).r1);
        holder.rB.setRating(posts.get(position).r2);
        holder.rC.setRating(posts.get(position).r3);
        holder.rD.setRating(posts.get(position).r4);
        Picasso.with(context).load(posts.get(position).imageUrl).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView reviewer, review;
        RatingBar rA,rB,rC,rD;
        public VH(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.profile_reviewer);
            reviewer= (TextView) itemView.findViewById(R.id.reviewer);
            review= (TextView) itemView.findViewById(R.id.review);
            rA= (RatingBar) itemView.findViewById(R.id.rateA);
            rB= (RatingBar) itemView.findViewById(R.id.rateB);
            rC= (RatingBar) itemView.findViewById(R.id.rateC);
            rD= (RatingBar) itemView.findViewById(R.id.rateD);
        }
    }
}
