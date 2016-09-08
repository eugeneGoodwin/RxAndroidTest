package com.test.my.rxandroidtest.presenters;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.test.my.rxandroidtest.R;
import com.test.my.rxandroidtest.databinding.HeaderLayoutBinding;
import com.test.my.rxandroidtest.databinding.ItemLayoutBinding;
import com.test.my.rxandroidtest.interfaces.Callback;
import com.test.my.rxandroidtest.model.NetModel;
import com.test.my.rxandroidtest.retrofit.entries.JsonPost;
import com.test.my.rxandroidtest.rxrecycleradapter.OnGetItemViewType;
import com.test.my.rxandroidtest.rxrecycleradapter.RxDataSource;
import com.test.my.rxandroidtest.rxrecycleradapter.TypesViewHolder;
import com.test.my.rxandroidtest.rxrecycleradapter.ViewHolderInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Android-dev on 08.09.2016.
 */
public class MainPresenter extends BaseProgressPresenter<MainPresenter.View>{

    private NetModel netModel = NetModel.instance(this);
    List<JsonPost> dataSet = new ArrayList<>();
    RxDataSource<JsonPost> rxDataSource;

    final int TYPE_HEADER = 0;
    final int TYPE_ITEM = 1;

    public void init(){

        RecyclerView recyclerView = view.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        rxDataSource = new RxDataSource<>(dataSet);
        /*rxDataSource.map(p -> p)
                //cast call this method with the binding Layout
                .<ItemLayoutBinding>bindRecyclerView(recyclerView, R.layout.item_layout)
                .subscribe(viewHolder -> {
                    ItemLayoutBinding b = viewHolder.getViewDataBinding();
                    b.userId.setText(String.valueOf(viewHolder.getItem().getId()));
                    b.title.setText(viewHolder.getItem().getTitle());
                    b.description.setText(viewHolder.getItem().getBody());
                });
        */

        //ViewHolderInfo List
        List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_layout, TYPE_ITEM));
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.header_layout, TYPE_HEADER));

        rxDataSource.bindRecyclerView(recyclerView, viewHolderInfoList, new OnGetItemViewType() {
            @Override public int getItemViewType(int position) {
                if (position % 2 == 0) //headers are at even pos
                {
                    return TYPE_HEADER;
                }
                return TYPE_ITEM;
            }
        }).subscribe(new Action1<TypesViewHolder<JsonPost>>() {
            @Override public void call(TypesViewHolder<JsonPost> vH) {
                final ViewDataBinding b = vH.getViewDataBinding();
                if (b instanceof ItemLayoutBinding) {
                    final ItemLayoutBinding iB = (ItemLayoutBinding) b;
                    iB.userId.setText(String.valueOf(vH.getItem().getId()));
                    iB.title.setText(vH.getItem().getTitle());
                    iB.description.setText(vH.getItem().getBody());
                } else if (b instanceof HeaderLayoutBinding) {
                    HeaderLayoutBinding hB = (HeaderLayoutBinding) b;
                    hB.headerTitle.setText(vH.getItem().getTitle());
                }
            }
        });

        update();
    }

    public void update(){
        showProgress();
        netModel.getPosts(new Callback<List<JsonPost>>(){
            @Override
            public void T(List<JsonPost> list){
                hideProgress();
                dataSet.clear();
                dataSet.addAll(list);
                rxDataSource.updateDataSet(dataSet).map(json -> {
                                                                    json.setTitle(json.getTitle().toUpperCase());
                                                                    return json;
                                                                }).updateAdapter();
            }
        });
    }

    public void start() {
        //Observable
        //        .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        //        .filter(integer -> integer % 2 == 0)
        //        .subscribe(System.out::println);
        //        .subscribe(i -> Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show());

        //Observable
        //        .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        //        .forEach(System.out::println);

        //Observable
        //        .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        //        .take(3)
        //        .subscribe(System.out::println);


        //Observable.just("Hello Word!!!")
        //        .map(s -> s.hashCode())
        //        .subscribe(System.out::println);

        //List<JsonPost> users = new ArrayList<JsonPost>();

        //users.add(new JsonPost());
        //users.add(new JsonPost());

        //Observable
        //        .just(users)
        //        .concatMap(postList -> Observable.from(postList))
        //        .subscribe(post -> System.out.println(post.getTitle()));

        update();
    }

    @Override
    public void retrofitError(Response res, Throwable ex){
        super.retrofitError(res, ex);
        TextView errorView = view.getStatus();
        errorView.setText(ex.getMessage());
    }

    public interface View extends BasePresenterInterface {
        RecyclerView getRecyclerView();
        TextView getStatus();
    }
}
