package gst.trainingcourse.manylanguage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.model.FullImage;

public class MoreImageAdapter extends RecyclerView.Adapter<MoreImageAdapter.ViewHolder> {

    private List<FullImage> mList;
    private Context mContext;

    public MoreImageAdapter(Context context, ArrayList<FullImage> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MoreImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_more_img, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoreImageAdapter.ViewHolder holder, int position) {
        FullImage fullImage = mList.get(position);
        //thư viện picasso hỗ trợ dowload ảnh từ url
        Picasso.with(mContext).load(fullImage.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgGirl);
        }
    }
}
