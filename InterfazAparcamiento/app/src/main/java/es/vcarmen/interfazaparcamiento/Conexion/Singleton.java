package es.vcarmen.interfazaparcamiento.Conexion;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by faper-pc on 08/03/2017.
 */

public class Singleton {
    private static Singleton myInstance;
    private RequestQueue requestQueue;
    private static Context myContext;

    public RequestQueue getRequestQueue(){
        if(requestQueue== null){
            requestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return requestQueue;
    }

    public Singleton(Context context) {
        myContext= context;
        requestQueue= getRequestQueue();
    }

    public static synchronized Singleton getInstance(Context context){

        if(myInstance== null){
            myInstance= new Singleton(context);
        }
        return myInstance;
    }

    public<T> void addRequestQueue(Request<T> request){

        requestQueue.add(request);

    }
}
