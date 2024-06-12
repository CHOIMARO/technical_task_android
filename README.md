# 프로젝트 제목 : technical_task_android
# 프로젝트 정보
- 본 프로젝트는 안드로이드 개발 기술과제를 위해 개발한 프로젝트였으나, 불합격 이후 모자랐던 부분들을 스스로 고치기 위한 프로젝트입니다. Kakao 이미지 검색 API를 사용하여 이미지를 검색하고, 검색한 이미지를 저장(북마크) 및 삭제할 수 있는 앱입니다.
- 개발 환경
  - 개발 언어 : Kotlin
  - minimun SDK : 26
  - 개발 툴 : Android Studio Jellifish | 2023.3.1
  - Android Gradle Plugin Version : 8.4.0
  - Gradle Version : 8.6
- 요구사항
  - [x] 개발 언어 Kotlin
  - [x] 최소 API 버전 26
  - [x] Kakao 이미지 검색 API 사용, 검색 결과 Json으로 받아 컨버팅
  - [x] Jetpack Compose 사용
  - [x] Coroutine과 Flow 활용
  - [x] 하단바(BottomBar) 검색 탭과 북마크 탭으로 구성
  - [x] 상단바(TopBar) 각 화면의 타이틀 노출
  - [x] 컨텐츠: 검색어 입력 필드 구성과 결과 목록 노출
  - [x] 북마크된 목록 정보 화면 표시
  - [x] 북마크된 아이템 단일 또는 다수개 삭제 기능 추가
  - [x] 1자 이상의 키워드 입력 후, 1초 이상 검색어 입력이 없을 경우 검색 수행 기능 구현
  - [x] 검색 결과가 없거나 오류가 발생한 경우 사용자에게 알림 표시
  - [x] 클린 아키텍처 활용
  - [x] 다국어 처리
  - [x] 기기회전, 테마 변경 대응
  
- 사용 기술 및 라이브러리
  - Material3 -> 쉬운 디자인과 유연한 테마 대응을 위해 사용하였습니다.
  - Android ViewModel -> 클린 아키텍처 구현을 위해 사용하였습니다.
  - Jepack Navigation -> 하단바에 탭을 만들어 Navigation 기능을 구현했습니다.
  - Jetpack Paging3 -> API를 조회하여 Paging을 이용해 필요한 만큼만 로드하여 자료를 화면에 표시했습니다.
  - Android Hilt and Dagger Hilt -> 의존성 주입을 통해 보일러플레이트를 줄이고, 가독성을 키웠습니다.
  - Retrofit2 and Okhttp3 -> REST API를 사용하여 네트워크와 통신하기 위해 사용했습니다.
  - Kotlin Serialization for Retrofit JSON converter -> 서버에서 수신한 데이터를 JSON 형식에 맞게 파싱하여 앱에서 사용할 수 있도록 하기 위해 사용했습니다.
  - Room -> 내부 DB를 사용하여 북마크 기능을 구현했습니다.
 
# 참고사항
- Kakao API Key는 local.properties에 다음과 같이 본인의 API 키를 입력해주세요.
- <img width="387" alt="스크린샷 2024-05-14 오전 8 30 30" src="https://github.com/CHOIMARO/technical_task_android/assets/53159069/60d8f4c6-076e-4905-968e-9b4f20f144f3">

# 발견된 문제점 및 해결점
- ~~현재 이미지를 가져와 API를 화면에 표시하는 과정에서 Paging3 기술을 사용하여 검색된 자료들을 전부 보여주고 있습니다. 하지만 초기 로딩 이후, 추가적인 데이터 로드 시에 API 26의 경우 강제종료, 상위버전(> API 26)의 경우 리스트가 새로고침됩니다.~~
- ~~오류 정보~~
<img width="1007" alt="image" src="https://github.com/CHOIMARO/technical_task_android/assets/53159069/bd4068f1-18d5-4793-be48-1dbef75be173">
- 해결 완료(2024.5.14)
  - Pager를 통해 수신받은 LoadState에서 LoadState.refresh is LoadState.Loading일 때 알 수 없는 오류가 발생하여 삭제 조치하여 검색된 자료가 원활하게 보이도록 수정했습니다.

# 배운 점
- 기존에 xml 기반 앱을 구현했었으나, 이번 기회를 통해 Compose의 강력한 기능을 배웠습니다. 다소 아쉬운 부분들이 있었으나, 부족함을 발판 삼아 더욱 성장해 나갈 수 있는 기회였습니다. 값진 기회주셔서 감사드립니다.

# 고칠 점
- ~~API로부터 받은 모델을 데이터 모델로 바꾸는 과정 최적화하기 toEntity & toDomain~~ 2024.05.26 완료
- ~~쓸모 없는 dataSourceImpl 지우고 dataSource만 사용하기~~ 2024.05.26 완료
- ~~DB로부터 받은 Like 데이터와 데이터 모델에 isLike 필드를 추가해서 Flow, combine으로 받아서 리턴하기~~ 2024.06.01
- ~~Exception 처리하기~~ 2024.06.09
- ~~확장성을 위해 Navigation 기능 통합하기~~ 2024.06.12
- 이미지 클릭 시 상세페이지로 이동하도록 구현해보기
- App / Presentation / Domain / Data 모듈로 나누기
