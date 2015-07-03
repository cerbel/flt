$(document).ready(function(){

	
	
	//CHARTS
	
	
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
    
    
    
    
    //graficos de torta
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
    //fin graficos de torta
    
    

    // Build the chart
    var chartDetailCost = new Highcharts.Chart({
    	chart: {
                type: 'column',
                renderTo: 'container-std-detail'
            },
            title: {
                text: 'New Stage VS Base Stage Detail Cost'
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
	            name: 'New Stage',
	            data: []
	        }, {
	            name: 'Base Stage',
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
                text: 'New Stage VS Base Stage Sub Type Cost'
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
	            name: 'New Stage',
	            data: []
	        }, {
	            name: 'Base Stage',
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
                text: 'New Stage VS Base Stage Type Cost'
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
	            name: 'New Stage',
	            data: []
	        }, {
	            name: 'Base Stage',
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
            name: 'New Stage',
            data: []
        }, {
            name: 'Base Stage',
            data: []
        }]
    });
	
    
    
    var chartSummaryFobs = new Highcharts.Chart({
		
        chart: {
            type: 'line',
            renderTo: 'container-summary-fobs'
        },
        title: {
            text: 'Monthly FOB Costs'
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
            name: 'New Stage',
            data: []
        }, {
            name: 'Base Stage',
            data: []
        }]
    });
    
    
    var chartSummaryDuties = new Highcharts.Chart({
		
        chart: {
            type: 'line',
            renderTo: 'container-summary-duties'
        },
        title: {
            text: 'Monthly Duties Costs'
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
            name: 'New Stage',
            data: []
        }, {
            name: 'Base Stage',
            data: []
        }]
    });
    
    
    var chartSummaryFreights = new Highcharts.Chart({
		
        chart: {
            type: 'line',
            renderTo: 'container-summary-freights'
        },
        title: {
            text: 'Monthly Duties Costs'
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
            name: 'New Stage',
            data: []
        }, {
            name: 'Base Stage',
            data: []
        }]
    });
    
    

	$('#result-stage-history').on('click', function() {
		
		
		var idBaseStage = $('#result-base-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage;
		ajaxInvokeMethods.postAjaxCallback("listCommentStage.action", param, callbackListCommentStage);
	});
    
	$('#btnAddComment').on('click', function() {
		
		
		var idBaseStage  = $('#result-base-stage-id').val();
		var commentStage = $('#commentStage').val();
		
		var param = "idBaseStage="+idBaseStage+"&commentStage="+commentStage;
		ajaxInvokeMethods.postAjaxCallback("insertCommentStage.action", param, callbackListCommentStage);
	});
	
	
    
    
	$('#btnRetry').on('click', function() {
		window.location.replace("changeStage.action");
	});
	
	$('#btnForecast').on('click', function() {
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('This action will generate a new Forecast stage(Can be removed later), are you sure?');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			var idNewStage  = $('#result-new-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
			ajaxInvokeMethods.postAjaxCallback("updateStageForecast.action", param, callbackUpdateStageForecast);
		});
	});
	
	
	$('#btnBudget').on('click', function() {
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('This action will generate a new Borecast stage(Can be removed later), are you sure? ');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			var idNewStage  = $('#result-new-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
			ajaxInvokeMethods.postAjaxCallback("updateStageBudget.action", param, callbackUpdateStageBudget);
		});
	});
	
	
	
	$('#btnApproveForecast').on('click', function() {
		
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('Are you sure to approve the Forecast stage?, This will generate a forecast process closure');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			var idNewStage  = $('#result-new-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
			ajaxInvokeMethods.postAjaxCallback("updateStageApproveForecast.action", param, callbackUpdateStageApproveForecast);
		});
	});
	
	
	$('#btnRejectForecast').on('click', function() {
		
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('Are you sure to reject the Forecast stage?, This will generate a forecast process');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage;
			ajaxInvokeMethods.postAjaxCallback("updateStageRejectForecast.action", param, callbackUpdateStageApproveForecast);
		});
	});
	
	
	$('#btnToApproveForecast').on('click', function() {
		
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('Are you sure to approve the Forecast stage?, This will generate a forecast process slope');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			var idNewStage  = $('#result-new-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
			ajaxInvokeMethods.postAjaxCallback("updateStageToApproveForecast.action", param, callbackUpdateStageApproveForecast);
		});
	});
	
	
	$('#btnApproveBudget').on('click', function() {
		
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('Are you sure to approve the Budget stage?, This will generate a budget process closure');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			var idNewStage  = $('#result-new-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
			ajaxInvokeMethods.postAjaxCallback("updateStageApproveBudget.action", param, callbackUpdateStageApproveBudget);
		});
	});
	
	
	$('#btnRejectBudget').on('click', function() {
		
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('Are you sure to reject the Budget stage?, This will generate a budget process');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			var idNewStage  = $('#result-new-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
			ajaxInvokeMethods.postAjaxCallback("updateStageRejectBudget.action", param, callbackUpdateStageApproveBudget);
		});
	});
	
	
	$('#btnToApproveBudget').on('click', function() {
		
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('Are you sure to approve the Budget stage?, This will generate a budget process slope');
		$('#alert-confirm-acept').on('click', function() {
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal({shown:true,  keyboard: false });
			
			var idBaseStage = $('#result-base-stage-id').val();
			var idNewStage  = $('#result-new-stage-id').val();
			
			var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
			ajaxInvokeMethods.postAjaxCallback("updateStageToApproveBudget.action", param, callbackUpdateStageApproveBudget);
		});
	});
	
	
	
	
	$('#filter-product-btn').on('click', function() {
		
		
		var idBaseStage = $('#result-base-stage-id').val();
		
		
		var idtrasport     = $('#filter-product-id-transport').val();
		var idFamily       = $('#filter-product-id-family').val();
		var idSiteSource   = $('#filter-product-id-siteSource').val();
		var idType         = $('#filter-product-id-type').val();
		var idPresentation = $('#filter-product-id-presentation').val();
		var idtypeLoad     = $('#filter-product-id-typeLoad').val();
		var idTrade        = $('#filter-product-id-trade').val();
		var idCustomDuty   = $('#filter-product-id-customDuty').val();
		var nameProduct    = $('#filter-product-name').val();
		
		
		var param = "idBaseStage="+idBaseStage+
					"&filterProductStageVO.idTransportProduct="+idtrasport+""+
					"&filterProductStageVO.idFamilyProduct="+idFamily+""+
					"&filterProductStageVO.idSiteSourceProduct="+idSiteSource+""+
					"&filterProductStageVO.idTypeProduct="+idType+""+
					"&filterProductStageVO.idPresentationProduct="+idPresentation+""+
					"&filterProductStageVO.idTypeLoadProduct="+idtypeLoad+""+
					"&filterProductStageVO.idTradeProduct="+idTrade+""+
					"&filterProductStageVO.idCustomDutiesProduct="+idCustomDuty+""+
					"&filterProductStageVO.nameProduct="+nameProduct+"";
		
		
		ajaxInvokeMethods.postAjaxCallback("applyFilterProduct.action", param, callbackApplyFilterProduct);
		
		
		$( "#id-filter-btn" ).removeClass( "icon-filter" ).addClass( "icon-filter icon-white" );
	});
	
	
	$('#result-stage-filters-refresh').on('click', function() { 
		
		var idBaseStage = $('#result-base-stage-id').val();
		
		
		$('#filter-product-id-transport').val("");
		$('#filter-product-id-family').val("");
		$('#filter-product-id-siteSource').val("");
		$('#filter-product-id-type').val("");
		$('#filter-product-id-presentation').val("");
		$('#filter-product-id-typeLoad').val("");
		$('#filter-product-id-trade').val("");
		$('#filter-product-id-customDuty').val("");
		$('#filter-product-name').val("");
		
		
		var idtrasport     = $('#filter-product-id-transport').val();
		var idFamily       = $('#filter-product-id-family').val();
		var idSiteSource   = $('#filter-product-id-siteSource').val();
		var idType         = $('#filter-product-id-type').val();
		var idPresentation = $('#filter-product-id-presentation').val();
		var idtypeLoad     = $('#filter-product-id-typeLoad').val();
		var idTrade        = $('#filter-product-id-trade').val();
		var idCustomDuty   = $('#filter-product-id-customDuty').val();
		var nameProduct    = $('#filter-product-name').val();
		
		var param = "idBaseStage="+idBaseStage+
					"&filterProductStageVO.idTransportProduct="+idtrasport+""+
					"&filterProductStageVO.idFamilyProduct="+idFamily+""+
					"&filterProductStageVO.idSiteSourceProduct="+idSiteSource+""+
					"&filterProductStageVO.idTypeProduct="+idType+""+
					"&filterProductStageVO.idPresentationProduct="+idPresentation+""+
					"&filterProductStageVO.idTypeLoadProduct="+idtypeLoad+""+
					"&filterProductStageVO.idTradeProduct="+idTrade+""+
					"&filterProductStageVO.idCustomDutiesProduct="+idCustomDuty+""+
					"&filterProductStageVO.nameProduct="+nameProduct+"";
		
		
		ajaxInvokeMethods.postAjaxCallback("applyFilterProduct.action", param, callbackApplyFilterProduct);
		
		$( "#id-filter-btn" ).removeClass( "icon-filter icon-white" ).addClass( "icon-filter" );
		
	});
	
	
	$('#result-stage-download').on('click', function() {
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage;
		window.location.replace("exportExcelResult.action?"+param);
	});
	
	
	$('#result-base-stage-id').on('change', function() {
		
//		$('#tab-result-cost a[href="#tab-summary"]').tab('show');
//		$('#tab-summary-index a[href="#tab-units"]').tab('show');  
		
		
		var idBaseStage = $('#result-base-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"";
		ajaxInvokeMethods.postAjaxCallback("obtieneIdStatusStageBase.action", param, callbackObtieneIdStatusStageBase);
		
	});
	
	
	$('#result-new-stage-id').on('change', function() {
		
//		$('#tab-result-cost a[href="#tab-summary"]').tab('show');
//		$('#tab-summary-index a[href="#tab-units"]').tab('show'); 
		
		
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("obtieneIdStatusStageNew.action", param, callbackObtieneIdStatusStageNew);
		
//		$("#tab-summary-index a[href='#tab-units']").trigger("click");
		
		$('#tab-result-cost a[href="#tab-std-detail"]').trigger("click");
	});
	
	

	
	$('#result-stage-filters').on('click', function() {
		$('#modal-search-products').modal('show');
	});
	
	$('#std-detail-products-filter').on('click', function() {
		$('#modal-search-products').modal('show');
	});

	
	$('#tab-result-cost a[href="#tab-summary"]').click(function (e) {
		e.preventDefault();
		
		$('#tab-result-cost a[href="#tab-summary"]').tab('show');
		
		
		$("#tab-summary-index a[href='#tab-units']").trigger("click");
	})
	$('#tab-result-cost a[href="#tab-impact-analysis"]').click(function (e) {
		e.preventDefault();
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initImpactAnalisysStage.action", param, callbackInitImpactAnalisysStage);
		
		
		$('#tab-result-cost a[href="#tab-impact-analysis"]').tab('show'); 
	})
	$('#tab-result-cost a[href="#tab-productivity"]').click(function (e) {
		e.preventDefault();
		
		
		
		$('#loading-local').show();
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initProductivity.action", param, callbackInitProductivity);
		
		$('#tab-result-cost a[href="#tab-productivity"]').tab('show'); 
	});
	
	
	
	
	$('#tab-result-cost a[href="#tab-monthly-data"]').click(function (e) {
		e.preventDefault();
		
		$('#loading-local').show();
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initMonthlyData.action", param, callbackInitMonthlyData);
		
		$('#tab-result-cost a[href="#tab-monthly-data"]').tab('show'); 
	});
	
	
	$('#tab-result-cost a[href="#tab-unit-cost"]').click(function (e) {
		e.preventDefault();
			
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		//asyncronus invoke
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initUnitCostProduct.action", param, callbackInitUnitCostProduct);
						
		
		$('#tab-result-cost a[href="#tab-unit-cost"]').tab('show'); 
	});
	
	
	
	
	
	$('#tab-result-cost a[href="#tab-std-detail"]').click(function (e) {
		e.preventDefault();
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initStandarDetail.action", param, callbackInitStandarDetail);
		
	})
	
	
	
	
	$('#tab-summary-index a[href="#tab-units"]').click(function (e) {
		e.preventDefault();
		
		$('#loading-local').show();
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initSummary.action", param, callbackInitSummaryUnits);		
		
		$('#tab-summary-index a[href="#tab-units"]').tab('show'); 
	})
	
	$('#tab-summary-index a[href="#tab-fob"]').click(function (e) {
		e.preventDefault();
		
		$('#loading-local').show();
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initSummary.action", param, callbackInitSummaryFobs);
		
		$('#tab-summary-index a[href="#tab-fob"]').tab('show'); 
	})
	
	$('#tab-summary-index a[href="#tab-duties"]').click(function (e) {
		e.preventDefault();
		
		$('#loading-local').show();
		
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		ajaxInvokeMethods.postAjaxCallback("initSummary.action", param, callbackInitSummaryDuties);
		
		$('#tab-summary-index a[href="#tab-duties"]').tab('show'); 
	})
	
	$('#tab-summary-index a[href="#tab-freights"]').click(function (e) {
		e.preventDefault();
		
		$('#loading-local').show();
		
		var idBaseStage = $('#result-base-stage-id').val();
		var idNewStage  = $('#result-new-stage-id').val();
		
		var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
		
		ajaxInvokeMethods.postAjaxCallback("initSummary.action", param, callbackInitSummaryFreights);
		
		$('#tab-summary-index a[href="#tab-freights"]').tab('show'); 
	});
	
	
	
//	CallBack Sentences
	
	function callbackUpdateStageBudget(data){
		var statusMessageError = data.statusMessageError;
		
		if (statusMessageError != ''){
			
			$('#modal-loading').modal("hide");
			$('#alert-confirm').modal('hide');
			
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(statusMessageError);		
			return false;
		}
		
		window.location.replace("resultCost.action");
	}
	
	
	
	function callbackListCommentStage(data){
		
		var statusMessageError = data.statusMessageError;
		var listCommentStageVO = data.listCommentStageVO;
		
		
		$( "#table-comments-tbody" ).empty();
		for (var j=0; j<listCommentStageVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listCommentStageVO[j].isidCommentStage + "</td>");
			row = row.concat("<td>" + listCommentStageVO[j].dateRegisterCommentStage + "</td>");
			row = row.concat("<td>" + listCommentStageVO[j].labelCommentStage + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-comments-tbody").append(row);
		}
		
		$('#modal-comments-history').modal('show');
		
		$('#commentStage').val("");
	}
	
	
	function callbackUpdateStageForecast(data){
		var statusMessageError = data.statusMessageError;
		
		window.location.replace("resultCost.action");
	}
	
	
	function callbackInitProductivity(data){ 
//		alert("1");
		
		var listProductivityVO = data.listProductivityVO;

		
		var totalUnitStageBProductivity    = 0;
		var totalUnitStageAProductivity    = 0;
		var totalDutiesStageAProductivity  = 0;
		var totalFreightStageAProductivity = 0;
		var totalDutiesStageBProductivity  = 0;
		var totalFreightStageBProductivity = 0;
		var totalInflationProductivity     = 0;
		var totalExchangeProductivity      = 0;
		var totalDutiesProductivity        = 0;
		var totalFreightProductivity       = 0;
		var totalLocalAddProductivity      = 0;
		
		$( "#table-tbody-productivity" ).empty();
		for (var j=0; j<listProductivityVO.length; j++){
			
			
			totalUnitStageBProductivity    += listProductivityVO[j].unitStageBProductivity;
			totalUnitStageAProductivity    += listProductivityVO[j].unitStageAProductivity;
			totalDutiesStageAProductivity  += listProductivityVO[j].dutiesStageAProductivity;
			totalFreightStageAProductivity += (listProductivityVO[j].freightStageAProductivity + listProductivityVO[j].otherStageAProductivity);
			totalDutiesStageBProductivity  += listProductivityVO[j].dutiesStageBProductivity;
			totalFreightStageBProductivity += (listProductivityVO[j].freightStageBProductivity + listProductivityVO[j].otherStageBProductivity);
			totalInflationProductivity     += listProductivityVO[j].inflationProductivity;
			totalExchangeProductivity      += listProductivityVO[j].exchangeProductivity;
			totalDutiesProductivity        += listProductivityVO[j].totalDutiesProductivity;
			totalFreightProductivity       += listProductivityVO[j].totalFreightProductivity;
			totalLocalAddProductivity      += listProductivityVO[j].totalLocalAddProductivity;
			
			
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listProductivityVO[j].idProductProductivity + "</td>");
			row = row.concat("<td>" + listProductivityVO[j].descProductProductivity + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listProductivityVO[j].unitStageBProductivity.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listProductivityVO[j].unitStageAProductivity.formatMoney(0) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + (listProductivityVO[j].dutiesStageBProductivity).formatMoney(3)  + "</td>");
			row = row.concat("<td style='text-align:right;'>" + ((listProductivityVO[j].freightStageBProductivity + listProductivityVO[j].otherStageBProductivity)).formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listProductivityVO[j].dutiesStageAProductivity).formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + ((listProductivityVO[j].freightStageAProductivity + listProductivityVO[j].otherStageAProductivity)).formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + (listProductivityVO[j].inflationProductivity).formatMoney(0) + "</td>"); 
			
			
			row = row.concat("<td style='text-align:right;'>" + listProductivityVO[j].exchangeProductivity.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listProductivityVO[j].totalDutiesProductivity.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listProductivityVO[j].totalFreightProductivity.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listProductivityVO[j].totalLocalAddProductivity.formatMoney(0) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-tbody-productivity").append(row);
		} 
		
		
		
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTALS " + "</strong></td>");
		row = row.concat("<td></td>");
		
		row = row.concat("<td style='text-align:right;'><strong>" + totalUnitStageBProductivity.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalUnitStageAProductivity.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong></strong></td>");
		row = row.concat("<td style='text-align:right;'><strong></strong></td>");
		row = row.concat("<td style='text-align:right;'><strong></strong></td>");
		row = row.concat("<td style='text-align:right;'><strong></strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalInflationProductivity.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalExchangeProductivity.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalDutiesProductivity.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalFreightProductivity.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalLocalAddProductivity.formatMoney(0) + "</strong></td>");
		row = row.concat("<td></td>");  

		row = row.concat("</tr>");
		$("#table-tbody-productivity").append(row);
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-productivity').fixedHeader();
	}
	
	function callbackInitSummaryFreights(data){
		
//		var statusMessageError = data.statusMessageError;
		var listSummaryVO      = data.listSummaryVO;
		
		var arrayAmountsA = new Array();
		var arrayAmountsB = new Array();
//		var arrayAmountsC = new Array();

		var yearToDateFreightsSummary = 0;
		var percentFreightsSummary    = 0;
		
		var sumAmountStageA = 0;
		var sumAmountStageB = 0;
		
		var sumDTE        = 0;
		var sumExExchange = 0;
		
		var freightOthersStageA = 0;
		var freightOthersStageB = 0;
		
		var freightOthersByMonth = 0;
		
		$( "#table-summary-freight-tbody" ).empty();
		for (var j=0; j<listSummaryVO.length; j++){
			
			
			freightOthersStageA = (listSummaryVO[j].amountFreightsStageASummary + listSummaryVO[j].amountOthersStageASummary);
			freightOthersStageB = (listSummaryVO[j].amountFreightsStageBSummary + listSummaryVO[j].amountOthersStageBSummary);
			
			freightOthersByMonth = (listSummaryVO[j].byMonthFreightsSummary + listSummaryVO[j].byMonthOthersSummary);
			
			yearToDateFreightsSummary = yearToDateFreightsSummary + freightOthersByMonth;
			
			
			if (parseInt((freightOthersByMonth - listSummaryVO[j].dteFreightsSummary)) == 0)
				percentFreightsSummary = 0;
			else
				percentFreightsSummary = listSummaryVO[j].amountFreightsStageASummary / (freightOthersByMonth - listSummaryVO[j].dteFreightsSummary);
			
			
			sumAmountStageA = sumAmountStageA + freightOthersStageA;
			sumAmountStageB = sumAmountStageB + freightOthersStageB;
			sumDTE          = sumDTE + listSummaryVO[j].dteFreightsSummary;
			sumExExchange   = sumExExchange + (freightOthersByMonth - listSummaryVO[j].dteFreightsSummary);
			
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listSummaryVO[j].monthSummary + "</td>");
			row = row.concat("<td style='text-align:right;'>" + freightOthersStageB.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + freightOthersStageA.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + freightOthersByMonth.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + yearToDateFreightsSummary.formatMoney(0) + "</td>");
			
			
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].dteFreightsSummary.formatMoney(0) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + (freightOthersByMonth - listSummaryVO[j].dteFreightsSummary).formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + percentFreightsSummary.formatMoney(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-summary-freight-tbody").append(row);
			
			arrayAmountsA[j] = parseInt(freightOthersStageA);
			arrayAmountsB[j] = parseInt(freightOthersStageB);
//			arrayAmountsC[j] = parseInt(listSummaryVO[j].amountFreightsStageCSummary);
		}
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTAL" + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageA.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumDTE.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumExExchange.formatMoney(0) + "</strong></td>");
		row = row.concat("<td></td>");
		row = row.concat("</tr>");
		$("#table-summary-freight-tbody").append(row);
		
		
		chartSummaryFreights.series[0].setData(arrayAmountsB);
		chartSummaryFreights.series[1].setData(arrayAmountsA);
//		chartSummaryFreights.series[2].setData(arrayAmountsC);
		
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
		
		$(window).resize();
		$('.t-f-h-freights').fixedHeader();
		
	}
	
	
	function callbackInitSummaryDuties(data){
		
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
			
			
			if (parseInt((listSummaryVO[j].byMonthDutiesSummary - listSummaryVO[j].dteDutiesSummary)) == 0)
				percentDutiesSummary = 0;
			else
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
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumAmountStageA.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumDTE.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumExExchange.formatMoney(0) + "</strong></td>");
		row = row.concat("<td></td>");
		row = row.concat("</tr>");
		$("#table-summary-duties-tbody").append(row);
		
		
		chartSummaryDuties.series[0].setData(arrayAmountsB);
		chartSummaryDuties.series[1].setData(arrayAmountsA);
//		chartSummaryDuties.series[2].setData(arrayAmountsC);
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
		$(window).resize();
		$('.t-f-h-duties').fixedHeader();
	}
	
	
	
	function callbackInitSummaryFobs(data){
		
//		var statusMessageError = data.statusMessageError;
		var listSummaryVO      = data.listSummaryVO;
		
		
		var arrayAmountsA = new Array();
		var arrayAmountsB = new Array();
		var arrayAmountsC = new Array();

		var yearToDateFobsSummary = 0;
		var percentFobsSummary    = 0;
		
		var sumAmountStageA = 0;
		var sumAmountStageB = 0;
		
		$( "#table-summary-fobs-tbody" ).empty();
		for (var j=0; j<listSummaryVO.length; j++){
			
			yearToDateFobsSummary = yearToDateFobsSummary + listSummaryVO[j].byMonthFobsSummary;
			
			percentFobsSummary = (listSummaryVO[j].amountFobsStageBSummary - listSummaryVO[j].amountFobsStageASummary) * 100 / listSummaryVO[j].amountFobsStageBSummary;
			
			
			sumAmountStageA = sumAmountStageA + listSummaryVO[j].amountFobsStageASummary;
			sumAmountStageB = sumAmountStageB + listSummaryVO[j].amountFobsStageBSummary;
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listSummaryVO[j].monthSummary + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountFobsStageBSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountFobsStageASummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].byMonthFobsSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + yearToDateFobsSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + percentFobsSummary.toFixed(2) + "</td>");
			
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
		
		
		chartSummaryFobs.series[0].setData(arrayAmountsB);
		chartSummaryFobs.series[1].setData(arrayAmountsA);
//		chartSummaryFobs.series[2].setData(arrayAmountsC);
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
		$(window).resize();
		$('.t-f-h-fob').fixedHeader();
	}
	
	
	
	function callbackApplyFilterProduct(data){
		
		
		$('#tab-result-cost a[href="#tab-summary"]').tab('show');
		$('#tab-summary-index a[href="#tab-units"]').tab('show'); 
		
		
		$("#tab-summary-index a[href='#tab-units']").trigger("click");
	}
	
	
	function callbackUpdateStageApproveForecast(data){
		
		var statusMessageError = data.statusMessageError;
		if (statusMessageError != ''){
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal("hide");
			
			$('#alert-errors').modal('show');
			
			$('#alert-errors-content').html(statusMessageError);		
			return false;
		}
		window.location.replace("resultCost.action");
	}
	
	
	function callbackUpdateStageApproveBudget(data){
		
		var statusMessageError = data.statusMessageError;
		if (statusMessageError != ''){
			
			$('#alert-confirm').modal('hide');
			$('#modal-loading').modal("hide");
			
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(statusMessageError);		
			return false;
		}
		window.location.replace("resultCost.action");
	}
	

	function callbackObtieneIdStatusStageNew(data){
		
		
		var labelStatusStageNew  = data.labelStatusStageNew;
		
		$('#strong-label-stage-new').empty();
		$('#strong-label-stage-new').append(labelStatusStageNew);
		
	}
	
	function callbackObtieneIdStatusStageBase(data){
		
		var idStatusStageBase    = data.idStatusStageBase;
		var labelStatusStageBase = data.labelStatusStageBase;
		
		
		$( "#divButtoms" ).empty();
		
		if (idStatusStageBase == 2){
		
			var btnRetry = "<button class='btn' type='button' id='btnRetry'>Retry</button>";
			$("#divButtoms").append(btnRetry);
			
			var btnForecast = "<button class='btn btn-success' type='button' id='btnForecast'>Forecast</button>";
			$("#divButtoms").append(btnForecast);
			
			var btnBudget   = "<button class='btn btn-success' type='button' id='btnBudget'>Budget</button>";
			$("#divButtoms").append(btnBudget);
			
			$('#btnRetry').on('click', function() {
				window.location.replace("changeStage.action");
			});
			
			
			$('#btnForecast').on('click', function() {
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('This action will generate a new Forecast stage(Can be removed later), are you sure?');
				$('#alert-confirm-acept').on('click', function() {
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
					ajaxInvokeMethods.postAjaxCallback("updateStageForecast.action", param, callbackUpdateStageForecast);
				});
			});
			
			
			$('#btnBudget').on('click', function() {
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('This action will generate a new Budget stage(Can be removed later), are you sure? ');
				$('#alert-confirm-acept').on('click', function() {
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
					ajaxInvokeMethods.postAjaxCallback("updateStageBudget.action", param, callbackUpdateStageBudget);
				});
			});
		}
		
		if (idStatusStageBase == 4){
			
			var btnRetry = "<button class='btn' type='button' id='btnRetry'>Retry</button>";
			$("#divButtoms").append(btnRetry);
			
			var btnForecastApprove = "<button class='btn btn-success' type='button' id='btnToApproveForecast'>To Approve</button>";
			$("#divButtoms").append(btnForecastApprove);
			
			
			$('#btnRetry').on('click', function() {
				window.location.replace("changeStage.action");
			});
			
			$('#btnToApproveForecast').on('click', function() {
				
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('Are you sure to approve the Forecast stage?, This will generate a forecast process slope');
				$('#alert-confirm-acept').on('click', function() {
					
					$('#alert-confirm').modal('hide');
					$('#modal-loading').modal({shown:true,  keyboard: false });
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
					ajaxInvokeMethods.postAjaxCallback("updateStageToApproveForecast.action", param, callbackUpdateStageApproveForecast);
				});
			});
		}
		
		if (idStatusStageBase == 9){
			
			var btnReject = "<button class='btn btn-danger' type='button' id='btnRejectForecast'>Reject</button>";
			$("#divButtoms").append(btnReject);
			
			var btnForecastApprove = "<button class='btn btn-success' type='button' id='btnApproveForecast'>Approve</button>";
			$("#divButtoms").append(btnForecastApprove);
			
			
			$('#btnRejectForecast').on('click', function() {
				
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('Are you sure to reject the Forecast stage?, This will generate a forecast process');
				$('#alert-confirm-acept').on('click', function() {
					
					$('#alert-confirm').modal('hide');
					$('#modal-loading').modal({shown:true,  keyboard: false });
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage;
					ajaxInvokeMethods.postAjaxCallback("updateStageRejectForecast.action", param, callbackUpdateStageApproveForecast);
				});
			});
			
			$('#btnApproveForecast').on('click', function() {
				
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('Are you sure to approve the Forecast stage?, This will generate a forecast process closure');
				$('#alert-confirm-acept').on('click', function() {
					
					$('#alert-confirm').modal('hide');
					$('#modal-loading').modal({shown:true,  keyboard: false });
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
					ajaxInvokeMethods.postAjaxCallback("updateStageApproveForecast.action", param, callbackUpdateStageApproveForecast);
				});
			});
		}
		
		
		if (idStatusStageBase == 7){
			
			var btnRetry = "<button class='btn' type='button' id='btnRetry'>Retry</button>";
			$("#divButtoms").append(btnRetry);
			
			var btnBudget = "<button class='btn btn-success' type='button' id='btnToApproveBudget'>To Approve</button>";
			$("#divButtoms").append(btnBudget);
			
			
			$('#btnToApproveBudget').on('click', function() {
				
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('Are you sure to approve the Budget stage?, This will generate a budget process slope');
				$('#alert-confirm-acept').on('click', function() {
					
					$('#alert-confirm').modal('hide');
					$('#modal-loading').modal({shown:true,  keyboard: false });
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
					ajaxInvokeMethods.postAjaxCallback("updateStageToApproveBudget.action", param, callbackUpdateStageApproveBudget);
				});
			});
			
			$('#btnRetry').on('click', function() {
				window.location.replace("changeStage.action");
			});
		}
			
			
		if (idStatusStageBase == 10){
			
			var btnReject = "<button class='btn btn-danger' type='button' id='btnRejectBudget'>Reject</button>";
			$("#divButtoms").append(btnReject);
			
			var btnBudget = "<button class='btn btn-success' type='button' id='btnApproveBudget'>Approve</button>";
			$("#divButtoms").append(btnBudget);
			
			
			$('#btnApproveBudget').on('click', function() {
				
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('Are you sure to approve the Budget stage?, This will generate a budget process closure');
				$('#alert-confirm-acept').on('click', function() {
					
					$('#alert-confirm').modal('hide');
					$('#modal-loading').modal({shown:true,  keyboard: false });
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage+"&idNewStage="+idNewStage+"";
					ajaxInvokeMethods.postAjaxCallback("updateStageApproveBudget.action", param, callbackUpdateStageApproveBudget);
				});
			});
		
		
			$('#btnRejectBudget').on('click', function() {
				
				$('#alert-confirm').modal('show');
				$('#alert-confirm-content').html('Are you sure to reject the Budget stage?, This will generate a budget process');
				$('#alert-confirm-acept').on('click', function() {
					
					$('#alert-confirm').modal('hide');
					$('#modal-loading').modal({shown:true,  keyboard: false });
					
					var idBaseStage = $('#result-base-stage-id').val();
					var idNewStage  = $('#result-new-stage-id').val();
					
					var param = "idBaseStage="+idBaseStage;
					ajaxInvokeMethods.postAjaxCallback("updateStageRejectBudget.action", param, callbackUpdateStageApproveBudget);
				});
			});
		}
		
		$('#strong-label-stage-base').empty();
		$('#strong-label-stage-base').append(labelStatusStageBase);

		
//		$("#tab-summary-index a[href='#tab-units']").trigger("click");
		$('#tab-result-cost a[href="#tab-std-detail"]').trigger("click");
	}
	
	
	
	function callbackInitSummaryUnits(data){
		
		var statusMessageError = data.statusMessageError;
		
		if (statusMessageError != "")
			alert(statusMessageError);
		
		var listSummaryVO  = data.listSummaryVO;
		
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
			
			percentUnitSummary = (listSummaryVO[j].amountUnitStageBSummary - listSummaryVO[j].amountUnitStageASummary ) * 100 / listSummaryVO[j].amountUnitStageASummary;
			
			sumAmountStageA = sumAmountStageA + listSummaryVO[j].amountUnitStageASummary;
			sumAmountStageB = sumAmountStageB + listSummaryVO[j].amountUnitStageBSummary;
			
			var row = "";
			row = row.concat("<tr>");
			 
			row = row.concat("<td>" + listSummaryVO[j].monthSummary + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountUnitStageBSummary.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listSummaryVO[j].amountUnitStageASummary.formatMoney(0) + "</td>");
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
		
		
		
		
		
		
		
		chartSummaryUnits.series[0].setData(arrayAmountsB);
		chartSummaryUnits.series[1].setData(arrayAmountsA);
//		chartSummaryUnits.series[2].setData(arrayAmountsC);
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
		$(window).resize();
		$('.t-f-h-units').fixedHeader();
		
	}
	
	
	
	
	function callbackInitStandarDetail(data){
		
		var statusMessageError = data.statusMessageError;
		var listStandardDetailVO  = data.listStandardDetailVO;
		
		
		var arrayAmountsA1 = [];
		var arrayAmountsA2 = [];
		
		
		var arrayAmountsB1 = [];
		var arrayAmountsB2 = [];
		
		
		var arrayAmountsC1 = [];
		var arrayAmountsC2 = [];
		
		var acum = 0;
		
		var countSubCostB1 = 0; 
		var countSubCostB2 = 0;
		
		var countCostC1 = 0; 
		var countCostC2 = 0; 
		
		//nuevo
		var arrayAmountsD1 = [];
		var arrayAmountsD2 = [];
		//nuevo
		
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
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].dteAmoutStandarDetail.formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].exchangeAmoutStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].percentAmoutStandarDetail.toFixed(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-std-detail-tbody").append(row);
			
			
			

			
			
			//Acumulador del total para promediar
			acum = acum + listStandardDetailVO[j].nominalAmoutStandarDetail;
			
			//Arreglo para el detalle del costo y graficarlo
			
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
			if (arrayAmountsB1.length == 0)
				arrayAmountsB1[countSubCostB1++] = [listStandardDetailVO[j].subTypeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageAStandarDetail)];
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
			
			if (arrayAmountsB2.length == 0)
				arrayAmountsB2[countSubCostB2++] = [listStandardDetailVO[j].subTypeCostStandarDetail, parseInt(listStandardDetailVO[j].amountStageBStandarDetail)];
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
		chartDetailCost.series[0].setData(arrayAmountsA2);
		chartDetailCost.series[1].setData(arrayAmountsA1);
		
		
		//Sub Type Cost
		chartSubTypeCost.series[0].setData(arrayAmountsB2);
		chartSubTypeCost.series[1].setData(arrayAmountsB1);
		

		//Type Cost
		chartTypeCost.series[0].setData(arrayAmountsC2);
		chartTypeCost.series[1].setData(arrayAmountsC1);
		
//		//nuevo
		for(var k=0; k<arrayAmountsB1.length;k++)
			if (arrayAmountsB1[k][0] != 'INTERCOMPANY')
				arrayAmountsD1[k-1] = arrayAmountsB1[k];
		
		chartDetailActualPercent.series[0].setData(arrayAmountsD1);
		
		
		for(var k=0; k<arrayAmountsB2.length;k++)
			if (arrayAmountsB2[k][0] != 'INTERCOMPANY')
				arrayAmountsD2[k-1] = arrayAmountsB2[k];		
		
		chartDetailForecastPercent.series[0].setData(arrayAmountsD2);
//		//nuevo
		
		
		$('#tab-result-cost a[href="#tab-std-detail"]').tab('show'); 
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
		
		
		
		$(window).resize();
		$('.t-f-h-std-detail').fixedHeader();
		
	}
	
	
	function callbackInitImpactAnalisysStage(data){
		
		var statusMessageError = data.statusMessageError;
		var listStandardDetailVO  = data.listStandardDetailVO;
		
		
		var totalAmountStageAStandarDetail = 0;
		var totalAmountStageBStandarDetail = 0;
		var totalNominalAmoutStandarDetail = 0;
		$( "#table-impact-analisys-tbody" ).empty();
		for (var j=0; j<listStandardDetailVO.length; j++){
			
			
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listStandardDetailVO[j].typeCostStandarDetail + "</td>");
			row = row.concat("<td>" + listStandardDetailVO[j].subTypeCostStandarDetail + "</td>");
			row = row.concat("<td>" + listStandardDetailVO[j].nameCostStandarDetail + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].amountStageBStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].amountStageAStandarDetail.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listStandardDetailVO[j].nominalAmoutStandarDetail.formatMoney(0) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#table-impact-analisys-tbody").append(row);
			
			
			totalAmountStageBStandarDetail  += listStandardDetailVO[j].amountStageBStandarDetail;
			totalAmountStageAStandarDetail  += listStandardDetailVO[j].amountStageAStandarDetail;
			totalNominalAmoutStandarDetail  += listStandardDetailVO[j].nominalAmoutStandarDetail;
		}
		
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>" + "TOTALS without FOB" + "</strong></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td></td>");
		
		row = row.concat("<td style='text-align:right;'><strong>" + totalAmountStageBStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalAmountStageAStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totalNominalAmoutStandarDetail.formatMoney(0) + "</strong></td>");
		row = row.concat("<td></td>");  

		row = row.concat("</tr>");
		$("#table-impact-analisys-tbody").append(row);
		
		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-impact-analysis').fixedHeader();
	}
	
	
	
	
	
	function callbackInitMonthlyData(data){
		
		var statusMessageError = data.statusMessageError;
		var listMonthlyDataVO  = data.listMonthlyDataVO;
		
		
		var sumUnitStageA = 0;
		var sumUnitStageB = 0;
		
		var sumFobStageA = 0;
		var sumFobStageB = 0;
		
		var sumDutiesStageA = 0;
		var sumDutiesStageB = 0;
		var sumDutiesDte = 0;
		var sumDutiesExchange = 0;
		
		var sumFreightOtherStageA = 0;
		var sumFreightOtherStageB = 0;
		var sumFreightDte = 0;
		var sumFreightExchange = 0;
		
		var totVarUnits    = 0;
		var totVarFobs     = 0;
		var totVarDuties   = 0;
		var totVarFreights = 0;
		
		$( "#monthly-data-table-tbody" ).empty();
		for (var j=0; j<listMonthlyDataVO.length; j++){
			
			sumUnitStageA = sumUnitStageA + listMonthlyDataVO[j].stageAUnitMonthly;
			sumUnitStageB = sumUnitStageB + listMonthlyDataVO[j].stageBUnitMonthly;
			
			sumFobStageA = sumFobStageA + listMonthlyDataVO[j].stageAFOBsMonthly;
			sumFobStageB = sumFobStageB + listMonthlyDataVO[j].stageBFOBsMonthly;
			
			
			sumDutiesStageA = sumDutiesStageA + listMonthlyDataVO[j].stageADutiesMonthly;
			sumDutiesStageB = sumDutiesStageB + listMonthlyDataVO[j].stageBDutiesMonthly;
			
			sumDutiesDte = sumDutiesDte + listMonthlyDataVO[j].dteDutiesMonthly;
			sumDutiesExchange = sumDutiesExchange + listMonthlyDataVO[j].exchangeDutiesMonthly;
			
			
			sumFreightOtherStageA = sumFreightOtherStageA + listMonthlyDataVO[j].stageAFreightMonthly;
			sumFreightOtherStageB = sumFreightOtherStageB + listMonthlyDataVO[j].stageBFreightMonthly;	
			sumFreightDte = sumFreightDte + listMonthlyDataVO[j].dteFreightMonthly;	
			sumFreightExchange = sumFreightExchange + listMonthlyDataVO[j].exchangeFreightMonthly;	
			
			
			totVarUnits    =  totVarUnits    + listMonthlyDataVO[j].unitVarianceMonthly;
			totVarFobs     =  totVarFobs     + listMonthlyDataVO[j].fOBsVarianceMonthly;
			totVarDuties   =  totVarDuties   + listMonthlyDataVO[j].dutiesVarianceMonthly;
			totVarFreights =  totVarFreights + listMonthlyDataVO[j].freightVarianceMonthly;
			
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listMonthlyDataVO[j].idProductMonthly + "</td>");
			row = row.concat("<td>" + listMonthlyDataVO[j].descProductMonthly + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageBUnitMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageAUnitMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].unitVarianceMonthly.toFixed(0) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageBFOBsMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageAFOBsMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].fOBsVarianceMonthly.toFixed(0) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageBDutiesMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageADutiesMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].dutiesVarianceMonthly.toFixed(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].dteDutiesMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].exchangeDutiesMonthly.formatMoney(0) + "</td>");
			
			if (parseInt(listMonthlyDataVO[j].exchangeDutiesMonthly) == 0)
				row = row.concat("<td style='text-align:right;'>" + 0 + "</td>");
			else
				row = row.concat("<td style='text-align:right;'>" + (listMonthlyDataVO[j].exchangeDutiesMonthly / listMonthlyDataVO[j].stageADutiesMonthly * 100).formatMoney(2) + "</td>");
				
			
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageBFreightMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].stageAFreightMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].freightVarianceMonthly.toFixed(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].dteFreightMonthly.formatMoney(0) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listMonthlyDataVO[j].exchangeFreightMonthly.formatMoney(0) + "</td>");
			
			if (parseInt(listMonthlyDataVO[j].exchangeFreightMonthly) == 0)
				row = row.concat("<td style='text-align:right;'>" + 0 + "</td>");
			else
				row = row.concat("<td style='text-align:right;'>" + (listMonthlyDataVO[j].exchangeFreightMonthly / listMonthlyDataVO[j].stageAFreightMonthly * 100).formatMoney(2) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#monthly-data-table-tbody").append(row);
		}
		
		row = "";
		row = row.concat("<tr>");
		row = row.concat("<td><strong>TOTAL</strong></td>");
		row = row.concat("<td></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumUnitStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumUnitStageA.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totVarUnits.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td style='text-align:right;'><strong>" + sumFobStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumFobStageA.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totVarFobs.formatMoney(0) + "</strong></td>");
		
		row = row.concat("<td style='text-align:right;'><strong>" + sumDutiesStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumDutiesStageA.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totVarDuties.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumDutiesDte.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumDutiesExchange.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'></td>");
		
		row = row.concat("<td style='text-align:right;'><strong>" + sumFreightOtherStageB.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumFreightOtherStageA.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + totVarFreights.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumFreightDte.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'><strong>" + sumFreightExchange.formatMoney(0) + "</strong></td>");
		row = row.concat("<td style='text-align:right;'></td>");
		
		row = row.concat("</tr>");
		
		$("#monthly-data-table-tbody").append(row);
		
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-monthly-data').fixedHeader();
		
	}
	
	function callbackInitUnitCostProduct(data){
		
//		localTransport,temporalWarehousing,ivaImports,presentation,busines,shipmentType,itemClass,transpMode,hubCode,localCode,productDescription,fob,duties,freight,other
		
		var statusMessageError = data.statusMessageError;
		var listUnitCostProductVO  = data.listUnitCostProductVO;
		
		var totalFobUSD = 0;
		var totalDutiesUSD = 0;
		var totalFreightUSD = 0;
		var totalOtherUSD = 0;
		
		var totalFobLC = 0;
		var totalDutiesLC = 0;
		var totalFreightLC = 0;
		var totalOtherLC = 0;
		
		
		$( "#unit-cost-tbody" ).empty();
		for (var j=0; j<listUnitCostProductVO.length; j++){
			
			totalFobUSD = totalFobUSD + listUnitCostProductVO[j].fobUnitCostProduct;
			totalDutiesUSD = totalDutiesUSD + listUnitCostProductVO[j].dutiesUnitCostProduct;
			totalFreightUSD = totalFreightUSD + listUnitCostProductVO[j].freightUnitCostProduct;
			totalOtherUSD = totalOtherUSD + listUnitCostProductVO[j].otherUnitCostProduct;
			
			totalFobLC = totalFobLC + listUnitCostProductVO[j].fobUnitCostLCProduct;
			totalDutiesLC = totalDutiesLC + listUnitCostProductVO[j].dutiesUnitCostLCProduct;
			totalFreightLC = totalFreightLC + listUnitCostProductVO[j].freightUnitCostLCProduct;
			totalOtherLC = totalOtherLC + listUnitCostProductVO[j].otherUnitCostLCProduct;
			
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listUnitCostProductVO[j].localCodeUnitCostProduct + "</td>");
			row = row.concat("<td>" + listUnitCostProductVO[j].hubCodeUnitCostProduct + "</td>");			
			row = row.concat("<td>" + listUnitCostProductVO[j].productDescriptionUnitCostProduct + "</td>");
			
			row = row.concat("<td>" + listUnitCostProductVO[j].localTransportUnitCostProduct + "</td>");
			row = row.concat("<td>" + listUnitCostProductVO[j].temporalWarehousingUnitCostProduct + "</td>");
			row = row.concat("<td>" + listUnitCostProductVO[j].ivaImportsUnitCostProduct + "</td>");
			row = row.concat("<td>" + listUnitCostProductVO[j].presentationUnitCostProduct + "</td>");
			row = row.concat("<td>" + listUnitCostProductVO[j].businesUnitCostProduct + "</td>");
			row = row.concat("<td>" + listUnitCostProductVO[j].shipmentTypeUnitCostProduct + "</td>");
	//		row = row.concat("<td>" + listUnitCostProductVO[j].itemClassUnitCostProduct + "</td>");
			row = row.concat("<td>" + listUnitCostProductVO[j].transpModeUnitCostProduct + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].fobUnitCostProduct.formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].dutiesUnitCostProduct.formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].freightUnitCostProduct.formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].otherUnitCostProduct.formatMoney(3) + "</td>");
			
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].fobUnitCostLCProduct.formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].dutiesUnitCostLCProduct.formatMoney(3) + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].freightUnitCostLCProduct.formatMoney(3)+ "</td>");
			row = row.concat("<td style='text-align:right;'>" + listUnitCostProductVO[j].otherUnitCostLCProduct.formatMoney(3) + "</td>");
			
			row = row.concat("</tr>");
			
			$("#unit-cost-tbody").append(row);
		}
		
			row = "";
			row = row.concat("<tr>");
			row = row.concat("<td><strong>TOTAL</strong></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td></td>");
			row = row.concat("<td style='text-align:right;'><strong>" + totalFobUSD.formatMoney(3) + "</strong></td>");
			row = row.concat("<td style='text-align:right;'><strong>" + totalDutiesUSD.formatMoney(3) + "</strong></td>");
			row = row.concat("<td style='text-align:right;'><strong>" + totalFreightUSD.formatMoney(3) + "</strong></td>");
			row = row.concat("<td style='text-align:right;'><strong>" + totalOtherUSD.formatMoney(3) + "</strong></td>");
			
			row = row.concat("<td style='text-align:right;'><strong>" + totalFobLC.formatMoney(3) + "</strong></td>");
			row = row.concat("<td style='text-align:right;'><strong>" + totalDutiesLC.formatMoney(3) + "</strong></td>");
			row = row.concat("<td style='text-align:right;'><strong>" + totalFreightLC.formatMoney(3) + "</strong></td>");
			row = row.concat("<td style='text-align:right;'><strong>" + totalOtherLC.formatMoney(3) + "</strong></td>");
		
		
			row = row.concat("</tr>");
			
			$("#unit-cost-tbody").append(row);
			
			
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-unit-cost').fixedHeader();
		
	}
	
});