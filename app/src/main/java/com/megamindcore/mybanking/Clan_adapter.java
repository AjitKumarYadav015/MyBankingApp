package com.megamindcore.mybanking;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.view.ContextMenu;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.recyclerview.widget.RecyclerView;

        import java.util.List;

        import static android.content.Context.MODE_PRIVATE;

public class Clan_adapter extends RecyclerView.Adapter<Clan_adapter.ImageViewHolder> {
    private Context mContext;
    private List<Clan> mclans;
    private OnItemClickListener mListener;



    public Clan_adapter(Context context, List<Clan> clans) {
        mContext = context;
        mclans = clans;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        final Clan clanCurrent = mclans.get(position);

        final String Account = clanCurrent.getKey();
        final String Name = clanCurrent.getm();
        final String Balance = clanCurrent.getn();

        holder.item_account.setText(Account.trim());
        holder.item_balance.setText(Balance.trim());
        holder.item_name.setText(Name.trim());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Toast.makeText(mContext, "Selected account : "+ Account + " Successfully!", Toast.LENGTH_SHORT).show();

                SharedPreferences sp = mContext.getSharedPreferences("Setting", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sp.edit();
                editor.putLong("selected_account",Integer.parseInt( (Account.trim()) ) );
                editor.apply();
                Intent intent =new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mclans.size();
    }


    public class ImageViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener  {
        public TextView item_name,item_account,item_balance;


        public ImageViewHolder(View itemView) {
            super(itemView);


            item_name = itemView.findViewById(R.id.item_name);

            item_account = itemView.findViewById(R.id.item_account);

            item_balance = itemView.findViewById(R.id.item_balance);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");

            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

    }






    public interface OnItemClickListener {
        void onItemClick(int position);


        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }







}

