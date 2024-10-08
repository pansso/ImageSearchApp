# LezhinTestApp

<img src=https://github.com/pansso/LezhinTestApp/assets/65775701/9ff5373b-782d-42e0-9135-03bfb0dd9909 width="200">
<img src=https://github.com/pansso/LezhinTestApp/assets/65775701/6c976839-ff6b-4aeb-90eb-f49a6cf38dcc width="200">
<img src=https://github.com/pansso/LezhinTestApp/assets/65775701/e8dfe224-cc86-4e18-8abb-67f86858c5b2 width="200">

## 목차

1. [소개](#소개)
2. [프로젝트 구성](#프로젝트-구성)
3. [라이브러리](#라이브러리)
4. [기능](#기능)
5. [미흡한 기능](#미흡한-기능)
6. [기타](#기타)


## 소개
Kakao 이미지 검색 api를 활용해 이미지를검색, 북마크, 필터링 할수있는 기능을 가진 앱입니다.<br>
구글 앱 아키텍처, mvvm, Jetpack Compose, Flow 등이 사용되었습니다.<br>

## 프로젝트 구성
- feature - core 구조의 멀티모듈 구조입니다.
- presentation -> domain -> data 형식의 구글 앱 아키텍처를 사용하였습니다.
- Mvvm 아키텍처를 사용하였습니다.
### 모듈화 구조
```
.
├── app
│
├── buildSrc
│
├── core
│   ├── common
│   ├── data
│   ├── model
│   └── domain
│
└── feature
    ├── main
    ├── search
    └── bookmark
```

## 라이브러리
- Jetpack Compose, Navigation, Paging3, Glide, Retrofit2, Room, hilt, Timber, 등을 사용하였습니다.
- Compose를 사용해 전체 UI구현, hilt를 사용해 Di,Paging3를 사용해 검색탭의 pagination기능, Room을 사용해 북마크 저장및 삭제기능을 구현하엿습니다.
- 디버깅 라이브러리와 충돌로 인해 Coil 대신 glide를 사용하엿습니다.
- 질의사항에 대한 공부를 위해 paging3라이브러리를 선택해 적용해보았습니다.

## 기능
1. Scaffold를 사용해 공용 검색어 필드 상단바, 컨텐츠, 하단바로 나누어 구현하였습니다.<br>

2. Paging3 라이브러리를 사용해 10개 단위로 검색 paging을 구현하였습니다.<br>

3. Room 을 사용해 사용자가 검색한 키워드 + imageUrl 을 사용해 북마크 기능을 구현하였습니다.<br>

4. debounce를 사용해 검색어필드의 중복입력을 방지하였습니다.<br>

5. 검색어 키워드 초기화 버튼을 누르면, 검색 탭에선 목록 초기화, 북마크 탭에선 필터링 하기 전의 북마크 목록을 불러옵니다.<br>

6. UiState를 사용해 api의 fail과 error처리를 하였습니다.<br>

7. flow를 사용해 room에서 북마크의 추가,제거,다중제거 기능을 구현하였습니다.<br>

8. 모바일과 테블릿 사이즈에 대응하게 Ui를 구현하였습니다. 테블릿 사이즈의 경우 gridlayout 포멧으로 변경됩니다.<br>

9. 한국어, 영어 다국어 처리를 하였고, 다크테마, 밝은테마에 따라 ui가 변경됩니다.<br>

10. 이미지 캐싱을 최적화하여 빠른 스크롤에도 이미지가 끊김없이 보이게 구현하였습니다.

## 기타
1. Room을 사용한 디스크 캐싱쪽은 최대한 가볍게 구현하였고 상세한 내용은 viewModel단에서 적용하였습니다.
2. 탭을 변경해도 검색어에 입력되어있는 필터를 유지해 공유하기, 북마크 다중 선택후 필터 사용하면 사라진 항목만 체크 해제해 ui에 적용하기등 부드러운 ui느낌을 구현하고자 하였습니다.



