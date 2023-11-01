<img src="./images/logo.png" alt="Logo of the project" align="right">

# 배포완료 (수정중)

# 도메인주소
http://ec2-3-34-190-252.ap-northeast-2.compute.amazonaws.com/

# 이젠어때

> 국내 여행계획을 세우고 여행상품을 구매할 수 있는 웹 사이트
> 
> https://docs.google.com/presentation/d/1apK6nsJc7gg_E3sAJbjPiI-DnfH6-_NlUcIixyS5VFU/edit?usp=sharing

## 소개:

이젠어때는 사용자들이 여행을 기획할 때, 여행지의 숙소, 관광지, 맛집의 정보를 확인하고 일정에 맞게 플랜을 기획할 수 있는 웹 사이트입니다.
이에 추가적으로 사용자의 편리함을 위해 자신의 여행플랜을 공유하고, 다른사람들의 플랜을 보는 커뮤니티 공간과, 여행상품을 구매할 수 있는 쇼핑공간이 있도록 이젠어때를 개발하였습니다.

> 여행 플랜 기획 : 자신이 선택한 여행지의 숙소, 관광지, 맛집의 위치등 정보를 보고 일정에 맞게 여행을 계획할 수 있습니다.

> 플랜 공유하기 : Day1, Day2등 하루단위로 완성된 자신의 플랜을 이미지로 내려받을 수 있고, 카카오톡을 통해 지인들에게 공유할 수 있습니다.

> 여행 커뮤니티 : 완성된 자신의 플랜을 공유할 수 있고, 다른사람의 플랜을 참고할 수 있는 공유 커뮤니티 공간이 있습니다.

> 여행상품 구매 : 여행에 꼭 필요한 물품을 구매할 수 있는 공간이 있습니다.

## 팀원

<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/Lee-hye-won"><img src="https://avatars.githubusercontent.com/u/105015513?v=4" width="100px;" alt=""/><br /><sub><b>팀장 : 이혜원</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/minwoochae"><img src="https://avatars.githubusercontent.com/u/130428663?v=4" width="100px;" alt=""/><br /><sub><b>팀원 : 채민우</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/leejiheee"><img src="https://avatars.githubusercontent.com/u/130732295?v=44" width="100px;" alt=""/><br /><sub><b>팀원 : 이지희</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/jhkim1102"><img src="https://avatars.githubusercontent.com/u/130732119?v=4" width="100px;" alt=""/><br /><sub><b>팀원 : 김종화</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/JOONGHWANLEE"><img src="https://avatars.githubusercontent.com/u/130732039?v=4" width="100px;" alt=""/><br /><sub><b>팀원 : 이중환</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>
<hr>

### [참고 HTML템플릿](https://themewagon.github.io/travelo/)

### [요구사항 정의서](https://docs.google.com/spreadsheets/d/150WOxxBdb120Stql9RnWlvUK_vRlqHGH37nTVox6e8Y/edit#gid=0)

### [간트 차트](https://docs.google.com/spreadsheets/d/1WsOu9WPZ1T770cEXPiNKnxmyKw5zxnEFisGoHuBn0dU/edit?pli=1#gid=1115838130)

### [ERD](https://www.erdcloud.com/d/rK5xCRNKjYrEiHbsX)

### [화면설계](https://www.figma.com/file/JXdW6QzP7QEJbPIjqog2jp/Untitled?type=design&node-id=0-1&mode=design&t=Fxcg4JCad8AJw5UW-0)

<hr>

## 📚 목차

* [작업 규칙](#작업-규칙)

<hr>

## 작업 규칙

### 커밋, 브랜치 네이밍 룰

#### 커밋

➕add : 새로운 기능에 대한 커밋

🔧fix : 잘못된 부분 수정

💣build : 빌드 관련 파일 수정에 대한 커밋(application.properties, pom.xml ...)

📝chore : 그 외 자잘한 수정에 대한 커밋(기타 변경)

🔖docs : 문서 수정에 대한 커밋

💥rm : 기능 삭제

👻refactor : 코드 리팩토링에 대한 커밋

🌀ing : 개발 중 커밋 (커밋 기준 ~~ 완료 / ~~ 미완료)

🎉complete : 기능 구현 완료에 대한 커밋

ex)
➕add : 로그인 기능 add

🌀ing : 로그인 기능 완료 / redirect url 미완료

<hr>

#### 브랜치

> `<strong>`절대 main 브랜치에 push 하지 않습니다!! develop 브랜치에 push 합니다!!`</strong>`

##### 브랜치 네이밍 규칙

feature/#{리퀘스트 번호}-{기능분류}-{기능명}

```
ex)
feature/#1-member-login
```

##### 풀 리퀘스트 네이밍 규칙

[#번호] 내용

```
ex)
[#1] 로그인 기능 구현
```

##### 로컬 저장소(내 컴퓨터) 에서 브랜치 생성하는 법

```git
프로젝트 루트 폴더에서
git branch {브랜치명} // 브랜치 생성
git checkout {브랜치명} // 브랜치 이동
```

##### 개발한 브랜치를 push 하고 싶어요

```git
git branch // 현재 작업중인 브랜치 확인

  develope
  main
* feature/#1-member-login   // *이 붙어있는 곳이 현재 작업중인 브랜치


git add .                                 // 트래킹 중이지 않은 파일 추가
git commit -m ":cyclone:ing : 로그인 기능 완료 / redirect url 미완료"       // 커밋 메시지 작성
git push origin feature/#1-member-login   // 작업이 끝나지 않았다면
git push origin feature/develop           // 작업이 끝났다면
```

##### push 한 후 쓰지 않는 로컬 브랜치를 지우고 싶어요

```git
git branch -d {브랜치명}
```

##### 깃허브에 있는 브랜치를 내려받고 싶어요

```git
git branch // 현재 작업중인 브랜치 확인

  develope
  main
* feature/#1-member-login   // *이 붙어있는 곳이 현재 작업중인 브랜치

git pull origin {내려받을 브랜치명}
```

<hr>

### 코드 네이밍 룰

#### 모든 자바 메소드명, 변수명은 카멜 케이스를 따릅니다.

#### 또한 누구나 알기 쉬운 단어를 사용합니다.

메소드명은 동사로 네이밍합니다.

👍

```java
private String personName; 

public void getUserId() {

}
```

👎

```java
private String PersonName;
private String personname; 

public void userid() {

}
```

#### 클래스 명은 파스칼 케이스를 따릅니다.

👍

```text
SampleCode.java
SampleCodeDto.java
```

👎

```text
samplecode.java
sampleCodeDto.java
```

#### HTML 파일 명, 패키지명 은 모두 소문자를 사용합니다.

👍

```text
samplecode.html
```

👎

```text
sample_code.html
sampleCode.html
```

#### 패키지명은 모두 소문자를 사용합니다.

#### ENUM이나 상수는 대문자로 네이밍합니다.
