import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.data.xy.XYDataset;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Interface {
	JFrame frame = new JFrame("Desafio POO");
	JPanel header = new JPanel();
	JPanel painel = new JPanel();
	JFrame janelaSelecaoArquivo = new JFrame();
	JFileChooser arquivo = new JFileChooser();
	JComboBox opcoes;
	File csv;
	
	ArrayList<String[]> linhas;
	
	int tamX = 640,tamY = 480;
	
	String[] valoresOpcoes = { 
			"Política de cancelamento", 
			"Valores de Pontuação", 
			"Noites mínimas",
			"Quartos" 
		};
	int[] colunas = {
				87,
				82,
				64,
				53
	};
	
	Facebook face;
	
	public Interface()
	{
		this.face = new Facebook();
		
		this.face.setAppid("")
			.setChavePrivada("")
			.setToken("")
			.addTag("100001383083662", "Plácido")
			.setStatus("Atividade da matéria de Programação Orientada a Objetos, em Java. Ignorem :)");
	}
	
	public void setTamanhoJanela( int x, int y ) {
		this.tamX = x;
		this.tamY = y;
	}
	
	public void mostrarJanelaPrincipal() 
	{
		this.frame.setSize(this.tamX,this.tamY);
		//this.frame.setLayout(new GridLayout(3,1));
//		this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		this.painel.setAlignmentY(10);
		
		JLabel titulo = new JLabel("Desafio POO");
		titulo.setFont(new Font("Arial",Font.BOLD, 18));
		titulo.setVerticalAlignment(JLabel.TOP);
		
		this.header.add(titulo);
		this.frame.add(this.header);
		
		JButton selecionarArquivo = new JButton("Selecionar Arquivo CSV");
		
		this.painel.add(selecionarArquivo);
		
		Interface principal = this;
		
		this.opcoes = new JComboBox( this.valoresOpcoes );
		this.painel.add( opcoes );
		
		JButton gerarGrafico = new JButton("Gerar gráfico");
		this.painel.add( gerarGrafico );
		
		selecionarArquivo.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				principal.janelaSelecionarArquivo();
			}
		});
		
		this.arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		this.arquivo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				principal.csv = principal.arquivo.getSelectedFile();
				principal.janelaSelecaoArquivo.dispose();
				
				try {
					principal.parsear();
					//principal.mostrarGrafico();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		gerarGrafico.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent arg0) {
				principal.mostrarGrafico();
			}
		});
		
		this.frame.add(this.painel);
		this.frame.setVisible(true);
	}
	
	public void janelaSelecionarArquivo() 
	{
		janelaSelecaoArquivo.setSize(300, 300);
		
		janelaSelecaoArquivo.setLayout(new CardLayout());
		
		this.arquivo.setFileFilter(new FileNameExtensionFilter("CSV","csv"));
		janelaSelecaoArquivo.add(this.arquivo);
		
		janelaSelecaoArquivo.setVisible(true);
	}
	
	public void mostrarGrafico()
	{
		int coluna = this.colunas[ this.opcoes.getSelectedIndex() ];
		
		int qtd_linhas = this.linhas.size();
		
		String dados[] = new String[ qtd_linhas ];
		int contadores[] = new int[ qtd_linhas ];
		
		for( int i = 0; i < qtd_linhas; i++ ) {
			String[] linha = this.linhas.get(i);
			if( linha.length > coluna && linha[ coluna ] != null ) {
				int indice = this.buscarArray( linha[ coluna ], dados );
				
				if( indice >= 0  ) {
					contadores[ indice ] += 1;
				} else {
					loopquebra:
					for( int j = 0; j < qtd_linhas; j++ ) {
						if( dados[j] == null) {
							dados[j] = linha[ coluna ];
							contadores[j] = 1;
							break loopquebra;
						}
					}
					
				}
			}
		}
		
//		int d = 0;
//		for( String dado : dados ) {
//			if( null == dado ) break;
//			System.out.println(dado + " = " + contadores[d] );
//			d++;
//		}
		
		Grafico grafico = new Grafico();
		grafico.setDados(dados, contadores);
		
		grafico.setTipo3D();	
		grafico.gerarGrafico();
		
		ChartFrame gframe = new ChartFrame("Gráfico", grafico.getGrafico() );
		
		gframe.setSize( 500, 350 );
		gframe.setVisible(true);
		
		JButton btnCompartilhar = new JButton("Compartilhar");
		gframe.add(btnCompartilhar);
		
		Interface principal = this;
		
		btnCompartilhar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File chart = new File("chart.png");
				try {
					ChartUtilities.saveChartAsPNG( chart, grafico.getGrafico(), 400, 300 );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				principal.face.setImagem( "Política de Cancelamento", chart );
				
				try {
					principal.face.post();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}
	
	public void parsear() throws IOException
	{
		Parser parser = new Parser();
		
		parser.setArquivo(this.csv);
		parser.parse();
		
		this.linhas = parser.getDados();
	}
	
	private int buscarArray(String valor, String arr[] ) {
		int i = 0;
		for( String dado: arr ) {
			if( valor.equalsIgnoreCase( dado ) ) return i;
			i++;
		}
		return -1;
	}
}
