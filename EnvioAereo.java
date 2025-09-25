public class EnvioAereo extends Envio {
    public EnvioAereo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return distancia * 5000 + peso * 4000;
    }
}
