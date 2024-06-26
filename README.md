알바생들을 위한 익명 커뮤니티 웹 사이트

*개요
 직장인들을 위한 웹 서비스 '블라인드'가 존재하지만 아르바이트생들을 위한 익명 커뮤니티 웹 사이트의 부재로 인한 개발

*주요 기능
1.	로그인 및 회원가입 기능
 로그인을 하기 위해서는 먼저 사이트 회원가입이 필요하다. 회원가입은 아르바이트생 사용자와 자영업자 사용자로 나누어 가입 받는다. 회원가입 시에는 이름, 닉네임, 이메일, 비밀번호 등의 정보를 입력한다. 아르바이트생 사용자는 아르바이트생들만의 커뮤니티를 이용하고, 자영업자 사용자는 자영업자들만의 커뮤니티를 이용한다. 각각의 사용자들은 토글 버튼을 눌러 커뮤니티를 전환해 가며 서로의 게시글을 확인해 볼 수 있다. 로그인도 마찬가지로 아르바이트생/자영업자 사용자로 나누어 로그인 한다. 

①	비회원 사용자
로그인 하지 않은 사용자는 사용자들이 작성한 게시글 열람만 가능하도록 기능을 제한한다.

②	아르바이트생 사용자
아르바이트생 사용자는 아르바이트생 커뮤니티에서 게시글 · 댓글 · 대댓글 작성, 좋아요 기능 사용이 가능하다. 토글 버튼을 통해 자영업자 커뮤니티를 확인할 수 있고, 좋아요 기능만 사용이 가능하다. 해당 사용자로 로그인 했을 경우 노란색 테마를 적용한다. 사용자 간 쪽지 기능도 사용할 수 있다.

③	자영업자 사용자
자영업자 사용자는 자영업자 커뮤니티에서 게시글 · 댓글 · 대댓글 작성, 좋아요 기능 사용이 가능하다. 마찬가지로 토글 버튼을 통해 아르바이트생 커뮤니티를 확인해 볼 수만 있고 게시글, 댓글 작성은 불가능 하다. 이처럼 각각의 사용자마다 커뮤니티 공간을 분리시키고 서로의 커뮤니티의 게시글, 댓글 작성을 제한하며 열람만 가능하게 함으로써 자신들만의 커뮤니티에서 정보를 주고받을 수 있게 한다. 해당 사용자로 로그인 했을 경우 파란색 테마를 적용한다. 마찬가지로 사용자 간 쪽지 기능을 사용할 수 있다.


④	관리자
회원가입 기능에서 만드는 것이 아닌, 데이터베이스 미리 저장해 둔 계정을 사용한다. 관리자는 커뮤니티 규정에 따라, 게시판에 올라온 부적절한 글이나 댓글을 삭제할 수 있다. 또한, 인증 게시판에 올라온 사용자 인증을 승인 · 거절할 수 있다.

2.	사용자 프로필 기능
 사용자 프로필 기능을 통해 회원정보를 수정할 수 있다. 닉네임 재설정 할 수 있다. 또한, 사용자가 작성한 게시글 · 댓글을 확인할 수 있다. 게시글이나 댓글에서 사용자의 닉네임을 클릭했을 때, 해당 사용자의 프로필도 확인할 수 있다.

3.	사용자 인증 기능
 사용자 프로필 화면에서 인증 버튼을 클릭하면 인증 기능을 사용할 수 있다. 양식(제목, 내용, 사진)에 맞게 글을 작성하면, 관리자가 확인 후 승인 · 거절하는 방식이다. 인증을 하지 않아도 회원가입이 가능하다.

4.	게시판 기능
게시판에서는 사용자들이 작성한 게시글을 확인할 수 있다. 작성하고 싶은 브랜드를 선택한 다음, 글쓰기 버튼을 통해 게시글을 작성할 수 있다. 게시글은 생성 · 수정 · 삭제가 가능하고, 작성된 게시글에 댓글, 대댓글, 좋아요 기능들을 할 수 있다. 본인이 작성한 게시글이 아닌 경우에는 수정 · 삭제가 불가능하다. 또한, 본인이 작성한 게시글은 좋아요 기능을 사용할 수 없다. 다른 사용자가 게시글을 조회하면 조회수가 1씩 증가한다. 이는 중복을 허용한다.

①  통합 게시판
아르바이트생 사용자, 자영업자 사용자가 같이 커뮤니티 활동을 할 수 있는 게시판이다. 전체 사용자가 작성한 게시글을 볼 수 있다.

②	인기 게시판
각각의 커뮤니티에서 높은 좋아요를 가지고 있는 게시글들이 해당 게시판에 출력된다. 이 게시판을 통해 사용자들은 현재 가장 이슈가 되는 게시글이 무엇인지 확인할 수 있다.

③	채용공고 게시판
채용공고를 올릴 수 있는 게시판이다. 자영업자 사용자들만 이 게시판에 글을 작성할 수 있다. 글의 내용에는 매장 위치, 급여, 시간대, 연락처 정보를 반드시 포함해야 한다. 

④	평점 게시판
리뷰 기능을 사용하여 사용자들이 작성한 리뷰를 확인할 수 있다. 

5.	댓글 기능
게시글 마다 댓글, 대댓글(댓글 밑에 답글) 작성이 가능하고, 좋아요 기능을 사용할 수 있다.
 
6.	리뷰 기능
평점 게시판을 통해 해당 브랜드에 대한 평가를 한 줄로 남길 수 있다. 리뷰를 남길 때 5점 만점의 평점 중 자신이 원하는 평점을 남길 수 있다. 리뷰 기능을 사용하려면 사용자 인증 기능을 먼저 완료해야 한다.

7.	검색 및 필터링 기능
①	게시판 내 검색
게시판 상단에 있는 검색 바를 통해서 해당 게시판 안에서 정보를 검색해 볼 수 있다. 제목, 작성자, 내용 등으로 검색이 가능하다.

②	정렬
각각의 게시판마다 정렬 버튼을 배치하여 작성된 게시글 목록 중 작성일, 댓글, 조회수, 좋아요 순으로 정렬해서 확인할 수 있다.

8.	쪽지 기능
사용자 간 메시지를 보낼 수 있는 쪽지 기능을 개발하였다. 사용자 유형에 구애받지 않고 모든 사용자 간의 쪽지 기능을 사용할 수 있도록 개발하였다. 주고받은 쪽지는 화면 상단에 있는 쪽지함에서 확인할 수 있다.






