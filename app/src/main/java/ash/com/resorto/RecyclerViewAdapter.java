package ash.com.resorto;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsH on 24/10/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {


    private List<String> res_name1 = new ArrayList<>();
    private List<String> avg_cost1 = new ArrayList<>();
    private List<String> res_img1 = new ArrayList<>();
    private List<String> res_address1 = new ArrayList<>();
    private List<String> res_rating1 = new ArrayList<>();
    private List<String> res_lattitude1 = new ArrayList<>();
    private List<String> res_longitude1 = new ArrayList<>();
    private Context context;
    SearchJsonParsing searchJsonParsing;
    Main2Activity main2Activity;

    public RecyclerViewAdapter(List<String> res_name1, List<String> avg_cost1, List<String> res_img1, List<String> res_address1, List<String> res_rating1, List<String> res_lattitude1, List<String> res_longitude1, Context context) {
        main2Activity = new Main2Activity();
        this.res_name1 = res_name1;
        this.avg_cost1 = avg_cost1;
        this.res_img1 = res_img1;
        this.res_address1 = res_address1;
        this.res_rating1 = res_rating1;
        this.res_lattitude1 = res_lattitude1;
        this.res_longitude1 = res_longitude1;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {

        holder.name.setText(res_name1.get(position));
        holder.pay.setText("Cost for 2 :" + avg_cost1.get(position));
        holder.rating.setText("Rated :" + res_rating1.get(position));
        holder.address.setText(res_address1.get(position));
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (200 * scale + 0.5f);
        final float scale1 = context.getResources().getDisplayMetrics().density;
        int pixels1 = (int) (200 * scale1 + 0.5f);
        Picasso.with(context).load(res_img1.get(position)).resize(pixels, pixels1).error(R.drawable.cast_album_art_placeholder).into(holder.imageView);


    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, null);
        return new viewHolder(view);
    }

    @Override
    public int getItemCount() {
        return res_name1.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView name;
        private TextView address;
        private TextView pay;
        private TextView rating;
        private Fragment fragment;
        private Button mapButton;

        public viewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.pay = (TextView) itemView.findViewById(R.id.pay);
            this.rating = (TextView) itemView.findViewById(R.id.ratingBar);
            this.mapButton = (Button) itemView.findViewById(R.id.button2);
            this.mapButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == mapButton.getId()) {
                Log.d("***********************", "" + res_name1.get(getAdapterPosition()));
                Log.d("***********************", "INRECYCLER VIEW");
                Main2Activity.getInstance().setLattitude(Double.valueOf(res_lattitude1.get(getAdapterPosition())));
                Main2Activity.getInstance().setLongitude(Double.valueOf(res_longitude1.get(getAdapterPosition())));
                Main2Activity.getInstance().activity();

            } else {

            }
        }

    }


}
