package com.amadt.fitrack.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amadt.fitrack.R;
import com.amadt.fitrack.database.WOD;
import com.amadt.fitrack.database.WODManager;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;

public class FindAWodActivity extends ExpandableListActivity {
    private static final String NAME = "Wod Name";
    private static final String GROUP= "Wod Group";
    private WODManager wm;
    
    static final String [] categories = {"Benchmark Girl", "New Girl", "Hero", "Custom"};
    
    private ExpandableListAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wm = new WODManager(this);
       
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        
        //For loop to create expandable parent list items
        for (int i = 0; i < categories.length; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(GROUP, categories[i] + " WODs");
            List<WOD> x = wm.getWODsByType(categories[i]);
            //For loop to create children list items in each parent item
            // simple nested for loop
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < x.size(); j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                curChildMap.put(NAME, x.get(j).getName());
                children.add(curChildMap);
                
                //curChildMap.put()
            }
            childData.add(children);
        }
        
        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { GROUP, NAME },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { GROUP, NAME },
                new int[] { android.R.id.text1, android.R.id.text2 }
                );
        setListAdapter(mAdapter);
        
        ListView lv = getExpandableListView();
        lv.setTextFilterEnabled(true);
        
        ColorDrawable gold = new ColorDrawable(this.getResources().getColor(R.drawable.gold));
        lv.setDivider(gold);
        lv.setDividerHeight(2);
        
        //Set WOD children to delegate to its related WOD View
        ((ExpandableListView) lv).setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @SuppressWarnings("unchecked")
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Object o = (Object)mAdapter.getChild(groupPosition, childPosition);
                HashMap<String, String> child = (HashMap<String,String>)o;
                                    
                String wod = child.get(NAME);
                Intent intent = new Intent(FindAWodActivity.this, WODViewActivity.class);
                intent.putExtra("WOD_NAME", wod);
                startActivity(intent);
                
                finish();
                
                return true;
            }
        });  
        
    }
}