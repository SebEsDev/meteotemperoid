package fr.aston.fragment.ui.temperature;

public class TemperatureConverter {

	// °F = (9/5)*(°C + 32)
	public static String fahrenheitFromCelcius(double celsius) {
		return Double.valueOf(((9.0/5.0)*celsius + 32.0)).toString();
	}
	
	// °C = (5/9)*(°F - 32)
	public static String celsiusFromFahrenheit(double fahrenheit) {
		return Double.valueOf((5.0/9.0)*(fahrenheit-32.0)).toString();
	}
}
