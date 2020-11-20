package com.megamindcore.mybanking.ui.dashboard;


        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.megamindcore.mybanking.Clan;
        import com.megamindcore.mybanking.Clan_adapter;
        import com.megamindcore.mybanking.Database;
        import com.megamindcore.mybanking.R;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;


@SuppressLint("Registered")
public class DashboardFragment extends Fragment implements Clan_adapter.OnItemClickListener {


    private RecyclerView mRecyclerView;
    private Clan_adapter mAdapter;

    private ProgressBar mProgressCircle;

    private List<Clan> mClan;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);



        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setReverseLayout(false);
        linearLayout.isSmoothScrollbarEnabled();
        mRecyclerView.setLayoutManager(linearLayout);




        mProgressCircle = view.findViewById(R.id.progress_circle);

        mClan = new ArrayList<>();


        mAdapter = new Clan_adapter(getActivity(), mClan);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DashboardFragment.this);


        mClan.clear();

        loadData(0,20);

        return view;
    }

    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }




    public void onDeleteClick(int position) {

        Toast.makeText(getActivity(), "Available Soon: " + position, Toast.LENGTH_SHORT).show();


    }



    private void loadData(int start,int end) {


        Database global_top = new Database(getActivity());

        final String Global_Top = "basic_users_list";
        boolean databased_check = doesDatabaseExist(getActivity(), "users_data.db");

        boolean table_global_check = global_top.isTableExists(Global_Top, true);

        if (databased_check == true) {
            if (table_global_check == true) {

                Cursor globals_Tops = global_top.Get_basic_users_list(start,end);
                while (globals_Tops.moveToNext()) {

                    String Account_no = globals_Tops.getString(0).trim();
                    String Name = globals_Tops.getString(1).trim();
                    String Balance = globals_Tops.getString(2).trim();
                    String Email = globals_Tops.getString(3).trim();

                    if (Account_no == null) {
                        Account_no = "!!";
                    }
                    if (Name == null) {
                        Name = "!!";
                    }
                    if (Balance == null) {
                        Balance = "!!";
                    }
                    if (Email == null) {
                        Email = "!!";
                    }



                    final Clan clan = new Clan();
                    clan.setKey(Account_no);
                    clan.setm(Name);
                    clan.setn(Balance);


                    mClan.addAll(Collections.singleton(clan));
                    mAdapter.notifyDataSetChanged();

                }

                mProgressCircle.setVisibility(RecyclerView.GONE);

            }else{Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}
        }else {Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}


    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }





}