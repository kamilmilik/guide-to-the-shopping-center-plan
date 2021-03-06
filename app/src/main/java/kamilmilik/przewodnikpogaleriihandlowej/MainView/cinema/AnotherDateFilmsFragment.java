package kamilmilik.przewodnikpogaleriihandlowej.MainView.cinema;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import kamilmilik.przewodnikpogaleriihandlowej.MainView.shops.Identifiers;
import kamilmilik.przewodnikpogaleriihandlowej.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnotherDateFilmsFragment extends Fragment  {
    private static final String TAG = "AnotherDateFilms";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mYear,mMonth,mDay;
    private Button chooseDateButton;
    private String[] labelsArray1;
    private String[] labelsArray2;
    private  int[] imagesArray;

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Log.i(TAG,mYear+"/" + mMonth + "/" + mDay);
            setPropertyFilmSetDependToChoosenDate(mAdapter);
        }
    };
    public AnotherDateFilmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_another_date_films, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chooseDateButton = view.findViewById(R.id.buttonChoiceDate);

        chooseDateButtonAction();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_another_date_films);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        labelsArray1 = new String[]{"To", "Pierwszy Snieg", "Lego Ninjago", "Twoj Vincent", "My Little Pony"};
        labelsArray2 = new String[]{"2D Napisy", "2D Napisy", "2D napisy", "3D Napisy", "3D Lektor"};
        imagesArray = new int[]{R.drawable.to, R.drawable.pierwszy_snieg, R.drawable.lego_ninjago_pl_cf65c6368c, R.drawable.vincent, R.drawable.pony};
        mAdapter = new MyAdapter(labelsArray1,labelsArray2,imagesArray);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void chooseDateButtonAction() {
        chooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = getResources().getConfiguration().locale;
                Locale.setDefault(locale);
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog =  new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
                dateDialog.show();
            }
        });
    }
public void setPropertyFilmSetDependToChoosenDate(RecyclerView.Adapter mAdapter){
        Log.i(TAG,mDay + " dzien");
        if(mDay > 20){
            labelsArray1 = new String[]{ "Twoj Vincent", "My Little Pony","To", "Pierwszy Snieg", "Lego Ninjago"};
            labelsArray2 = new String[]{ "3D Napisy", "3D Lektor","2D Napisy", "2D Napisy", "2D napisy"};
            imagesArray = new int[]{ R.drawable.vincent, R.drawable.pony,R.drawable.to, R.drawable.pierwszy_snieg, R.drawable.lego_ninjago_pl_cf65c6368c};
        }else{
            labelsArray1 = new String[]{"To", "Pierwszy Snieg", "Lego Ninjago", "Twoj Vincent", "My Little Pony"};
            labelsArray2 = new String[]{"2D Napisy", "2D Napisy", "2D napisy", "3D Napisy", "3D Lektor"};
            imagesArray = new int[]{R.drawable.to, R.drawable.pierwszy_snieg, R.drawable.lego_ninjago_pl_cf65c6368c, R.drawable.vincent, R.drawable.pony};
        }
            mAdapter = new MyAdapter(labelsArray1,labelsArray2,imagesArray);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
}


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] labelTextArray1;
        private String[] labelTextArray2;
        private int[] imagesArray;

        public MyAdapter(String[] labelTextArray1,String[] labelTextArray2, int[] imagesArray){
            this.labelTextArray1 = labelTextArray1;
            this.labelTextArray2 = labelTextArray2;
            this.imagesArray = imagesArray;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_films,parent,false);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            setUpCardViewElements(holder,position);
            clickToCardAction(holder,position);
        }
        public void setUpCardViewElements(MyAdapter.ViewHolder holder, int position){
            holder.image.setBackgroundResource(imagesArray[position]);
            holder.titleCinema.setText(labelTextArray1[position]);
            holder.textUnderTitleCinema.setText(labelTextArray2[position]);
        }
        private void clickToCardAction(final MyAdapter.ViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), SelectedFilmActivity.class);
                    intent.putExtra(Identifiers.FILM_IMAGE_KEY, imagesArray[position]);
                    intent.putExtra(Identifiers.FILM_NAME_KEY, labelTextArray1[position]);
                    intent.putExtra(Identifiers.FILM_TEXT_LEVEL_KEY, labelTextArray2[position]);
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return imagesArray.length;
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleCinema, textUnderTitleCinema;
            private ImageView image;
            public ViewHolder(View itemView) {
                super(itemView);
                titleCinema = itemView.findViewById(R.id.titleShopEvent);
                textUnderTitleCinema = itemView.findViewById(R.id.textUnderTitleCinema);
                image = itemView.findViewById(R.id.imagesShopEvent);

            }

        }
    }
}
