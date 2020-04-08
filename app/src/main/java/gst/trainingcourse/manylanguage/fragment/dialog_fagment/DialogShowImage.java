package gst.trainingcourse.manylanguage.fragment.dialog_fagment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import gst.trainingcourse.manylanguage.R;

public class DialogShowImage extends DialogFragment {

    public static DialogShowImage newInstance(int resource) {

        Bundle args = new Bundle();
        args.putInt("img", resource);
        DialogShowImage fragment = new DialogShowImage();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView mImgGirl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_show_image, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImgGirl = view.findViewById(R.id.imgGirl);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mImgGirl.setImageResource(bundle.getInt("img"));
        }
    }
}
