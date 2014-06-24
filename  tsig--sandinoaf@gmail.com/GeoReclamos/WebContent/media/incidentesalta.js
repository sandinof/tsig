var map = new OpenLayers.Map("map_container");
var wms = new OpenLayers.Layer.WMS("Montevideo",
"http://localhost:8080/geoserver/TSIG/wms", {
layers : "TSIG:DameMdeo"
});


//Llenar mapa
epsg4326 =  new OpenLayers.Projection("EPSG:4326"); //WGS 1984 projection
projectTo = map.getProjectionObject(); //The map projection (Spherical Mercator)

var jsonResponse;
var vectorLayer = new OpenLayers.Layer.Vector("pepe");
jsonResponse = document.getElementById("formIncidente:lstP").value;

var objResponse = JSON.parse(jsonResponse);
for(var i = 0; i < objResponse.length; i++) {
    var obj = objResponse[i];
    var feature = new OpenLayers.Feature.Vector(		
            new OpenLayers.Geometry.Point(obj.st_x, obj.st_y).transform(epsg4326, projectTo)
           
        );  
    vectorLayer.addFeatures(feature);  
   
}

map.addLayers([wms, vectorLayer]);
var markers = new OpenLayers.Layer.Markers( "Markers" );
map.addLayer(markers);
map.addControl(new OpenLayers.Control.LayerSwitcher());


map.setCenter(new OpenLayers.LonLat(-56.229473170625, -34.818454350979), 10);

map.events.register("click", map, queryDatabase);


function queryDatabase(e) {

	
// Read the map coordinates from the click event
var lonlat = map.getLonLatFromViewPortPx(e.xy);

var lat = document.getElementById("formIncidente:Lat");
lat.value = lonlat.lat;
var long = document.getElementById("formIncidente:Long");
long.value = lonlat.lon;

var jsonResponse;
var flag = false;
jsonResponse= document.getElementById("formIncidente:lstP").value;

var objResponse = JSON.parse(jsonResponse);
for(var i = 0; i < objResponse.length; i++) {
    var obj = objResponse[i];
    console.log(obj.st_y + lat.value + obj.st_x + long.value);
    	 var pointdb = new OpenLayers.Geometry.Point(obj.st_x, obj.st_y).transform(epsg4326, projectTo);
    	 var point = new OpenLayers.Geometry.Point(long.value, lat.value).transform(epsg4326, projectTo);
    	 
    	  var distancia = pointdb.distanceTo(point);
    	  if(distancia <= 0.008007593849688752){   
    		  flag = true;
    		  }
}
if(!flag){
	markers.clearMarkers();

	var size = new OpenLayers.Size(21,25);
	var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
	var icon = new OpenLayers.Icon('media/marker.png', size, offset);
	markers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(lonlat.lon,lonlat.lat),icon));
	markers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(lonlat.lon,lonlat.lat),icon.clone()));

	/*document.getElementById("formIncidente:lstP").value = JSON.stringify(objResponse);*/
}

}