import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilCultura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cultura);

        // Referências dos componentes
        EditText inputAreaPlantada = findViewById(R.id.inputAreaPlantada);
        EditText inputAreaAdubada = findViewById(R.id.inputAreaAdubada);
        EditText inputAreaDefensivo = findViewById(R.id.inputAreaDefensivo);
        EditText inputQtdSacas = findViewById(R.id.inputQtdSacas);
        TextView labelQtdGraos = findViewById(R.id.labelQtdGraos);
        TextView labelCotacao = findViewById(R.id.labelCotacao);
        TextView labelResultado = findViewById(R.id.labelResultado);
        Button botaoCalcular = findViewById(R.id.botaoCalcular);
        EditText inputCotacao = findViewById(R.id.inputCotacao);  // Referência para o campo de cotação

        // Configuração inicial
        double cotacaoPorSaca = 5.0; // Valor fixo para teste; pode ser dinâmico.
        labelCotacao.setText("Cotação: R$ " + cotacaoPorSaca + " por saca");

        // Preencher automaticamente os campos de adubação e defensivo com o valor de área plantada
        inputAreaPlantada.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String areaPlantada = inputAreaPlantada.getText().toString();
                if (!areaPlantada.isEmpty()) {
                    inputAreaAdubada.setText(areaPlantada);
                    inputAreaDefensivo.setText(areaPlantada);
                }
            }
        });

        // Ação do botão Calcular
        botaoCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Validação dos campos
                    if (isCampoVazio(inputAreaPlantada) || isCampoVazio(inputQtdSacas)) {
                        Toast.makeText(PerfilCultura.this, "Por favor, preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Captura dos valores
                    int qtdSacas = Integer.parseInt(inputQtdSacas.getText().toString());
                    double qtdGraos = qtdSacas * 60.0; // Exemplo de cálculo: 60 kg por saca
                    double resultado = qtdGraos * cotacaoPorSaca;

                    // Exibe os resultados
                    labelQtdGraos.setText("Quantidade de Grãos: " + qtdGraos + " kg");
                    labelResultado.setText("Valor Total: R$ " + resultado);

                    // Atualiza a cotação se o campo estiver preenchido
                    if (!inputCotacao.getText().toString().isEmpty()) {
                        cotacaoPorSaca = Double.parseDouble(inputCotacao.getText().toString());
                        labelCotacao.setText("Cotação: R$ " + cotacaoPorSaca + " por saca");
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(PerfilCultura.this, "Erro nos valores inseridos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isCampoVazio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
    }
}