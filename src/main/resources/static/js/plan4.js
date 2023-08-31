// 페이지가 로드되었을 때 실행되는 함수
document.addEventListener("DOMContentLoaded", function() {

    // 검색 버튼 클릭 이벤트를 자동으로 트리거하여 검색 결과 출력
    var searchButton = document.getElementById("searchButton");
    searchButton.click();
});

document.getElementById("searchButton").addEventListener("click", function() {
    var selectedAreaCode = document.getElementById("areaCodeSelect").value;
    var selectedContentType = document.getElementById("contentTypeIdSelect").value;


    var currentPage = 1;
    var searchData = []; // 전체 검색 결과를 저장할 배열
    var itemsPerPage = Number.MAX_SAFE_INTEGER;// 한 페이지에 보여줄 결과 개수
	
	
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/search-data?areaCode=" + encodeURIComponent(selectedAreaCode) +
             "&contentType=" + encodeURIComponent(selectedContentType), true);
         

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            searchData = JSON.parse(xhr.responseText);
            showResultsWithLazyLoading();
        }
    };
	

    xhr.send();
	
	

    function showResultsWithLazyLoading() {
        var searchResultsElement = document.getElementById("results");
        searchResultsElement.innerHTML = ""; // 결과 영역 초기화      

        // 현재 페이지에 해당하는 결과만 표시
        var startIndex = (currentPage - 1) * itemsPerPage;
        var endIndex = Math.min(startIndex + itemsPerPage, searchData.length);

        // 검색 결과 생성 코드
        for (let i = startIndex; i < endIndex; i++) {
            let data = searchData[i];
            
            var searchInput = document.getElementById("searchInput");
      		var searchTerm = searchInput.value.toLowerCase(); // 검색어를 소문자로 변환하여 대소문자 구분 없이 검색
      		
            let resultItem = document.createElement("div");
            resultItem.className = "dataList";
            resultItem.style = "display:flex; margin-bottom:10px; padding-bottom:10px; border-bottom:1px solid black; cursor: pointer;";
			
		
			
            let placeimg = data.placeimg;
            let placeName = data.placeName; 
            
            let lazyImage = document.createElement("img");
	        lazyImage.setAttribute("data-src", placeimg); // 지연 로딩을 위해 data-src 속성에 이미지 URL 설정
	        lazyImage.style = "min-width:150px; width:150px; height:120px; background-size: cover;";
	        lazyImage.alt = `${placeName} Image`;
	
	        let h4 = document.createElement("h4");
	        h4.style = "margin-left:15px;";
	        h4.textContent = placeName;
	
	        resultItem.appendChild(lazyImage);
	        resultItem.appendChild(h4);
	                   
            if (placeName.toLowerCase().includes(searchTerm)){
			let placeTel = data.placeTel;
            let placeAddress = data.placeAddress;
            let placeLatitude = data.placeLatitude;
            let placeLongitude = data.placeLongitude;

            resultItem.innerHTML = `
                <img src="${placeimg}" style="min-width:150px; width:150px; height:120px; background-size: cover;" alt="${placeName} Image">
                <h4 style="margin-left:15px; font-weight: bold; font-size:1.25rem;">${placeName}</h4>
            `;
            
            currentData = {
                    placeName: placeName,
                    placeAddress: placeAddress,
                    placeTel: placeTel,
                    placeimg: placeimg,
                    placeLatitude: placeLatitude,
                    placeLongitude: placeLongitude
                };
			
            searchResultsElement.appendChild(resultItem);
            addClickListener(resultItem, data.placeName, data.placeAddress, data.placeTel,data.placeimg, data.placeLatitude, data.placeLongitude);
        	}
        	
        	// 이미지 지연 로딩 처리
		    const lazyImages = document.querySelectorAll("img[data-src]");
		    lazyImages.forEach(lazyImage => {
		        lazyImage.src = lazyImage.getAttribute("data-src");
		        lazyImage.removeAttribute("data-src");
		    });
        }

    }
	
});



function addClickListener(element, placeName, placeAddress, placeTel, placeimg, placeLatitude, placeLongitude) {
    element.addEventListener("click", function() {
        currentData = {
            placeName: placeName,
            placeAddress: placeAddress,
            placeTel: placeTel,
            placeimg: placeimg,
            placeLatitude: placeLatitude,
            placeLongitude: placeLongitude
        };
        showDetail(placeName, placeAddress, placeTel, placeimg, placeLatitude, placeLongitude);
    });
}

 var resultDetailDiv = document.getElementById("resultDetail");
 
// 상세 정보 표시 함수
function showDetail(placeName, placeAddress, placeTel, placeimg, placeLatitude, placeLongitude) {
    resultDetailDiv.innerHTML = `
    
    <div style="min-width:950px; max-width:950px; margin:30px; padding-bottom:20px; display:flex; border-bottom:1px solid black;">
    	<div style="flex-shrink: 0;"> 
    		<!-- 이미지는 flex-shrink 속성을 0으로 지정해서 줄어들지 않게 합니다 -->
        	<img src="${placeimg}" alt="${placeName} Image" style="width:360px; height:240px;">
    	</div>
    	<div style="margin-left:20px; flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
        	<!-- flex: 1은 이 div가 가능한 모든 공간을 차지하게 합니다 -->
        	<h3 style="align-self: flex-start; letter-spacing: 0.1em;">${placeName}</h3>
        	<div style="align-self: flex-start;">
            	<p>주소: ${placeAddress}</p>
            	<p>Tel : ${placeTel}</p>
        	</div>
        	
        	<div class="d-grid gap-2 d-md-flex justify-content-md-end" style="margin-top:20px;">
    			<button onclick="insertContent1()" type="button" class="btn btn-outline-danger">일정 추가하기</button>
    		</div>
    	
    	</div>
	</div>
    <div id="map" style="width:880px; height:570px; margin-left:60px;"></div>
	
    `;
        var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(placeLongitude, placeLatitude), // 지도의 중심좌표
          level: 2, // 지도의 확대 레벨
        };

      var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

      // 마커가 표시될 위치입니다
      var markerPosition = new kakao.maps.LatLng(placeLongitude, placeLatitude);

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        position: markerPosition,
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(map);
}

function insertContent1() {
    
    // 현재 show active 클래스를 가진 tabPane 찾기
    let activeTabPane = document.querySelector(".tab-pane.show.active");
    
    if (!activeTabPane) {
        alert("먼저 일자를 선택해주세요!");
        return;
    }
    
    // dataList 클래스를 가진 새로운 div 생성 및 내용 설정
    let dataListDiv = document.createElement("div");
    dataListDiv.className = "dataList1";
    dataListDiv.style = "display:flex;  padding:10px; border-bottom:1px solid black;";
    
    dataListDiv.innerHTML = `
    <img class="place_img" name="place_img" src="${currentData.placeimg}" style="min-width:150px; width:150px; height:120px; background-size: cover;" alt="${currentData.title} Image">
    <h4 class="placeName" name="placeName" style="margin-left:15px; margin-right:5px; width:170px;">${currentData.placeName}</h4>
    <input type="hidden" class="placeAddress" name="placeAddress" value="${currentData.placeAddress}">
    <input type="hidden" class="placeTel" name="placeTel" value="${currentData.placeTel}">
    <input type="hidden" class="placeLongitude" name="placeLongitude" value="${currentData.placeLongitude}">
    <input type="hidden" class="placeLatitude" name="placeLatitude" value="${currentData.placeLatitude}">
    <button type="button" class="btn btn-outline-secondary align-self-center justify-content-md-end" onclick="deleteContent1(event)" style="height:30px; line-height: 0;">-</button>
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

function deleteContent1(event) {

    // 클릭한 버튼의 가장 가까운 .dataList 부모 요소를 찾음
    let dataListDiv = event.target.closest(".dataList1");
    
    if (dataListDiv) {
        dataListDiv.remove(); // 해당 div 삭제
    } else {
        console.error("dataList 요소를 찾을 수 없습니다.");
    }
}