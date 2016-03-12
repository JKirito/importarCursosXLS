package funciones;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPOI {

	// NO es un espacio en blanco (tampoco un "enter")
	public static String CARACTER_REEMPLAZAR = ' ' + "";

	public static Iterator<Row> getFilasDelArchivo(InputStream inputStream, boolean is2007plus) throws IOException {

		Workbook myWorkBook;

		if (is2007plus)
			// Finds the workbook instance for XLSX file
			myWorkBook = new XSSFWorkbook(inputStream);
		else
			myWorkBook = new HSSFWorkbook(inputStream);

		// Return first sheet from the XLSX workbook
		Sheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();

		myWorkBook.close();

		return rowIterator;
	}

	public static String valorCeldaToString(Cell cell) {
		if (cell == null) {
			return null;
		}
		String celdaString = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			celdaString = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			celdaString = new BigDecimal(cell.getNumericCellValue()) + "";
			break;
		default:
			celdaString = null;
		}
		return celdaString == null ? celdaString : celdaString.trim();
	}

	public static Integer valorCeldaToInt(Cell cell) {
		BigInteger cellBigInt = valorCeldaToBigInt(cell);
		return cellBigInt == null ? null : cellBigInt.intValue();
	}

	public static BigInteger valorCeldaToBigInt(Cell cell) {
		if (cell == null) {
			return null;
		}
		BigInteger celdaInteger = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			try {
				celdaInteger = new BigInteger(cell.getStringCellValue().replaceAll("\\.|,|-|\\s|" + CARACTER_REEMPLAZAR, ""));
			} catch (Exception e) {
				celdaInteger = null;
			}
			break;
		case Cell.CELL_TYPE_NUMERIC:
			celdaInteger = new BigDecimal(cell.getNumericCellValue()).toBigInteger();
			break;
		default:
			celdaInteger = null;
		}
		return celdaInteger;
	}

	public static Timestamp valorCeldaToTimestamp(Cell cell) throws Exception {
		Date cellDate = valorCeldaToDate(cell);
		return cellDate == null ? null : new Timestamp(cellDate.getTime());
	}
	
    public static String valorCeldaToHora(Cell cell) throws Exception {
    	Date cellDate = valorCeldaToDate(cell);
    	if(cellDate == null)
    		return "A confirmar";
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
        return formateador.format(cellDate);
    }

	public static Date valorCeldaToDate(Cell cell) throws Exception {
		if (cell == null) {
			return null;
		}
		Date celdaDate = null;

		try {
			celdaDate = cell.getDateCellValue();
		} catch (Exception e) {
			// celdaDate = null;
			throw e;
		}

		return celdaDate;
	}

}
