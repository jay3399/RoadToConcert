package com.example.RoadToConcert.service;

public class memberService {


  /**
   * 회원가입 -> 이메일 // oAuth
   *
   * 이메일 입력시 이미 가입된회원이면 그대로 로그인
   * 신규회원이면 , 인증페이지띄우고 이메일인증링크 보내고 링크클릭시 인증완료
   * 비밀번호 찾기가 필요없음 , 같은기기면  그 자체로 인증
   *
   *
   * 로그인 -> 이메일로그인시 , 같은기기면 !! 한번 이메일에서 인증후 그대로 로그인가능
   * 같은기기 인증키 ?  device-id 는 로컬스토리지에 저장
   * 만약 기기가 바뀌었을시에는 , 이메일 // 디바이스id 불일치시 추가 이메일 인증후에 로그인가능
   *
   * 소셜로그인 -> 그대로 로그인
   *
   *
   *
   */


}
