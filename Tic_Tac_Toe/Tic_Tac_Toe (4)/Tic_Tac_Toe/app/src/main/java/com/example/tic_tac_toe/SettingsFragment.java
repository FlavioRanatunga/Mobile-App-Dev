package com.example.tic_tac_toe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int grid =0;

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    private int winCondition = 3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view= inflater.inflate(R.layout.fragment_settings, container, false);

        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                get(MainActivityData.class);

        Button btn3x3 =  view.findViewById(R.id.btn3x3);
        Button btn4x4 =  view.findViewById(R.id.btn4x4);
        Button btn5x5 =  view.findViewById(R.id.btn5x5);

        Button btnWin3InARow =  view.findViewById(R.id.btnWin3InARow);
        Button btnWin4InARow =  view.findViewById(R.id.btnWin4InARow);
        Button btnWin5InARow =  view.findViewById(R.id.btnWin5InARow);

        Button btnPlay= view.findViewById(R.id.btnPlay);


        btn3x3.setVisibility(View.VISIBLE);
        btn4x4.setVisibility(View.VISIBLE);
        btn5x5.setVisibility(View.VISIBLE);
        btnWin3InARow.setVisibility(View.VISIBLE);
        btnWin4InARow.setVisibility(View.VISIBLE);
        btnWin5InARow.setVisibility(View.VISIBLE);

        btn3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4x4.setVisibility(View.INVISIBLE);
                btn5x5.setVisibility(View.INVISIBLE);
                btnWin4InARow.setVisibility(View.INVISIBLE);
                btnWin5InARow.setVisibility(View.INVISIBLE);
                grid =0;


            }
        });

        btn4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3x3.setVisibility(View.INVISIBLE);
                btn5x5.setVisibility(View.INVISIBLE);
                btnWin5InARow.setVisibility(View.INVISIBLE);
                grid =4;


            }
        });

        btn5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3x3.setVisibility(View.INVISIBLE);
                btn4x4.setVisibility(View.INVISIBLE);
                grid =5;
            }
        });

        btnWin3InARow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnWin4InARow.setVisibility(View.INVISIBLE);
                btnWin5InARow.setVisibility(View.INVISIBLE);
                winCondition =3;
            }
        });

        btnWin4InARow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnWin3InARow.setVisibility(View.INVISIBLE);
                btnWin5InARow.setVisibility(View.INVISIBLE);
                winCondition =4;
            }
        });

        btnWin5InARow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnWin3InARow.setVisibility(View.INVISIBLE);
                btnWin4InARow.setVisibility(View.INVISIBLE);
                winCondition =5;
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(grid ==0) {
                    mainActivityDataViewModel.setClickedValue(0);
                }else if(grid ==4) {
                    mainActivityDataViewModel.setClickedValue(4);
                }else if(grid ==5) {
                    mainActivityDataViewModel.setClickedValue(5);
                }


            }
        });

        return view;

    }
}