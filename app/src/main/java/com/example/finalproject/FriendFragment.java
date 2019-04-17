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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendFragment extends Fragment {

    ListView listView;
    ArrayList<Friend> friendArrayList = new ArrayList<Friend>(); // collections of Friend Objects
    // Had arrayAdapter takes in any dataType, I would only need friendArrayList
    // Since it needs a string, I need to create a String array list
    ArrayList<String> friendsStringList = new ArrayList<String>(); // collections of Friend objects' names

    // Mock up data for Friends
    Friend friend1;
    Friend friend2;
    Friend friend3;
    Friend friend4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Make sure to create view and return view
        View view = inflater.inflate(R.layout.fragment_friends, container,false);

        // Setting listView
        listView = (ListView)view.findViewById(R.id.friendList);

        // Creating friend objects
        friend1 = new Friend("Gree", 0);
        friend2 = new Friend("Kob", 1);
        friend3 = new Friend("Aulk", 2);
        friend4 = new Friend("Dilly",3);


        /*
            Real scenario would be something like this"
            1: Fetch the data from the server using volley call(rest call)
            2: Retrieve data as JSON/AJAX parse it to string or int
            3: assign those to a string or int variable
            4: String name = someVolleyCalls.fetchName.toString();
            5: int id = someVolleyCalls.fetchId.toInterger();
            6: Friend friend1 = new Friend(name, id);
         */

        // Adding in friend obj into a Friend array
        friendArrayList.add(friend1);
        friendArrayList.add(friend2);
        friendArrayList.add(friend3);
        friendArrayList.add(friend4);

        // Sort friend in the list by their first name
        sortFriendListByFirstName();


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

                // Creating an Alert dialog of options for the user when they click on the selected friend on the listView
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

    // This function sorts the friendArrayList by their first name
    void sortFriendListByFirstName(){
       // Time complexity: Maybe O(n^2)
       for(int i = 0; i < friendArrayList.size(); i++){

           // Looping through the friendArrayList starting at index 0
           for(int j = i + 1; j < friendArrayList.size(); j++ ){
               // looping again starting at index 1 of friendArrayList
               if(friendArrayList.get(i).getName().compareTo(friendArrayList.get(j).getName()) > 0){
                    Friend tempFriend = friendArrayList.get(i); // creating a copy of the first index of the friendArrayList
                    friendArrayList.set(i,friendArrayList.get(j)); // Setting the first index of friendArrayList to the second index of friendArrayList
                    friendArrayList.set(j, tempFriend); // Setting the second index of friendArrayList to the tempFriend we copied
               }
           }
       }

       /*
            Example of this algorithm
            List: Bob, Apple
            1) We compare Bob to Apple
            2) This will return a 1 because Bob is greater than Apple
            3) tempFriend = Bob
            4) Bob becomes Apple
            5) Current List: Apple, Apple
            6) Apple(the second one) = tempFriend <-- where tempFriend is Bob
            7) Apple(the second one) becomes Bob
            8) End List: Apple, Bob
        */

    }
}
