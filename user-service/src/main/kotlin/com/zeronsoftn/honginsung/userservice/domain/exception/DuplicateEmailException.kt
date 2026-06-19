package com.zeronsoftn.honginsung.userservice.domain.exception

class DuplicateEmailException :
    IllegalArgumentException("이미 등록된 이메일입니다.")