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
        Button botaoCalcular = findViewById(R.id.botaoCalcular);

        // Ação do botão Calcular
        botaoCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Validação dos campos
                    if (isCampoVazio(inputAreaPlantada) || isCampoVazio(inputAreaAdubada) ||
                            isCampoVazio(inputAreaDefensivo) || isCampoVazio(inputQtdSacas)) {
                        Toast.makeText(PerfilCultura.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Captura dos valores
                    int qtdSacas = Integer.parseInt(inputQtdSacas.getText().toString());

                    // Cálculo de grãos (1 saca = 60 kg)
                    double qtdGraos = qtdSacas * 60.0;
                    String unidade = qtdGraos >= 1000 ? "toneladas" : "kg";
                    double qtdGraosFinal = qtdGraos >= 1000 ? qtdGraos / 1000 : qtdGraos;

                    // Exibição do resultado
                    labelQtdGraos.setText("Quantidade de Grãos: " + String.format("%.2f", qtdGraosFinal) + " " + unidade);
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