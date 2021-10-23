
public class GeometryCalculator {
    // метод должен использовать абсолютное значение radius
    public static double getCircleSquare(double radius) {
        if (radius >= 0) {
            return Math.PI * Math.pow(radius, 2);
        }else {
            System.out.print("Radius below zero! Exception with code ");
            return -1;}
    }

    // метод должен использовать абсолютное значение radius
    public static double getSphereVolume(double radius) {
        if (radius >= 0) {
            return ((double) 4 / 3) * Math.PI * Math.pow(radius,3);
        }else {
            System.out.print("Radius below zero! Exception with code ");
            return -1;}

    }

    public static boolean isTriangleRightAngled(double a, double b, double c) {
        return  ((a + b) > c && (a + c) > b && (b + c) > a);
    }

    // перед расчетом площади рекомендуется проверить возможен ли такой треугольник
    // методом isTriangleRightAngled, если невозможен вернуть -1.0
    public static double getTriangleSquare(double a, double b, double c) {
        double p = getHalfPerimeter(a,b,c);
        if (isTriangleRightAngled(a, b, c)) {
            return Math.sqrt(p * (p - a) * (p - b) * (p - c));
        } else {
            System.out.print("There is NOT possible to make triangle! Exception with code ");
            return -1;
        }
    }
    private static double getHalfPerimeter(double a, double b, double c){
        return  (a + b + c) / (double) 2;
    }
}
