package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendFragment extends Fragment {

    ListView listView;
    ArrayList<Friend> friendArrayList = new ArrayList<Friend>();
    ArrayList<String> friendsStringList = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Make sure to create view and return view
        View view = inflater.inflate(R.layout.fragment_friends, container,false);

        // Setting listView
        listView = (ListView)view.findViewById(R.id.friendList);

        // Creating friend objects
        Friend friend1 = new Friend("Bob", 0);
        Friend friend2 = new Friend("Tree", 1);
        Friend friend3 = new Friend("Hulk", 2);

        // Adding in friend obj into a Friend array
        friendArrayList.add(friend1);
        friendArrayList.add(friend2);
        friendArrayList.add(friend3);

        // Putting friend's name in a string array
        for(int i = 0; i < friendArrayList.size(); i++){
            friendsStringList.add(friendArrayList.get(i).getName());
        }

        // Adapter takes in String only, so instead of Friend
        // I created a String which is based off the Friend array
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendFragment.this.getContext(), android.R.layout.simple_list_item_1, friendsStringList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Creating an Alert dialog of options for the user
                // Whether they want to delete the friend or not
                AlertDialog.Builder adb = new AlertDialog.Builder(FriendFragment.this.getContext());
                adb.setTitle("Remove Friend?");
                adb.setMessage("Are you sure you want to remove " + friendsStringList.get(position) + " from your friend list?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Friend friendToRemove = friendArrayList.get(positionToRemove);
                        // make volley call to remove this
                        friendArrayList.remove(friendToRemove);
                        friendsStringList.remove(friendToRemove.getName()); // may not handle when two friends have the same name

                        // list view is checking for changes in the data attach to adapter
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();

            }
        });

        return view;
    }
}
