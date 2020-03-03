package ql.cev.ql9;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CompetitionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        if(Common.tech)
            mdatabase = FirebaseDatabase.getInstance().getReference().child("Competitions");
        else
            mdatabase = FirebaseDatabase.getInstance().getReference().child("CompetitionsCultural");
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog, BlogzoneViewHolder> FBRA = new FirebaseRecyclerAdapter<Blog, BlogzoneViewHolder>(
                Blog.class,
                R.layout.card_items_images,
                BlogzoneViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(BlogzoneViewHolder viewHolder, final Blog model, int position) {
                viewHolder.setImageUrl(getApplicationContext(), model.getImageUrl());
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
        void setImageUrl(Context ctx, String imageUrl) {
            ImageView post_image = mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(imageUrl).into(post_image);
        }
    }
}