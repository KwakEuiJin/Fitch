package org.app.gimalpro;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//아이디 중복확인 php클래스

public class Validate extends StringRequest {
    final static  private String URL="http://yuijin.ivyro.net/Validate.php";
    private Map<String,String> map;

    public Validate(String userID, Response.Listener<String>listener){
        super(Method.POST, URL, listener,null);

        map=new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}