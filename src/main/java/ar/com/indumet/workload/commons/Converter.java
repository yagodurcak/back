package ar.com.indumet.workload.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class Converter {
    private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());
    private static ObjectMapper mapper= new ObjectMapper();

    /**
     * Mapea una instancia de una clase a una instancia de otra clase
     * @param from El objeto original
     * @param toClass La clase destino
     * @param <T> Clase destino parametrizable
     * @return Instacia de clase destino
     */
    public static<T> T convertValue(Object from, Class<T> toClass){
        try{
            if(toClass.equals(from.getClass())){
                return (T)from;
            }
            mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false); //FAIL_ON_UNKNOWN_PROPERTIES Objeto Origen tiene mas atributos que el objeto destino y esto permite haciendo solo para las propiedades que se parezcan
            return mapper.convertValue(from, toClass);
        }catch(Exception e){
            e.printStackTrace(System.out);
            LOGGER.severe("Error al intentar mappear un objeto de la clase " + from.getClass() + "a la clase "+ toClass+". \n Mensaje de error: "+e.getCause().getMessage());
            return null;
        }
    }

    /**
     * Mapea una lista de objetos de tipo A a una lista de objetos de tipo B
     * @param from La lista original
     * @param toClass La clase de objetos de la lista resultante
     * @param <T> Clase destino parametrizable
     * @return Lista de objetos de tipo B
     */
    public static <T> List<T> convertValue(List from, Class<T> toClass){
        try{
            List<T> list= new ArrayList<T>();
            for (Object element: from) {

                list.add(convertValue(element,  toClass));
            }
            return list;
        }catch(Exception e){
            e.printStackTrace(System.out);
            LOGGER.severe("Error al intentar mappear un objeto de la clase " + from.getClass() + "a la clase "+ toClass+". \n Mensaje de error: "+e.getCause().getMessage());
            return null;
        }
    }

    //De iterable a lista
    public static <T> List<T> convertValue(Iterable from, Class<T> toClass){
        try{
            List<T> list= new ArrayList<T>();
            from.forEach(element -> {
                list.add(convertValue(element,  toClass));
            });
            return list;
        }catch(Exception e){
            e.printStackTrace(System.out);
            LOGGER.severe("Error al intentar mappear un objeto de la clase " + from.getClass() + "a la clase "+ toClass+". \n Mensaje de error: "+e.getCause().getMessage());
            return null;
        }
    }

    /**
     * Conviernto un JSON String a una instancia de una clase A
     * @param from El JSON string que se quiere instanciar
     * @param toClass La clase A que representa al contenido del JSON String
     * @param <T> Clase destino parametrizable
     * @return Instancia de clase A
     */
    public static<T> T convertValue(String from, Class<T> toClass){
        try{
            if(toClass.equals(String.class)){
                return (T)from;
            }
            mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(from, toClass);
        }catch(Exception e){
            e.printStackTrace(System.out);
            LOGGER.severe("Error al intentar mappear un objeto de la clase " + from.getClass() + "a la clase "+ toClass+". \n Mensaje de error: "+e.getCause().getMessage());
            return null;
        }
    }

    /**
     * Convierte una instancia de clase a un JSON String
     * @param fromObject Objeto a convertir
     * @param <T> Clase de objeto origen parametrizable
     * @return JSON String que representa al objeto de entrada
     */
    public static<T> String convertValue(T fromObject){
        try{
            if(fromObject.equals(String.class)){
                return fromObject.toString();
            }
            mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.writeValueAsString(fromObject);
        }catch(Exception e) {
            e.printStackTrace(System.out);
            LOGGER.severe("Error al intentar convertir un objeto de la clase " + fromObject + "a un JSON String. \nMensaje de error: " + e.getCause().getMessage());
            return null;
        }
    }

    /**
     * Conviernte un XML string a una instancia de una clase (unmarshalling)
     * @param xml_string El XML string a convertir
     * @param toClass La clase de destino
     * @return Objeto instancia de clase destino
     * @throws JAXBException Excepcion de unmarshalling
     */
    public static Object unmarshal(String xml_string, Class toClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(toClass);
        Unmarshaller um = context.createUnmarshaller();
        StringReader reader = new StringReader(xml_string);
        return um.unmarshal(reader);
    }

    /**
     * Conviernte un
     * @param objectToConvert
     * @param fromClass
     * @return
     * @throws JAXBException Excepcion de unmarshalling
     */
    public static String marshal(Object objectToConvert, Class fromClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(fromClass);
        Marshaller m = null;
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter result = new StringWriter();
        m.marshal(objectToConvert, result);
        return result.toString();
    }

    /**
     * Convierte un Node de org.w3c.dom a un XML string
     * @param node Instancia org.w3c.dom.Node
     * @return XML string que represetan al Node
     * @throws TransformerException Excepcion de error en la transformacion
     */
    public static String xmlGenericToString(Node node) throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter sw = new StringWriter();
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }
}
