//fertig
//von Susi gebaut


package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button ausfallendeVButton;
    private Button eigeneVButton;
    private Button vorVerzeichnisButton;
    private Button buttonLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ausfallendeVButton = (Button)findViewById(R.id.ausfallende_v_button);
        eigeneVButton = (Button)findViewById(R.id.eigene_v_button);
        vorVerzeichnisButton = (Button)findViewById(R.id.alle_v_button);

        ausfallendeVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAusfallend();
            }
        });

        eigeneVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEigene();
            }
        });

        vorVerzeichnisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickVorVerzeichnis();
            }
        });


       // buttonLast = (Button) findViewById(R.id.button_last);

        /*buttonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBaumLetzte();
            }
        });*/
    }

    private void clickAusfallend (){
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("open_ausfallend_fragment", true);
        i.putExtra("Button_Ausfallend", ausfallendeVButton.getText());
        startActivity(i);
    }

    private void clickEigene (){
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("open_eigene_fragment", true);
        i.putExtra("Button_Eigene", eigeneVButton.getText());
        startActivity(i);
    }

    private void clickVorVerzeichnis (){
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("open_vorverzeichnis_fragment", true);
        i.putExtra("Button_VorVerzeichnis", vorVerzeichnisButton.getText());
        startActivity(i);
    }

   /* private void clickBaumLetzte () {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("BaumLetzte",true);
        intent.putExtra("Button_BaumLetzte", buttonLast.getText());
        startActivity(intent);
    }*/


}



