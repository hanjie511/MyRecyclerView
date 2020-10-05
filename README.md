# MyRecyclerView
一个带有下拉刷新和上拉加载功能的RecyclerView  
## 效果预览  
![](https://hanjie-oos.oss-cn-shenzhen.aliyuncs.com/test1_20201005.gif)![](https://hanjie-oos.oss-cn-shenzhen.aliyuncs.com/test2_20201005.gif)![](https://hanjie-oos.oss-cn-shenzhen.aliyuncs.com/test3_20201005.gif)  
## 如何引用  
### Gradle  
* step1. Add the JitPack repository to your root build.gradle at the end of repositories:  
```java  
allprojects {
  repositories {
  ...
  maven { url 'https://jitpack.io' }
  }
}  
```  
* step2. Add the dependency  
```java  
dependencies {
  implementation 'com.github.hanjie511:MyRecyclerView:v1.0.0'
}  
```  
### Maven  
```java  
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>  
    
<dependency>
  <groupId>com.github.hanjie511</groupId>
  <artifactId>MyRecyclerView</artifactId>
  <version>v1.0.0</version>
</dependency>  
```  
## 在项目中使用  
* Step1.在布局文件中添加该控件：  
```java  
<com.example.hj.mylibrary.HJRecyclerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/recyclerView"
    />  
```  
* Step2.找到该控件并为其设置适配器  
```java  
private HJRecyclerView hjRecyclerView;  
  .  
  .  
  .
hjRecyclerView=findViewById(R.id.recyclerView);  
hjRecyclerView.setLinearLayout();
hjRecyclerView.setAdapter(RecyclerView.Adapter adapter);  
```
* Step3.设置下拉刷新和上拉加载的监听  
```java  
hjRecyclerView.setHjRefreshAndLoadMoreListener(new HJRecyclerView.HJRefreshAndLoadMoreListener() {
    @Override
    public void onLoadMore() {//上拉加载回调
      ...  
      myRecyclerViewAdapter.notifyDataSetChanged();
      hjRecyclerView.setLoadingComplete(true);

    }

    @Override
    public void onRefresh() {//下拉刷新回调
      ...  
      myRecyclerViewAdapter.notifyDataSetChanged();
      hjRecyclerView.setLoadingComplete(true);
    }
   });  
```  
## 其他的方法  
#### getRecyclerView();  
#### setLinearLayout();  
#### setGridLayout(int spanCount);  
#### setStaggeredGridLayout(int spanCount);  
#### setAdapter(RecyclerView.Adapter adapter);  
#### getLayoutManager();  
#### setLoadingComplete(boolean hasData);


