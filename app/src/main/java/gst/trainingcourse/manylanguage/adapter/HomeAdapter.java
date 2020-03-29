package gst.trainingcourse.manylanguage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.model.Information;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Information> mList;

    public HomeAdapter(List<Information> information) {
        this.mList = information;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Information information = mList.get(position);

        holder.txtName.setText(information.getmHoTen());
        holder.txtLink.setText(information.getmLink());
        Linkify.addLinks(holder.txtLink, Linkify.WEB_URLS);
        holder.imgGirl.setImageResource(information.getmImage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtLink;
        public ImageView imgGirl;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtLink = itemView.findViewById(R.id.txtLink);
            imgGirl = itemView.findViewById(R.id.imgGirl);
        }
    }
}
