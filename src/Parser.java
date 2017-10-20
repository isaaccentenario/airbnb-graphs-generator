import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.opencsv.*;;

public class Parser {
	
	File arquivo;
	ArrayList<String[]> dados = new ArrayList<String[]>();
	
	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}
	
	public void parse() throws IOException
	{
		@SuppressWarnings("deprecation")
		CSVReader reader = new CSVReader( new FileReader( this.arquivo.getPath() ), ',','"', 1 );
		
		String[] nextLine;
		
		while( ( nextLine = reader.readNext() ) != null ) {
			this.dados.add( nextLine );
		}
	}
	
	public ArrayList getDados()
	{
		return this.dados;
		
	}
}
