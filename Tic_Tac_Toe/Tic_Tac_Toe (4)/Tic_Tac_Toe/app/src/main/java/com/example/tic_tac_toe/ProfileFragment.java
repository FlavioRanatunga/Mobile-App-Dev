package com.example.tic_tac_toe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Avatar> avatarArrayList;
    private int[] imageResourceID;

    private RecyclerView recyclerview;



    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                get(MainActivityData.class);
        EditText inputName = view.findViewById(R.id.txtInputName);
        Button saveButton = view.findViewById(R.id.btnSave);
        Button multiButton = view.findViewById(R.id.multi_button);
        Button aiButton = view.findViewById(R.id.Ai_button);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityDataViewModel.setClickedValue(0);
                mainActivityDataViewModel.setProfileBtnFlag(true);
                mainActivityDataViewModel.setName(inputName.getText().toString());


            }
        });

        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivityDataViewModel.setMultiplayerFlag(true);


            }
        });

        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivityDataViewModel.setMultiplayerFlag(false);


            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarArrayList = new ArrayList<>();

        imageResourceID = new int[]{
                R.drawable.avatar_default,
                R.drawable.avatar_batman,
                R.drawable.avatar_walter,
                R.drawable.avatar_harley,
                R.drawable.avatar_trump,
                R.drawable.avatar_einstein,
                R.drawable.avatar_chaplin,
                R.drawable.avatar_spirited,

        };

        for (int i= 0 ; i< imageResourceID.length; i++){
            Avatar avatars = new Avatar(imageResourceID[i]);
            avatarArrayList.add(avatars);
        }

        recyclerview = view.findViewById(R.id.avatarRecyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview.setHasFixedSize(true);
        AvatarAdapter avatarAdapter = new AvatarAdapter(getContext(), avatarArrayList, new MainActivityData(), new AvatarAdapter.ItemClickListner() {
            @Override
            public void onItemClick(Avatar avatars) {
                MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                        get(MainActivityData.class);

                int clickedImageNumber = avatarArrayList.indexOf(avatars);
                mainActivityDataViewModel.setClickedImageNumber(clickedImageNumber);
            }
        });
        recyclerview.setAdapter(avatarAdapter);
        avatarAdapter.notifyDataSetChanged();
    }


}