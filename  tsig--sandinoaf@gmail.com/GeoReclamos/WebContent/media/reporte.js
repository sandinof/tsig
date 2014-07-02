function consulta() {
document.getElementById('map_container').innerHTML = "";

var map = new OpenLayers.Map("map_container");

var cantidad = document.getElementById('formReport:cantidad_input').value;

var wms = new OpenLayers.Layer.WMS("Montevideo",
		"http://localhost:8080/geoserver/TSIG/wms", {
		layers : "TSIG:DameMdeo"
		});


var zonas = new OpenLayers.Layer.WMS("OpenLayers WMS",
"http://localhost:8080/geoserver/TSIG/wms?viewparams=cantidad:"+cantidad, {
layers : "TSIG:hitzones",
transparent: "true"
},{
opacity: 0.5
});
map.addLayers([wms, zonas]);

map.setCenter(new OpenLayers.LonLat(-56.229473170625, -34.818454350979), 10);    
}
