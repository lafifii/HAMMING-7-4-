package com.algebralineal.tpalgebralineal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class EncodeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
    }

    public void Gotodecifrar(View view) {

        String mensajeoriginal =  ((EditText) findViewById(R.id.Mensaje)).getText().toString();
        TextView respuesta = (TextView) findViewById(R.id.rpta);
        if(mensajeoriginal==""){
            respuesta.setText("Ingrese mensaje a decifrar");
            return;
        }
        int [] arrBinario = Cadena_a_Binario(mensajeoriginal,mensajeoriginal.length());
        String mensajeCodificado = FPrincipal_Codificar(arrBinario);
        respuesta.setText(mensajeCodificado);
    }

    public static int[] Cadena_a_Binario(String s, int n){
        n= n*8;
        int [] bits = new int[n];
        int cont=0;
        for(int i=0;i<s.length();i++){
            for(int j = 7; j >= 0; j--)
                bits[cont++] = (((1<<j) & s.charAt(i)) != 0 ? 1:0);
        }

        return bits;
    }

    public static int [] Codificar(int [] a){
        int[]arr = new int[7];
        int caux=0;
        for(int i=0;i<7;i++) arr[i] = (i==0 || i== 1 || i == 3)? 0 :a[caux++];
        //Matriz que representa las operaciones -1: no hay dato
        int [][] matrizhamming = new int[4][7];

        for(int i=0; i< 4;i++)
            for(int j=0;j<7;j++)
                matrizhamming[i][j] = (i == 0 && j != 0 && j!= 1 && j!= 4) ? arr[j] : -1;

        for(int i= 1; i<4;i++){
            for(int j=0;j<7;j++){
                if(i==1)
                    matrizhamming[i][j] = (j%2 == 0 && j!=0) ? arr[j]: matrizhamming[i][j];
                else if(i==2)
                    matrizhamming[i][j] = (j == 2 || j == 5 || j==6) ? arr[j]: -1;

                else if(i==3)
                    matrizhamming[i][j] = (j>3)? arr[j] : matrizhamming[i][j];
            }
        }

        int cont;
        for(int i= 1; i <4;i++){
            cont = 0;
            for(int j=0;j<7;j++)
                if(matrizhamming[i][j] == 1) cont++;
            if(i==1) matrizhamming[1][0] = cont%2;
            else if(i==2) matrizhamming[2][1] = cont%2;
            else if(i==3) matrizhamming[3][3] = cont%2;
        }


        //Bajamos los bits de paridad
        arr[0] = matrizhamming[1][0];
        arr[1] = matrizhamming[2][1];
        arr[3] = matrizhamming[3][3];

        return arr;
    }

    public static int[] Codificar_MensajeEntero(int [] arr){

        int n = (arr.length * 7)/4;
        int[] bits = new int[n];
        int pos_bits = 0;
        int[] bits_aux = new int[4];
        for(int i=0;i<arr.length;i = i + 4){
            for(int j=0;j<4;j++)
                bits_aux[j] = arr[j+i];
            int[] codificado = Codificar(bits_aux);
            for(int j=0;j<7;j++)
                bits[pos_bits++] = codificado[j];
        }

        return bits;
    }

    public static  String FPrincipal_Codificar(int [] arr){
        int [] MensajeCifradoBits = Codificar_MensajeEntero(arr);
        String msj = "";
        for(int i=0;i<MensajeCifradoBits.length;i++)
            msj = msj + MensajeCifradoBits[i];
        return  msj;
    }
    public void GotoError(View view) {
        TextView ed = (TextView) findViewById(R.id.rpta);
        String s =  ed.getText().toString();
        int [] arr = new int[s.length()];
        for(int i=0;i<s.length();i++)
            arr[i] = (s.charAt(i)-48);

        int []arr2 = Generar_Error(arr);

        String s2 = "";
        for(int i=0;i<arr.length;i++)
            s2+= arr2[i];
        ed.setText(s2);

    }
    public static int[] Generar_Error(int [] arr){
        for(int i=0;i<arr.length;i = i + 7){
            Random rn = new Random();
            int p = rn.nextInt(i+7 - i + 1) + i;
            arr[p] = arr[p] == 0 ? 1: 0;
        }
        return arr;
    }
    public void GotoCorrect(View view){
        TextView ed = (TextView) findViewById(R.id.rpta);
        String s =  ed.getText().toString();
        int [] arr = new int[s.length()];
        for(int i=0;i<s.length();i++) arr[i] = (s.charAt(i)-48);
        int [] arr2 = Detectar_Corregir_error(arr);
        String rpta= "";
        if(arr2.length>=8)
            rpta = Binario_a_Cadena(arr2,arr2.length);
        else
            for(int i=0;i<arr2.length;i++)
                rpta+= arr2[i];

        ed.setText(rpta);
    }
    public static int[] Detectar_Corregir_error(int [] arr){

        int n = (arr.length*4)/7;
        int pos = 0;
        int[] bits = new int[n];
        for(int i=0;i<arr.length;i+=7){
            int[] aux = new int[7];
            for(int j=0;j<7;j++)
                aux[j] = arr[i+j];
            int fixed[] = Corregir(aux);
            for(int j=0;j<4;j++)
                bits[pos++] = fixed[j];
        }
        return bits;
    }

    public static int[] Corregir(int[] arr){

        int p1 = (arr[2] + arr[4] + arr[6]) % 2;
        int p2 = (arr[2] + arr[5] + arr[6]) % 2;
        int p3 = (arr[4] + arr[5] + arr[6]) % 2;

        ArrayList<Integer> posiciones = new ArrayList<>();
        if(p1!= arr[0]) posiciones.add(1);
        if(p2 != arr[1]) posiciones.add(2);
        if(p3!=arr[3]) posiciones.add(4);

        if(posiciones.size()>0){
            int cont = 0;
            for(int x:posiciones) cont = cont +x;
            cont = cont -1;
            int numero = arr[cont];
            arr[cont] = (numero==0)? 1 : 0;

        }
        int[]arr2 = new int[4];
        int pos = 0;


        for(int i=0;i<7;i++)
            if(i!=0 && i!=1 && i!=3) arr2[pos++] = arr[i];


        return arr2;
    }
    public static String Binario_a_Cadena(int [] bits, int n){

        String s="";
        int num;
        int cont = 0;
        for(int i=0;i<n;i+=8){
            num= 0;
            for(int j=7;j>=0;j--)
                if(bits[cont++] == 1)
                    num+= Math.pow(2,j);
            s+= (char)num;
        }
        return s;
    }
}
