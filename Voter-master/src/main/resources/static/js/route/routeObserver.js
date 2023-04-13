let mymap;
let markers = [];
let lines = L.polyline([0,0]);
let id = routeid;
// let id = document.querySelector("input[name='id']").value;
// const editB = document.querySelector('#editRoute')
// editB.addEventListener('click',
//     () => window.location.href = "/editRoute/"+routeid+"?routeId="+routeid+"&submit=value")

function printMap(){
    mymap = L.map('mapid').setView([55.752, 37.617], 11);
    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}' +
        '?access_token=pk.eyJ1IjoiZXZlbnRhc2Vydm8iLCJhIjoiY2s2OGcxaWU5MDRtYzNucWZqdXRicnFpMyJ9.HRdmn4ful40N4svL9ix8vA', {
        maxZoom: 18,
        attribution:
            'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1
    }).addTo(mymap);
}

function importMarkers(){
    markersI.forEach(element => {
        marker = new L.Marker([element.latitude, element.longtitude], {draggable:false});
        mymap.addLayer(marker);
        marker.bindTooltip(element.description,
            {
                permanent: true,
                direction: 'right'
            }
        );
        markers.push(marker);
    })
    updateLines();
}

function connectTheDots(data){
    let c = [];
    data.forEach(marker => {
        let x = marker._latlng.lat;
        let y = marker._latlng.lng;
        c.push([x, y]);
    })
    return c;
}

function updateLines(dat){
    if (dat!=null){
        mymap.removeLayer(lines);
        lines = L.polyline(connectTheDots(dat));
        lines.addTo(mymap)
    } else {
        mymap.removeLayer(lines);
        lines = L.polyline(connectTheDots(markers));
        lines.addTo(mymap)
    }
}

printMap();
importMarkers();