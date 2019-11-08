package com.example.sote;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//Temporarily implements Filterable
public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> implements Filterable {

    public List<User> user_list;
    //Temporary
    public List<User> userListFull;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public UserRecyclerAdapter(List<User> user_list) {
        this.user_list = user_list;
        //Temporary
        userListFull = new ArrayList<>(user_list);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        final String userDataId = user_list.get(position).UserId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        String bloodGroup = user_list.get(position).getBloodgroup();
        holder.setUserBloodgroup("Blood Group: " + bloodGroup);

        String image_url = user_list.get(position).getImage();
        holder.setUserData(image_url);

        String name = user_list.get(position).getName();
        holder.setUserName("Username: " + name);

        final String phone = user_list.get(position).getPhone();
        holder.setUserPhone("Telephone: " + phone);

        String weight = user_list.get(position).getWeight();
        holder.setUserWeight("Weight: " + weight + " Kgs");

        //hide the call button of the logged in user
        if (userDataId.equals(currentUserId)) {
            holder.callBtn.setEnabled(false);
            holder.callBtn.setVisibility(View.INVISIBLE);
            holder.callText.setVisibility(View.INVISIBLE);

        }


        //call the for donation of blood
        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String phone_no = user_list.get(position).getPhone();
              context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_no)));


            }
        });


    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private CircleImageView userImage;
        private TextView userPhone;
        private TextView userBloodgroup;
        private TextView userName;
        private TextView userWeight;
        private ImageView userDonations;
        private TextView donationsCount;
        private ImageView callBtn;
        private TextView callText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            //link with adapter class
            donationsCount = mView.findViewById(R.id.user_donations_count);
            callBtn = mView.findViewById(R.id.user_call_btn);
            callText = mView.findViewById(R.id.user_call_text);
        }

        public void setUserPhone(String phone) {
            userPhone = mView.findViewById(R.id.user_phone);
            userPhone.setText(phone);
        }

        public void setUserBloodgroup(String bloodgroup){
            userBloodgroup = mView.findViewById(R.id.user_bloodgroup);
            userBloodgroup.setText(bloodgroup);
        }

        public void setUserName(String name) {
            userName = mView.findViewById(R.id.user_username);
            userName.setText(name);
        }

        public void setUserWeight(String weight) {
            userWeight = mView.findViewById(R.id.user_weight);
            userWeight.setText(weight);
        }

        public void setUserData(String image) {
            userImage = mView.findViewById(R.id.user_image);

            RequestOptions placeholderOptions = new RequestOptions();
            placeholderOptions.placeholder(R.drawable.image_placeholder);

            Glide.with(context).applyDefaultRequestOptions(placeholderOptions).load(image).into(userImage);
        }
    }

    //Temporary
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    //Temporary
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(userListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User user: userListFull) {
                    if (user.getBloodgroup().toLowerCase().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            user_list.clear();
            user_list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
