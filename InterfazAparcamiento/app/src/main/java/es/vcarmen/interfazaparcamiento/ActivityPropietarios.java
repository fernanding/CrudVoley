package es.vcarmen.interfazaparcamiento;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import es.vcarmen.interfazaparcamiento.Modelo.Propietarios;


public class ActivityPropietarios extends AppCompatActivity {

    private Button btnGet;
    private Button btnDelete;
    private Button btnActualizar;
    private Button btnCrear;

    private TextView txtConectado;
    private TextView scrolltxt;

    static ArrayList<Propietarios> listaPropietarios;

    EditText editTextdni;
    EditText editTextNombre;
    EditText editTextApellidos;

    ArrayAdapter arrayAdapter;
    ListView listView;

    // ========== Pruebas de GET ============ //

    private String server_url = "http://192.168.1.43:3000/propietarios/";
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


        editTextdni = (EditText) findViewById(R.id.editTextDni);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApellidos = (EditText) findViewById(R.id.editTextApellidos);


        txtConectado = (TextView) findViewById(R.id.textView2);
        scrolltxt = (TextView) findViewById(R.id.textView3);

        listaPropietarios = new ArrayList<Propietarios>();

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


                 Propietarios c[] = gson.fromJson(response, Propietarios[].class);

                for (int i = 0; i < c.length - 1; i++) {
                    //txt.append(c[i].toString()+"\n");
                    listaPropietarios.add(c[i]);

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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void leerDatos() {


        ArrayAdapter<Propietarios> a = new ArrayAdapter<Propietarios>(ActivityPropietarios.this, R.layout.simple_list_item_1, listaPropietarios);
        listView.setAdapter(a);
    }

    private void actualizar() {

        StringRequest strq = new StringRequest(Request.Method.PUT, server_url + editTextdni.getText().toString(),
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

                parametros.put("dni", editTextdni.getText().toString());
                parametros.put("nombre", editTextNombre.getText().toString());

                parametros.put("apellidos", editTextApellidos.getText().toString());


                return parametros;
            }
        };

        requestQueue.add(strq);
        leerDatos();

    }

    private void borrar() {

        StringRequest strq = new StringRequest(Request.Method.DELETE, server_url + editTextdni.getText().toString(),
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

                parametros.put("dni", editTextdni.getText().toString());


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
                Toast t = Toast.makeText(ActivityPropietarios.this, response, Toast.LENGTH_LONG);
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

                parametros.put("dni", editTextdni.getText().toString());
                parametros.put("nombre", editTextNombre.getText().toString());
                parametros.put("apellidos", editTextApellidos.getText().toString());


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
                .setName("ActivityPropietarios Page") // TODO: Define a title for the content shown.
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

