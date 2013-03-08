package com.amadt.fitrack.controller;

import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.amadt.fitrack.database.WODHistory;
import com.amadt.fitrack.database.WODHistoryManager;

public class GraphLine {
    
	String name;
	public GraphLine(String name){
		this.name = name;
	}
	public Intent getWODHistoryIntent(Context context, String name){	
		WODHistoryManager whm = new WODHistoryManager(context);
		whm.addWODToHistory("Randy", System.currentTimeMillis(), 150, "we");
		whm.addWODToHistory("Randy", System.currentTimeMillis()+1000000, 90, "we");
		List<WODHistory> Hist = whm.getAllHistory("Randy");
			
	
		TimeSeries series = new TimeSeries("line1");
		for(int i=0;i<Hist.size(); i++){
			series.add(new Date(Hist.get(i).getDate()), Hist.get(i).getDuration());
			//series.add(x[i],y[i]);
		}
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		
		mRenderer.setPointSize(5f);
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setPanEnabled(true, false);
	    mRenderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
	    mRenderer.setZoomRate(1.05f);
	    mRenderer.setXLabels(12);
	    mRenderer.setYLabels(10);
	    mRenderer.setXTitle("DAYS");
	    mRenderer.setYTitle("DURATIONS");
	    mRenderer.setLabelsColor(Color.WHITE);
	    mRenderer.setShowGrid(true);
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(3f);
		renderer.setColor(Color.YELLOW);
		mRenderer.addSeriesRenderer(renderer);
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, name);
		
		return intent;
	}
}
