package interfaceJGAL;

import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineGraph extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public LineGraph(double[] bestFitness, double[] averageFitness) {
		this.setBounds(0, 0, 460, 270);
		final XYDataset dataset= createDataSet(bestFitness, averageFitness);
		final JFreeChart chart= createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(470, 270));
		this.add(chartPanel);
	}

	private XYDataset createDataSet(double[] bestFitness, double[] averageFitness) {
		final XYSeries series1 = new XYSeries(GAL_GUI.language.Results[1]);
		for(int i=0;i<bestFitness.length;i++)
			series1.add(i,bestFitness[i]);

        final XYSeries series2 = new XYSeries(GAL_GUI.language.Results[4]);
        for(int i=0;i<bestFitness.length;i++)
			series2.add(i,averageFitness[i]);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
                
        return dataset;
	}

	private JFreeChart createChart(XYDataset dataset) {
		 final JFreeChart chart = ChartFactory.createXYLineChart(
			GAL_GUI.language.Results[5],	// chart title
			GAL_GUI.language.Results[6],	// x axis label
			GAL_GUI.language.Results[7],	// y axis label
            dataset,						// data
            PlotOrientation.VERTICAL,
            true,							// include legend
            true,							// tooltips
            false							// urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
	}

}
