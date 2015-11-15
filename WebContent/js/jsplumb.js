;(function() {
	
	jsPlumb.ready(function() {
						
		// setup some defaults for jsPlumb.	
		var instance = jsPlumb.getInstance({
			Endpoint : ["Dot", {radius:2}],
			
			HoverPaintStyle : {strokeStyle:"#1e8151", lineWidth:2 },
			ConnectionOverlays : [
				[ "Arrow", { 
					location:1,
					id:"arrow",
                    length:14,
                    foldback:0.8
				} ],
                [ "Label", { label:"", id:"label", cssClass:"aLabel" }]
			],
			Container:"statemachine-demo"
		});

		var windows = jsPlumb.getSelector(".statemachine-demo .w");

        // initialise draggable elements.  
		instance.draggable(windows);

		// suspend drawing and initialise.
		instance.doWhileSuspended(function() {
										
			// make each ".ep" div a source and give it some parameters to work with.  here we tell it
			// to use a Continuous anchor and the StateMachine connectors, and also we give it the
			// connector's paint style.  note that in this demo the strokeStyle is dynamically generated,
			// which prevents us from just setting a jsPlumb.Defaults.PaintStyle.  but that is what i
			// would recommend you do. Note also here that we use the 'filter' option to tell jsPlumb
			// which parts of the element should actually respond to a drag start.
			instance.makeSource(windows, {
				filter:".ep",				// only supported by jquery
				//anchor:"Continuous",
				//connector:[ "StateMachine", { curviness:20 } ],
				anchor:["Continuous", {faces:["left", "right"]}],
				connector:[ "Flowchart", { stub:[40, 60], gap:10, cornerRadius:5, alwaysRespectStubs:true } ],
				connectorStyle:{ strokeStyle:"#5c96bc", lineWidth:2, outlineColor:"transparent", outlineWidth:4 },
				maxConnections:10,
				onMaxConnections:function(info, e) {
					alert("Maximum connections (" + info.maxConnections + ") reached");
				}
			});						

			// initialise all '.w' elements as connection targets.
	        instance.makeTarget(windows, {
				dropOptions:{ hoverClass:"dragHover" },
				//anchor:"Continuous"
				anchor:["Continuous", {faces:["left"]}]
			});
			
			// and finally, make a couple of connections
			instance.connect({ source:"token1", target:"token3" , connector : "Straight", anchor:["Continuous", {faces:["left", "right"]}]});
			instance.connect({ source:"token2", target:"token3" , connector : "Straight", anchor:["Continuous", {faces:["left", "right"]}]});
			instance.connect({ source:"token3", target:"token4" , connector : "Straight", anchor:["Continuous", {faces:["left", "right"]}]});
			instance.connect({ source:"token3", target:"token5" , connector : "Straight", anchor:["Continuous", {faces:["left", "right"]}]});
			instance.connect({ source:"token3", target:"token6" , connector : "Straight", anchor:["Continuous", {faces:["left", "right"]}]});
			instance.connect({ source:"token3", target:"token7" , connector : "Straight", anchor:["Continuous", {faces:["left", "right"]}]});
			
		});
		
		$.each($("div._jsPlumb_overlay"), function(idx, item){
			$(item).hide();
		});
		$.each($("div.ep"), function(idx, item){
			$(item).hide();
		});
	});


})();