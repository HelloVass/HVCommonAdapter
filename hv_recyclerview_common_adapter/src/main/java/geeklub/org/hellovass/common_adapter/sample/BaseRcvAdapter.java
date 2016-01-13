package geeklub.org.hellovass.common_adapter.sample;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import geeklub.org.hellovass.common_adapter.listener.OnRcvItemClickListener;
import geeklub.org.hellovass.common_adapter.listener.OnRcvItemLongClickListener;
import java.util.List;

/**
 * Created by HelloVass on 15/10/27.
 */
public abstract class BaseRcvAdapter<DATA> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

  private final static String TAG = BaseRcvAdapter.class.getSimpleName();

  protected Context mContext;

  private List<DATA> mDataList;

  private View mFooterView;

  private OnRcvItemClickListener mOnItemClickListener;

  private OnRcvItemLongClickListener mOnItemLongClickListener;

  public final static int ITEM_VIEW_TYPE_FOOTER = 0;

  @IntDef({ ITEM_VIEW_TYPE_FOOTER }) public @interface ItemViewType {

  }

  public BaseRcvAdapter(Context context, List<DATA> dataList) {
    this.mContext = context;
    this.mDataList = dataList;
  }

  @Override public int getItemViewType(int position) {
    if (position >= mDataList.size()) {
      return ITEM_VIEW_TYPE_FOOTER;
    } else {
      DATA data = getData(position);
      return getItemViewTypeHV(data);
    }
  }

  @Override
  public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, @ItemViewType int viewType) {
    if (viewType == ITEM_VIEW_TYPE_FOOTER) {
      return new BaseRecyclerViewHolder(mFooterView);
    } else {

      final BaseRecyclerViewHolder viewHolder = new BaseRecyclerViewHolder(
          LayoutInflater.from(parent.getContext())
              .inflate(getLayoutResID(viewType), parent, false));

      viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
          }
        }
      });

      viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
          if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, viewHolder.getAdapterPosition());
          }
          return true;
        }
      });

      return viewHolder;
    }
  }

  @Override public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
    if (position >= 0 && position < mDataList.size()) {
      convert(holder, getData(position));
    }
  }

  public DATA getData(int position) {
    return mDataList.get(position);
  }

  public List<DATA> getDataList() {
    return mDataList;
  }

  public void clearDataList() {
    mDataList.clear();
  }

  public void appendDataList(List<DATA> dataList) {
    mDataList.addAll(dataList);
  }

  public boolean isDataListEmpty() {
    return mDataList == null || mDataList.isEmpty();
  }

  @Override public int getItemCount() {
    int fCount = mFooterView == null ? 0 : 1;
    return mDataList.size() + fCount;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  /**
   * 添加 Footer
   */
  public void addFooterView(View view) {
    if (view != null) {
      mFooterView = view;
    }
  }

  /**
   *
   * @param holder
   * @param data
   */
  protected abstract void convert(BaseRecyclerViewHolder holder, DATA data);

  /**
   * 得到对应的 Model
   */
  protected abstract int getItemViewTypeHV(DATA data);

  /**
   * 返回布局的 ID
   */
  protected abstract int getLayoutResID(@ItemViewType int viewType);

  public void setOnItemClickListener(OnRcvItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  public void setOnItemLongClickListener(OnRcvItemLongClickListener onItemLongClickListener) {
    mOnItemLongClickListener = onItemLongClickListener;
  }
}
