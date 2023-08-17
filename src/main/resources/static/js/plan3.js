// 새로운 영역을 위한 변수들
var areaCodeSelect3 = document.getElementById("areaCodeSelect3");
var contentTypeIdSelect3 = document.getElementById("contentTypeIdSelect3");
var resultsDiv3 = document.getElementById("results3");
var resultDetailDiv3 = document.getElementById("resultDetail3");

var searchButton2 = document.getElementById("searchButton3");

// 사용자가 지역 코드 선택할 때의 이벤트 리스너 추가
// areaCodeSelect3.addEventListener("change", updateContentTypes3);

// 검색 버튼 클릭 시 결과 업데이트
searchButton3.addEventListener("click", updateResults3);

var currentData = null;

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


      // OPTION 요소 생성 및 추가
      var option = document.createElement("option");
      option.value = code;
      option.text = name;
      areaCodeSelect3.appendChild(option);
    }

    // 초기 로드시 지역 코드를 기반으로 콘텐츠 유형 업데이트
    // updateContentTypes2();
  })
  .catch((error) => console.error("지역 코드 호출 오류:", error));

// 지역 코드 선택 시 콘텐츠 유형 목록을 가져오고 셀렉트 박스 업데이트
function updateContentTypes3() {
  contentTypeIdSelect3.innerHTML = ""; // 콘텐츠 유형 목록 초기화

  var selectedAreaCode = areaCodeSelect3.value;
  var contentTypes = [
    {
      id: 1,
      name: "식당",
      url:
        "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=TEST&arrange=O&contentTypeId=39&areaCode=" +
        selectedAreaCode +
        "&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D",
    },
    {
      id: 2,
      name: "숙소",
      url:
        "https://apis.data.go.kr/B551011/KorService1/searchStay1?numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=TEST&areaCode=" +
        selectedAreaCode +
        "&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D",
    },
    {
      id: 3,
      name: "관광지",
      url:
        "https://apis.data.go.kr/B551011/KorService1/areaBasedSyncList1?numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=TEST&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D&listYN=Y&arrange=A&contentTypeId=12&areaCode=" +
        selectedAreaCode,
    },
  ];

  for (var i = 0; i < contentTypes.length; i++) {
    var option = document.createElement("option");
    option.value = contentTypes[i].id;
    option.text = contentTypes[i].name;
    contentTypeIdSelect3.appendChild(option);
  }

  // 초기 로드시 콘텐츠 결과 업데이트
  // updateResults2();
}

// 콘텐츠 유형 선택 시 결과 업데이트
function updateResults3() {
    resultsDiv3.innerHTML = ""; // 결과 영역 초기화

    let selectedContentTypeId = contentTypeIdSelect3.value;
    let selectedAreaCode = areaCodeSelect3.value;

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

                //위도 경도
        		let mapx = item.getElementsByTagName("mapx")[0].textContent;
        		let mapy = item.getElementsByTagName("mapy")[0].textContent;
        		
        		console.log(item);

                let resultElement = document.createElement("div");
                resultElement.innerHTML = `
                    <div class="dataList" style="display:flex; margin-bottom:10px; padding-bottom:10px; border-bottom:1px solid black; cursor: pointer;">
                        <img src="${firstImage}" style="min-width:150px; width:150px; height:120px; background-size: cover;" alt="${title} Image">
                        <h4 style="margin-left:15px;">${title}</h4>
                    </div>
                `;
                
                currentData = {
                    title: title,
                    address: address,
                    tel: tel,
                    firstImage: firstImage,
                    mapx: mapx,
                    mapy: mapy
                };

                addClickListener3(resultElement, title, address, tel, firstImage, mapx, mapy, item);
                resultsDiv3.appendChild(resultElement);
            }
        })
        .catch((error) => console.error("API 호출 오류:", error));
}

function addClickListener3(element, title, address, tel, firstImage, mapx, mapy, itemData) {
    element.addEventListener("click", function() {
		currentData = {
            title: title,
            address: address,
            tel: tel,
            firstImage: firstImage,
            mapx: mapx,
            mapy: mapy
        };
        showDetail3(title, address, tel, firstImage, mapx, mapy);
    });
}

// 상세 정보 표시 함수
function showDetail3(title, address, tel, firstImage, mapx, mapy) {
    resultDetailDiv.innerHTML = `
    
    <div style="min-width:950px; max-width:950px; margin:30px; padding-bottom:20px; display:flex; border-bottom:1px solid black;">
    	<div style="flex-shrink: 0;"> 
    		<!-- 이미지는 flex-shrink 속성을 0으로 지정해서 줄어들지 않게 합니다 -->
        	<img src="${firstImage}" alt="${title} Image" style="width:360px; height:240px;">
    	</div>
    	<div style="margin-left:20px; flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
        	<!-- flex: 1은 이 div가 가능한 모든 공간을 차지하게 합니다 -->
        	<h3 style="align-self: flex-start; letter-spacing: 0.1em; font-weight: bold;">${title}</h3>
        	<div style="align-self: flex-start;">
            	<p>주소: ${address}</p>
            	<p>Tel: ${tel}</p>
        	</div>
        	
        	<div class="d-grid gap-2 d-md-flex justify-content-md-end" style="margin-top:20px;">
    			<button onclick="insertContent()" type="button" class="btn btn-outline-danger">일정 추가하기</button>
    		</div>
    	
    	</div>
	</div>
    <div id="map" style="width:880px; height:570px; margin-left:60px;"></div>
    `;

var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(mapy, mapx), // 지도의 중심좌표
          level: 2, // 지도의 확대 레벨
        };

      var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

      // 마커가 표시될 위치입니다
      var markerPosition = new kakao.maps.LatLng(mapy, mapx);

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        position: markerPosition,
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(map);
}


function insertContent() {
    // 현재 show active 클래스를 가진 tabPane 찾기
    let activeTabPane = document.querySelector(".tab-pane.show.active");
    
    if (!activeTabPane) {
        alert("먼저 일자를 선택해주세요!");
        return;
    }
    
    // dataList 클래스를 가진 새로운 div 생성 및 내용 설정
    let dataListDiv = document.createElement("div");
    dataListDiv.className = "dataList";
    dataListDiv.style = "display:flex;  padding:10px; border-bottom:1px solid black;";
    
    
    dataListDiv.innerHTML = `
    <img src="${currentData.firstImage}" style="min-width:150px; width:150px; height:120px; background-size: cover;" alt="${currentData.title} Image">
    <h4 style="margin-left:15px; margin-right:5px; width:170px;">${currentData.title}</h4>
    <button type="button" class="btn btn-outline-secondary align-self-center" onclick="deleteContent(event)" style="height:30px; line-height: 0;">-</button>
`;

    
    // 해당 tabPane 내의 list-group 선택
    let listGroup = activeTabPane.querySelector(".list-group");
    
    if (listGroup) {
        // list-group 내에 새로운 div 추가
        listGroup.appendChild(dataListDiv);
    } else {
        console.error("list-group을 찾을 수 없습니다.");
    }
}


function deleteContent(event) {
    // 클릭한 버튼의 가장 가까운 .dataList 부모 요소를 찾음
    let dataListDiv = event.target.closest(".dataList");
    
    if (dataListDiv) {
        dataListDiv.remove(); // 해당 div 삭제
    } else {
        console.error("dataList 요소를 찾을 수 없습니다.");
    }
}


