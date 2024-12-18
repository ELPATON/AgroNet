package com.example.AgroNet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilCultura extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "PerfilCulturaPrefs";
    private static final String CULTURA_KEY = "CulturaNome";

    private EditText inputNomeCultura, inputAreaPlantada, inputAreaAdubada, inputAreaDefensivo, inputQtdSacas, inputCotacao;
    private TextView labelTituloCultura, labelQtdGraos, labelResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cultura);

        // Inicializar os componentes
        inputNomeCultura = findViewById(R.id.inputNomeCultura);
        inputAreaPlantada = findViewById(R.id.inputAreaPlantada);
        inputAreaAdubada = findViewById(R.id.inputAreaAdubada);
        inputAreaDefensivo = findViewById(R.id.inputAreaDefensivo);
        inputQtdSacas = findViewById(R.id.inputQtdSacas);
        inputCotacao = findViewById(R.id.inputCotacao);
        labelTituloCultura = findViewById(R.id.tituloCultura);
        labelQtdGraos = findViewById(R.id.labelQtdGraos);
        labelResultado = findViewById(R.id.labelResultado);
        Button botaoCalcular = findViewById(R.id.botaoCalcular);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Carregar a cultura salva
        carregarNomeCultura();

        // Atualizar o título conforme o nome inserido no campo
        inputNomeCultura.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                atualizarTituloCultura();
            }
        });

        // Configurar ação do botão Calcular
        botaoCalcular.setOnClickListener(v -> calcularResultado());
    }

    private void carregarNomeCultura() {
        String nomeCulturaSalvo = sharedPreferences.getString(CULTURA_KEY, "Milho");
        labelTituloCultura.setText("Perfil de Cultura: " + nomeCulturaSalvo);
        inputNomeCultura.setText(nomeCulturaSalvo); // Preencher o campo com o nome salvo
    }

    private void atualizarTituloCultura() {
        String nomeCultura = inputNomeCultura.getText().toString().trim();
        if (!nomeCultura.isEmpty()) {
            labelTituloCultura.setText("Perfil de Cultura: " + nomeCultura);
            salvarNomeCultura(nomeCultura);
        } else {
            labelTituloCultura.setText("Perfil de Cultura: Milho");
            salvarNomeCultura("Milho");
        }
    }

    private void salvarNomeCultura(String nomeCultura) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CULTURA_KEY, nomeCultura);
        editor.apply();
    }

    private void calcularResultado() {
        try {
            if (isCampoVazio(inputAreaPlantada) || isCampoVazio(inputQtdSacas) || isCampoVazio(inputCotacao)) {
                Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Captura dos valores
            int qtdSacas = Integer.parseInt(inputQtdSacas.getText().toString());
            double cotacao = Double.parseDouble(inputCotacao.getText().toString());
            double qtdGraos = qtdSacas * 60.0; // 1 saca = 60kg
            double valorTotal = qtdSacas * cotacao;

            // Ajuste da unidade (kg ou toneladas)
            String unidade = qtdGraos >= 1000 ? "toneladas" : "kg";
            double qtdGraosFinal = qtdGraos >= 1000 ? qtdGraos / 1000 : qtdGraos;

            // Exibição dos resultados
            labelQtdGraos.setText("Quantidade de Grãos: " + String.format("%.2f", qtdGraosFinal) + " " + unidade);
            labelResultado.setText("Valor Total: R$ " + String.format("%.2f", valorTotal));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Erro no formato dos valores inseridos. Por favor, corrija.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCampoVazio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
    }
}
