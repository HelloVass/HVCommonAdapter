# HVCommonAdapter


[![](https://jitpack.io/v/HelloVass/HVCommonAdapter.svg)](https://jitpack.io/#HelloVass/HVCommonAdapter)

## RecyclerCommonAdapter

一个适用于 RecyclerView 的通用 Adapter。


## 使用

### 1.继承 BaseRcvAdapter

```java
public class WaterFlowAdapter extends BaseRcvAdapter<UserPost>{

}
```

### 2.根据业务 bean 中的 type 字段返回 item 对应的类型

```java
 @Override protected int getItemViewTypeHV(UserPost post) {
    return post.type;
  }
```

### 3. 根据 itemViewType 返回对应的布局资源 Id



```java

private final static int ITEM_VIEW_TYPE_AUDIO = 133;
private final static int ITEM_VIEW_TYPE_IMAGE = 233;
private final static int ITEM_VIEW_TYPE_VIDEO = 666;

@Override protected int getLayoutResId(int itemViewType) {

    switch(itemViewType){
      case ITEM_VIEW_TYPE_AUDIO:
        return R.layout.item_water_flow_audio;
      
      case ITEM_VIEW_TYPE_IMAGE:
        return R.layout.item_water_flow_image;
        
      case ITEM_VIEW_TYPE_VIDEO:
        return R.layout.item_water_flow_video;
    }

  }
```

### 4. 重写 convert 方法，根据 itemViewType 设置数据
```java
@Override protected void convert(BaseRecyclerViewHolder holder, UserPost post, int itemViewType) {

  switch(itemViewType){
      case ITEM_VIEW_TYPE_AUDIO:
        TextView audio = holder.getView(R.id.XX);
        audio.setXX();
        ...
        break;
        
      
      case ITEM_VIEW_TYPE_IMAGE:
        // 同理
        break;
       
        
      case ITEM_VIEW_TYPE_VIDEO:
        // 同理
        break;

}
```

### Tips

BaseRecyclerViewHolder 提供了 `getView(int viewResId)` 方法，可以方便地从布局中取出对应的 View，所以不需要写什么 XXRecyclerViewHolder 了！

```java
/**
   * 通过传入的 viewId 找到相应的 子控件
   *
   * @param viewId childView 的 ID
   * @param <V> childView
   */
  public <V extends View> V getView(int viewId) {
    View view = mViewSparseArray.get(viewId);
    if (view == null) {
      view = mConvertView.findViewById(viewId);
      mViewSparseArray.put(viewId, view);
    }
    return (V) view;
  }
```



