package org.psc.workerws.files;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.LongStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class FilesLogic {

    private static final int ENTRIES_PER_ROUND = 50;
    private static final int ROUNDS = 10;

    public Path createRandomZipFile() {
        Path file = Try.of(() -> Files.createTempFile(
                "TEMP_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_nnnnnnnnn")), ".csv"))
                .getOrElse(() -> null);

        for (int i = 0; i < ROUNDS; i++) {
            LongStream.range(i * ENTRIES_PER_ROUND, i * ENTRIES_PER_ROUND + ENTRIES_PER_ROUND)
                    .mapToObj(l -> new Record(l, RandomStringUtils.randomAlphanumeric(5), LocalDateTime.now(),
                            RandomStringUtils.randomAlphanumeric(200)))
                    .forEach(record -> Try.run(() -> Files.writeString(file, record.toCsvRecord().concat("\n"),
                            StandardOpenOption.APPEND)));
        }


        return createZipFile(file);
    }

    private Path createZipFile(Path csvFile) {
        Path zipFile = Try.of(() -> Files.createTempFile(
                StringUtils.removeEnd(csvFile.getFileName().toString(), ".csv"), ".zip"))
                .getOrElse(() -> null);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile.toFile()))) {
            ZipEntry entry = new ZipEntry("randomEntries.csv");
            entry.setSize(csvFile.toFile().length());
            zos.putNextEntry(entry);
            zos.write(Files.readAllBytes(csvFile));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Try.run(() -> Files.delete(csvFile));
        return zipFile;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Record {

        private Long id;
        private String name;
        private LocalDateTime changedAt;
        private String value;

        String toCsvRecord() {
            var stringBuilder = new StringBuilder(500);
            ReflectionUtils.doWithFields(this.getClass(), field -> stringBuilder.append(field.get(this)).append(';'));
            return stringBuilder.toString();
        }
    }

}
