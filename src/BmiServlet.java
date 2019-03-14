import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet("/calculate")
public class BmiServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = response.getWriter();

        String height = request.getParameter("height");
        String weight = request.getParameter("weight");
        String heightUnit = request.getParameter("height-unit");
        String wieghtUnit = request.getParameter("weight-unit");

        try {
            BigDecimal proportion = calcululateBMI(height,heightUnit,weight,wieghtUnit);
            writer.printf("<div style=\";font-size:16px;margin:25px;\">Value of yours body mass index: " + proportion);
            writer.println("<br/>" + showInfo(proportion) + "</div>");
        } catch (Exception e) {
            writer.println("An error occured.");
        }

    }

    private BigDecimal calcululateBMI(String height, String heightUnit, String weight, String weightUnit) {

        BigDecimal heightValue = BigDecimal.valueOf(Double.valueOf(height));
        BigDecimal weightValue = BigDecimal.valueOf(Double.valueOf(weight));

//        System.out.println("Height: " + height + " " + heightUnit);
//        System.out.println("Weight: " + weight + " " + weightUnit);

        if (heightUnit.equals("inches"))
            heightValue = heightValue.multiply(BigDecimal.valueOf(2.54));
        if (weightUnit.equals("pounds"))
            weightValue = weightValue.multiply(BigDecimal.valueOf(0.45));

//        System.out.println("Height: " + heightValue + "cm");
//        System.out.println("Weight: " + weightValue + "kg");

        BigDecimal heightPower = (heightValue.multiply(heightValue)).divide(BigDecimal.valueOf(100),100, RoundingMode.UNNECESSARY); //z cm do m

        return weightValue.divide(heightPower.divide(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
    }

    private String showInfo(BigDecimal proportion) {
        if (proportion.compareTo(BigDecimal.valueOf(18.5)) < 1) return "This is less than the correct weight.";
        else if (proportion.compareTo(BigDecimal.valueOf(18.5)) >= 1 && proportion.compareTo(BigDecimal.valueOf(24.99)) < 1) return "This is the correct weight.";
        else return "This is more than the correnct weight.";
    }

}