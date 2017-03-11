package es.vcarmen.interfazaparcamiento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by faper-pc on 09/03/2017.
 */

public class Index extends AppCompatActivity {

    Button btn_propietario;
    Button btn_plaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        btn_propietario = (Button) findViewById(R.id.btnPropi);
        btn_plaza = (Button) findViewById(R.id.btnPlazas);


    }
    public void inicioCrud(View view){
      Intent intent= new Intent(this,ActivityPropietarios.class);
        startActivity(intent);

    }
    public void inicioCrud2(View view){
        Intent intent= new Intent(this,ActivityPlazas.class);
        startActivity(intent);

    }
}
