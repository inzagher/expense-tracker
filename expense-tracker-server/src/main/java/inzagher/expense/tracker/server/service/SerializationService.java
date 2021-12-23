package inzagher.expense.tracker.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class SerializationService {
    public byte[] serializeAndZip(Object object, String zipEntryName) {
        log.info("Serialize data to zip archive");
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ZipOutputStream zos = new ZipOutputStream(bos)) {
                zos.putNextEntry(new ZipEntry(zipEntryName));
                JAXBContext jaxbContext = createJaxbContext(object.getClass());
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(object, zos);
                zos.closeEntry();
            }
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T deserializeZippedData(Class<T> target, byte[] data, String zipEntryName) {
        log.info("Deserialize data from zip archive");
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            try (ZipInputStream zis = new ZipInputStream(bis)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().equals(zipEntryName)) {
                        JAXBContext jaxbContext = createJaxbContext(target);
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        return (T)unmarshaller.unmarshal(zis);
                    }
                }
                throw new RuntimeException("Zip entry not found.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JAXBContext createJaxbContext(Class<?> c) {
        try { return JAXBContext.newInstance(c); }
        catch(JAXBException e) { throw new RuntimeException(e); }
    }
}
