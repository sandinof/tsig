var map = new OpenLayers.Map("map_container");
var wms = new OpenLayers.Layer.WMS("Montevideo",
"http://localhost:8080/geoserver/TSIG/wms", {
layers : "TSIG:DameMdeo"
});


//Llenar mapa
epsg4326 =  new OpenLayers.Projection("EPSG:4326"); 
projectTo = map.getProjectionObject(); 

//
var jsonResponse;
var enproceso = new OpenLayers.Layer.Vector("En Proceso");
var asignado = new OpenLayers.Layer.Vector("Asignado");
var desestimado = new OpenLayers.Layer.Vector("Desestimado");
var resuelto = new OpenLayers.Layer.Vector("Resuelto");

jsonResponse = document.getElementById("form:lstP").value;
var attributes; 
var objResponse = JSON.parse(jsonResponse);
for(var i = 0; i < objResponse.length; i++) {
    var obj = objResponse[i];
    var feature = new OpenLayers.Feature.Vector(		
            new OpenLayers.Geometry.Point(obj.x, obj.y).transform(epsg4326, projectTo),
            attributes = {id: obj.incidenteid, descripcion: obj.descripcion, reportado: obj.categorias}
        );  
    if(obj.estado === "EN PROCESO"){
    	enproceso.addFeatures(feature);  
    };
    if(obj.estado === "ASIGNADO"){
    	asignado.addFeatures(feature);  
    };
    if(obj.estado === "DESESTIMADO"){
    	desestimado.addFeatures(feature);  
    };
    if(obj.estado === "RESUELTO"){
    	resuelto.addFeatures(feature);  
    };
  
}



//Add a selector control to the vectorLayer with popup functions
/*var controls = {
  selector: new OpenLayers.Control.SelectFeature(vectorLayer, { onSelect: createPopup, onUnselect: destroyPopup })
};*/

/*map.addControl(controls['selector']);
controls['selector'].activate();*/

map.addLayers([wms, enproceso, asignado, desestimado, resuelto]);
map.addControl(new OpenLayers.Control.LayerSwitcher());


map.setCenter(new OpenLayers.LonLat(-56.229473170625, -34.818454350979), 11);


document.getElementById("form:cargarcontrol").onclick = function() {
	document.getElementById("form:numeroIn").value = id;
};

map.events.register("click", map, queryDatabase);

function queryDatabase(e) {
	var lonlat = map.getLonLatFromViewPortPx(e.xy);
	
	for(var i = 0; i < objResponse.length; i++) {
	    var obj = objResponse[i];
	    var pointdb = new OpenLayers.Geometry.Point(obj.x, obj.y).transform(epsg4326, projectTo);
   	 var point = new OpenLayers.Geometry.Point(lonlat.lon, lonlat.lat).transform(epsg4326, projectTo);
   	 
   	  var distancia = pointdb.distanceTo(point);
   	  if(distancia <= 0.008007593849688752){  
   		document.getElementById("form:zoid").value = obj.zonaid;
   		document.getElementById("form:zonanom").value = obj.descripcionzona;
   		document.getElementById("form:numeroIn").value = obj.incidenteid;
   		document.getElementById("form:descIn").value = obj.descripcion;
   		document.getElementById("form:categoriaIn").value = obj.categorias;
   		document.getElementById("form:estadoIn").value = obj.estado;
   		
   	document.getElementById("form:cargarcontrol").click();
   		
   		  }
	    console.log(obj.st_y + "," + lonlat.lat + "," + obj.st_x + "," + lonlat.lon);
	 
	   }
};

