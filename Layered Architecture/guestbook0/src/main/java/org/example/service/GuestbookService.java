package org.example.service;

import org.example.dto.Guestbook;

import java.util.List;

public interface GuestbookService {
    public static final Integer LIMIT = 5; // 한 페이지에 5개씩 보여준다
    public List<Guestbook> getGuestbooks(Integer start);
    public int deleteGuestbook(Long id, String ip);
    public Guestbook addGuestbook(Guestbook guestbook, String ip);
    public int getCount();

    // 이런 인터페이스를 구현하는 클래스가 필요하다 -> GuestbookServiceImpl
}