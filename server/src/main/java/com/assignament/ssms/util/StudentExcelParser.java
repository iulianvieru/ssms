package com.assignament.ssms.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Sheet;




import com.assignament.ssms.model.Student;

public class StudentExcelParser {
	
	

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Student> studentList;

	public StudentExcelParser(List<Student> studentList) {
		this.studentList = studentList;
		workbook = new XSSFWorkbook();
	}

	
	/**
	 * TODO: validate data	 
	 */
	public StudentExcelParser(MultipartFile file) {
		try {
			List<Student> importedStudents= new ArrayList<Student>();
			Workbook workbook = new XSSFWorkbook(file.getInputStream());

			Sheet sheet = workbook.getSheet("Students");
			Iterator<Row> rows = sheet.iterator();

			// List<Stude> tutorials = new ArrayList<Tutorial>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Student student = new Student();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						student.setId((long) currentCell.getNumericCellValue());
						break;

					case 1:
						student.setFirstName(currentCell.getStringCellValue());
						break;

					case 2:
						student.setLastName(currentCell.getStringCellValue());
						break;

					case 3:
						student.setBirthDate(currentCell.getDateCellValue());
						break;

					default:
						break;
					}

					cellIdx++;
				}

				importedStudents.add(student);
			}

			workbook.close();
			this.studentList=importedStudents;

			// return tutorials;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Students");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Student ID", style);
		createCell(row, 1, "First Name", style);
		createCell(row, 2, "Last Name", style);
		createCell(row, 3, "Birth Date", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Boolean) {

			cell.setCellValue((Boolean) value);
		} else if (value instanceof Date) {
			
			cell.setCellValue((Date) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		CellStyle dateStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		dateStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy/mm/dd h:mm"));
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Student student : studentList) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, student.getId(), style);
			createCell(row, columnCount++, student.getFirstName(), style);
			createCell(row, columnCount++, student.getLastName(), style);
			
			createCell(row, columnCount++, student.getBirthDate(), dateStyle);

		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}

	public List<Student> getStudentList() {
		return studentList;

	}
	

}
