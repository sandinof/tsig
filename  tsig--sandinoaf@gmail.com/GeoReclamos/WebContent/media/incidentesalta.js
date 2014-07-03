var map = new OpenLayers.Map("map_container");
var wms = new OpenLayers.Layer.WMS("Montevideo",
"http://localhost:8080/geoserver/TSIG/wms", {
layers : "TSIG:DameMdeo"
});


//Llenar mapa
epsg4326 =  new OpenLayers.Projection("EPSG:4326"); 
projectTo = map.getProjectionObject(); 

var flag = false;
var jsonResponse;
var vectorLayer = new OpenLayers.Layer.Vector("incidentes");
jsonResponse = document.getElementById("formIncidente:lstP").value;
var attributes; 
var objResponse = JSON.parse(jsonResponse);
for(var i = 0; i < objResponse.length; i++) {
    var obj = objResponse[i];
    var feature = new OpenLayers.Feature.Vector(		
            new OpenLayers.Geometry.Point(obj.st_x, obj.st_y).transform(epsg4326, projectTo),
            attributes = {id: obj.incidenteid, descripcion: obj.descripcion, reportado: obj.reportado}
        );  
    vectorLayer.addFeatures(feature);  
   
}

//Add a selector control to the vectorLayer with popup functions
var controls = {
  selector: new OpenLayers.Control.SelectFeature(vectorLayer, { onSelect: createPopup, onUnselect: destroyPopup })
};


function createPopup(feature) {
	
	if(feature.attributes.reportado === 0){
		 
	if(document.getElementById("formIncidente:msUns").value.indexOf(feature.attributes.id) > -1){
		flag = true;
		feature.popup = new OpenLayers.Popup.FramedCloud("pop",
			      feature.geometry.getBounds().getCenterLonLat(),
			      null,
			      '<div class="markerContent">'+feature.attributes.descripcion+'</div>',
			      null,
			      true,
			      function() { controls['selector'].unselectAll(); }
			  );
	
	}
	else
		{
		flag = true;
		
		feature.popup = new OpenLayers.Popup.FramedCloud("pop",
			      feature.geometry.getBounds().getCenterLonLat(),
			      null,
			      '<div class="markerContent">'+feature.attributes.descripcion+' <button type="button" id="'+feature.attributes.id+'" onclick="masuno('+ feature.attributes.id +');return false;">Click me</button> </div>',
			      null,
			      true,
			      function() { controls['selector'].unselectAll(); }
			  ); 
		}
	
	 }else{
		 flag = true;
		 feature.popup = new OpenLayers.Popup.FramedCloud("pop",
			      feature.geometry.getBounds().getCenterLonLat(),
			      null,
			      '<div class="markerContent">'+feature.attributes.descripcion+'</div>',
			      null,
			      true,
			      function() { controls['selector'].unselectAll(); }
			  );
	
	 }
  //feature.popup.closeOnMove = true;
  map.addPopup(feature.popup);

}

function destroyPopup(feature) {
  feature.popup.destroy();
  feature.popup = null;
  //flag = false;
  
}

map.addControl(controls['selector']);
controls['selector'].activate();

function masuno(id){
	document.getElementById(id).style.display = "none";
	document.getElementById("formIncidente:btnGuardarCambios").style.display = "block";
	if(!document.getElementById("formIncidente:msUns").value || 0 === document.getElementById("formIncidente:msUns").value.length){
		document.getElementById("formIncidente:msUns").value = id;
	}else{
		document.getElementById("formIncidente:msUns").value = document.getElementById("formIncidente:msUns").value + "," + id;
	}	
}

map.addLayers([wms, vectorLayer]);
var markers = new OpenLayers.Layer.Markers( "Markers" );
map.addLayer(markers);
map.addControl(new OpenLayers.Control.LayerSwitcher());


map.setCenter(new OpenLayers.LonLat(-56.229473170625, -34.818454350979), 11);

map.events.register("click", map, queryDatabase);

function queryDatabase(e) {

	
// Read the map coordinates from the click event
var lonlat = map.getLonLatFromViewPortPx(e.xy);

var lat = document.getElementById("formIncidente:Lat");
lat.value = lonlat.lat;
var long = document.getElementById("formIncidente:Long");
long.value = lonlat.lon;

if(!flag){
	markers.clearMarkers();
	console.log("haber si hace postback");
	var size = new OpenLayers.Size(21,25);
	var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
	var icon = new OpenLayers.Icon('media/marker.png', size, offset);
	markers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(lonlat.lon,lonlat.lat),icon));
	markers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(lonlat.lon,lonlat.lat),icon.clone()));
	
}
else{flag = false;}
}