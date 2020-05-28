/*Requerimientos
- Saber el clima de Bs As en un momento dado
- Quiero poder recibir sugerencias de atuendos que tengan una prenda para cada categoría, aunque a futuro podrán tener más
- La sugerencia de prendas debe ser acorde a la temperatura actual.
- Tengo que poder configurar facilmente los diferentes servicios de obtencion del clima (adapter)
- No incurrir en costos innecesarios (AccuWeatherAPI nos cobra 0,05 USD a partir del décimo llamado diario)
*/

class Clima {
    DateTime dateTime;
    BigDecimal precipitationProbability;
    int Temperatura;
}

public class Prenda(){
    TipoPrenda tipo;
    Material material;
    Color colorPrimario;
    Color colorSecundario;
    int temperaturaMinima;
    int temperaturaMaxima;
}

public class Atuendo{
	Prenda prendaSuperior;
	Prenda prendaInferior;
	Prenda calzado;
	Prenda accesorio;
}


public class Sugerenciador{

	//Quiero poder recibir sugerencias de atuendos que tengan una prenda para cada categoría, aunque a futuro podrán tener más
	//La sugerencia internamente llama al obtenerClimaDe(ciudad) y en base a la temperatura actual arma la lista de atuendos
	public List<Atuendo> sugerencia(List<Prenda> prendasSuperior, List<Prenda> prendasInferior, List<Prenda> calzados, List<Prenda> accesorios){

		Pronostico pronostico = new Pronostico()
		List<Map<String, Object>> condicionesClimaticas = pronostico.obtenerClimaDe("Buenos Aires, Argentina");

		int temperaturaActual = condicionesClimaticas.get(0).get("Temperatura");		

		List<Prenda> prendasSuperiorSugeridas = filtrarPorTemperatura(prendasSuperior, temperaturaActual);
		List<Prenda> prendasInferiorSugeridas = filtrarPorTemperatura(prendasInferior, temperaturaActual);
		List<Prenda> calzadosSugeridos = filtrarPorTemperatura(calzados, temperaturaActual);
		List<Prenda> accesoriosSugeridos = filtrarPorTemperatura(accesorios, temperaturaActual);
	
		return //combinaciones posibles de las 4 prendas
	}

	//La sugerencia de prendas debe ser acorde a la temperatura actual.
	private List<Prenda> filtrarPorTemperatura(List<Prenda> prendas, int temperaturaActual){
		prendas.stream().map(prenda => prenda.temperaturaMinima < temperaturaActual && prenda.temperaturaMaxima > temperaturaActual)
	}
}

//Adapter para configurar facil los diferentes servicios de obtención del clima
//Lo llamaria 2 veces al dia (cada 12 horas) ya que en cada llamado me devuelve una lista con el clima de las proximas 12 horas
//De esta manera no tengo costos innecesarios
public class Pronostico {

	//Saber el clima de Bs As en un momento dado
	public List<Clima> obtenerClimaDe(String ciudad) {
		AccuWeatherAPI apiClima = new AccuWeatherAPI();
		List<Map<String, Object>> condicionesClimaticas = apiClima.getWeather(“Buenos Aires, Argentina”);

		return condicionesClimaticas.stream().map(condicionClimatica -> Pronostico::traducirAClima).ToList();
	}

	//Obtengo solo las propiedades que me importan
	private Clima traducirAClima(Map<String, Object> condicionesClimaticas) {
		Clima clima = new Clima();
		clima.setDateTime(condicionesClimaticas.get("DateTime"));
		clima.setPrecipitationProbability(condicionesClimaticas.get("PrecipitationProbability"));
		clima.setTemperatura(condicionesClimaticas.get("Temperature").get("Value"));
		return clima;
	}
}

public final class AccuWeatherAPI {

    public final List<Map<String, Object>> getWeather(String ciudad) {
		return Arrays.asList(new HashMap<String, Object>(){{
			put("DateTime", "2019-05-03T01:00:00-03:00");
			put("EpochDateTime", 1556856000);
			put("WeatherIcon", 33);
			put("IconPhrase", "Clear");
			put("IsDaylight", false);
			put("PrecipitationProbability", 0);
			put("MobileLink", "http://m.accuweather.com/en/ar/villa-vil/7984/");
			put("Link", "http://www.accuweather.com/en/ar/villa-vil/7984");
			put("Temperature", new HashMap<String, Object>(){{
				put("Value", 57);
				put("Unit", "F");
				put("UnitType", 18);
			}});
		}});
	}
}

public enum TipoPrenda {
    Categoria categoria;

    TipoPrenda(Categoria categoria)
    SACO(Categoria.SUPERIOR), PANTALON(Categoria.INFERIOR), CAMISA(Categoria.SUPERIOR)
}

public enum Material {
    ALGODON, LAICRA, ETC
}

public enum Categoria {
    SUPERIOR{
        public Bool admiteTIpo(){
            ...
        }
    }, INFERIOR, CALZADO, ACCESORIO

}

public class Color{
    Integer red;
    Integer green;
    Integer blue;
}