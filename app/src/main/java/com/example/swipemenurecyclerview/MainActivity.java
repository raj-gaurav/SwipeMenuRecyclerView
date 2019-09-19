package com.example.swipemenurecyclerview;

import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     String email;
    RecyclerView rv;
    ArrayList<GetSet> list=new ArrayList<GetSet>();;
    CustomAdapter adapter;
    FloatingActionButton fab;
    MyDbHelper mydb=new MyDbHelper(this);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recycler view start
        rv=findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setBackgroundColor(Color.rgb(217,13,79));
        //add data start
            showUsers();
        //add data end




        adapter=new CustomAdapter(this,list);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);
        rv.setAdapter(adapter);
        adapter.setClickListener(new CustomAdapter.RecyclerTouchListener() {
            @Override
            public void onClickItem(View v, int position) {
                email=list.get(position).getEmail();
                Snackbar.make(v,email,Snackbar.LENGTH_SHORT).show();
            }
        });

        //recycler view end

        //floating button action start
            fab=findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        customDialog();
                }
            });
        //floating button action end


    }
    //insert data start
    //to insert start
        private void customDialog(){

        AlertDialog.Builder myDialog= new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
        View myView=inflater.inflate(R.layout.input_user,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(myView);

        final EditText nm=myView.findViewById(R.id.name);
        final EditText em=myView.findViewById(R.id.email);
        final EditText ph=myView.findViewById(R.id.phone);
        final Button ins=myView.findViewById(R.id.ins);

        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nm.getText().toString().trim();
                String email=em.getText().toString().trim();
                String phone=ph.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    nm.setError("Required field...");
                }
                 else if(TextUtils.isEmpty(email)){
                    em.setError("Required field...");
                }
                else if(TextUtils.isEmpty(phone)){
                    ph.setError("Required field...");
                }

                else {
                    Boolean res = mydb.insertData(name, email, phone);
                    if (res) {
                        Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                        //adapter.notifyDataSetChanged();
                        list.clear();
                        showUsers();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Something went wrong....Data not inserted",Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                }




            }
        });
        dialog.show();
    }
    //end
    //insert data end

    //fetch data from database start
    private void showUsers(){

        //list.add(new GetSet("Example","exaample@example.com","8587953804"));
        Cursor res=mydb.getAllData();
        if(res!=null && res.getCount()>0){
            while(res.moveToNext()){
                list.add(new GetSet(res.getString(1),res.getString(2),res.getString(3)));
            }



        }
    }
    //fetch data from database end

    //Swipe right to delete start
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            //list.remove(viewHolder.getAdapterPosition());
            final int[] flag={0};
            int pos=viewHolder.getAdapterPosition();

            email=list.get(pos).getEmail();
            list.remove(pos);
            Snackbar.make(viewHolder.itemView,"Removed",Snackbar.LENGTH_SHORT).setAction("UNDO",new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    /*Toast.makeText(getApplicationContext(),"Data retrieved",Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    flag[0]=1;
                    list.clear();
                    showUsers();*/


                }


            }).show();


               // Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
            int c=mydb.fetchSpecificData(email);

            if(flag[0]==0)
            {
                int res= (int) mydb.deleteData(String.valueOf(c));
                if(res!=0)
                Toast.makeText(getApplicationContext(),"Data deleted"+email,Toast.LENGTH_SHORT).show();
            }

            adapter.notifyDataSetChanged();



        }
    };
    //swipe right to delete end
}
