// 새로운 영역을 위한 변수들
var areaCodeSelect2 = document.getElementById("areaCodeSelect2");
var contentTypeIdSelect2 = document.getElementById("contentTypeIdSelect2");
var resultsDiv2 = document.getElementById("results2");
var resultDetailDiv2 = document.getElementById("resultDetail2");
var mapDiv = document.getElementById("map");

var searchButton2 = document.getElementById("searchButton2");

// 사용자가 지역 코드 선택할 때의 이벤트 리스너 추가
// areaCodeSelect2.addEventListener("change", updateContentTypes2);

// 검색 버튼 클릭 시 결과 업데이트
searchButton2.addEventListener("click", updateResults2);

// 초기 로드시 지역 코드 목록을 가져옴
fetch(
  "https://apis.data.go.kr/B551011/KorService1/areaCode1?numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=TEST&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D"
)
  .then((response) => response.text())
  .then((data) => {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(data, "text/xml");

    var items = xmlDoc.getElementsByTagName("item");

    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      var code = item.getElementsByTagName("code")[0].textContent;
      var name = item.getElementsByTagName("name")[0].textContent;

      console.log(item);

      // OPTION 요소 생성 및 추가
      var option = document.createElement("option");
      option.value = code;
      option.text = name;
      areaCodeSelect2.appendChild(option);
    }

    // 초기 로드시 지역 코드를 기반으로 콘텐츠 유형 업데이트
    // updateContentTypes2();
  })
  .catch((error) => console.error("지역 코드 호출 오류:", error));

// 지역 코드 선택 시 콘텐츠 유형 목록을 가져오고 셀렉트 박스 업데이트
function updateContentTypes2() {
  contentTypeIdSelect2.innerHTML = ""; // 콘텐츠 유형 목록 초기화

  var selectedAreaCode = areaCodeSelect2.value;
  var contentTypes = [
    // ... 새로운 영역을 위한 콘텐츠 유형 정보
  ];

  for (var i = 0; i < contentTypes.length; i++) {
    var option = document.createElement("option");
    option.value = contentTypes[i].id;
    option.text = contentTypes[i].name;
    contentTypeIdSelect2.appendChild(option);
  }

  // 초기 로드시 콘텐츠 결과 업데이트
  // updateResults2();
}

// 콘텐츠 유형 선택 시 결과 업데이트
function updateResults2() {
    resultsDiv2.innerHTML = ""; // 결과 영역 초기화

    let selectedContentTypeId = contentTypeIdSelect2.value;
    let selectedAreaCode = areaCodeSelect2.value;

    let contentTypes = {
        1: "39",
        2: "32",
        3: "12",
    };

    let apiUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=TEST&arrange=O&contentTypeId=${contentTypes[selectedContentTypeId]}&areaCode=${selectedAreaCode}&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D`;

    fetch(apiUrl)
        .then((response) => response.text())
        .then((data) => {
            let parser = new DOMParser();
            let xmlDoc = parser.parseFromString(data, "text/xml");

            let items = xmlDoc.getElementsByTagName("item");

            for (let i = 0; i < items.length; i++) {
                let item = items[i];
                let titleElement = item.getElementsByTagName("title")[0];
                let title = titleElement ? titleElement.textContent : "";

                let addressElement = item.getElementsByTagName("addr1")[0];
                let address = addressElement ? addressElement.textContent : "";

                let telElement = item.getElementsByTagName("tel")[0];
                let tel = telElement ? telElement.textContent : "";

                let firstImageElement = item.getElementsByTagName("firstimage")[0];
                let firstImage = firstImageElement ? firstImageElement.textContent : "";

                console.log(title);

                let resultElement = document.createElement("div");
                resultElement.innerHTML = `
                    <div style="display:flex; margin-bottom:10px; padding-bottom:10px; border-bottom:1px solid black; cursor: pointer;">
                        <img src="${firstImage}" style="min-width:150px; width:150px; height:120px; background-size: cover;" alt="${title} Image">
                        <h4 style="margin-left:15px;">${title}</h4>
                    </div>
                `;

                addClickListener2(resultElement, title, address, tel, firstImage);
                resultsDiv2.appendChild(resultElement);
            }
        })
        .catch((error) => console.error("API 호출 오류:", error));
}

function addClickListener2(element, title, address, tel, firstImage) {
    element.addEventListener("click", function() {
        showDetail2(title, address, tel, firstImage);
    });
}

// 상세 정보 표시 함수
function showDetail2(title, address, tel, firstImage) {
    resultDetailDiv2.innerHTML = `
    <div style="min-width:880px; margin:30px; padding-bottom:20px; display:flex; border-bottom:1px solid black;">
    	<div style="flex-shrink: 0;"> 
    		<!-- 이미지는 flex-shrink 속성을 0으로 지정해서 줄어들지 않게 합니다 -->
        	<img src="${firstImage}" alt="${title} Image" style="width:360px; height:240px;">
    	</div>
    	<div style="margin-left:20px; flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
        	<!-- flex: 1은 이 div가 가능한 모든 공간을 차지하게 합니다 -->
        	<h3 style="align-self: flex-start; letter-spacing: 0.1em; font-weight: bold;">${title}</h3>
        	<div style="align-self: flex-start;">
            	<p>Address: ${address}</p>
            	<p>Tel: ${tel}</p>
        	</div>
    	</div>
	</div>
	<div id="map" style="width:80%; height:350px;"></div>
    `;

    // 지도 생성 및 마커 설정
    var mapOptions = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
    };
    
    var map = new kakao.maps.Map(mapDiv, mapOptions);
    
    var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);
    var marker = new kakao.maps.Marker({
        position: markerPosition
    });
    
    marker.setMap(map);
}
