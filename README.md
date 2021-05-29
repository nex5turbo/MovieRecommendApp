# MovieRecommendApp

## 결과화면
![Screenshot_20210530-000259_MovieRecommend](https://user-images.githubusercontent.com/50979183/120075099-ed34dc00-c0da-11eb-808e-dc0d3e473f94.jpg)
![Screenshot_20210530-000308_MovieRecommend](https://user-images.githubusercontent.com/50979183/120075102-edcd7280-c0da-11eb-9cf9-b8ea48a50528.jpg)
![Screenshot_20210530-000313_MovieRecommend](https://user-images.githubusercontent.com/50979183/120075103-ee660900-c0da-11eb-9f30-25dca0ef4bb1.jpg)
![Screenshot_20210530-000320_MovieRecommend](https://user-images.githubusercontent.com/50979183/120075104-eefe9f80-c0da-11eb-9afb-48cfc0362536.jpg)


## Naver Movie API
하나만 짚고 넘어가겠다. JSON 받아와서 영화 포스터 이미지를 추출하려고 할 때,    
java.net.MalformedURLException: no protocol 에러가 뜨는 상황이 발생한다.    
검색하고 뭐하면서 URL인코딩 해보고 이스케이프 시퀀스 해보고 다해봤는데, 해결못했다.    
그러다 발견한 사실... url이 잘못된게 아니고 이미지가 없는 상황이다.    
이미지가 없어서 해당 영화의 네이버 상세페이지를 url로 올려둔건데, 이미지 파일도 없고,     
받아올 데이터도 없으니 이런 에러가 뜨는 것이다.     
혹시나 이걸 모르고 삽질하는 분들을 위해 ... ~~peess~~..
    
**정정 : 그냥 json에 이미지 url 자체가 없는 것**


## 너무 느려
영화진흥위원회 API와 Naver API를 같이 써서 이미지, 장르, 평점을 받아오는 데 성공!    
근데 속도가 너무 너무 느리다.     
진흥위원회 API에는 평점이랑 포스터 이미지가 없고, Naver API에는 장르가 없다.    
(장르가 있는데 내가 못찾는 것 같다.. 장르만 받아올 수 있으면 굳이 진흥위원회 API를 쓸 필요가 없는데 ㅠㅠ)    
네이버 API에서 장르를 받아올 수 있는 방법을 조금만 찾아보고, 정 안되면 두 개를 연동해서 헤로쿠 서버에    
따로 JSON을 생성해야 할 것 같다ㅠㅠ 

**TMDB API를 발견했다!.! 영어로 되어있긴한데 아 몰라 일단 트라이**        
    
**08-27 02:00 TMDB는 나한테 필요한 모든 정보가 들어있다.    
영화 관련 OPEN API가 필요하다면 TMDB API를 쓰진 않더라도 꼭 살펴보길 바랍니다.**    
    
    
## ViewPager
이제 영화 정보를 받아오는 부분은 얼추 끝이났다. 추가로 설명하자면 TMDB는 한 페이지에 받을 수 있는    
목록이 20개로 제한이 되어있다.(분명 내가 못 찾은걸수도 있다.)      
![TMDB검색결과 -> 20개씩 6페이지로 구성](https://user-images.githubusercontent.com/50979183/91540313-95075880-e955-11ea-914d-19dae5a159f6.png)    
    
그래서 목록이 20개가 넘는다면 페이지를 올리며 반복적으로 요청해야한다. 이 부분만 하면 끝!    
    
이제 뷰페이저를 통해 앱을 좀 깔끔하게 정리해보려고 한다. 내가 원하는 기능들을 메뉴 탭으로 선택하는 방법도 있지만,    
뷰페이저를 통해 슬라이딩 메뉴도 지원하고 코드도 최대한 간결하게 만들어보려고 한다.    
앱단에서의 작업은 중반정도 달리고 있는 것 같다. 현재 코드 작성 상태는 짬뽕코드지만(클래스명도 다 개판이다.),
화면 구성과 기능들을 좀 더 구현한뒤에 코드를 좀 깔끔하게 정리해야겠다.

## RecyclerView Paging
영화 검색, 박스오피스(이 부분은 아직 생각이 많다... 박스오피스 관련 정보는 tmdb에 없는데 영화진흥위원회에서 받아오면
이미지 파일이 없기 때문에.... 어떻게 해야할지 생각을 좀 더 해봐야겠다. 일단 페이징은 완료했다.), 비슷한 영화 찾기
부분까지 완료를 했다. 비슷한 영화 찾는건 졸작때 했던 dp, pixel 변환을 통해 동적으로 핸드폰 사이즈에 맞춰서
리사이클러뷰로 표시를 해주었다.

오늘(08.30)은 앞서 적어두었던 목록이 20개가 넘어갈 때 한번에 로딩하면 시간이 너무 오래걸리는 부분에 대해서 해결했다.
리사이클러뷰 무한 스크롤!! 먼저 20개의 목록을 받아둔 뒤 그 다음 페이지에 대한 정보는 받지 않는다.
그리고 사용자가 리사이클러뷰에 대해 마지막 인덱스에 접근하면 다음 검색 결과 페이지를 접근하여 정보를 받아온다.
이 부분은 생각보다 간단했다.


```
myList.setOnScrollListener(new RecyclerView.OnScrollListener() {
       @Override
       public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
           if (!myList.canScrollVertically(-1)) {
               Log.i(TAG, "Top of list");
           } else if (!myList.canScrollVertically(1)) {
               Log.i(TAG, "End of list");
           } else {
               Log.i(TAG, "idle");
           }
       }
   });
```
위 코드는 [이 블로그](https://medium.com/@ydh0256/android-recyclerview-%EC%9D%98-%EC%B5%9C%EC%83%81%EB%8B%A8%EA%B3%BC-%EC%B5%9C%ED%95%98%EB%8B%A8-%EC%8A%A4%ED%81%AC%EB%A1%A4-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EA%B0%90%EC%A7%80%ED%95%98%EA%B8%B0-f0e5fda34301)에 있는 코드이다.
리사이클러뷰에 setOnScrollListener를 붙여서 리사이클러뷰가 더 이상 스크롤 가능한지 확인한다음 스크롤이 더이상 안된다면
마지막 인덱스에 접근한 상태이기 때문에 다음 페이지를 로딩하는 방식이다.

## Glide
영화정보를 파싱해서 리사이클러뷰에 띄워주는데, 텍스트 형식의 정보들은 시간이 별로 안걸리지만, 이미지의 경우 리사이클러뷰에서 URL로 받아오지 않고
JSON 파싱을 하는동안 이미지URL에서 Bitmap까지 받아와 Bitmap을 리사이클러뷰 데이터에 넣어주는 방식을 사용했다.

솔직히 이미지의 크기도 작고 리사이클러뷰에 넣는 데이터의 개수가 적기 때문에 시간에 큰 영향을 주지는 않았다.
그래도 약간의 로딩시간이 거슬려 이미지를 받아오는 방법을 알아보던중 Glide라는 라이브러리를 발견하게 되었다.
그리고 적용하여 데이터 파싱에서는 이미지를 받아오지 않고 URL만 리사이클러뷰 데이터에 넣어준 다음, 리사이클러뷰
어댑터에서 GLIDE를 사용해 이미지를 받아왔다.


**와 빠르다!**


이미지 로딩에 속도부터가 다르다. 기존에 쓰던 방식은 이미지까지 받아온 다음 리사이클러뷰에 넣어주기 때문에,
JSON파싱이 모두(이미지 받아오는것 포함) 끝나야 리사이클러뷰에 아이템이 표시되었는데, Glide를 사용하니 리사이클러뷰가
먼저 생성이 되고, 이미지를 비동기적으로 받아온다. 진짜 속도 차이가 어마무시하게 난다.
    
## Finish
UI적으로나 기능적으로나 부족한 부분이 많다.    
**(UI는 어쨌든 컴퓨터만 두들길 줄 아는 내 입장에서는 이 정도가 최선이라고 생각한다.)**    
    
하지만 이번 프로젝트를 진행하면서 네이티브 내에서 기능을 구현하는 것만이 아니라,    
이미 존재하는 API들을 잘 활용하고 구성하는 것 역시 개발에서 큰 의미를 차지한다는 것을 배웠다.    
    
한마디로 코드만 작성하는게 아니라, 데이터를 어떻게 가공하는지 설계하는 것이 중요하다는 것이다.    
이번에는 Okhttp를 사용해서 꽤 복잡하게 API를 사용했는데, Retrofit이라는 라이브러리가 JSON데이터를    
다루는데 굉장히 유용하다는 것을 발견했다. 기회가 되면 이것도 Retrofit으로 개선을 한 번 해보는걸로 하고,    
다음에 프로젝트를 할 때, Retrofit을 제대로 사용해봐야겠다.    

