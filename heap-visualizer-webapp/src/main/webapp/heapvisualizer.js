/*!
 * Java Heap Visualizer
 *
 * Copyright 2011, Ryan Fernandes (https://code.google.com/u/@VBFTRFJWDxFDXgJ4/)
 * Licensed under The MIT License.
 * see license.txt
 *
 */

//The Data Model that holds a Memory Component 
function MemoryComponent() {
	this.name = "";
	this.type = "";
	this.usage = null;
	this.html = null;
	this.MAX_TREND_POINTS=25;
	this.trend = new Array();
	this.performanceColors = ['#c6fdb4','#81ef5d','#52da25'];
	
	this.setUsage = function (u) {
		this.usage = u;
		this.trend.push(Math.floor(u.used/(1024*1024)));
		
		if (this.trend.length > this.MAX_TREND_POINTS) {
			this.trend.splice(0,1);
		}
	}

}


//This object manages the loading, display and refreshing of the Memory Components
//Memory components include heap, non-heap and their respective sub components 
function HeapVizualizer() {
	this.heap				= new MemoryComponent();
	this.nonHeap			= new MemoryComponent();
	this.heapComponents		= new Array(); //list of memorydetails
	this.nonHeapComponents	= new Array(); //list of memorydetails
	this.isInited			= false;

	//jvm monitor callback to refresh/load data
	this.dataReceived  = function(jsondata) {
		var data = jsondata;//$.parseJSON(jsondata);

		this.populateMemoryData(data);

		if (this.isInited == false) {

			this.isInited = true;
		}
		this.updateValues();
	}

	//render the component on the page
	this.updateValues = function () {
		this.refreshData(this.heap);
		this.refreshData(this.nonHeap);
		var _this=this;
		$(_this.heapComponents).each(function() {
			_this.refreshData(this);
		});
		$(_this.nonHeapComponents).each(function() {
			_this.refreshData(this);
		});
	}

	//refresh component data on screen helper
	//draws bullet graphs using the sparkline jQuery API
	this.refreshData = function (component) {
		//helper function
		var formatData = function (component) {
			var f = function (n) {
				return Math.floor(n/(1024*1024))+"MB";
			}
			return	"<table cellspacing=1 cellpadding=0 class='datatable'><tr><td>used:</td><td>"+f(component.usage.used)+"</td><td bgcolor='red'>&nbsp;</td></tr>"+
					"<tr><td>committed:</td><td>"+f(component.usage.committed)+"</td><td bgcolor="+component.performanceColors[0]+">&nbsp;</td></tr>"+
					"<tr><td>max      :</td><td>"+f(component.usage.max)+"</td><td bgcolor='green'>&nbsp;</td></tr></table>";

		}

		var uservalues = [component.usage.used, component.usage.max, component.usage.committed]
		$("#"+this.getSafeID(component.name)+"_plot").sparkline(uservalues, {type: 'bullet', barColor: 'green', targetWidth: '4', width: '100%', performanceColor: 'green',rangeColors: component.performanceColors})
		$("#"+this.getSafeID(component.name)+"_values").html(formatData(component));
		$("#"+this.getSafeID(component.name)+"_trend").sparkline(component.trend, {type: 'line', width:'100%', height: '22px', lineColor: component.performanceColors[2], fillColor:'black'});
	}


	//populate data from server into local object model
	this.populateMemoryData = function(d) {
		this.heap.type		= d.heap.type;
		this.heap.setUsage(d.heap.usage);
		this.heap.name		= d.heap.name
		if (this.heap.html==null) {
			this.heap.html= this.createHTML("",this.heap.name);
			$("#heapdata").prepend(this.heap.html);
		}

		this.nonHeap.type	= d.nonHeap.type;
		this.nonHeap.setUsage(d.nonHeap.usage);
		this.nonHeap.name	= d.nonHeap.name
		if (this.nonHeap.html==null) {
			this.nonHeap.html= this.createHTML("",this.nonHeap.name);
			$("#nonheapdata").prepend(this.nonHeap.html);
		}

		this.refreshComponentData("h",this.heapComponents,d.heapComponents);
		this.refreshComponentData("n",this.nonHeapComponents,d.nonHeapComponents);
	}

	//refresh data values in component
	this.refreshComponentData = function(c, oldComponents, newComponents) {
		if (oldComponents.length > 0) {
			//update components
			for (var x=0;x<oldComponents.length;x++) {
				var curr = oldComponents[x];

				for (var y=0;y<newComponents.length;y++) {
					if (curr.name == newComponents[y].name) {
						curr.setUsage(newComponents[y].usage);
						break;
					}
				}
			}
		}
		else {
			//add components
			var _this = this;
			$(newComponents).each(function () {
				var m = new MemoryComponent();
				m.name = this.name;
				m.type = this.type;
				m.setUsage(this.usage);
				m.html	= _this.createHTML("sub",this.name);
				if (c=="h") {
					$("#heapdataComponents").append(m.html);
				}
				else {
					$("#nonheapdataComponents").append(m.html);
				}
				oldComponents.push(m);
			});
		} 
	}

	//Utility function to create a DOM ID that is valid
	this.getSafeID = function (id) {
		return id.replace(/ /g,"_").replace(/\[/g,"_").replace(/\]/g,"_").replace(/-/g,"_");
	}

	//create html elements for every memory component
	this.createHTML = function (prefix,name) {
		var id= this.getSafeID(name);
		return '<div class="'+prefix+'dataContainer">'+
				'<div id="'+ id +'_name" class="rf-gauge_name">'+name+' </div>'+
				'<div id="'+ id +'_plot"'+'class="rf-gauge_plot"></div>'+
				'<div id="'+ id +'_trend"'+'class="rf-gauge_trend"></div>'+
				'<div id="'+ id +'_values"'+'class="rf-gauge_usage"></div>'+
				'</div>';
	}



}


//Receives JSON data from the server containing information about the
//heap of the currently running JVM
function monitor(viz) {

	$.getJSON('./monitor')
	.success(function(data) { 
		viz.dataReceived(data);
		$("#loader").hide();
	})
	.error(function(jqXHR, textStatus, errorThrown) {$("#logger").html(textStatus+" "+errorThrown);})
	.complete(function() { });

	setTimeout(function(){monitor(viz)},2000); // frequency
}

function getMetaData() {
	
	$.getJSON('./metadata')
	.success(function(data) {
		var msg =	"<b>System:</b> ["+data.osData +"]" + 
					"<b>  JVM:</b> ["+data.jvmData + "]" +
					"<b>  VM ID:</b> ["+data.vmID +"]";
		$("#metadata").prepend(msg);
		$("#metadata").show(1000);
	})
	.error(function(jqXHR, textStatus, errorThrown) {$("#logger").html(textStatus+" "+errorThrown);})
	.complete(function() { });
}

function triggerGC() {
	$.get("./actions?GC=true");
}