package com.example.mobilestore;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Product> products;
    Context context;

    public MyAdapter(ShowProduct showProduct, List<Product> products) {
        this.products = products;
        this.context=showProduct;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Product ld=products.get(position);
        //holder.txtid.setText(ld.getId());
        holder.txtname.setText(ld.getproduct_name());
        holder.txtmovie.setText(ld.getdescription());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user ;
                user= FirebaseAuth.getInstance().getCurrentUser();
                String UserID;
                UserID=user.getUid();
                DatabaseReference nm= FirebaseDatabase.getInstance().getReference("Product").child(UserID).child(ld.getPkey());
                nm.removeValue();


            }
        });

        holder.img_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                dialog.setContentView(R.layout.cust_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
               EditText id=dialog.findViewById(R.id.P_ide);
                EditText name=dialog.findViewById(R.id.P_namee);
                EditText des=dialog.findViewById(R.id.P_Dese);
                Button Update=dialog.findViewById(R.id.update);
                id.setText(ld.getid());
                name.setText(ld.getproduct_name());
                des.setText(ld.getdescription());
                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
                        String  UserID=user.getUid();
                        FirebaseAuth auth=FirebaseAuth.getInstance();
                        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //    max = (snapshot.getChildrenCount());
                                User userProfile = snapshot.getValue(User.class);
                                if (userProfile != null) {
                                    // Toast.makeText(Welcome.this, "User Name"+userProfile.name, Toast.LENGTH_SHORT).show();

                                    DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("Product").child(UserID);
                                    //String pKey = dRef.push().getKey();
                                    Product product = new Product(id.getText().toString(), name.getText().toString()
                                            , des.getText().toString(),ld.getPkey());


                                    dRef.child(ld.getPkey()).setValue(product).addOnCompleteListener(
                                            new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        dialog.dismiss();
                                                    Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
                                                    

                                                    } else {
                                                        dialog.dismiss();
                                                        Toast.makeText(context, "Task" + task.getException(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                    );
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtid,txtname,txtmovie;
        ImageView img_wish;
        CardView carditem;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtid=(TextView)itemView.findViewById(R.id.txt_price);
            txtname=(TextView)itemView.findViewById(R.id.txt_wishname);
            txtmovie=(TextView)itemView.findViewById(R.id.txt_color);
            carditem=itemView.findViewById(R.id.carditem);
            textView=itemView.findViewById(R.id.textView);
            img_wish=itemView.findViewById(R.id.img_wish);
        }
    }
}
