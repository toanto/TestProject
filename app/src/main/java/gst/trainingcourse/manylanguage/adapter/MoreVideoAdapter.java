package gst.trainingcourse.manylanguage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.interface_click.onClickItemRecyclerviewVideo;

public class MoreVideoAdapter extends RecyclerView.Adapter<MoreVideoAdapter.ViewHolder> {

    private ArrayList<String> mArrayList;
    private onClickItemRecyclerviewVideo mOnClick;

    public MoreVideoAdapter(ArrayList<String> arrayList, onClickItemRecyclerviewVideo clickItemRecyclerviewVideo) {
        this.mArrayList = arrayList;
        this.mOnClick = clickItemRecyclerviewVideo;
    }

    @NonNull
    @Override
    public MoreVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_more_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreVideoAdapter.ViewHolder holder, int position) {
        holder.txtLinkVideo.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtLinkVideo;
        public RelativeLayout relativeLayout;

        private View.OnClickListener mClickLinkVideo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onClickText(getAdapterPosition());
            }
        };

        public ViewHolder(View itemView) {
            super(itemView);

            txtLinkVideo = itemView.findViewById(R.id.txtLinkVideo);
            relativeLayout = itemView.findViewById(R.id.rlLinkVideo);
            relativeLayout.setOnClickListener(mClickLinkVideo);
        }
    }
}
