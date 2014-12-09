package com.around.me.autocomplete;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;

import com.around.me.StubAutoCompleteActivity;
import com.robotium.solo.Solo;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class AutoCompleteAdapterTests extends ActivityInstrumentationTestCase2<StubAutoCompleteActivity> {

    private AutoCompleteTextView mAutoCompleteTextView;
    private Solo mSolo;

    public AutoCompleteAdapterTests() {
        super(StubAutoCompleteActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        StubAutoCompleteActivity activity = getActivity();
        mAutoCompleteTextView = activity.getAutoCompleteTextView();
        mSolo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
        super.tearDown();
    }

    public void test_adapter_should_begin_with_0_items() {
        // arrange
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), new MockAutoComplete());
        mAutoCompleteTextView.setAdapter(adapter);

        // act
        getInstrumentation().waitForIdleSync();

        // assert
        assertThat(adapter.getCount()).isEqualTo(0);
    }

    public void test_adapter_should_have_5_items_after_text_input() {
        // arrange
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), new MockAutoComplete());
        mAutoCompleteTextView.setAdapter(adapter);

        getInstrumentation().waitForIdleSync();

        // act
        mAutoCompleteTextView.requestFocus();
        getInstrumentation().waitForIdleSync();

        mSolo.typeText(mAutoCompleteTextView, "Add");
        getInstrumentation().waitForIdleSync();

        mSolo.waitForText("Address 3");

        // assert
        assertThat(mAutoCompleteTextView.isPopupShowing()).isTrue();
        assertThat(adapter.getCount()).isEqualTo(5);
    }

    public void test_adapter_should_clear_items_when_text_is_empty() {
        // arrange
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), new MockAutoComplete());
        mAutoCompleteTextView.setAdapter(adapter);

        getInstrumentation().waitForIdleSync();

        // act
        mAutoCompleteTextView.requestFocus();
        getInstrumentation().waitForIdleSync();

        mSolo.typeText(mAutoCompleteTextView, "Add");
        getInstrumentation().waitForIdleSync();
        mSolo.waitForText("Address 3");

        mSolo.clearEditText(mAutoCompleteTextView);
        getInstrumentation().waitForIdleSync();

        // assert
        assertThat(mAutoCompleteTextView.isPopupShowing()).isFalse();
        assertThat(adapter.getCount()).isEqualTo(0);
    }


    public static class MockAutoComplete implements IAutoComplete {

        @Override
        public List<String> search(String input) {
            return Arrays.asList("Address 1", "Address 2", "Address 3", "Address 4", "Address 5");
        }
    }
}
