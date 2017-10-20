import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.*;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.*;
import org.jfree.data.category.*;
import org.jfree.data.function.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafico {
	
	String[] dadosEntrada;
	int[] contadores;
	JFreeChart grafico;
	
	int tipo = 4; // 1 = linha, 2 = torta, 3 = barra, 4 = 3d
	
	public void setDados(String[] dados, int[] quantidades ) {
		this.dadosEntrada = dados;
		this.contadores = quantidades;
	}
	
	public void gerarGrafico()
	{
		switch (this.tipo) {
			case 1:
				DefaultCategoryDataset dados = new DefaultCategoryDataset();
				
				for( int i = 0; i < this.dadosEntrada.length; i++ ) {
					//dados.addValue(this.dadosEntrada[i], this.contadores[i] );
				}
				
				break;
			case 4:
				 	DefaultPieDataset pieDataset = new DefaultPieDataset();
				   
				 	for( int i = 0; i < this.dadosEntrada.length; i++ ) {
				 		if( this.dadosEntrada[i] == null || this.contadores[i] < 1 ) continue;
				 		pieDataset.setValue( this.dadosEntrada[i], this.contadores[i] );
				 	}
				    
				    JFreeChart chart = ChartFactory.createPieChart3D(
				        "Gráfico 3D", pieDataset, true, true, true );
				    
//				    //Render the frame
//				    ChartFrame chartFrame = new ChartFrame("Simple 3D Pie Chart", chart);
//				    chartFrame.setVisible(true);
//				    chartFrame.setSize(400, 300);
				    
				    this.grafico = chart;
				break;
			
			default:;
				break;
		}
	}
	
	public void setTipoTorta()
	{
		this.tipo = 2;
	}
	
	public void setTipoBarra()
	{
		this.tipo = 3;
	}
	
	public void setTipoLinha()
	{
		this.tipo = 1;
	}
	
	public void setTipo3D() 
	{
		this.tipo = 4;
	}
	
	public JFreeChart getGrafico() {
		//return this.grafico;
		return this.grafico;
	}
}
