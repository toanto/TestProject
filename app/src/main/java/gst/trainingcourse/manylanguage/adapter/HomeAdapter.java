package gst.trainingcourse.manylanguage.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.interface_click.onClickItemRecycerviewGirl;
import gst.trainingcourse.manylanguage.model.Information;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Information> mList;
    private ArrayList<Information> mArrayList;
    private onClickItemRecycerviewGirl mOnClick;
    private Context mContext;

    public HomeAdapter(Context context, List<Information> information, onClickItemRecycerviewGirl clickItemRecycerview) {
        this.mList = information;
        this.mOnClick = clickItemRecycerview;
        this.mContext = context;
        mArrayList = new ArrayList<>();
        mArrayList.addAll(mList);
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
        public TextView txtName, txtLink, txtMoreImg, txtMoreVideo;
        public ImageView imgGirl, imgLike, imgUnLike;


        public View.OnClickListener onClickImage = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onClickImage(getAdapterPosition());
            }
        };

        public View.OnClickListener onClickIconLove = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onClickIconLove(getAdapterPosition());
                if (imgUnLike.isPressed()) {
                    imgLike.setVisibility(View.VISIBLE);
                    imgUnLike.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "Like", Toast.LENGTH_SHORT).show();
                }
                if (imgLike.isPressed()) {
                    imgLike.setVisibility(View.INVISIBLE);
                    imgUnLike.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "UnLike", Toast.LENGTH_SHORT).show();
                }
            }
        };

        public View.OnClickListener onClickTextMoreVideo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onClickMoreVideo(getAdapterPosition());
            }
        };

        public View.OnClickListener onClickTextMoreImg = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onClickMoreImage(getAdapterPosition());
            }
        };

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtLink = itemView.findViewById(R.id.txtLink);
            txtMoreImg = itemView.findViewById(R.id.txtMoreImg);
            txtMoreVideo = itemView.findViewById(R.id.txtMoreVideo);
            imgGirl = itemView.findViewById(R.id.imgGirl);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgUnLike = itemView.findViewById(R.id.imgUnLike);

            txtMoreImg.setOnClickListener(onClickTextMoreImg);
            txtMoreVideo.setOnClickListener(onClickTextMoreVideo);
            imgGirl.setOnClickListener(onClickImage);
            imgLike.setOnClickListener(onClickIconLove);
            imgUnLike.setOnClickListener(onClickIconLove);
        }
    }

    public void querySearch(String data) {
        data = data.toLowerCase(Locale.getDefault());
        mList.clear();

        if (data.length() == 0) {
            mList.addAll(mArrayList);
        } else {
            for (Information information : mArrayList) {
                if (information.getmHoTen().toLowerCase(Locale.getDefault()).contains(data)) mList.add(information);
            }
        }

        notifyDataSetChanged();
    }
}
