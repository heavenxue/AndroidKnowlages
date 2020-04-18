package com.lixue.admin.mvvm;

import com.lixue.admin.databinding.Account;

public interface MCallback {
    void onSuccess(Account account);
    void onFailed();
}
