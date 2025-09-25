public class EnvioMaritimo extends Envio {
    public EnvioMaritimo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return distancia * 800 + peso * 1000;
    }
}
