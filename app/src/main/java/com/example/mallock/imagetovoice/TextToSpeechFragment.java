package com.example.mallock.imagetovoice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TextToSpeechFragment extends Fragment {
    String url;

    public TextToSpeechFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_to_audio, container, false);

        final EditText editText = view.findViewById(R.id.edit_text);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = new TextToSpeech(getContext())
                        .setText(editText.getText().toString())
                        .build();

                url = url.replace(" ", "%20");
                GETMethod getMethod = new GETMethod(url, getContext());
                getMethod.execute();
            }
        });

        return view;
    }
}
