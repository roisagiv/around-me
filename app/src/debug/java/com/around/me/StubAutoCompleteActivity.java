package com.around.me;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.AutoCompleteTextView;

/**
 */
public class StubAutoCompleteActivity extends FragmentActivity {

    private AutoCompleteTextView mAutoCompleteTextView;

    public AutoCompleteTextView getAutoCompleteTextView() {
        return mAutoCompleteTextView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stub_autocomplete_activity);
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
    }
}
