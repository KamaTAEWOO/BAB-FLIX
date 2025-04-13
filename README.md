# BAB_FLIX

**BAB_FLIX**는 최신 영화 및 TV 프로그램을 손쉽게 탐색하고 즐길 수 있는 Android 애플리케이션입니다. 이 앱은 TMDB API를 사용하여 다양한 영화 및 TV 쇼 정보를 제공합니다.

## 기능

- **영화 및 TV 프로그램 검색**: TMDB API를 사용하여 영화 및 TV 프로그램을 검색하고 상세 정보를 제공합니다.
- **즐겨찾기 기능**: 사용자가 좋아하는 영화를 즐겨찾기 할 수 있습니다.
- **상세 정보**: 영화/TV 프로그램의 개요, 장르, 출연자, 출시일 등 다양한 정보를 제공합니다.
- **이미지 표시**: 영화 포스터 및 배경 이미지를 표시하여 시각적으로 풍성한 경험을 제공합니다.

## 설치 방법

1. 이 프로젝트를 클론합니다.
   ```bash
   git clone https://github.com/yourusername/BAB_FLIX.git
   
2.	Android Studio에서 프로젝트를 엽니다.
3.	필요한 종속성(Dependencies)을 다운로드합니다.
      ./gradlew build
4.  프로젝트를 실행하여 애플리케이션을 Android 에뮬레이터나 실제 디바이스에서 테스트합니다.
5.     API 키를 설정합니다. TMDB API 키를 [TMDB](https://www.themoviedb.org/)에서 생성한 후, `local.properties` 파일에 추가합니다.
   ```properties
   TMDB_API_KEY=your_api_key_here
   ```
6.	앱을 실행합니다.

## 사용 방법

1.	앱을 실행하면 인기 영화 및 TV 프로그램 목록이 표시됩니다.
2.	원하는 영화를 선택하면 상세 정보를 확인할 수 있습니다.

## 기여 방법

기여를 원하시는 분들은 아래 단계를 따르세요:
1.	이 프로젝트를 포크합니다.
2.	새로운 브랜치에서 작업합니다. (git checkout -b feature/새로운기능)
3.	변경 사항을 커밋합니다. (git commit -am '새로운 기능 추가')
4.	브랜치를 푸시합니다. (git push origin feature/새로운기능)
5.	Pull Request를 생성하여 기여를 제출합니다.

## 라이센스
이 프로젝트는 MIT 라이센스 하에 배포됩니다. 자세한 내용은 [LICENSE](https://opensource.org/licenses/MIT) 파일을 참조하세요.

## 이미지
<img src="../../Downloads/Screenshot_20250413_180643.png" width="300" alt=""/>
<img src="../../Downloads/Screenshot_20250413_180650.png" width="300" alt=""/>
<img src="../../Downloads/Screenshot_20250413_180714.png" width="300" alt=""/>