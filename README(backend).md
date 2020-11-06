# BACKEND

## ERROR CODE

200 : OK

202 : ACCEPTED

400 : BAD REQUEST

401 : UNAUTHORIZED

403 : FORBIDDEN



## Account Controller

### 비회원

#### 1. 회원가입

> method : POST
>
> url : localhost:8080/newuser/signup
>
> requestBody : Account (user_id, user_email, user_password, user_name)
>
> **(200)** res.data.success : true 
>
> **(202)** res.data.success : false, res.status
>
> > res.data.message : "duplicated user_id"

#### 2. 이메일 유효성 검사

> method : GET
>
> url : localhost:8080/newuser/signup
>
> requestParam : email
>
> **(OK)** res.data.origin_hash : 해시값
>
> **(202)** res.data.origin_hash : null

#### 3. 인증번호 검사

> method : GET
>
> url : localhost:8080/newuser/signup/hashcheck
>
> requestParam : auth_number, hash_number
>
> **(OK)** res.data.result : true
>
> **(202)** res.data.result : false

#### 4. 로그인

> method : POST
>
> url : localhost:8080/newuser/login
>
> requestBody : user_id, user_password
>
> **(200)** res.data.result.success : true
>
> >  res.data.accessToken & res.data.refreshToken
>
> **(401)** res.data.result.success : false
>
> > res.data.message : authenticate ERROR

#### 5. 토큰 재발급

> method : POST
>
> url : localhost:8080/newuser/refresh
>
> requestBody : X
>
> requestHeader : accessToken & refreshToken
>
> **(200)** res.data.accessToken
>
> **(401)** res.data.msg : refresh token is expired / your refresh token does not exist



### 회원 (accessToken 필요)

<<<<<<< HEAD
=======
#### 0. 비밀번호 변경

> method : POST
>
> url : localhost:8080/user/changepw
>
> requestBody : Account (user_id, user_email, user_password, user_name)
>
> **(200)** 
>
> > res.data.success : true
>
> **(200)**
>
> > res.data.success : false

>>>>>>> 5da61901d92eb409b9cebfcbf770471a096e5a21
#### 1.  로그아웃

> method : POST
>
> url : localhost:8080/user/logout
>
> requestBody : X
>
> **(200)** res.response.status : OK
>
> **(401)** err.response.status :  UNAUTHORIZED

#### 2. 문자 인증

> method : GET
>
> url : localhost:8080/user/adopt/create
>
> requestParam : phone ( ex. 010-1234-5678 / 01012345678 )
>
> **(200)** res.data.success : true
>
> > res.data.number : 인증번호
>
> **(400)** res.data.success : false (번호가 잘못됨)

<<<<<<< HEAD
#### 3. 입양신청
=======
#### 3. 입양신청 등록
>>>>>>> 5da61901d92eb409b9cebfcbf770471a096e5a21

> method : POST
>
> url : localhost:8080/user/adopt/create
>
> requestBody : Apply (ani_num, user_name, user_phone, user_email, title)
>
> **(200)** res.data.success : true 
>
> **(400)** err.data.success : false

<<<<<<< HEAD
=======
#### 3-1. 입양신청 수정

> method : POST
>
> url : localhost:8080/user/adopt/create
>
> requestBody : Apply (uid, ani_num, regtime, user_name, user_phone, user_email, title)
>
> **(200)** res.data.success : true 
>
> **(400)** err.data.success : false

>>>>>>> 5da61901d92eb409b9cebfcbf770471a096e5a21
#### 4. 입양신청 목록 조회

> method : GET
>
> url : localhost:8080/user/adopt/read
>
> requestParam : X
>
> **(200)** res.response.status : OK
>
> > res.data.user_id & res.data.list
>
> **(401)** res.response.status : UNAUTHORIZED

#### 5. 입양신청서 조회

> method : GET
>
> url : localhost:8080/user/adopt/read
>
> requestParam : uid
>
> **(200)** res.response.status : OK
>
> > res.data.apply & res.data.user_id
>
> **(401)** res.response.status : UNAUTHORIZED



<<<<<<< HEAD
=======
## AnimalController

### 비회원

#### 1. 전체동물조회

> method : GET
>
> url : localhost:8080/newuser/animal/allread
>
> **(200)**  res.response.status : OK
>
> > res.data.animalList
>
> **(202)** err.response.status : ACCPTED



### 회원

#### 1. 전체동물조회

> method : GET
>
> url : localhost:8080/user/animal/allread
>
> **(200)** res.response.status : OK
>
> > res.data.animalList
>
> **(202)** res.response.status : ACCEPTED

#### 2. 즐겨찾는동물조회

> method : GET
>
> url : localhost:8080/user/animal/like
>
> **(200)** res.response.status : OK
>
> > res.data.animalList

#### 3. 설문기록조회

> method : GET
>
> url : localhost:8080/user/animal/surveyread
>
> 





>>>>>>> 5da61901d92eb409b9cebfcbf770471a096e5a21


## AnimalDetailController

### 비회원

#### 1. 동물상세조회

> method : GET
>
> url : localhost:8080/animal/detail
>
> requestParam : desertion_no
>
> **(200)** res.response.status : OK
>
> > res.data.animal & res.data.personality
>
> **(202)** res.response.status : ACCEPTED