package com.lixue.admin.mvvm;

import android.app.Application;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.lixue.admin.asmlifecycledemo.BR;
import com.lixue.admin.databinding.Account;


public class MVVMViewModel extends BaseObservable {
    private String result;
    private MVVMModel model;
    private String userInput;

    //一般要传入Application对象，方便在ViewModel中使用application，
    //比如sharedpreferrences需要使用
    //当然在这个例子当中，没有用到application
    public MVVMViewModel(Application application){
        model = new MVVMModel();
    }

    public MVVMViewModel(){
        model = new MVVMModel();
    }

    public void getData(View view){
        model.getAccountData(userInput, new MCallback() {
            @Override
            public void onSuccess(Account account) {
                String info = account.getName() + "|" + account.getLevel();
                setResult(info);
            }

            @Override
            public void onFailed() {
                setResult("获取数据失败");
            }
        });
    }

    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    @Bindable
    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
        notifyPropertyChanged(BR.userInput);
    }
}
