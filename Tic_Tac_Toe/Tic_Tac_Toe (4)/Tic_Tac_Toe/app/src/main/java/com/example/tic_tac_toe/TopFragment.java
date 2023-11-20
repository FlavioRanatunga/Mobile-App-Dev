package com.example.tic_tac_toe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopFragment newInstance(String param1, String param2) {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                get(MainActivityData.class);

        Button btnProfile = view.findViewById(R.id.btnProfile);

        mainActivityDataViewModel.getProfileBtnFlag().observe(getViewLifecycleOwner(),flag->{
            if (flag==false){
                btnProfile.setText("Login");
            }
            else {

                btnProfile.setText(mainActivityDataViewModel.getName());}

        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityDataViewModel.setClickedValue(1);

            }
        });


        ImageView btnSettings = view.findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityDataViewModel.setClickedValue(2);
            }
        });

        ImageView avatarImageView = view.findViewById(R.id.avatarImageView);
         int[] imageResources = {
                 R.drawable.avatar_default,
                 R.drawable.avatar_batman,
                 R.drawable.avatar_walter,
                 R.drawable.avatar_harley,
                 R.drawable.avatar_trump,
                 R.drawable.avatar_einstein,
                 R.drawable.avatar_chaplin,
                 R.drawable.avatar_spirited,
        };

        mainActivityDataViewModel.getClickedImageNumber().observe(getViewLifecycleOwner(),number->{
            avatarImageView.setImageResource(imageResources[number]);
        });

        return view;
    }
}