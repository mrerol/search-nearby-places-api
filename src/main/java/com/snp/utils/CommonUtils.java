package com.snp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

  private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
  private static final ObjectMapper mapper = new ObjectMapper();

  public static <T> String marshalEntity(T entity) {
    try {
      if (Objects.isNull(entity)) {
        return "";
      }
      Class<?> theClass = entity.getClass();
      JAXBContext jaxbContext = JAXBContext.newInstance(theClass);
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
      marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
      StringWriter stringWriter = new StringWriter();
      String localPart = theClass.getAnnotation(XmlType.class).name();
      if (theClass.isAnnotationPresent(XmlRootElement.class)) {
        localPart = theClass.getAnnotation(XmlRootElement.class).name();
      }
      QName qName =
          new QName(theClass.getPackage().getAnnotation(XmlSchema.class).namespace(), localPart);
      JAXBElement<T> rootJAXB = new JAXBElement<>(qName, (Class) theClass, null, entity);
      marshaller.marshal(rootJAXB, stringWriter);
      return stringWriter.toString();
    } catch (Exception e) {
      logger.error("Could not marshall entity!", e);
      return "";
    }
  }

  public static <T> T unmarshallEntity(File file, Class<T> entityClass) {
    T entity = null;
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
      Unmarshaller marshaller = jaxbContext.createUnmarshaller();
      entity = marshaller.unmarshal(new StreamSource(file), entityClass).getValue();
    } catch (Exception e) {
      logger.error("Could not unmarshall entity!", e);
    }
    return entity;
  }

  public static <T> T unmarshallEntity(String entityString, Class<T> entityClass) {
    T entity = null;
    try {
      if (entityClass == entityString.getClass()) {
        entity = (T) entityString;
      } else {
        JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
        Unmarshaller marshaller = jaxbContext.createUnmarshaller();
        entity = (T) marshaller.unmarshal(new StringReader(entityString));
      }
    } catch (Exception e) {
      logger.error("Could not unmarshall entity!", e);
    }
    return entity;
  }

  public static String toJson(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      logger.error("Json object could not converted to string!", e);
      return "";
    }
  }
}
