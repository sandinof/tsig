var map = new OpenLayers.Map("map_container");

var mvd = new OpenLayers.Layer.WMS("Montevideo",
		"http://localhost:8080/geoserver/TSIG/wms", {
			layers : "TSIG:DameMdeo",
});

var zonas = new OpenLayers.Layer.WMS("OpenLayers WMS",
		"http://localhost:8080/geoserver/TSIG/wms", {
			layers : "TSIG:zonageo",
			transparent : "true"
		},{
			opacity : 0.5
		});

var polygonLayer = new OpenLayers.Layer.Vector("Polygon Layer");

map.addLayers([ mvd, zonas, polygonLayer ]);
map.addControl(new OpenLayers.Control.LayerSwitcher());
map.addControl(new OpenLayers.Control.MousePosition());

drawControls = {
        polygon: new OpenLayers.Control.DrawFeature(polygonLayer, OpenLayers.Handler.Polygon)
};

map.addControl(drawControls['polygon']);
drawControls['polygon'].activate();

map.setCenter(new OpenLayers.LonLat(-56.229473170625, -34.818454350979), 10);

function SavePoligono() {
	var zona = document.getElementById("form:zonaSelec");
	var zonas = drawControls['polygon'].layer;
	var jsonResult = new Array();
	for ( var i = 0; i < zonas.features.length; i++) {
		var zonaArray = zonas.features[i].geometry.getVertices(false);
		var jsonArr = new Array();
		for ( var j = 0; j < zonaArray.length; j++) {
			jsonArr[j] = zonaArray[j].x + " " + zonaArray[j].y;
		}
		jsonArr[jsonArr.length] = zonaArray[0].x + " " + zonaArray[0].y;
		jsonResult[i] = jsonArr;
	}
	zona.value = JSON.stringify(jsonResult);
}