package com.example.videolibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;

/**
 * Created by usuwi on 05/07/2017.
 */

public class SearchActivity extends Activity {
    private static boolean USE_INTERNAL_SPEECH_RECOGNIZER = false;
    private static final int REQUEST_SPEECH = 1;
    private SearchFragment mFragment;
    private SpeechRecognitionCallback mSpeechRecognitionCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
        if (!USE_INTERNAL_SPEECH_RECOGNIZER) {
            mSpeechRecognitionCallback = new SpeechRecognitionCallback() {
                @Override
                public void recognizeSpeech() {
                    startActivityForResult(mFragment.getRecognizerIntent(), REQUEST_SPEECH);
                }
            };
            mFragment.setSpeechRecognitionCallback(mSpeechRecognitionCallback);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SPEECH && resultCode == RESULT_OK) {
            mFragment.setSearchQuery(data, true);
        }
    }
}
