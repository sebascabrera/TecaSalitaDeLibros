package com.salitadelibros.salita.controller;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.salitadelibros.salita.dtos.*;
import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.services.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PdfControlador {

    @Autowired
    private LibroServicio libroServicio; // Asegúrate de tener un bean para LibroServicio

    @GetMapping("/libros-pdf")
    public void generarPDF(HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=libros.pdf");

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(response.getOutputStream()));
        Document document = new Document(pdfDocument, PageSize.A4);

        List<LibroDTO> libros = getLibrosOrdenadosPorTitulo();

        Paragraph header = new Paragraph("Listado de libros")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20);
        document.add(header);


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateTimeString = now.format(formatter);


        Paragraph dateTime = new Paragraph("Fecha y hora de generación: " + dateTimeString)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(10);
        document.add(dateTime);

        Table table = new Table(new float[]{2, 6, 3, 4, 5, 6, 6, 4, 4});

// Agregar encabezados de columna
        Cell titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200)); // Color gris suave
        titleCell.add(new Paragraph("ID"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("Título"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("Género"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("Categorías"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("Editorial"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("Autores"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("Ilustradores"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("Fecha de Edición"));
        table.addCell(titleCell);
        titleCell = new Cell().setBackgroundColor(new DeviceRgb(200, 200, 200));
        titleCell.add(new Paragraph("ISBN"));
        table.addCell(titleCell);


        for (LibroDTO libro : libros) {
            table.addCell(libro.getId().toString());
            table.addCell(libro.getTitulo());
            table.addCell(libro.getGenero().toString());
            table.addCell(libro.getCategorias().stream().map(CategoriaDTO::getPalabraCategoria).collect(Collectors.joining(", ")));
            table.addCell(libro.getEditorial().getNombreEditorial());
            table.addCell(libro.getAutores().stream().map(autor -> autor.getNombreAutor() + " " + autor.getApellidoAutor()).collect(Collectors.joining(", ")));
            table.addCell(libro.getIlustradores().stream().map(ilustrador -> ilustrador.getNombreIlustrador() + " " + ilustrador.getApellidoIlustrador()).collect(Collectors.joining(", ")));
            table.addCell(libro.getFechaDeEdicion());
            table.addCell(libro.getIsbn());
        }

        document.add(table);

        document.close();
    }
    private List<LibroDTO> getLibrosOrdenadosPorTitulo() {
        List<LibroDTO> libros = libroServicio.getLibros().stream()
                .map(LibroDTO::new)
                .collect(Collectors.toList());
        libros.sort(Comparator.comparing(LibroDTO::getTitulo));

        return libros;
    }
}
