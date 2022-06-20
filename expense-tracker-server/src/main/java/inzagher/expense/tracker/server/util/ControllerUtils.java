package inzagher.expense.tracker.server.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class ControllerUtils {
    private ControllerUtils() {}

    public static ResponseEntity<InputStreamResource> createFileResponse(
            InputStream stream, String fileName) throws IOException {
        var contentDisposition = "attachment; filename=" + fileName;
        var cacheControl = "no-cache, no-store, must-revalidate";
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
        header.add("Cache-Control", cacheControl);
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return org.springframework.http.ResponseEntity.ok().headers(header)
                .contentLength(stream.available())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }
}
