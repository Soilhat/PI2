package service.csv.impl;

import Model.Move;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import service.csv.CsvLittleMoveService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CsvLittleMoveServiceImpl implements CsvLittleMoveService
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss,SSS");
	@Override
	public List<Move> extractContent(InputStream is) throws IOException
	{	
        List<Move> results = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try(
				InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
				CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
			)
        {
        	Map<String, Integer> headers = createHeader(csvReader.readNext());
        	
        	String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) 
            {
            	Move element = createRow(nextLine, headers);
            	results.add(element);
            }
        }
		return results;
	}

	
	@Override
	public Map<String, Integer> extractHeader(InputStream is) throws IOException
	{
       try(
				InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
				CSVReader csvReader = new CSVReader(reader);
			)
        {
			return createHeader(csvReader.readNext());
        }
	}

	private Map<String, Integer> createHeader(String[] headers)
	{
		Map<String, Integer> headerMap = new HashMap<>();
        
		for (int i = 0; i < headers.length; i++)
		{
			headerMap.put(headers[i], Integer.valueOf(i));
		}

		return headerMap;
	}
	
	private Map<String, Integer> filteredHeader(Map<String, Integer> headerMap, String[] fields)
	{
		Map<String, Integer> result = new HashMap<>();
        
		for (String field : fields)
		{
			if(headerMap.containsKey(field))
			{
				result.put(field, headerMap.get(field));
			}
		}

		return result;
	}
	
	private Move createRow(String[] values, Map<String, Integer> headers)
	{
		Move move = new Move();

		move.setTime(new Timestamp(Long.parseLong(values[headers.get("Timestamp")])));

        move.setAngle(Integer.parseInt(values[headers.get("Angle")]));


        move.setRadius(Integer.parseInt(values[headers.get("Radius")]));

        move.setHeight(Integer.parseInt(values[headers.get("Height")]));

        move.setLoad(Integer.parseInt(values[headers.get("Load")]));

        move.setDate(new Date(move.getTime().getTime()));

        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        try
		{
			move.setX(format.parse(values[headers.get("x")]).doubleValue());
	        move.setY(format.parse(values[headers.get("y")]).doubleValue());
	        move.setZ(format.parse(values[headers.get("z")]).doubleValue());
	        move.setDistance(format.parse(values[headers.get("distance (m)")]).doubleValue());
	        move.setVitesse(format.parse(values[headers.get("vitesse (m/sec)")]).doubleValue());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

    	return move;
	}
}
