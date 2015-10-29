package wordtools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class FileToStringBuilder {

	public FileToStringBuilder(){
		
	}
	
	public String getStringFromFile(String path){
		List<String> lines = null;
		String wholeString = "";
		try {
			lines = Files.readAllLines(Paths.get(path),
					StandardCharsets.UTF_8);
			for (int i = 0; i < lines.size(); i++) {
				wholeString= wholeString+ "\n" +lines.get(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wholeString;
	}
	
}
