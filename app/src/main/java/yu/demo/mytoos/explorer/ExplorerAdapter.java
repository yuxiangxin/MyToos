package yu.demo.mytoos.explorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yu.demo.mytoos.R;

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerAdapter.ExplorerHolder> {

    private ArrayList<ExplorerBean> mDatas;
    private Context mContext;

    public ExplorerAdapter (Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    public void setDatasWithRefresh (ArrayList<ExplorerBean> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExplorerHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        return new ExplorerHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder (@NonNull ExplorerHolder viewHolder, int i) {
        viewHolder.bindView(getItem(i));
    }

    private ExplorerBean getItem (int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount () {
        return mDatas.size();
    }

    class ExplorerHolder extends RecyclerView.ViewHolder {
        private TextView contentTv;

        public ExplorerHolder (@NonNull ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.adapter_explorer_view, parent, false));
            contentTv = itemView.findViewById(R.id.content_tv);
        }

        public void bindView (ExplorerBean item) {
            contentTv.setText(item.toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    if (mOnItemClickEvent != null) {
                        int adapterPosition = getAdapterPosition();
                        mOnItemClickEvent.onItemClick(adapterPosition, getItem(adapterPosition));
                    }
                }
            });
        }
    }

    private OnItemClickEvent mOnItemClickEvent;

    public void setOnItemClickEvent (OnItemClickEvent onItemClickEvent) {
        mOnItemClickEvent = onItemClickEvent;
    }

    public interface OnItemClickEvent {
        void onItemClick (int position, ExplorerBean item);
    }
}
