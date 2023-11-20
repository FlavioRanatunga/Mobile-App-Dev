package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SearchViewModel sViewModel;
    ImageListViewModel imageViewModel;
    ErrorViewModel errViewModel;
    Button searchImg;
    RecyclerView imgList;
    ProgressBar progressBar;
    EditText searchField;
    Button singleView;
    Button doubleView;
    Button uploadImg;
    private Button selectedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        imageViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ImageListViewModel.class);
        errViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ErrorViewModel.class);

        searchImg = findViewById(R.id.loadBtn);
        imgList = findViewById(R.id.listId);
        progressBar = findViewById(R.id.progressBarId);
        searchField = findViewById(R.id.searchFieldId);
        singleView = findViewById(R.id.singleC);
        doubleView = findViewById(R.id.doubleC);
        uploadImg = findViewById(R.id.uploadBtn);

        imgList.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        singleView.setBackgroundColor(Color.GREEN);

        if(sViewModel.getSearch() != null)
        {
            searchField.setText(sViewModel.getSearch());
        }

        if(imageViewModel.getColumnView() == 1)
        {
            doubleView.setBackgroundColor(Color.TRANSPARENT);
            singleView.setBackgroundColor(Color.GREEN);
        }
        else {
            singleView.setBackgroundColor(Color.TRANSPARENT);
            doubleView.setBackgroundColor(Color.GREEN);
        }
        //imageViewModel.setLoadFlag(false);

        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgList.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                String searchValues = searchField.getText().toString();
                imageViewModel.clearImageList();
                SearchThread searchThread = new SearchThread(searchValues,MainActivity.this,sViewModel);
                searchThread.start();
            }
        });

        sViewModel.search.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.INVISIBLE);
                errViewModel.setErrorCode(0);
                imageViewModel.setSelectedImage(null);
                Toast.makeText(MainActivity.this, "Searching",Toast.LENGTH_LONG).show();
                ImageLoaderThread imageLoaderThread = new ImageLoaderThread(MainActivity.this, sViewModel, imageViewModel, errViewModel);
                progressBar.setVisibility(View.VISIBLE);
                imageLoaderThread.start();

            }
        });

        imageViewModel.getLoadFlag().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loaderFlag) {
                if(loaderFlag == true) {
                    Log.d("imageList", "ENTERED!!");
                    progressBar.setVisibility(View.INVISIBLE);
                    imgList.setVisibility(View.VISIBLE);
                    //imgList.setImageBitmap(imageViewModel.getImage()); // for one image
                    int spanCount = imageViewModel.getColumnView();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, spanCount,
                            GridLayoutManager.VERTICAL, false);
                    imgList.setLayoutManager(gridLayoutManager);
                    ImgListAdapter adapter = new ImgListAdapter(imageViewModel.getImageList(), imageViewModel);
                    imgList.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, "Search Complete",Toast.LENGTH_LONG).show();
                }
            }
        });

        errViewModel.errorCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("ErrorViewModel", "Entered");
                if(errViewModel.getErrorCode() > 0)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    //Toast.makeText(MainActivity.this, "Images not found!",Toast.LENGTH_LONG).show();
                }
            }
        });

        singleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedBtn != null) {
                    selectedBtn.setBackgroundColor(Color.TRANSPARENT);
                }
                doubleView.setBackgroundColor(Color.TRANSPARENT);
                singleView.setBackgroundColor(Color.GREEN);
                imageViewModel.setColumnView(1);
            }
        });

        doubleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedBtn != null) {
                    selectedBtn.setBackgroundColor(Color.TRANSPARENT);
                }
                singleView.setBackgroundColor(Color.TRANSPARENT);
                doubleView.setBackgroundColor(Color.GREEN);

                imageViewModel.setColumnView(2);
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                Bitmap selectedImage = imageViewModel.getSelectedImage();
                if (selectedImage == null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method if no image is selected
                }

                UploadThread uploadThread = new UploadThread(MainActivity.this,imageViewModel, errViewModel);
                uploadThread.start();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        /*searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sViewModel.setSearch(String.valueOf(searchField.getText()));
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });*/

    }
}