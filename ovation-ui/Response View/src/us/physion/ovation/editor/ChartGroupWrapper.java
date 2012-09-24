/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.editor;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.RectangleInsets;
import ovation.NumericData;
import ovation.NumericDataFormat;
import ovation.Ovation;

/**
 *
 * @author huecotanks
 */
class ChartGroupWrapper implements ResponseGroupWrapper
{
    DefaultXYDataset _ds;
    String _xAxis;
    String _yAxis;
    String _title;
    Map<String, Integer> dsCardinality;
    

    
    ChartGroupWrapper(DefaultXYDataset ds, String xAxis, String yAxis)
    {
        _ds = ds;
        _xAxis = xAxis;
        _yAxis = yAxis;
        dsCardinality = new HashMap<String, Integer>();
    }
    DefaultXYDataset getDataset(){ return _ds;}
    String getXAxis() { return _xAxis;}
    String getYAxis() { return _yAxis;}
    void setTitle(String s) {_title = s;}
    String getTitle() {return _title;}
    
    ChartPanel generateChartPanel()
    {
        JFreeChart chart = ChartFactory.createXYLineChart(getTitle(), getXAxis(), getYAxis(), getDataset(), PlotOrientation.VERTICAL, true, true, true);
        ChartPanel p = new ChartPanel(chart);

        chart.setTitle(convertTitle(getTitle()));
        chart.setPadding(new RectangleInsets(20, 20, 20, 20));
        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setLabelFont(new Font("Times New Roman", 1, 15));//new Font("timesnewroman", Font.LAYOUT_LEFT_TO_RIGHT, 15));
        plot.getRangeAxis().setLabelFont(new Font("Times New Roman", 1, 15));//new Font("timesnewroman", Font.LAYOUT_LEFT_TO_RIGHT, 15));
        return p;
    }
    
    private TextTitle convertTitle(String s)
    {
        return new TextTitle(s, new Font("Times New Roman", 1, 20));
    }
    
    public JPanel generatePanel()
    {
        return generateChartPanel();
    }
    
     protected void addXYDataset(ChartWrapper cw)
    {
        NumericData d = cw.getNumericData();
        
        if (d == null)
        {
            return; //TODO: handle URLResponses of numericData
        }
        long[] shape = d.getShape(); 
        double samplingRate = cw.getSamplingRate();
        long size = 1;
        for (int dimension = 0; dimension<shape.length; dimension++)
        {
            size = size*shape[dimension];
        }
        
        if (shape.length == 1)
        {
            int existingSeries = _ds.indexOf(cw.getName());
            int scale = 0;
            if (dsCardinality.containsKey(cw.getName()))
            {
                scale = dsCardinality.get(cw.getName());
            }
            String newName = cw.getName() + "-" + String.valueOf(scale+1);

            if (d.getDataFormat() == NumericDataFormat.FloatingPointDataType)
            {
 
                double[] floatingData = d.getFloatingPointData();
                double[][] data = new double[2][(int) size];

                if (scale >= 0)
                {
                    for (int i = 0; i < (int) size; ++i) {
                    data[1][i] = (floatingData[i]);
                    data[0][i] = i / samplingRate;
                    }
                }else{
                    for (int i = 0; i < (int) size; ++i) {
                    data[1][i] = (floatingData[i] + _ds.getYValue(existingSeries, i)*scale)/(scale +1);
                    data[0][i] = i / samplingRate;
                    } 
                }
                
                dsCardinality.put(cw.getName(), scale + 1);

                if (existingSeries >= 0) {
                    _ds.addSeries(newName, data);
            
                } else {
                    _ds.addSeries(cw.getName(), data);
                }

            }
            else if (d.getDataFormat() == NumericDataFormat.SignedFixedPointDataType)
            {
                int[] integerData = d.getIntegerData();
                double[][] data = new double[(int) size][2];
                
                if (scale >= 0)
                {
                    for (int i = 0; i < (int) size; ++i) {
                    data[1][i] = (integerData[i]);
                    data[0][i] = i / samplingRate;
                    }
                }else{
                    for (int i = 0; i < (int) size; ++i) {
                    data[1][i] = (integerData[i] + _ds.getYValue(existingSeries, i)*scale)/(scale +1);
                    data[0][i] = i / samplingRate;
                    } 
                }
                dsCardinality.put(cw.getName(), scale + 1);

                if (existingSeries >= 0) {
                    _ds.addSeries(newName, data);
            
                } else {
                    _ds.addSeries(cw.getName(), data);
                }
            }
            else if (d.getDataFormat() == NumericDataFormat.UnsignedFixedPointDataType)
            {
                long[] longData = d.getUnsignedIntData();
                double[][] data = new double[(int) size][2];
                
                if (scale >= 0)
                {
                    for (int i = 0; i < (int) size; ++i) {
                    data[1][i] = (longData[i]);
                    data[0][i] = i / samplingRate;
                    }
                }else{
                    for (int i = 0; i < (int) size; ++i) {
                    data[1][i] = (longData[i] + _ds.getYValue(existingSeries, i)*scale)/(scale +1);
                    data[0][i] = i / samplingRate;
                    } 
                }
                dsCardinality.put(cw.getName(), scale + 1);

                if (existingSeries >= 0) {
                    _ds.addSeries(newName, data);
            
                } else {
                    _ds.addSeries(cw.getName(), data);
                }
                
                _ds.addSeries(cw.getName(), data);
            }
            
            else{
                Ovation.getLogger().debug("NumericData object has unknown type: " + d.getDataFormat());
            }
        }
    }

    @Override
    public boolean shouldAdd(ResponseWrapper r) {
        if (r instanceof ChartWrapper)
        {
            //if units match
            ChartWrapper cw = (ChartWrapper) r;
            if (cw.getXUnits().equals(_xAxis) && cw.getYUnits().equals(_yAxis))
                return true;
        }
        return false;
    }

    @Override
    public void add(ResponseWrapper r) {
        if (r instanceof ChartWrapper)
        {
            String preface = "Aggregate responses: ";
            addXYDataset((ChartWrapper)r);
            String name = "";
            if (getTitle().startsWith(preface))
            {
                name = getTitle().substring(preface.length());
            } else{
                name = getTitle();
            }
            setTitle(preface + name + ", " + ((ChartWrapper)r).getName());
        }
    }
}