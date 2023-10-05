import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DOMVentas {
public static void main(String[] args) {

        try {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(new File("sales.xml"));
        Element rootElement = document.getDocumentElement();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ingrese el procentaje deseado del 5% al 15%: ");
        double porcentaje = Double.parseDouble(reader.readLine());


        if (porcentaje < 5 || porcentaje > 15) {
        System.out.println("El % ingresado no es entre 5% y 15%");

        return;
        }

        System.out.print("Ingresa el numero del departamento: ");
        String departamento = reader.readLine();

        NodeList salesList = rootElement.getElementsByTagName("sale_record");


        for (int i = 0; i < salesList.getLength(); i++) {
        Element saleRecordElement = (Element) salesList.item(i);
        Element departmentElement = (Element) saleRecordElement.getElementsByTagName("department").item(0);


        if (departmentElement.getTextContent().equals(departamento)) {

        Element salesElement = (Element) saleRecordElement.getElementsByTagName("sales").item(0);

        double ventas = Double.parseDouble(salesElement.getTextContent());
        double nuevasVentas = ventas * (1 + porcentaje / 100);

        salesElement.setTextContent(String.format("%.2f", nuevasVentas));
        }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("new_sales.xml"));
        transformer.transform(source, result);

        System.out.println("Se genero un nuevo documento con los datos guardados.");
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
}