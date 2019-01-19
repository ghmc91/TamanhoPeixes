package br.com.gustavocosta.projeto01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.ObjectInputStream;

import smile.regression.OLS;

public class MainActivity extends AppCompatActivity {

    private EditText diasEditText, tempEditText;
    private OLS ols;
    private TextView resultadoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diasEditText = findViewById(R.id.dias_edittext);
        tempEditText = findViewById(R.id.temp_edittext);
        resultadoTextView = findViewById(R.id.resultado_textView);
        ols = carregarModelo();
        if (ols == null) {
            finish();
        }

    }

    private OLS carregarModelo() {
        try {
            InputStream is = getResources().openRawResource(R.raw.modelo);
            ObjectInputStream ois = new ObjectInputStream(is);
            OLS ols = (OLS) ois.readObject();
            Log.i("dsa", "Modelo carregado!");
            Log.i("dsa", "R2: " + ols.RSquared());
            return ols;
        } catch (Exception ex) {
            Log.e("dsa", "Erro ao recuperar objeto: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }


    public void processar(View v) {
        //Toast.makeText(this, "Clicou no botão", Toast.LENGTH_SHORT).show();
        double dias = 0, temperatura = 0;
        try {
            dias = Double.parseDouble(diasEditText.getText().toString());
            temperatura = Double.parseDouble(tempEditText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Verifique os dados", Toast.LENGTH_SHORT).show();
            Log.e("dsa", e.getMessage());
            return;
        }

        //Toast.makeText(this, "Dados válidos", Toast.LENGTH_SHORT).show();
        double resultado = ols.predict(new double[]{dias,temperatura});
        resultadoTextView.setText(String.valueOf(resultado));
    }
}
