package yu.demo.mytoos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yu.demo.mytoos.explorer.ExplorerAdapter;
import yu.demo.mytoos.explorer.ExplorerAnalyze;
import yu.demo.mytoos.explorer.ExplorerBean;

public class ExplorerAnalyzeActivity extends AppCompatActivity implements ExplorerAdapter.OnItemClickEvent {
    private RecyclerView mRecyclerView;
    private View mLoadingView;

    private String mTargetPath;
    private ExplorerAdapter mAdapter;
    private AsyncTask<Void, Void, ArrayList<ExplorerBean>> mExecute;


    public static void toNative (Context context, String path) {
        Intent intent = new Intent(context, ExplorerAnalyzeActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_analyze_list);

        mRecyclerView = findViewById(R.id.recycler_view);
        mLoadingView = findViewById(R.id.loading_iv);
        mTargetPath = getIntent().getStringExtra("path");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExplorerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickEvent(this);

        processData();
    }

    @SuppressLint("StaticFieldLeak")
    private void processData () {
        mExecute = new AsyncTask<Void, Void, ArrayList<ExplorerBean>>() {
            @Override
            protected ArrayList<ExplorerBean> doInBackground (Void... voids) {
                return new ExplorerAnalyze().analyzeDir(mTargetPath);
            }

            @Override
            protected void onPreExecute () {
                mLoadingView.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute (ArrayList<ExplorerBean> explorerBeans) {
                mLoadingView.setVisibility(View.GONE);
                mAdapter.setDatasWithRefresh(explorerBeans);
            }
        }.execute();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (mExecute != null) {
            mExecute.cancel(true);
        }
    }

    @Override
    public void onItemClick (int position, ExplorerBean item) {
        toNative(this, item.getFile().getAbsolutePath());
    }
}
