package ql.cev.ql9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        recyclerView = findViewById(R.id.recyclerview_schedule);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        if(Common.tech)
            mdatabase = FirebaseDatabase.getInstance().getReference().child("schedule");
        else
            mdatabase = FirebaseDatabase.getInstance().getReference().child("scheduleCultural");
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog, BlogzoneViewHolder> FBRA = new FirebaseRecyclerAdapter<Blog, BlogzoneViewHolder>(
                Blog.class,
                R.layout.card_items_result,
                BlogzoneViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(BlogzoneViewHolder viewHolder, final Blog model, int position) {
               viewHolder.setTitle(model.getTitle());
               viewHolder.setDesc(model.getDesc());
            }
        };
        recyclerView.setAdapter(FBRA);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public static class BlogzoneViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public BlogzoneViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title) {
            TextView post_title = mView.findViewById(R.id.event_name);
            post_title.setText(title);
        }
        void setDesc(String desc) {
            TextView post_desc = mView.findViewById(R.id.result_date_time);
            post_desc.setText(desc);
        }
    }
}