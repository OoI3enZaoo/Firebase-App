package com.example.ben.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ben.firebase.data.course;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static CardViewAdapter mAdapter;

  ArrayList<String> subjectArray = new ArrayList<>();
  ArrayList<String> codeArray = new ArrayList<>();

  final static String DB_URL = "https://salon-b177d.firebaseio.com/";
  Firebase fire;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Firebase.setAndroidContext(this);
    fire = new Firebase(DB_URL);
    this.receieveData();
    OnItemTouchListener itemTouchListener = new OnItemTouchListener() {
      @Override
      public void onCardViewTap(View view, int position) {

        //Listener ของ cardview
      }

    };
    mAdapter = new CardViewAdapter(subjectArray, itemTouchListener);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclelistview);
    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    recyclerView.setAdapter(mAdapter);
    mAdapter.notifyDataSetChanged();
  }
  public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private List<String> cards;
    private OnItemTouchListener onItemTouchListener;

    public CardViewAdapter(List<String> cards, OnItemTouchListener onItemTouchListener) {
      this.cards = cards;
      this.onItemTouchListener = onItemTouchListener;
    }

    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
      View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course, viewGroup, false);
      return new CardViewAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(CardViewAdapter.ViewHolder viewHolder, int i) {

      viewHolder.subject.setText(subjectArray.get(i));
      viewHolder.code.setText(codeArray.get(i));

    }

    @Override
    public int getItemCount() {
      return cards == null ? 0 : cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      //   private TextView sname;
      private TextView subject;
      private TextView code;

      public ViewHolder(View itemView) {
        super(itemView);
        subject = (TextView) itemView.findViewById(R.id.subject);
        code = (TextView) itemView.findViewById(R.id.code);
        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            onItemTouchListener.onCardViewTap(v, getLayoutPosition());
          }
        });

      }
    }
  }

  interface OnItemTouchListener {

    void onCardViewTap(View view, int position);
  }

  //RERIVE
  private void receieveData() {
    fire.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        getUpdates(dataSnapshot); //จะเรียกใช้เมื่อมีการเพิ่ม child
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        getUpdates(dataSnapshot); //จะเรียกใช้เมื่อมีการ update child
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {

      }
    });
  }
  private void getUpdates(DataSnapshot ds) {
    codeArray.clear();
    subjectArray.clear();
    for(DataSnapshot data : ds.getChildren()) {
      course c = new course();
      if (data.getValue(course.class).getCode() != null) {
        c.setCode(data.getValue(course.class).getCode());
        codeArray.add(c.getCode()); //ทำการเพิ่ม code ไปยัง array ของ RecyclerView
      }
      if (data.getValue(course.class).getSubject() != null) {
        c.setSubject(data.getValue(course.class).getSubject());
        subjectArray.add(c.getSubject()); //ทำการเพิ่ม subject ไปยัง array ของ RecyclerView
      }
    }
    mAdapter.notifyDataSetChanged(); //ทำการ refresh listview
  }
}


