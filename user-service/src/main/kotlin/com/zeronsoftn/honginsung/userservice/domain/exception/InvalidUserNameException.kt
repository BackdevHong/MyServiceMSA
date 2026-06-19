package com.zeronsoftn.honginsung.userservice.domain.exception

class InvalidUserNameException :
    IllegalArgumentException("사용자 이름은 비어 있을 수 없습니다.")