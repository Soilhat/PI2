package service.csv;


import Model.Move;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public interface CsvLittleMoveService
{
	List<Move> extractContent(InputStream is) throws IOException;
	Map<String, Integer> extractHeader(InputStream is) throws IOException;
}
