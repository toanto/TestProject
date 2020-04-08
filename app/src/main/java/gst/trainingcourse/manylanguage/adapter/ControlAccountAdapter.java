package gst.trainingcourse.manylanguage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.interface_click.onClickItemRecyclerviewAccount;
import gst.trainingcourse.manylanguage.model.Account;

public class ControlAccountAdapter extends RecyclerView.Adapter<ControlAccountAdapter.Viewholder> {

    private List<Account> mAccountArrayList;
    private ArrayList<Account> mArrayList;
    private onClickItemRecyclerviewAccount mOnClick;

    public ControlAccountAdapter(List<Account> mAccountArrayList, onClickItemRecyclerviewAccount mOnClick) {
        this.mAccountArrayList = mAccountArrayList;
        this.mOnClick = mOnClick;
        mArrayList = new ArrayList<>();
        mArrayList.addAll(mAccountArrayList);
    }

    @NonNull
    @Override
    public ControlAccountAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_account, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ControlAccountAdapter.Viewholder holder, int position) {
        Account account = mAccountArrayList.get(position);

        holder.txtCountAccount.setText(position + 1 + "");
        holder.txtUsername.setText(account.getUsername());
        holder.txtPassword.setText(account.getPassword());
        holder.txtEmail.setText(account.getEmail());
        holder.txtAddress.setText(account.getAddress());
        holder.txtPhone.setText(account.getTelephone());
    }

    @Override
    public int getItemCount() {
        return mAccountArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView txtUsername, txtPassword, txtCountAccount, txtEmail, txtAddress, txtPhone;
        public ImageView imgDelete;

        public View.OnClickListener onClickImgDelete = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onClickImgDelete(getAdapterPosition());
            }
        };

        public Viewholder(View itemView) {
            super(itemView);

            txtCountAccount = itemView.findViewById(R.id.txtCountAccount);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtPassword = itemView.findViewById(R.id.txtPassword);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(onClickImgDelete);
        }
    }

    public void querySearch(String data) {
        data = data.toLowerCase(Locale.getDefault());
        mAccountArrayList.clear();

        if (data.length() == 0) {
            mAccountArrayList.addAll(mArrayList);
        } else {
            for (Account account : mArrayList) {
                if (account.getUsername().toLowerCase(Locale.getDefault()).contains(data)) mAccountArrayList.add(account);
            }
        }
        //k goi se k update
        notifyDataSetChanged();
    }
}
