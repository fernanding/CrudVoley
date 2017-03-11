package es.vcarmen.interfazaparcamiento;

/**
 * Created by faper-pc on 11/03/2017.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.vcarmen.interfazaparcamiento.Conexion.Singleton;
import es.vcarmen.interfazaparcamiento.Modelo.Plazas;
import es.vcarmen.interfazaparcamiento.Modelo.Propietarios;


public class ActivityPlazas extends AppCompatActivity {

    private Button btnGet;
    private Button btnDelete;
    private Button btnActualizar;
    private Button btnCrear;

    private TextView txtConectado;
    private TextView scrolltxt;

    private ArrayList<Plazas> listaPlazas;

    EditText editTextnumplaza;
    EditText editTextmantenimiento;
    EditText editTextPlanta;
    EditText editTextdniPro;

    ArrayAdapter arrayAdapter;
    ListView listView;

    // ========== Pruebas de GET ============ //

    private String server_url = "http://192.168.1.43:3000/plazas/";

    String idBorrar = "";

    private RequestQueue requestQueue;
    private Context ctx;

    public Gson gson = new Gson();

    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCrear = (Button) findViewById(R.id.button3);
        btnGet = (Button) findViewById(R.id.button);
        btnDelete = (Button) findViewById(R.id.button2);
        btnActualizar = (Button) findViewById(R.id.button4);


        editTextnumplaza = (EditText) findViewById(R.id.editTextnumplaza);
        editTextPlanta = (EditText) findViewById(R.id.editTextPlanta);
        editTextmantenimiento = (EditText) findViewById(R.id.editTextmantenimiento);
        editTextdniPro = (EditText) findViewById(R.id.dniPro);

        txtConectado = (TextView) findViewById(R.id.textView2);
        scrolltxt = (TextView) findViewById(R.id.textView3);

        listaPlazas = new ArrayList<Plazas>();

        listView = (ListView) findViewById(R.id.lista);


        ctx = this;
        requestQueue = Volley.newRequestQueue(ctx);


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });


        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerDatos();

            }
        });


        StringRequest stringRequestGET = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Plazas c[] = gson.fromJson(response, Plazas[].class);

                for (int i = 0; i < c.length - 1; i++) {
                    //txt.append(c[i].toString()+"\n");
                    listaPlazas.add(c[i]);

                }
                txtConectado.setText("Conectado. Cargados datos de la BBDD" + "\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtConectado.setText("Error al cargar de la BBDD.");
                // error.printStackTrace();
            }
        });


        Singleton.getInstance(getApplicationContext()).addRequestQueue(stringRequestGET);



        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void leerDatos() {


        ArrayAdapter<Plazas> a = new ArrayAdapter<Plazas>(ActivityPlazas.this, R.layout.simple_list_item_1, listaPlazas);
        listView.setAdapter(a);
    }

    private void actualizar() {

        StringRequest strq = new StringRequest(Request.Method.PUT, server_url + editTextnumplaza.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("numplaza", editTextnumplaza.getText().toString());
                parametros.put("planta", editTextPlanta.getText().toString());

                parametros.put("mantenimiento", editTextmantenimiento.getText().toString());
                parametros.put("dniPropietario", editTextdniPro.getText().toString());

                return parametros;
            }
        };

        requestQueue.add(strq);
        leerDatos();

    }

    private void borrar() {

        StringRequest strq = new StringRequest(Request.Method.DELETE, server_url + editTextnumplaza.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("numplaza", editTextnumplaza.getText().toString());


                return parametros;
            }
        };

        requestQueue.add(strq);
        leerDatos();
    }

    private void crear() {


        StringRequest strq = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast t = Toast.makeText(ActivityPlazas.this, response, Toast.LENGTH_LONG);
                t.show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<>();

                parametros.put("numplaza", editTextnumplaza.getText().toString());
                parametros.put("planta", editTextPlanta.getText().toString());
                parametros.put("mantenimiento", editTextmantenimiento.getText().toString());
                parametros.put("dniPropietario", editTextdniPro.getText().toString());


                return parametros;
                //metodo
            }

        };

        requestQueue.add(strq);
        leerDatos();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ActivityPlantas Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

