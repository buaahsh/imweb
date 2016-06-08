/*!
 * Ext JS Library 3.4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */
Ext.onReady(function(){
    // tabs for the center
    var tabs = new Ext.TabPanel({
        region: 'center',
        margins:'3 3 3 0', 
        activeTab: 0,
        defaults:{autoScroll:true},

        items:[{
            title: 'Bogus Tab',
            html: "123"
        },{
            title: 'Another Tab',
            html:"123"
        },{
            title: 'Closable Tab',
            html: "123",
            closable:true
        }]
    });

    // Panel for the west
    var nav = new Ext.Panel({
        title: 'Navigation',
        region: 'west',
        split: true,
        width: 200,
        collapsible: true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'
    });

    var win = new Ext.Window({
        title: 'Layout Window',
        closable:true,
        width:600,
        height:350,
        //border:false,
        plain:true,
        layout: 'border',

        items: [nav, tabs]
    });

    win.show(this);
});