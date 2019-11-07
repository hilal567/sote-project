package com.example.sote;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView user_list_view;
    private Context context;

    public List<User> user_list;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private UserRecyclerAdapter userRecyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        user_list = new ArrayList<>();
        user_list_view = view.findViewById(R.id.user_list_view);

        firebaseAuth = FirebaseAuth.getInstance();

        userRecyclerAdapter = new UserRecyclerAdapter(user_list);
        user_list_view.setLayoutManager(new LinearLayoutManager(getContext()));
        user_list_view.setAdapter(userRecyclerAdapter);
        user_list_view.setHasFixedSize(true);

        if (firebaseAuth.getCurrentUser() != null) {

        //Query query = firebaseFirestore.collection("Users");
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    for (DocumentChange doc: documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String userDataId = doc.getDocument().getId();
                            User user = doc.getDocument().toObject(User.class).withId(userDataId);
                            user_list.add(user);

                            userRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
      }

        /*searchView = (SearchView) view.findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (userRecyclerAdapter != null) {
                    userRecyclerAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });*/

        //inflate view for this fragment
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userRecyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

}
