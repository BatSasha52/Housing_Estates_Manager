public class Utils {

  public static double getVolume(double lenght, double witdth, double height) {
    return lenght * witdth * height;
  }

  public static int generateNum(int a, int b) {

    if (b < a) {
      a = a + b;
      b = a - b;
      a = a - b;
    }

    return (int) (Math.random() * (b - a ) + a);
  }

  public static double generateNum(double a, double b) {

    if (b < a) {
      a = a + b;
      b = a - b;
      a = a - b;
    }

    return Math.random() * (b - a) + a;
  }

  public static String generateString(String [] strings) {
    return strings[Utils.generateNum(0,  strings.length)];
  }
}