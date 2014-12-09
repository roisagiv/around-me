package com.around.me.autocomplete;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class AutoCompleteAdapter extends ArrayAdapter<String> {

    private IAutoComplete mAutoComplete;
    private AutoCompleteFilter mFilter;

    public AutoCompleteAdapter(Context context, IAutoComplete autoComplete) {
        super(context, android.R.layout.simple_list_item_1);
        mAutoComplete = autoComplete;

        mFilter = new AutoCompleteFilter();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }


    private class AutoCompleteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {

                List<String> items = mAutoComplete.search(constraint.toString());

                // Assign the data to the FilterResults
                filterResults.values = items;
                filterResults.count = items.size();

                clear();
                addAll(items);

            } else {

                clear();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
