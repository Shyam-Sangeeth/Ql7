package ql.cev.ql9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class CevCornerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private String current_user="";
    private String post_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cev_corner);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.cevcorner));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mCurrentUser = mAuth.getCurrentUser();
        if (mCurrentUser != null) {
            current_user= mCurrentUser.getEmail();
        }
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(CevCornerActivity.this, RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
        mdatabase = FirebaseDatabase.getInstance().getReference().child("cevcorner");
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthlistener);
        FirebaseRecyclerAdapter<Blog, CevCornerActivity.BlogzoneViewHolder> FBRA = new FirebaseRecyclerAdapter<Blog, CevCornerActivity.BlogzoneViewHolder>(
                Blog.class,
                R.layout.activity_corner_items1,
                CevCornerActivity.BlogzoneViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(final CevCornerActivity.BlogzoneViewHolder viewHolder, final Blog model,final int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                final String str=model.getDesc();
                Config.videoId=model.getTitle();
                viewHolder.setImageUrl(getApplicationContext(), model.getImageUrl());
                viewHolder.setUserName(model.getUsername());
                viewHolder.imageView= viewHolder.mView.findViewById(R.id.post_my_image);
                viewHolder.share= viewHolder.mView.findViewById(R.id.shr);
                viewHolder.delete= viewHolder.mView.findViewById(R.id.dlt);
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (str.equals("video")) {
                            startActivity(new Intent(CevCornerActivity.this, VideoActivity.class));
                        }
                    }
                });
                post_user=model.getEmail();
                if (!post_user.equals(current_user)){
                    viewHolder.delete.setVisibility(View.INVISIBLE);
                }
                viewHolder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DownloadImage().execute(model.getImageUrl());
                        File file = getApplicationContext().getFileStreamPath("my_image.jpeg");
                        String imageFullPath = file.getAbsolutePath();
                        Intent imageIntent = new Intent(Intent.ACTION_SEND);
                        Uri imageUri = Uri.parse(imageFullPath);
                        imageIntent.setType("image/jpeg");
                        imageIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(imageIntent);
                    }
                });
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_user=model.getEmail();
                        if (post_user.equals(current_user)){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CevCornerActivity.this);
                            alertDialog.setTitle("DELETE");
                            alertDialog.setMessage("Are you sure want to delete this post ?");
                            alertDialog.setIcon(R.drawable.delete);
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {
                                    mdatabase.child(post_key).removeValue();
                                    Toast toast = Toast.makeText(getApplicationContext(),"Post deleted", Toast.LENGTH_SHORT);
                                    View view = toast.getView();
                                    view.setBackgroundResource(R.drawable.nice_button_enabled);
                                    toast.show();
                                }
                            });
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,	int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(FBRA);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    public void PostMyPhoto(View view) {
        Intent in = new Intent(CevCornerActivity.this, PostActivity.class);
        in.putExtra("name", "cevcorner");
        startActivity(in);
    }
    public void Logout(View view){
        mAuth.signOut();
    }
    public static class BlogzoneViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Button share,delete;
        ScaleImageView imageView;
        public BlogzoneViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setTitle(String title) {
            TextView post_title = mView.findViewById(R.id.event_name);
            post_title.setText(title);
        }
        public void setDesc(String desc) {
            TextView post_desc = mView.findViewById(R.id.result_date_time);
            post_desc.setText(desc);
        }
        public void setImageUrl(Context ctx, String imageUrl) {
            ImageView post_image = mView.findViewById(R.id.post_my_image);
            Picasso.with(post_image.getContext()).load(imageUrl).into(post_image);
        }
        public void setUserName(String userName) {
            TextView postUserName = mView.findViewById(R.id.post_user);
            postUserName.setText(userName);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (Exception e) {
                String TAG = "DownloadImage";
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }
        protected void onPostExecute(Bitmap result) {
            Toast.makeText(CevCornerActivity.this,"downloaded",Toast.LENGTH_SHORT).show();
            saveImage(getApplicationContext(), result, "my_image.jpeg");
        }
    }
    @SuppressLint("WorldReadableFiles")
    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_WORLD_READABLE);
            b.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
            foStream.close();
            Toast.makeText(CevCornerActivity.this,"saved",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }
}