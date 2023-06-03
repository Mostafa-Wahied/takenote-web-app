package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileImportService {

    public List<Student> importStudents(MultipartFile file, boolean hasHeader) throws IOException {
        List<Student> students = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        if (hasHeader && rowIterator.hasNext()) {
            rowIterator.next(); // Skip header row
        }
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String firstName = row.getCell(0).getStringCellValue();
            String lastName = row.getCell(1).getStringCellValue();
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);

            students.add(student);
        }
        workbook.close();
        inputStream.close();

        return students;
    }
}