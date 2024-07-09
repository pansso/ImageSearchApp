# LezhinTestApp

<img src=https://github.com/pansso/LezhinTestApp/assets/65775701/9ff5373b-782d-42e0-9135-03bfb0dd9909 width="300px">
<img src=https://github.com/pansso/LezhinTestApp/assets/65775701/6c976839-ff6b-4aeb-90eb-f49a6cf38dcc width="300px">
<img src=https://github.com/pansso/LezhinTestApp/assets/65775701/e8dfe224-cc86-4e18-8abb-67f86858c5b2 width="300px">
## 목차

1. [질의 답변 사항](#질의-답변-사항)
2. [소개](#소개)
3. [프로젝트 구성](#프로젝트-구성)
4. [라이브러리](#라이브러리)
5. [기능](#기능)
6. [미흡한 기능](#미흡한-기능)
7. [기타](#기타)

## 질의 답변 사항
1. Kotlin Coroutine Flow 관련
   - ColdFlow와 HotFlow:
       - ColdFlow : 각 수집자마다 새로운 flow 인스턴스가 생성됩니다. 수집이 시작될때만 데이터를 방출합니다. flow 블록들이 이러한 경우에 속하고, 일회성 이벤트등에 사용됩니다. 파일읽기, 네트워크 요청등 시간이 걸리는작업에 사용됩니다.
       - HotFlow : 활성 수집자의 수와 관계없이 데이터를 지속적으로 방출합니다. 여러 수집자가 동일한 flow 인스턴스를 공유합니다. ```StateFlow```, ```SharedFlow```등이 hotFlow입니다.

   - StateFlow와 SharedFlow에 대해
       - StateFlow : 항상 상태값을 지니고 있으며 초기값이 필요합니다. 중복된 값을 무시하고 변경된 값이 들어올때만 동작합니다.(distinctUntilChanced) ui등의 상태관리에 주로 사용됩니다. 가장 최근의 값만 유지합니다.
       - SharedFlow : 초기값이 없고 중복값을 사용할수있습니다. 여러 수집자가 동시에 구독할수있습니다. ```replay``` 파라미터를 사용해 새 구독자에게 이전 값을 전달할수 있습니다.
    
2. AndroidVieweModel 관련
   - owner에 대해 자세히 설명해주세요 :
       - Activity, fragment 가 owner로 사용되어 viewModel의 생명주기등을 관리합니다. owner의 생명주기에따라 viewModel의 생명주기가 결정됩니다.

   - composeNavigation과 Dagger Hilt를 사용하는경우 hiltViewModel()로 ViewModel Instance를 가져올때 owner를 어떻게 설정해야 하는지 자세히 설명해주세요
       - hiltViewModel()은 Hilt를 사용할때 Viewmodel을 주입받기 위한 Compose 함수이며 따로 owner의 설정이 필요없는걸로 알고있습니다.
       - navigation을 사용할때 navBackstackEntry에서 owner를 설정하기도 합니다.
    
3. Paging3 관련
   - PagingSource 와 getRefreshKey 함수의 파라미터와 리턴값에 대해 자세히 설명해주세요
       - getRefreshKey는 새로고침시 어느 지점에서 연결되어 호출되냐를 정하는 함수입니다. param인 ```State: PagingState<Key, Value>```는 페이지의 키타입(int), value는 로드된 데이터의 타입입니다. return값으로는 새로고침시 사용할 키값을 리턴합니다. null이면 처음부터 데이터를 로드, key를 가질시 특정 위치부터 로드합니다.
   
   - PagingSource의 load 함수의 파라미터와 리턴값에 대해 자세히 설명해주세요
       - param 은 ```LoadParams<Key>``` return 값은 ```LoadResult<Key,Value>``` 입니다. 파라미터는 로드할 아이템의 수, 리턴값은 성공할경우 ```LoadResult.Page``` 실패할경우 ```LoadResult.Error``` 가 반환됩니다.

## 소개
Kakao 이미지 검색 api를 활용해 이미지를검색, 북마크, 필터링 할수있는 기능을 가진 앱입니다.<br>
구글 앱 아키텍처, mvvm, Jetpack Compose, Flow 등이 사용되었습니다.<br>
현재 api키는 공개되어있지만 추후에 local.property등으로 옮겨 비공개로 전환할 예정입니다.<br>

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

## 미흡한 기능
1. 폴더블 레이아웃은 비율만 맞추어놓고 테스트 해보지않았습니다.
2. paging3 라이브러리를 처음 사용하여 숙련도가 낮은 코드가 완성되었습니다. :anguished:
   - lazyColumn을 사용하는 북마크쪽은 회전변경에 스크롤 상태가 유지되는대, pagigng3를 사용하는 검색탭은 스크롤 상태가 초기화 되는문제가 있습니다.
   - paging3 라이브러리 자체의 state와 정의한 uiState 두개로 관리되어 지저분한 Ui 소스가 되었습니다.
3. 화면구성 요구사항 이해를 잘못해 급하게 Ui 구성을 바꾸어서 중복되어있는 코드가 남아있습니다.
4. 북마크에 저장된 키워드를 ui로 보여주지않아 검색하는대 불편이 있습니다.

## 기타
1. Room을 사용한 디스크 캐싱쪽은 최대한 가볍게 구현하였고 상세한 내용은 viewModel단에서 적용하였습니다.
2. 탭을 변경해도 검색어에 입력되어있는 필터를 유지해 공유하기, 북마크 다중 선택후 필터 사용하면 사라진 항목만 체크 해제해 ui에 적용하기등 부드러운 ui느낌을 구현하고자 하였습니다.



