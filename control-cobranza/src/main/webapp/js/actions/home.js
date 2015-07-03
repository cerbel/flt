$(document).ready(function(){
	
	
	
	// Radialize the colors
    Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
        return {
            radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
            stops: [
                [0, color],
                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
            ]
        };
    });
    
	
    var chartDetailActualPercent = new Highcharts.Chart({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            renderTo: 'container-std-actual-percent'
        },
        title: {
            text: 'Actual Stages, Cost Detail Distribution'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    },
                    connectorColor: 'silver'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Cost share',
            data: []
        }]
    });
    
    
    var chartDetailForecastPercent = new Highcharts.Chart({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            renderTo: 'container-std-forecast-percent'
        },
        title: {
            text: 'Forecast Stages, Cost Detail Distribution'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    },
                    connectorColor: 'silver'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Cost share',
            data: []
        }]
    });
    
    
	


    // Build the chart
    var chartDetailCost = new Highcharts.Chart({
    	chart: {
                type: 'column',
                renderTo: 'container-std-detail'
            },
            title: {
                text: 'Actual VS Forecast Detail Cost'
            },
            xAxis: {
                categories: []
            },
            yAxis: {
                allowDecimals: false,
                labels: {
                	enabled: true
                },
                title: {
                    text: 'Cost Stage'
                }
            },
            credits: {
                enabled: false
            },
	        series: [{
	            name: 'Actual',
	            data: []
	        }, {
	            name: 'Forecast',
	            data: []
	        }],
	         legend: {
	             enabled: true
	         },
	         plotOptions: {
	             column: {
	                 dataLabels: {
	                     enabled: true,
	                     color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || '#fff'
	                 }
	             }
	         }
        });
    
    
    var chartSubTypeCost = new Highcharts.Chart({
    	chart: {
                type: 'column',
                renderTo: 'container-std-subTypeCost'
            },
            title: {
                text: 'Actual VS Forecast Sub Type Cost'
            },
            xAxis: {
                categories: []
            },
            yAxis: {
                allowDecimals: false,
                labels: {
                	enabled: true
                },
                title: {
                    text: 'Cost Stage'
                }
            },
            credits: {
                enabled: false
            },
	        series: [{
	            name: 'Actual',
	            data: []
	        }, {
	            name: 'Forecast',
	            data: []
	        }],
	         legend: {
	             enabled: true
	         },
	         plotOptions: {
	             column: {
	                 dataLabels: {
	                     enabled: true,
	                     color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || '#fff'
	                 }
	             }
	         }
        });
    
    
    var chartTypeCost = new Highcharts.Chart({
    	chart: {
                type: 'column',
                renderTo: 'container-std-typeCost'
            },
            title: {
                text: 'Actual VS Forecast Type Cost'
            },
            xAxis: {
                categories: []
            },
            yAxis: {
                allowDecimals: false,
                labels: {
                	enabled: true
                },
                title: {
                    text: 'Cost Stage'
                }
            },
            credits: {
                enabled: false
            },
	        series: [{
	            name: 'Actual',
	            data: []
	        }, {
	            name: 'Forecast',
	            data: []
	        }],
	         legend: {
	             enabled: true
	         },
	         plotOptions: {
	             column: {
	                 dataLabels: {
	                     enabled: true,
	                     color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || '#fff'
	                 }
	             }
	         }
        });
    
    var chartSummaryFreights = new Highcharts.Chart({
		
        chart: {
            type: 'line',
            renderTo: 'container-summary-freights'
        },
        title: {
            text: 'Monthly Freights & Others'
        },
        subtitle: {
            text: 'Source: Merck.com'
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Freight & Others K USD$'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'Actual',
            data: []
        }, {
            name: 'Forecast',
            data: []
        }, {
            name: 'Budget',
            data: []
        }]
    });
	
    var chartSummaryDuties = new Highcharts.Chart({
		
        chart: {
            type: 'line',
            renderTo: 'container-summary-duties'
        },
        title: {
            text: 'Monthly Duties'
        },
        subtitle: {
            text: 'Source: Merck.com'
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Duties K USD$'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'Actual',
            data: []
        }, {
            name: 'Forecast',
            data: []
        }, {
            name: 'Budget',
            data: []
        }]
    });
	
    var chartSummaryUnits = new Highcharts.Chart({
    		
        chart: {
            type: 'line',
            renderTo: 'container-summary-units'
        },
        title: {
            text: 'Monthly Units'
        },
        subtitle: {
            text: 'Source: Merck.com'
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Units UND'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'Actual',
            data: []
        }, {
            name: 'Forecast',
            data: []
        }, {
            name: 'Budget',
            data: []
        }]
    });
	
    
    var chartSummaryFobs = new Highcharts.Chart({
		
        chart: {
            type: 'line',
            renderTo: 'container-summary-fobs'
        },
        title: {
            text: 'Monthly Transfer Price'
        },
        subtitle: {
            text: 'Source: Merck.com'
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Transfer Price K USD$'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'Actual',
            data: []
        }, {
            name: 'Forecast',
            data: []
        }, {
            name: 'Budget',
            data: []
        }]
    });
	
	var chartHomeUnits = new Highcharts.Chart({
    	
		chart: {
	        type: 'gauge',
	        plotBackgroundColor: null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: false,
	        renderTo: 'container-home-units'
	    },
	    title: {
	    	text: '<p style="font-size:15px;text-align:center;">Actual: 0 UND</p><p style="font-size:11px;text-align:center;"><strong>0 %</strong></p><p style="font-size:14px;text-align:center;"><strong>-0 UND</strong></p>',
	        useHTML: true
	    },
	    pane: {
	        startAngle: -150,
	        endAngle: 150,
	        background: [{
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#FFF'],
	                    [1, '#333']
	                ]
	            },
	            borderWidth: 0,
	            outerRadius: '109%'
	        }, {
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#333'],
	                    [1, '#FFF']
	                ]
	            },
	            borderWidth: 1,
	            outerRadius: '107%'
	        }, {
	            // default background
	        }, {
	            backgroundColor: '#DDD',
	            borderWidth: 0,
	            outerRadius: '105%',
	            innerRadius: '103%'
	        }]
	    },
	       
	    // the value axis
	    yAxis: {
	        min: -100,
	        max: 100,
	        
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto'
	        },
	        title: {
	            text: '%'
	        },
	        plotBands: [{
	            from: -100,
	            to: 0,                
	            color: '#55BF3B' // red
	        },  {
	            from: 0,
	            to: 100,
	            color: '#DF5353' // green
	        }]
	    },
	
	    series: [{
	        name: 'Units',
	        data: [0]
	    }] 
	});
	
	
	var chartHomeFobs = new Highcharts.Chart({
    	
		chart: {
	        type: 'gauge',
	        plotBackgroundColor: null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: false,
	        renderTo: 'container-home-fobs'
	    },
	    title: {
	    	text: '<p style="font-size:15px;text-align:center;">Actual: 0 K USD$</p><p style="font-size:11px;text-align:center;"><strong>0 %</strong></p><p style="font-size:14px;text-align:center;"><strong>0 K</strong></p>',
	        useHTML: true
	    },
	    pane: {
	        startAngle: -150,
	        endAngle: 150,
	        background: [{
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#FFF'],
	                    [1, '#333']
	                ]
	            },
	            borderWidth: 0,
	            outerRadius: '109%'
	        }, {
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#333'],
	                    [1, '#FFF']
	                ]
	            },
	            borderWidth: 1,
	            outerRadius: '107%'
	        }, {
	            // default background
	        }, {
	            backgroundColor: '#DDD',
	            borderWidth: 0,
	            outerRadius: '105%',
	            innerRadius: '103%'
	        }]
	    },
	       
	    // the value axis
	    yAxis: {
	        min: -100,
	        max: 100,
	        
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto'
	        },
	        title: {
	            text: '%'
	        },
	        plotBands: [{
	            from: -100,
	            to: 0,                
	            color: '#55BF3B' // red
	        },  {
	            from: 0,
	            to: 100,
	            color: '#DF5353' // green
	        }]
	    },
	
	    series: [{
	        name: 'Units',
	        data: [0]
	    }]
	});
	
	
	var chartHomeDuties = new Highcharts.Chart({
    	
		chart: {
	        type: 'gauge',
	        plotBackgroundColor: null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: false,
	        renderTo: 'container-home-duties'
	    },
	    title: {
	    	text: '<p style="font-size:15px;text-align:center;">Actual: 0 K USD$</p><p style="font-size:11px;text-align:center;"><strong>0 %</strong></p><p style="font-size:14px;text-align:center;"><strong>0 K</strong></p>',
	        useHTML: true
	    },
	    pane: {
	        startAngle: -150,
	        endAngle: 150,
	        background: [{
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#FFF'],
	                    [1, '#333']
	                ]
	            },
	            borderWidth: 0,
	            outerRadius: '109%'
	        }, {
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#333'],
	                    [1, '#FFF']
	                ]
	            },
	            borderWidth: 1,
	            outerRadius: '107%'
	        }, {
	            // default background
	        }, {
	            backgroundColor: '#DDD',
	            borderWidth: 0,
	            outerRadius: '105%',
	            innerRadius: '103%'
	        }]
	    },
	       
	    // the value axis
	    yAxis: {
	        min: -100,
	        max: 100,
	        
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto'
	        },
	        title: {
	            text: '%'
	        },
	        plotBands: [{
	            from: -100,
	            to: 0,                
	            color: '#55BF3B' // red
	        },  {
	            from: 0,
	            to: 100,
	            color: '#DF5353' // green
	        }]
	    },
	
	    series: [{
	        name: 'Units',
	        data: [0]
	    }]
	});
	
	
	

	var chartHomefreights = new Highcharts.Chart({
    	
		chart: {
	        type: 'gauge',
	        plotBackgroundColor: null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: false,
	        renderTo: 'container-home-freights'
	    },
	    title: {
	    	text: '<p style="font-size:15px;text-align:center;">Actual: 0 K USD$</p><p style="font-size:11px;text-align:center;"><strong>0 %</strong></p><p style="font-size:14px;text-align:center;"><strong>0 K</strong></p>',
	        useHTML: true
	    },
	    pane: {
	        startAngle: -150,
	        endAngle: 150,
	        background: [{
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#FFF'],
	                    [1, '#333']
	                ]
	            },
	            borderWidth: 0,
	            outerRadius: '109%'
	        }, {
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#333'],
	                    [1, '#FFF']
	                ]
	            },
	            borderWidth: 1,
	            outerRadius: '107%'
	        }, {
	            // default background
	        }, {
	            backgroundColor: '#DDD',
	            borderWidth: 0,
	            outerRadius: '105%',
	            innerRadius: '103%'
	        }]
	    },
	       
	    // the value axis
	    yAxis: {
	        min: -100,
	        max: 100,
	        
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto'
	        },
	        title: {
	            text: '%'
	        },
	        plotBands: [{
	            from: -100,
	            to: 0,                
	            color: '#55BF3B' // red
	        },  {
	            from: 0,
	            to: 100,
	            color: '#DF5353' // green
	        }]
	    },
	
	    series: [{
	        name: 'Units',
	        data: [0]
	    }]
	});
	
	
	$('#tab-charts a[href="#tab-home-charts"]').click(function (e) {
		
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("homeCompareStages.action", params, callbackHomeCompareStages);
		
		$('#tab-charts a[href="#tab-home-charts"]').tab('show');
	})
	
	$('#tab-charts a[href="#tab-summary-charts"]').click(function (e) {
		
		e.preventDefault();
		
		$("#tab-summary-index a[href='#tab-units']").trigger("click");
		
		
		$('#tab-charts a[href="#tab-summary-charts"]').tab('show');
	})
	
	$('#tab-charts a[href="#tab-detail-charts"]').click(function (e) {
		
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("homeStandardCompareStages.action", params, callbackHomeStandardCompareStages);
		
		
		$('#tab-charts a[href="#tab-detail-charts"]').tab('show');
	})
	
	
	$('#tab-summary-index a[href="#tab-units"]').click(function (e) {
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("homeSummaryCompareStages.action", params, callbackHomeSummaryCompareStagesUnits);
		
		$('#tab-summary-index a[href="#tab-units"]').tab('show'); 
	})
	
	$('#tab-summary-index a[href="#tab-fob"]').click(function (e) {
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("homeSummaryCompareStages.action", params, callbackHomeSummaryCompareStagesFobs);
		
		$('#tab-summary-index a[href="#tab-fob"]').tab('show'); 
	})
	
	$('#tab-summary-index a[href="#tab-duties"]').click(function (e) {
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("homeSummaryCompareStages.action", params, callbackHomeSummaryCompareStagesDuties);
		
		$('#tab-summary-index a[href="#tab-duties"]').tab('show'); 
	});
	
	$('#tab-summary-index a[href="#tab-freights"]').click(function (e) {
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("homeSummaryCompareStages.action", params, callbackHomeSummaryCompareStagesFreights);
		
		 
	});
	
	
	function callbackHomeCompareStages(data){
		
		var homeCompareVO           = data.homeCompareVO;
		var listHomeCompareFamilyVO = data.listHomeCompareFamilyVO;
		var statusMessageError      = data.statusMessageError;
		
		
		if (statusMessageError != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(statusMessageError);
			
			return false;
		}
		
		
		//Units
		var unitsStageA = homeCompareVO.stageA.totalUnitsStage;
		var unitsStageB = homeCompareVO.stageB.totalUnitsStage;
		
		var percentStages = (unitsStageA - unitsStageB) * 100 / unitsStageB;
		var diffStages    = unitsStageA - unitsStageB;
		
		chartHomeUnits.setTitle({
	        text: '<p style="font-size:15px;text-align:center;">Actual: '+homeCompareVO.stageA.totalUnitsStage.formatMoney(0)+' UND</p><p style="font-size:11px;text-align:center;"><strong>'+percentStages.formatMoney(2)+' %</strong></p><p style="font-size:14px;text-align:center;"><strong>'+diffStages.formatMoney(0)+' UND</strong></p>'
	    });
		
		var serie = new Array();
		serie[0] = percentStages;
		chartHomeUnits.series[0].setData(serie);
		
		
		//Fobs
		var fobsStageA = homeCompareVO.stageA.totalFobsStage / 1000;
		var fobsStageB = homeCompareVO.stageB.totalFobsStage / 1000; 
		
		var percentStages = (fobsStageA - fobsStageB) * 100 / fobsStageB;
		var diffStages    = fobsStageA - fobsStageB;
		
		chartHomeFobs.setTitle({
	        text: '<p style="font-size:15px;text-align:center;">Actual: '+fobsStageA.formatMoney(0)+' K USD$</p><p style="font-size:11px;text-align:center;"><strong>'+percentStages.formatMoney(2)+' %</strong></p><p style="font-size:14px;text-align:center;"><strong>'+diffStages.formatMoney(0)+' K</strong></p>'
	    });

		var serie = new Array();
		serie[0] = percentStages;
		chartHomeFobs.series[0].setData(serie);
		
		
		//Duties
		var dutiesStageA = homeCompareVO.stageA.totalDutiesStage / 1000;
		var dutiesStageB = homeCompareVO.stageB.totalDutiesStage / 1000; 
		
		var percentStages = (dutiesStageA - dutiesStageB) * 100 / dutiesStageB;
		var diffStages    = dutiesStageA - dutiesStageB;

		chartHomeDuties.setTitle({
	        text: '<p style="font-size:15px;text-align:center;">Actual: '+dutiesStageA.formatMoney(0)+' K USD$</p><p style="font-size:11px;text-align:center;"><strong>'+percentStages.formatMoney(0)+' %</strong></p><p style="font-size:14px;text-align:center;"><strong>'+diffStages.formatMoney(0)+' K</strong></p>'
	    });

		var serie = new Array();
		serie[0] = percentStages;
		chartHomeDuties.series[0].setData(serie);
		
		
		
		
		//Freights
		var freightStageA = (homeCompareVO.stageA.totalFreightsStage + homeCompareVO.stageA.totalOthersStage) / 1000;
		var freightStageB = (homeCompareVO.stageB.totalFreightsStage + homeCompareVO.stageB.totalOthersStage) / 1000; 
		
		
		var percentStages = (freightStageA - freightStageB) * 100 / freightStageB;
		var diffStages    = freightStageA - freightStageB;
//
		chartHomefreights.setTitle({ 
	        text: '<p style="font-size:15px;text-align:center;">Actual: '+freightStageA.formatMoney(0)+' K USD$</p><p style="font-size:11px;text-align:center;"><strong>'+percentStages.formatMoney(0)+' %</strong></p><p style="font-size:14px;text-align:center;"><strong>'+diffStages.formatMoney(0)+' K</strong></p>'
	    });
//
		var serie = new Array();
		serie[0] = percentStages;
		chartHomefreights.series[0].setData(serie);		
		
		
		
		$( "#table-summary-unit-tbody" ).empty();
		for (var j=0; j<listHomeCompareFamilyVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");

			var percentUnits = (listHomeCompareFamilyVO[j].unitStageAFamily - listHomeCompareFamilyVO[j].unitStageBFamily) * 100 / listHomeCompareFamilyVO[j].unitStageBFamily;
			
			row = row.concat("<td>" + listHomeCompareFamilyVO[j].nameFamily + "</td>");
			row = row.concat("<td>" + percentUnits.formatMoney(2) + "%</td>");
			
			if (percentUnits < 0)
				row = row.concat("<td><i class='icon-arrow-down'></i></td>");
			if (percentUnits >= 0)
				row = row.concat("<td><i class='icon-arrow-up'></i></td>");
						
			row = row.concat("</tr>");
			
			$("#table-summary-unit-tbody").append(row);
		}
		
		$( "#table-summary-fob-tbody" ).empty();
		for (var j=0; j<listHomeCompareFamilyVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");

			var percentUnits = (listHomeCompareFamilyVO[j].fobStageAFamily - listHomeCompareFamilyVO[j].fobStageBFamily) * 100 / listHomeCompareFamilyVO[j].fobStageBFamily;
			
			row = row.concat("<td>" + listHomeCompareFamilyVO[j].nameFamily + "</td>");
			row = row.concat("<td>" + percentUnits.formatMoney(2) + "%</td>");
			
			if (percentUnits < 0)
				row = row.concat("<td><i class='icon-arrow-down'></i></td>");
			if (percentUnits >= 0)
				row = row.concat("<td><i class='icon-arrow-up'></i></td>");
						
			row = row.concat("</tr>");
			
			$("#table-summary-fob-tbody").append(row);
		}
		
		
		$( "#table-summary-duty-tbody" ).empty();
		for (var j=0; j<listHomeCompareFamilyVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");

			var percentUnits = (listHomeCompareFamilyVO[j].dutiesStageAFamily - listHomeCompareFamilyVO[j].dutiesStageBFamily) * 100 / listHomeCompareFamilyVO[j].dutiesStageBFamily;
			
			row = row.concat("<td>" + listHomeCompareFamilyVO[j].nameFamily + "</td>");
			row = row.concat("<td>" + percentUnits.formatMoney(2) + "%</td>");
			
			if (percentUnits < 0)
				row = row.concat("<td><i class='icon-arrow-down'></i></td>");
			if (percentUnits >= 0)
				row = row.concat("<td><i class='icon-arrow-up'></i></td>");
						
			row = row.concat("</tr>");
			
			$("#table-summary-duty-tbody").append(row);
		}	
		
		
		$( "#table-summary-freight-tbody" ).empty();
		for (var j=0; j<listHomeCompareFamilyVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");

			var percentUnits = ((listHomeCompareFamilyVO[j].freightStageAFamily + listHomeCompareFamilyVO[j].otherStageAFamily) - (listHomeCompareFamilyVO[j].freightStageBFamily + listHomeCompareFamilyVO[j].otherStageBFamily)) * 100 / (listHomeCompareFamilyVO[j].freightStageBFamily + listHomeCompareFamilyVO[j].otherStageBFamily);
			
			row = row.concat("<td>" + listHomeCompareFamilyVO[j].nameFamily + "</td>");
			row = row.concat("<td>" + percentUnits.formatMoney(2) + "%</td>");
			
			if (percentUnits < 0)
				row = row.concat("<td><i class='icon-arrow-down'></i></td>");
			if (percentUnits >= 0)
				row = row.concat("<td><i class='icon-arrow-up'></i></td>");
						
			row = row.concat("</tr>");
			
			$("#table-summary-freight-tbody").append(row);
		}
		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
	}
	
	
	function callbackHomeStandardCompareStages(data){
		
		var listStandardDetailVO = data.listStandardDetailVO;
		
		var arrayAmountsA1 = [];
		var arrayAmountsA2 = [];
		
		var arrayAmountsB1 = [];
		var arrayAmountsB2 = [];
		
		var arrayAmountsC1 = [];
		var arrayAmountsC2 = [];
		
		var arrayAmountsD1 = [];
		var arrayAmountsD2 = [];
		
		
		var acum = 0;
		var countSubCostB1 = 0; 
		var countSubCostB2 = 0; 
		
		var countCostC1 = 0; 
		var countCostC2 = 0; 
		
		$( "#table-std-detail-tbody" ).empty();
		
		var totalAmountStageAStandarDetail = 0;
		var totalAmountStageBStandarDetail = 0;
		var totalNominalAmoutStandarDetail = 0;
		var totalDteAmoutStandarDetail = 0;
		var totalExchangeAmoutStandarDetail = 0;
		for (var j=0; j<listStandardDetailVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listStandardDetailVO[j].typeCostStandarDetail + "</td>");
			row = row.concat("<td>" + listStandardDetailVO[j].subTypeCostStandarDetail + "</td>");
			row = row.concat("<td>" + listStandardDetailVO[j].nameCostStandarDetail + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].amountStageBStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].amountStageAStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].nominalAmoutStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].dteAmoutStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].exchangeAmoutStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].percentAmoutStandarDetail.toFixed(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-std-detail-tbody").append(row);
			
			
			//Acumulador del total para promediar
			acum = acum + listStandardDetailVO[j].exchangeAmoutStandarDetail;
			
			//Arreglo para el detalle del costo y graficarlo
//			arrayAmountsA1[j] = [listStandardDetailVO[j].nameCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageAStandarDetail)];
//			arrayAmountsA2[j] = [listStandardDetailVO[j].nameCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageBStandarDetail)];
			
			
			
			
			
			
			if (listStandardDetailVO[j].nameCostStandarDetail != 'Transfer Price'){
				arrayAmountsA1[j-1] = [listStandardDetailVO[j].nameCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageAStandarDetail)];
				arrayAmountsA2[j-1] = [listStandardDetailVO[j].nameCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageBStandarDetail)];
				
				
				//acumulando resultados
				totalAmountStageAStandarDetail  += listStandardDetailVO[j].amountStageAStandarDetail;
				totalAmountStageBStandarDetail  += listStandardDetailVO[j].amountStageBStandarDetail;
				totalNominalAmoutStandarDetail  += listStandardDetailVO[j].nominalAmoutStandarDetail;
				totalDteAmoutStandarDetail      += listStandardDetailVO[j].dteAmoutStandarDetail;
				totalExchangeAmoutStandarDetail += listStandardDetailVO[j].exchangeAmoutStandarDetail;
				
				
			}
			

			
			
			
			
			//Arreglo para los sub costos y graficarlos.
			if (arrayAmountsB1.length == 0){
				arrayAmountsB1[countSubCostB1++] = [listStandardDetailVO[j].subTypeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageAStandarDetail)];
			}
			else{
				var encontradoB1 = false;
				for(var k=0; k<arrayAmountsB1.length;k++){
					if (arrayAmountsB1[k][0] == listStandardDetailVO[j].subTypeCostStandarDetail){
						arrayAmountsB1[k][1] = arrayAmountsB1[k][1] + parseInt(listStandardDetailVO[j].amountStageAStandarDetail);
						
						encontradoB1 = true;
						break;
					}
				}
				
				if (encontradoB1 == false)
					arrayAmountsB1[countSubCostB1++] = [listStandardDetailVO[j].subTypeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageAStandarDetail)];
			}
			
			
			if (arrayAmountsB2.length == 0){
				arrayAmountsB2[countSubCostB2++] = [listStandardDetailVO[j].subTypeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageBStandarDetail)];
			}
			else{
				var encontradoB2 = false;
				for(var k=0; k<arrayAmountsB2.length;k++){
					if (arrayAmountsB2[k][0] == listStandardDetailVO[j].subTypeCostStandarDetail){
						arrayAmountsB2[k][1] = arrayAmountsB2[k][1] + parseInt(listStandardDetailVO[j].amountStageBStandarDetail);
						
						encontradoB2 = true;
						break;
					}
				}
				
				if (encontradoB2 == false)
					arrayAmountsB2[countSubCostB2++] = [listStandardDetailVO[j].subTypeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageBStandarDetail)];
			}
		
			//Arreglo para los costos principales y graficarlos.
			if (arrayAmountsC1.length == 0)
				arrayAmountsC1[countCostC1++] = [listStandardDetailVO[j].typeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageAStandarDetail)];
			else{
				var encontradoC1 = false;
				for(var k=0; k<arrayAmountsC1.length;k++){
					if (arrayAmountsC1[k][0] == listStandardDetailVO[j].typeCostStandarDetail){
						arrayAmountsC1[k][1] = arrayAmountsC1[k][1] + parseInt(listStandardDetailVO[j].amountStageAStandarDetail);
						
						encontradoC1 = true;
						break;
					}
				}
				
				if (encontradoC1 == false)
					arrayAmountsC1[countCostC1++] = [listStandardDetailVO[j].typeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageAStandarDetail)];
			}
			
			
			if (arrayAmountsC2.length == 0)
				arrayAmountsC2[countCostC2++] = [listStandardDetailVO[j].typeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageBStandarDetail)];
			else{
				var encontradoC2 = false;
				for(var k=0; k<arrayAmountsC2.length;k++){
					if (arrayAmountsC2[k][0] == listStandardDetailVO[j].typeCostStandarDetail){
						arrayAmountsC2[k][1] = arrayAmountsC2[k][1] + parseInt(listStandardDetailVO[j].amountStageBStandarDetail);
						
						encontradoC2 = true;
						break;
					}
				}
				
				if (encontradoC2 == false)
					arrayAmountsC2[countCostC2++] = [listStandardDetailVO[j].typeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageBStandarDetail)];
			}
		
		}
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTALS without FOB" + "</strong></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		
		
		row = row.concat("<td style='text-align:right;'><strong>" + totalAmountStageBStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalAmountStageAStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalNominalAmoutStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalDteAmoutStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalExchangeAmoutStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td></td>");  

		row = row.concat("</tr>");
		$("#table-std-detail-tbody").append(row);
		
		//Detail Cost
		chartDetailCost.series[0].setData(arrayAmountsA1);
		chartDetailCost.series[1].setData(arrayAmountsA2);

		
		
		//Sub Type Cost
		chartSubTypeCost.series[0].setData(arrayAmountsB1);
		chartSubTypeCost.series[1].setData(arrayAmountsB2);
		

		//Type Cost
		chartTypeCost.series[0].setData(arrayAmountsC1);
		chartTypeCost.series[1].setData(arrayAmountsC2);
		
		
		
		for(var k=0; k<arrayAmountsB1.length;k++)
			if (arrayAmountsB1[k][0] != 'INTERCOMPANY')
				arrayAmountsD1[k-1] = arrayAmountsB1[k];
		
		chartDetailActualPercent.series[0].setData(arrayAmountsD1);
		
		
		for(var k=0; k<arrayAmountsB2.length;k++)
			if (arrayAmountsB2[k][0] != 'INTERCOMPANY')
				arrayAmountsD2[k-1] = arrayAmountsB2[k];		
		
		chartDetailForecastPercent.series[0].setData(arrayAmountsD2);
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
		
//		$(window).resize();
		$('.t-f-h-std-detail').fixedHeader();
	}
	
	function callbackHomeSummaryCompareStagesUnits(data){
			
		var statusMessageError = data.statusMessageError;
		var listSummaryVO      = data.listSummaryVO;
		
		var arrayAmountsA = new Array();
		var arrayAmountsB = new Array();
		var arrayAmountsC = new Array();

		var yearToDateUnitSummary = 0;
		var percentUnitSummary    = 0;
		
		var sumAmountStageA = 0;
		var sumAmountStageB = 0;
		
		$( "#table-summary-units-tbody" ).empty();
		for (var j=0; j<listSummaryVO.length; j++){
			
			yearToDateUnitSummary = yearToDateUnitSummary + listSummaryVO[j].byMonthUnitSummary;
			
			percentUnitSummary = (listSummaryVO[j].amountUnitStageASummary - listSummaryVO[j].amountUnitStageBSummary ) * 100 / listSummaryVO[j].amountUnitStageBSummary;
			
			sumAmountStageA = sumAmountStageA + listSummaryVO[j].amountUnitStageASummary;
			sumAmountStageB = sumAmountStageB + listSummaryVO[j].amountUnitStageBSummary;
			
			
			var row = "";
			row = row.concat("<tr>");
			 
			row = row.concat("<td>" + listSummaryVO[j].monthSummary + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountUnitStageASummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountUnitStageBSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].byMonthUnitSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + yearToDateUnitSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + percentUnitSummary.toFixed(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-summary-units-tbody").append(row);
			
			
			arrayAmountsA[j] = listSummaryVO[j].amountUnitStageASummary;
			arrayAmountsB[j] = listSummaryVO[j].amountUnitStageBSummary;
			arrayAmountsC[j] = listSummaryVO[j].amountUnitStageCSummary;
		}
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTALS" + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageA.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td style='text-align:right;'></td>");
		row = row.concat("<td style='text-align:right;'></td>");
		row = row.concat("<td style='text-align:right;'></td>");
		row = row.concat("</tr>");
		$("#table-summary-units-tbody").append(row);
		
		chartSummaryUnits.series[0].setData(arrayAmountsA);
		chartSummaryUnits.series[1].setData(arrayAmountsB);
		chartSummaryUnits.series[2].setData(arrayAmountsC);
		
		

		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-summary-units').fixedHeader();
		$(window).resize();
		
	}
	
	function callbackHomeSummaryCompareStagesFobs(data){
		
//		var statusMessageError = data.statusMessageError;
		var listSummaryVO      = data.listSummaryVO;
		
		var arrayAmountsA = new Array();
		var arrayAmountsB = new Array();
		var arrayAmountsC = new Array();

		var yearToDateUnitSummary = 0;
		var percentUnitSummary    = 0;
		
		var sumAmountStageA = 0;
		var sumAmountStageB = 0;
		
		$( "#table-summary-fobs-tbody" ).empty();
		for (var j=0; j<listSummaryVO.length; j++){
			
			yearToDateUnitSummary = yearToDateUnitSummary + listSummaryVO[j].byMonthUnitSummary;
			
			percentUnitSummary = (listSummaryVO[j].amountFobsStageASummary - listSummaryVO[j].amountFobsStageBSummary ) * 100 / listSummaryVO[j].amountFobsStageBSummary;
			
			sumAmountStageA = sumAmountStageA + listSummaryVO[j].amountFobsStageASummary;
			sumAmountStageB = sumAmountStageB + listSummaryVO[j].amountFobsStageBSummary;
			
			var row = "";
			row = row.concat("<tr>");
			 
			row = row.concat("<td>" + listSummaryVO[j].monthSummary + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].amountFobsStageASummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].amountFobsStageBSummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].byMonthFobsSummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (yearToDateUnitSummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (percentUnitSummary).toFixed(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-summary-fobs-tbody").append(row);
			
			arrayAmountsA[j] = parseInt(listSummaryVO[j].amountFobsStageASummary);
			arrayAmountsB[j] = parseInt(listSummaryVO[j].amountFobsStageBSummary);
			arrayAmountsC[j] = parseInt(listSummaryVO[j].amountFobsStageCSummary);
		}
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTAL" + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageA.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		row = row.concat("</tr>");
		$("#table-summary-fobs-tbody").append(row);
		
		chartSummaryFobs.series[0].setData(arrayAmountsA);
		chartSummaryFobs.series[1].setData(arrayAmountsB);
//		chartSummaryFobs.series[2].setData(arrayAmountsC);
		$('.t-f-h-summary-fobs').fixedHeader();
		$(window).resize();
	}
	
	function callbackHomeSummaryCompareStagesDuties(data){
		
		var statusMessageError = data.statusMessageError;
		var listSummaryVO      = data.listSummaryVO;
		
		var arrayAmountsA = new Array();
		var arrayAmountsB = new Array();
		var arrayAmountsC = new Array();

		var yearToDateDutiesSummary = 0;
		var percentDutiesSummary    = 0;
		
		var sumAmountStageA = 0;
		var sumAmountStageB = 0;
		
		var sumDTE        = 0;
		var sumExExchange = 0;
		
		$( "#table-summary-duties-tbody" ).empty();
		for (var j=0; j<listSummaryVO.length; j++){
			
			yearToDateDutiesSummary = yearToDateDutiesSummary + listSummaryVO[j].byMonthDutiesSummary;
			
 			percentDutiesSummary = listSummaryVO[j].amountDutiesStageASummary / (listSummaryVO[j].byMonthDutiesSummary - listSummaryVO[j].dteDutiesSummary);
 			
 			sumAmountStageA = sumAmountStageA + listSummaryVO[j].amountDutiesStageASummary;
			sumAmountStageB = sumAmountStageB + listSummaryVO[j].amountDutiesStageBSummary;
			sumDTE          = sumDTE + listSummaryVO[j].dteDutiesSummary;
			sumExExchange   = sumExExchange + (listSummaryVO[j].byMonthDutiesSummary - listSummaryVO[j].dteDutiesSummary);
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listSummaryVO[j].monthSummary + "</td>");			
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountDutiesStageBSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountDutiesStageASummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].byMonthDutiesSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + yearToDateDutiesSummary.formatMoney(0) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].dteDutiesSummary.formatMoney(0) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].byMonthDutiesSummary - listSummaryVO[j].dteDutiesSummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + percentDutiesSummary.toFixed(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-summary-duties-tbody").append(row);
			
			arrayAmountsA[j] = parseInt(listSummaryVO[j].amountDutiesStageASummary);
			arrayAmountsB[j] = parseInt(listSummaryVO[j].amountDutiesStageBSummary);
			arrayAmountsC[j] = parseInt(listSummaryVO[j].amountDutiesStageCSummary);
		}
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTAL" + "</strong></td>");		
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageA.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageB.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumDTE.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumExExchange.formatMoney(0) + "</strong></td>");
		row = row.concat("<td></td>");
		row = row.concat("</tr>");
		$("#table-summary-duties-tbody").append(row);

		chartSummaryDuties.series[0].setData(arrayAmountsA);
		chartSummaryDuties.series[1].setData(arrayAmountsB);
//		chartSummaryDuties.series[2].setData(arrayAmountsC);
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-summary-duties').fixedHeader();
		$(window).resize();
	}
	
	
	
	function callbackHomeSummaryCompareStagesFreights(data){
		
		var listSummaryVO      = data.listSummaryVO;
		
		var arrayAmountsA = new Array();
		var arrayAmountsB = new Array();
		var arrayAmountsC = new Array();

		var yearToDateFreightsSummary = 0;
		var percentFreightsSummary    = 0;
		
		var sumAmountStageA = 0;
		var sumAmountStageB = 0;
		
		var sumDTE        = 0;
		var sumExExchange = 0;
		
		var freightOthersStageA = 0;
		var freightOthersStageB = 0;
		
		var freightOthersByMonth = 0;
		
		$( "#table-summary-freights-tbody" ).empty();
		for (var j=0; j<listSummaryVO.length; j++){
			
			freightOthersStageA = (listSummaryVO[j].amountFreightsStageASummary + listSummaryVO[j].amountOthersStageASummary);
			freightOthersStageB = (listSummaryVO[j].amountFreightsStageBSummary + listSummaryVO[j].amountOthersStageBSummary);
									
			yearToDateFreightsSummary = yearToDateFreightsSummary + (listSummaryVO[j].byMonthFreightsSummary + listSummaryVO[j].byMonthOthersSummary);
			
			percentFreightsSummary = (listSummaryVO[j].amountFreightsStageASummary + listSummaryVO[j].amountOthersStageBSummary) / ((listSummaryVO[j].byMonthFreightsSummary + listSummaryVO[j].byMonthOthersSummary) - (listSummaryVO[j].dteFreightsSummary + listSummaryVO[j].dteOthersSummary));
			
			sumAmountStageA = sumAmountStageA + freightOthersStageA;
			sumAmountStageB = sumAmountStageB + freightOthersStageB;
			sumDTE          = sumDTE + listSummaryVO[j].dteFreightsSummary;
			sumExExchange   = sumExExchange + (freightOthersByMonth - listSummaryVO[j].dteFreightsSummary);			
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listSummaryVO[j].monthSummary + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].amountFreightsStageASummary + listSummaryVO[j].amountOthersStageASummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].amountFreightsStageBSummary + listSummaryVO[j].amountOthersStageBSummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].byMonthFreightsSummary + listSummaryVO[j].byMonthOthersSummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + yearToDateFreightsSummary.formatMoney(0) + "</td>");
			
			
			row = row.concat("<td style='text-align:right;'>" + (listSummaryVO[j].dteFreightsSummary + listSummaryVO[j].dteOthersSummary).formatMoney(0) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + ((listSummaryVO[j].byMonthFreightsSummary + listSummaryVO[j].byMonthOthersSummary) - (listSummaryVO[j].dteFreightsSummary + listSummaryVO[j].dteOthersSummary)).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + percentFreightsSummary.toFixed(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-summary-freights-tbody").append(row);
			
			arrayAmountsA[j] = parseInt((listSummaryVO[j].amountFreightsStageASummary + listSummaryVO[j].amountOthersStageASummary));
			arrayAmountsB[j] = parseInt((listSummaryVO[j].amountFreightsStageBSummary + listSummaryVO[j].amountOthersStageBSummary));
			arrayAmountsC[j] = parseInt((listSummaryVO[j].amountFreightsStageCSummary + listSummaryVO[j].amountOthersStageCSummary));
		}
	
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTAL" + "</strong></td>");		
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageA.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageB.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumDTE.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumExExchange.formatMoney(0) + "</strong></td>");
		row = row.concat("<td></td>");
		row = row.concat("</tr>");
		$("#table-summary-freights-tbody").append(row);
		
		chartSummaryFreights.series[0].setData(arrayAmountsA);
		chartSummaryFreights.series[1].setData(arrayAmountsB);
//		chartSummaryFreights.series[2].setData(arrayAmountsC);
		
		
		$('#tab-summary-index a[href="#tab-freights"]').tab('show');
		$('.t-f-h-summary-freights').fixedHeader();
		$(window).resize();
		
	}
	
	
	
	
	$("#tab-charts a[href='#tab-detail-charts']").trigger("click"); 
	
});