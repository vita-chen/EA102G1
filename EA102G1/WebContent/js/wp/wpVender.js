function init() {
var text_new = document.querySelectorAll('.text_new');
for (var i = 0; i < text_new.length; i++) {
    if (text_new[i].innerText.length > 108) {
        var str = text_new[i].innerText.substring(108);
        var str_new = text_new[i].innerText.replace(str, '...read more');
        text_new[i].innerText = str_new;
    }
}

$('.new_case').each(function(i){
    if(i % 2 == 0){
        $(this).addClass('right_case');
    }
})


var addr = addr2.substring(0,addr2.lastIndexOf('號')+1);
$('iframe').attr('src', 'https://www.google.com/maps/embed/v1/place?key=AIzaSyD7LkDHulMgpoqnxVDobzNsfX0Ceb26t1Y&q=' + addr)

// 建立 Geocoder() 物件
var gc = new google.maps.Geocoder();
var mymap = new google.maps.Map($('#map').get(0), {
    zoom: 15,
    center: { lat: 25.0479, lng: 121.5170 }
});

var addr = '${VenderVO.ven_addr}';

// 用使用者輸入的地址查詢
gc.geocode({ 'address': addr }, function(result, status) {
    // 確認 OK
    if (status == google.maps.GeocoderStatus.OK) {
        var latlng = result[0].geometry.location;
        //  取得查詢結果第0筆中的經緯度物件
        mymap.setCenter(latlng); //將查詢結果設為地圖的中心

        var marker = new google.maps.Marker({
            position: { lat: latlng.lat(), lng: latlng.lng() },
            map: mymap,
            animation: google.maps.Animation.DROP, // DROP掉下來、BOUNCE一直彈跳
            draggable: true // true、false可否拖拉
        });

    }
});

} 
window.addEventListener('load', init);
