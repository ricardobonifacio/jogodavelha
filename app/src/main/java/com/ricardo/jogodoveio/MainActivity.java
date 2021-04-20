package com.ricardo.jogodoveio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private Button[] arrayButton = new Button[10];
    private String vez = "X";
    private int jogadas = 0;
    private int ponto = 0;
    private String[] matriz = new String[10];
    private TextView pontoO;
    private TextView pontoX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("2A91991357EE0F4BBD9FA12CBC8F9995").build();
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        // "2A91991357EE0F4BBD9FA12CBC8F9995"
        mAdView.loadAd(adRequest);

        pontoO = findViewById(R.id.pontoO);
        pontoX = findViewById(R.id.pontoX);

        Button btnReiniciar = findViewById(R.id.btnReiniciar);
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontoO.setText("0");
                pontoX.setText("0");
            }
        });

        inicializarButtons();
        onClickButtons();
    }

    private void inicializarButtons(){
        arrayButton[1] = findViewById(R.id.btn1);
        arrayButton[2] = findViewById(R.id.btn2);
        arrayButton[3] = findViewById(R.id.btn3);
        arrayButton[4] = findViewById(R.id.btn4);
        arrayButton[5] = findViewById(R.id.btn5);
        arrayButton[6] = findViewById(R.id.btn6);
        arrayButton[7] = findViewById(R.id.btn7);
        arrayButton[8] = findViewById(R.id.btn8);
        arrayButton[9] = findViewById(R.id.btn9);
    }

    private void onClickButtons(){
        for (int x = 1; x < 10; x ++){
            final int finalx = x;
            arrayButton[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jogada(finalx);
                }
            });
            matriz[x] = "";
        }
    }

    private void jogada(int x){
        if (matriz[x].equals("")){
            matriz[x] = vez;
            jogadas++;
            if (vez == "X"){
                vez = "O";
            }else{
                vez = "X";
            }
        }
        exibirButtons();
        verifica();
    }

    private void exibirButtons(){
        for (int x = 1; x < 10; x ++){
            //arrayButton[x].setText(matriz[x]);
            if (matriz[x].equals("O")){
                arrayButton[x].setBackground(this.getResources().getDrawable(R.drawable.velho_o));
            }
            if (matriz[x].equals("X")){
                arrayButton[x].setBackground(this.getResources().getDrawable(R.drawable.velho_x));
            }
            if (matriz[x].equals("")){
                arrayButton[x].setBackground(this.getResources().getDrawable(R.drawable.branco));
            }
        }
    }

    private void verifica(){
        if ((matriz[1].equals(matriz[2])) && (matriz[1].equals(matriz[3])) && (matriz[1].toString() != "")){
            ganhador(matriz[1]);
            pontua(matriz[1]);
            return;
        }

        if ((matriz[4].equals(matriz[5])) && (matriz[4].equals(matriz[6])) && (matriz[4].toString() != "")){
            ganhador(matriz[4]);
            pontua(matriz[4]);
            return;
        }

        if ((matriz[7].equals(matriz[8])) && (matriz[7].equals(matriz[9])) && (matriz[7].toString() != "")){
            ganhador(matriz[7]);
            pontua(matriz[7]);
            return;
        }

        if ((matriz[1].equals(matriz[5])) && (matriz[1].equals(matriz[9])) && (matriz[1].toString() != "")){
            ganhador(matriz[1]);
            pontua(matriz[1]);
            return;
        }

        if ((matriz[3].equals(matriz[5])) && (matriz[3].equals(matriz[7])) && (matriz[3].toString() != "")){
            ganhador(matriz[3]);
            pontua(matriz[3]);
            return;
        }

        if ((matriz[1].equals(matriz[4])) && (matriz[1].equals(matriz[7])) && (matriz[1].toString() != "")){
            ganhador(matriz[1]);
            pontua(matriz[1]);
            return;
        }

        if ((matriz[2].equals(matriz[5])) && (matriz[2].equals(matriz[8])) && (matriz[2].toString() != "")){
            ganhador(matriz[2]);
            pontua(matriz[2]);
            return;
        }

        if ((matriz[3].equals(matriz[6])) && (matriz[3].equals(matriz[9])) && (matriz[3].toString() != "")){
            ganhador(matriz[3]);
            pontua(matriz[3]);
            return;
        }

        if (jogadas == 9){
            ganhador("");
            return;
        }
    }

    private void ganhador2(String stganhador){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Resultado");
        if (stganhador.equals("")){
            builder.setMessage("Empate");
        }else {
            builder.setMessage("Vencedor " + stganhador);
        }
        builder.setPositiveButton("Novo Jogo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jogadas = 0;
                for (int x = 1; x < 10; x ++){
                    matriz[x] = "";
                }
                exibirButtons();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ganhador(String stganhador){
        final Dialog dgConsulta = new Dialog(this);
        dgConsulta.setContentView(R.layout.dialog_vencedor);
        TextView txtVendedor = dgConsulta.findViewById(R.id.txtVendedor);
        ImageView imagVenc = dgConsulta.findViewById(R.id.imagVenc);
        Button btProximo = dgConsulta.findViewById(R.id.btProximo);

        if (stganhador.equals("")){
            txtVendedor.setText("Vencedor:\nEmpate");
            imagVenc.setBackground(this.getResources().getDrawable(R.drawable.branco));
        }else {
            txtVendedor.setText("Vencedor:\n" + stganhador);
            if (stganhador.equals("O")){
                imagVenc.setBackground(this.getResources().getDrawable(R.drawable.velho_o));
            }
            if (stganhador.equals("X")){
                imagVenc.setBackground(this.getResources().getDrawable(R.drawable.velho_x));
            }
        }

        btProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogadas = 0;
                for (int x = 1; x < 10; x ++){
                    matriz[x] = "";
                }
                exibirButtons();
                dgConsulta.dismiss();
            }
        });

        //.setBackground(this.getResources().getDrawable(R.drawable.velho_o));
        dgConsulta.show();
    }

    private void pontua(String vencedor){
        if (vencedor.equals("X")){
            ponto = Integer.parseInt(pontoX.getText().toString());
            pontoX.setText(String.valueOf(ponto + 1));
        }

        if (vencedor.equals("O")){
            ponto = Integer.parseInt(pontoO.getText().toString());
            pontoO.setText(String.valueOf(ponto + 1));
        }
    }
}
