package com.d.coronavirustracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.d.coronavirustracker.R;
import com.d.coronavirustracker.fragments.CountryDetailsBottomSheetFragment;
import com.d.coronavirustracker.models.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private Context mContext;
    private List<Country> mData;
    private FragmentActivity fragmentActivity;

    public CountryAdapter(FragmentActivity fragmentActivity,Context mContext, List<Country> mData) {
        this.fragmentActivity = fragmentActivity;
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        final Country country = mData.get(position);

        Glide.with(mContext).load(country.getFlag()).into(holder.flag);
        holder.tvCountry.setText(country.getCountry());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryDetailsBottomSheetFragment bottomSheetFragment = new CountryDetailsBottomSheetFragment(country);
                bottomSheetFragment.show(fragmentActivity.getSupportFragmentManager(),"Bottom Sheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        private ImageView flag;
        private TextView tvCountry;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flag);
            tvCountry = itemView.findViewById(R.id.country);
        }
    }
}
