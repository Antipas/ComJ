package j.com.componentserivce.base;

import android.text.TextUtils;

public class BaseResult {
    public String status;

    public boolean isSuccessStatus(){
     return !TextUtils.isEmpty(status) && status.equals("success");
    }
}
