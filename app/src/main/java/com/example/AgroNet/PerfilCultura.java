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
                    double qtdGraos = qtdSacas * 60.0; // 1 saca = 60kg
                    double valorTotal = qtdSacas * cotacaoPorSaca;

                    // Ajuste da unidade (kg ou toneladas)
                    String unidade = qtdGraos >= 1000 ? "toneladas" : "kg";
                    double qtdGraosFinal = qtdGraos >= 1000 ? qtdGraos / 1000 : qtdGraos;

                    // Exibição dos resultados
                    labelQtdGraos.setText("Quantidade de Grãos: " + String.format("%.2f", qtdGraosFinal) + " " + unidade);
                    labelResultado.setText("Valor Total: R$ " + String.format("%.2f", valorTotal));
                } catch (NumberFormatException e) {
                    Toast.makeText(PerfilCultura.this, "Erro no formato dos valores inseridos. Por favor, corrija.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método auxiliar para verificar campos vazios
    private boolean isCampoVazio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
    }
}